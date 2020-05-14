package top.imuster.goods.service.impl;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.enums.ReleaseType;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.goods.api.dto.MongoProductInfo;
import top.imuster.goods.api.dto.ProductContentRecommendDto;
import top.imuster.goods.api.dto.ProductRealtimeRecommendDto;
import top.imuster.goods.api.dto.ProductRecommendDto;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.dao.ProductContentRecommendRepository;
import top.imuster.goods.dao.ProductRealtimeRecommendRepository;
import top.imuster.goods.dao.ProductRecommendDao;
import top.imuster.goods.service.ProductInfoService;
import top.imuster.goods.service.RecommendProductService;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: RecommendProductServiceImpl
 * @Description: RecommendProductServiceImpl
 * @author: hmr
 * @date: 2020/5/1 14:04
 */
@Service("recommendProductService")
public class RecommendProductServiceImpl implements RecommendProductService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ProductInfoService productInfoService;

    @Resource
    private ProductRecommendDao productRecommendDao;

    @Resource
    private ProductContentRecommendRepository productContentRecommendRepository;

    @Resource
    private ProductRealtimeRecommendRepository productRealtimeRecommendRepository;


    @Override
    public Message<Page<ProductInfo>> getOfflineRecommendListByUserId(Integer pageSize, Integer currentPage, Long userId) {
        if(userId == null){
            return productInfoService.getProductBriefInfoByPage(currentPage, pageSize);
        }else{
            Page<ProductInfo> page = new Page<>();
            List<ProductInfo> list = new ArrayList<>();
            ProductRecommendDto reco = productRecommendDao.findByUserId(userId);
            List<MongoProductInfo> recs = reco.getRecs();
            recs.stream().forEach(mongoProductInfo -> {
                Long productId = mongoProductInfo.getProductId();
                list.add(productInfoService.getProductBriefInfoById(productId.longValue()));
            });
            page.setData(list);
            page.setTotalCount(list.size());
            return Message.createBySuccess(page);
        }
    }

    @Override
    public Message<Page<ProductInfo>> getRealtimeRecommend(Integer pageSize, Integer currentPage, Long userId) {
        String redisKey = RedisUtil.getProductRealtimeRecommendListKey(userId);
        Boolean aBoolean = redisTemplate.hasKey(redisKey);
        if(aBoolean) return getInfoFromRedis(pageSize, currentPage, redisKey);
        ProductRealtimeRecommendDto recommendInfo = productRealtimeRecommendRepository.findByUserId(userId);
        List<MongoProductInfo> recs = recommendInfo.getRecs();
        List<Long> ids = recs.stream().map(MongoProductInfo::getProductId).collect(Collectors.toList());
        redisTemplate.opsForList().leftPush(redisKey, ids);
        return getInfoFromRedis(pageSize, currentPage, redisKey);
    }

    @Override
    public Message<Page<ProductInfo>> getContentRecommend(Integer pageSize, Integer currentPage, Long productId) {
        String redisKey = RedisUtil.getProductContentRecommendListKey(productId);
        Boolean flag = redisTemplate.hasKey(redisKey);
        if(flag) return getInfoFromRedis(pageSize, currentPage, redisKey);

        ProductContentRecommendDto res = productContentRecommendRepository.findByProductId(productId);
        List<MongoProductInfo> recs = res.getRecs();
        List<Long> productIds = recs.stream().map(MongoProductInfo::getProductId).collect(Collectors.toList());
        redisTemplate.opsForList().leftPush(redisKey, productIds);
        return getInfoFromRedis(pageSize, currentPage, redisKey);
    }

    private Message<Page<ProductInfo>> getInfoFromRedis(Integer pageSize, Integer currentPage, String redisKey) {
        Integer start = (currentPage - 1) * pageSize;
        Page<ProductInfo> page = new Page<>();
        Integer size = redisTemplate.opsForList().size(redisKey).intValue();
        Integer end = start + pageSize > size - 1 ? size : start + pageSize;

        List<Long> res = redisTemplate.opsForList().range(redisKey, start, end);
        List<ProductInfo> data = null;
        if(res != null && !res.isEmpty()){
             data = productInfoService.getProductBriefByIds(res);
        }
        page.setData(data);
        page.setTotalCount(size);
        return Message.createBySuccess(page);
    }

    @Override
    public Message<List<Object>> recommendTagNames(String text) throws IOException {
        Analyzer anal = new IKAnalyzer(true);
        StringReader reader=new StringReader(text);
        //分词
        TokenStream ts = anal.tokenStream("", reader);
        CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);
        //遍历分词数据
        ts.reset();
        Set<String> words = new HashSet<>();
        while(ts.incrementToken()){
            String s = term.toString();
            if(s.length() <= 1) continue;
            words.add(s);
        }
        ts.close();
        reader.close();
        String tempKey = UUID.randomUUID().toString();
        redisTemplate.opsForSet().add(tempKey, (String[])words.toArray(new String[words.size()]));
        Set<String> result = redisTemplate.opsForSet().intersect(RedisUtil.getRedisTagNameKey(ReleaseType.GOODS), tempKey);
        List<Object> res = Arrays.asList(result.toArray());
        redisTemplate.delete(tempKey);
        return Message.createBySuccess(res);
    }
}
