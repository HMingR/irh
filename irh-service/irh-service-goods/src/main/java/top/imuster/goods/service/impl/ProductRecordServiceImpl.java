package top.imuster.goods.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.service.ProductInfoService;
import top.imuster.goods.service.ProductRecordService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName: ProductRecordServiceImpl
 * @Description: ProductRecordServiceImpl
 * @author: hmr
 * @date: 2020/4/26 16:08
 */
@Service("productRecordService")
public class ProductRecordServiceImpl implements ProductRecordService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Resource
    private ProductInfoService productInfoService;

    @Override
    public Message<Page<ProductInfo>> getUserRecordList(Integer pageSize, Integer currentPage, Long userId) throws IOException {
        String browseRecordKey = RedisUtil.getBrowseRecordKey(BrowserType.ES_SELL_PRODUCT, userId);
        Set<ZSetOperations.TypedTuple<Integer>> set = redisTemplate.opsForZSet().reverseRangeWithScores(browseRecordKey, (currentPage - 1) * pageSize, pageSize);

        HashMap<Long, Double> scoreMap = new HashMap<>();
        Iterator<ZSetOperations.TypedTuple<Integer>> iterator = set.iterator();
        while (iterator.hasNext()){
            ZSetOperations.TypedTuple<Integer> typedTuple = iterator.next();
            long targetId = typedTuple.getValue().longValue();
            double score = typedTuple.getScore();
            scoreMap.put(targetId, score);
        }
        Set<Long> ids = scoreMap.keySet();

        if(ids.isEmpty()){
            return Message.createBySuccess();
        }
        ArrayList<Long> longs = new ArrayList<>(ids);
        Page<ProductInfo> page = new Page<>();
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        List<ProductInfo> productBriefByIds = productInfoService.getProductBriefByIds(longs);

        productBriefByIds.stream().forEach(productInfo -> {
            Long id = productInfo.getId();
            Double time = scoreMap.get(id);
            if(time != null)  productInfo.setBrowseDate(String.valueOf(time));
        });

        page.setData(productBriefByIds);
        page.setTotalCount(redisTemplate.opsForZSet().size(browseRecordKey).intValue());
        return Message.createBySuccess(page);
    }

    @Override
    public Message<String> deleteAll(Long userId) {
        String browseRecordKey = RedisUtil.getBrowseRecordKey(BrowserType.ES_SELL_PRODUCT, userId);
        redisTemplate.delete(browseRecordKey);
        return Message.createBySuccess();
    }

    @Override
    public Message<String> deleteByIndex(Long targetId, Long userId) {
        String browseRecordKey = RedisUtil.getBrowseRecordKey(BrowserType.ES_SELL_PRODUCT, userId);
        redisTemplate.opsForZSet().remove(browseRecordKey, targetId);
        return Message.createBySuccess();
    }
}
