package top.imuster.goods.service.impl;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.ReleaseAnnotation;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.common.core.dto.SendDetailPageDto;
import top.imuster.common.core.enums.OperationType;
import top.imuster.common.core.enums.ReleaseType;
import top.imuster.common.core.enums.TemplateEnum;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.goods.api.dto.ESProductDto;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.dao.ProductInfoDao;
import top.imuster.goods.service.ProductInfoService;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * ProductInfoService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("productInfoService")
public class ProductInfoServiceImpl extends BaseServiceImpl<ProductInfo, Long> implements ProductInfoService {

    private int batchSize = 100;

    @Resource
    private ProductInfoDao productInfoDao;

    @Resource
    private GenerateSendMessageService generateSendMessageService;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public BaseDao<ProductInfo, Long> getDao() {
        return this.productInfoDao;
    }

    @Override
    public Integer updateProductCategoryByCondition(ProductInfo productInfo) {
        return productInfoDao.updateProductCategoryByCondition(productInfo);
    }

    @Override
    public Long getConsumerIdById(Long id) {
        return productInfoDao.selectSalerIdByProductId(id);
    }

    @Override
    public ProductInfo getProductInfoByMessageId(Long id) {
        return productInfoDao.selectProductInfoByMessageId(id);
    }

    @Override
    public ProductInfo getProductBriefInfoById(Long id) {
        ProductInfo productInfo = productInfoDao.selectProductBriefInfoById(id);
        return productInfo;
    }

    @Override
    public Message<String> releaseProduct(ProductInfo productInfo) {
        productInfoDao.insertEntry(productInfo);
        SendDetailPageDto sendMessage = new SendDetailPageDto();
        productInfo.setId(productInfo.getId());
        sendMessage.setObject(productInfo);
        sendMessage.setTemplateEnum(TemplateEnum.PRODUCT_TEMPLATE);
        generateSendMessageService.sendToMq(sendMessage);
        convertInfo(new ESProductDto(productInfo));
        return Message.createBySuccess();
    }

    /**
     * @Author hmr
     * @Description 将信息转换成ES中的结构并发送到MQ中
     * @Date: 2020/5/8 9:22
     * @param productDto
     * @reture: void
     **/
    @ReleaseAnnotation(type = ReleaseType.GOODS, value = "#p0", operationType = OperationType.INSERT)
    private void convertInfo(ESProductDto productDto){
    }

    @Override
    public Message<Page<ProductInfo>> list(Long userId, Integer pageSize, Integer currentPage) {
        Page<ProductInfo> infoPage = new Page<>();
        ProductInfo productInfo = new ProductInfo();
        productInfo.setConsumerId(userId);
        productInfo.setState(2);
        infoPage = this.selectPage(productInfo, infoPage);
        return Message.createBySuccess(infoPage);
    }

    @Override
    public void transBrowserTimesFromRedis2DB(List<BrowserTimesDto> browserTimesDtos) {
        if(browserTimesDtos == null || browserTimesDtos.isEmpty()) return;
        HashSet<Long> targetIds = new HashSet<>();
        HashSet<Long> times = new HashSet<>();
        browserTimesDtos.stream().forEach(browserTimesDto -> {
            Long targetId = browserTimesDto.getTargetId();
            Long total = browserTimesDto.getTotal();
            targetIds.add(targetId);
            times.add(total);
        });
        Long[] ids = targetIds.toArray(new Long[targetIds.size()]);
        Long[] totals = times.toArray(new Long[times.size()]);
        for (int i = 0; i <= ids.length / batchSize; i++){
            ArrayList<ProductInfo> update = new ArrayList<>();
            Long[] selectIds = new Long[batchSize];
            for (int j = i * batchSize, x = 0; j < (i + 1) * batchSize; j++, x++) {
                selectIds[x] = ids[j];
                if(j == ids.length - 1) break;
            }
            Map<Long, Long> result = productInfoDao.selectBrowserTimesByIds(ids);
            for (int z = 0; z < selectIds.length; z++){
                if(selectIds[z] == null || selectIds[z] == 0) break;
                Long selectId = selectIds[z];
                Long total = totals[i * batchSize + z];
                Long original = result.get(selectId);
                original = original + total;
                ProductInfo condition = new ProductInfo();
                condition.setId(selectId);
                condition.setBrowserTimes(original);
                update.add(condition);
            }
            productInfoDao.updateBrowserTimesByCondition(update);
        }

    }

    @Override
    @ReleaseAnnotation(type = ReleaseType.GOODS, value = "#p0", operationType = OperationType.REMOVE)
    public Message<String> deleteById(Long id, Long userId) {
        Long userIdByProductId = productInfoDao.selectUserIdByProductId(id);
        if(userIdByProductId == null) return Message.createByError("删除失败,请刷新后重试");
        if(!userId.equals(userIdByProductId)){
            log.error("id为{}的用户试图删除id为{}的商品，但是该商品不属于他", userId, id);
            return Message.createByError("非法操作,你当前的操作已经被记录");
        }
        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(id);
        productInfo.setState(1);
        productInfoDao.updateByKey(productInfo);
        return Message.createBySuccess();
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