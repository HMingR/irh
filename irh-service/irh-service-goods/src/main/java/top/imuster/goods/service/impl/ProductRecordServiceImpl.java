package top.imuster.goods.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.BrowseRecordDto;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.service.ProductInfoService;
import top.imuster.goods.service.ProductRecordService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        List<String> list = redisTemplate.opsForList().range(browseRecordKey, (currentPage - 1) * pageSize, pageSize);
        if(list == null || list.isEmpty()){
            return Message.createBySuccess();
        }
        Page<ProductInfo> page = new Page<>();
        ArrayList<ProductInfo> res = new ArrayList<>();
        BrowseRecordDto browseRecordDto;
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        for (String a : list) {
            browseRecordDto = objectMapper.readValue(a, BrowseRecordDto.class);
            ProductInfo articleInfo = productInfoService.getProductBriefInfoById(browseRecordDto.getTargetId());
            articleInfo.setBrowseDate(browseRecordDto.getCreateTime());
            res.add(articleInfo);
        }
        page.setData(res);
        page.setTotalCount(redisTemplate.opsForList().size(browseRecordKey).intValue());
        return Message.createBySuccess(page);
    }

    @Override
    public Message<String> deleteAll(Long currentUserIdFromCookie) {
        String browseRecordKey = RedisUtil.getBrowseRecordKey(BrowserType.ES_SELL_PRODUCT, currentUserIdFromCookie);
        redisTemplate.delete(browseRecordKey);
        return Message.createBySuccess();
    }

    @Override
    public Message<String> deleteByIndex(Integer index, Long userId) {
        String browseRecordKey = RedisUtil.getBrowseRecordKey(BrowserType.ES_SELL_PRODUCT, userId);
        Object target = redisTemplate.opsForList().index(browseRecordKey, index);
        redisTemplate.opsForList().remove(browseRecordKey, 1, target);
        return Message.createBySuccess();
    }
}
