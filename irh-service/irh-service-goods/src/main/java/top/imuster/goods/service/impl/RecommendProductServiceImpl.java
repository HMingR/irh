package top.imuster.goods.service.impl;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;
import redis.clients.jedis.Jedis;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.common.core.enums.ReleaseType;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.goods.api.dto.*;
import top.imuster.goods.api.pojo.ProductDemandInfo;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.dao.*;
import top.imuster.goods.service.ProductDemandInfoService;
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

    @Resource
    DemandRecommendRepository demandRecommendRepository;

    @Resource
    ProductDemandInfoService productDemandInfoService;

    @Resource
    ProductUserTagRecommendTRepository productUserTagRecommendTRepository;

    @Autowired
    Jedis jedis;

    @Override
    public Message<Page<ProductInfo>> getOfflineRecommendListByUserId(Integer pageSize, Integer currentPage, Long userId) {
        if(userId == null){
            return productInfoService.getProductBriefInfoByPage(currentPage, pageSize);
        }else{
            String setKey = RedisUtil.getProductOfflineRecommendSetKey(userId);
            if(redisTemplate.hasKey(setKey)) getInfoFromRedis(pageSize, currentPage, setKey);

            String userBrowseRecordBitmapKey = RedisUtil.getUserBrowseRecordBitmap(BrowserType.ES_SELL_PRODUCT, userId);
            ArrayList<Long> ids = new ArrayList<>();

            ProductRecommendDto reco = productRecommendDao.findByUserId(userId);
            List<MongoProductInfo> recs = reco.getRecs();
            appendList(recs, ids);

            ProductUserTagRecommendDto tagRec = productUserTagRecommendTRepository.findByUserId(userId);
            List<MongoProductInfo> recs1 = tagRec.getRecs();
            appendList(recs1, ids);

            ids.stream().forEach(id -> {
                Boolean isBrowes = jedis.getbit(userBrowseRecordBitmapKey, id);
                if(!isBrowes) redisTemplate.opsForSet().add(setKey, id);
            });

            return getInfoFromRedis(pageSize, currentPage, setKey);
        }
    }

    private void appendList(List<MongoProductInfo> target, List<Long> result){
        if(target != null && !target.isEmpty()){
            List<Long> ids = target.stream().map(MongoProductInfo::getProductId).collect(Collectors.toList());
            result.addAll(ids);
        }
    }

    @Override
    public Message<Page<ProductInfo>> getRealtimeRecommend(Integer pageSize, Integer currentPage, Long userId) {
        String redisKey = RedisUtil.getProductRealtimeRecommendListKey(userId);
        Boolean aBoolean = redisTemplate.hasKey(redisKey);
        if(aBoolean) return getInfoFromRedis(pageSize, currentPage, redisKey);

        String userBrowseRecordBitmap = RedisUtil.getUserBrowseRecordBitmap(BrowserType.ES_SELL_PRODUCT, userId);
        ProductRealtimeRecommendDto recommendInfo = productRealtimeRecommendRepository.findByUserId(userId);
        List<MongoProductInfo> recs = recommendInfo.getRecs();
        if(recs.isEmpty()) return getOfflineRecommendListByUserId(pageSize, currentPage, userId);
        List<Long> ids = recs.stream().map(MongoProductInfo::getProductId).collect(Collectors.toList());
        ids.stream().forEach(id -> {
            if(!jedis.getbit(userBrowseRecordBitmap, id)){
                redisTemplate.opsForSet().add(redisKey, id);
            }
        });
        return getInfoFromRedis(pageSize, currentPage, redisKey);
    }

    @Override
    public Message<Page<ProductInfo>> getContentRecommend(Integer pageSize, Integer currentPage, Long productId) {
        String redisKey = RedisUtil.getProductContentRecommendListKey(productId);
        Boolean flag = redisTemplate.hasKey(redisKey);
        if(flag) return getInfoFromRedis(pageSize, currentPage, redisKey);

        ProductContentRecommendDto res = productContentRecommendRepository.findByProductId(productId);
        if(res == null) return Message.createBySuccess(new Page<>());
        List<MongoProductInfo> recs = res.getRecs();
        List<Long> productIds = recs.stream().map(MongoProductInfo::getProductId).collect(Collectors.toList());
        productIds.stream().forEach(id -> {
            redisTemplate.opsForSet().add(redisKey, id);
        });
        return getInfoFromRedis(pageSize, currentPage, redisKey);
    }

    @Override
    public Message<Page<ProductDemandInfo>> getDemandRecommend(Long userId, Integer pageSize, Integer currentPage) {
        if(userId == null){
            return productDemandInfoService.list(pageSize, currentPage);
        }
        DemandRecommendDto recommendRes = demandRecommendRepository.findByUserId(userId);
        if(recommendRes == null || recommendRes.getRecs() == null || recommendRes.getRecs().isEmpty()){
            return productDemandInfoService.list(pageSize, currentPage);
        }

        String redisKey = RedisUtil.getDemandRecommendListKey(userId);
        String userBrowseRecordBitmap = RedisUtil.getUserBrowseRecordBitmap(BrowserType.ES_DEMAND_PRODUCT, userId);
        List<MongoProductInfo> recs = recommendRes.getRecs();
        List<Long> mongoIds = recs.stream().map(MongoProductInfo::getProductId).collect(Collectors.toList());
        for(Long id : mongoIds){
            if(jedis.getbit(userBrowseRecordBitmap, id)) continue;
            redisTemplate.opsForSet().add(redisKey, id);
        }
        return getDemandInfoFromRedis(pageSize, currentPage, redisKey);
    }

    private Message<Page<ProductDemandInfo>> getDemandInfoFromRedis(Integer pageSize, Integer currentPage, String redisKey){
        Integer start = (currentPage - 1) * pageSize;
        Integer size = redisTemplate.opsForSet().size(redisKey).intValue();

        List<Long> res = redisTemplate.opsForSet().randomMembers(redisKey, pageSize);
        List<ProductDemandInfo> data = null;
        if(res != null && !res.isEmpty()){
            data = productDemandInfoService.getInfoByIds(res);
        }
        Page<ProductDemandInfo> page = new Page<>();
        page.setData(data);
        page.setTotalCount(size);

        //当总数都被浏览完的时候需要重新加载
        if(start + pageSize > size - 1){
            redisTemplate.delete(redisKey);
        }
        return Message.createBySuccess(page);

    }

    private Message<Page<ProductInfo>> getInfoFromRedis(Integer pageSize, Integer currentPage, String redisKey) {
        Integer start = (currentPage - 1) * pageSize;
        Page<ProductInfo> page = new Page<>();
        Integer size = redisTemplate.opsForSet().size(redisKey).intValue();

        List<Long> res = redisTemplate.opsForSet().randomMembers(redisKey, pageSize);
        List<ProductInfo> data = null;
        if(res != null && !res.isEmpty()){
             data = productInfoService.getProductBriefByIds(res);
        }
        page.setData(data);
        page.setTotalCount(size);

        if(start + pageSize > size - 1){
            redisTemplate.delete(redisKey);
        }
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
