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
import top.imuster.goods.api.dto.ProductRecommendDto;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.dao.ProductRecommendDao;
import top.imuster.goods.service.ProductInfoService;
import top.imuster.goods.service.RecommendProductService;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * @ClassName: RecommendProductServiceImpl
 * @Description: RecommendProductServiceImpl
 * @author: hmr
 * @date: 2020/5/1 14:04
 */
@Service("recommendProductService")
public class RecommendProductServiceImpl implements RecommendProductService {

    private RedisTemplate redisTemplate;

    @Autowired
    private ProductInfoService productInfoService;

    @Resource
    private ProductRecommendDao productRecommendDao;


    @Override
    public Message<Page<ProductInfo>> getRecommendListByUserId(Integer pageSize, Integer currentPage, Long userId) {
        if(userId == null){
            return productInfoService.getProductBriefInfoByPage(currentPage, pageSize);
        }else{
            Page<ProductInfo> page = new Page<>();
            List<ProductInfo> list = new ArrayList<>();
            ProductRecommendDto reco = productRecommendDao.findByUserId(userId);
            List<MongoProductInfo> recs = reco.getRecs();
            recs.stream().forEach(mongoProductInfo -> {
                Integer productId = mongoProductInfo.getProductId();
                list.add(productInfoService.getProductBriefInfoById(productId.longValue()));
            });
            page.setData(list);
            page.setTotalCount(list.size());
            return Message.createBySuccess(page);
        }
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
