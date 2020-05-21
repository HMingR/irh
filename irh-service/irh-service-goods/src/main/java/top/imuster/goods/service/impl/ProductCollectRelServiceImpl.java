package top.imuster.goods.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.goods.api.dto.ProductAndDemandDto;
import top.imuster.goods.api.pojo.ProductCollectRel;
import top.imuster.goods.api.pojo.ProductDemandInfo;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.dao.ProductCollectRelDao;
import top.imuster.goods.service.ProductCollectRelService;
import top.imuster.goods.service.ProductDemandInfoService;
import top.imuster.goods.service.ProductInfoService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * ProductCollectRelService 实现类
 * @author 黄明人
 * @since 2020-04-26 15:48:44
 */
@Service("productCollectRelService")
public class ProductCollectRelServiceImpl extends BaseServiceImpl<ProductCollectRel, Long> implements ProductCollectRelService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ProductCollectRelDao productCollectRelDao;

    @Resource
    private ProductInfoService productInfoService;

    @Resource
    private ProductDemandInfoService productDemandInfoService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public BaseDao<ProductCollectRel, Long> getDao() {
        return this.productCollectRelDao;
    }

    @Override
    public Message<String> collect(Long userId, Integer type, Long id) {
        ProductCollectRel collectRel = new ProductCollectRel();
        collectRel.setProductId(id);
        collectRel.setType(type);
        collectRel.setUserId(userId);
        collectRel.setState(2);
        Integer count = productCollectRelDao.selectEntryListCount(collectRel);
        if(count != 0) return Message.createByError("您已经收藏了该商品,请刷新后重试");

        productCollectRelDao.insertEntry(collectRel);
        redisTemplate.opsForHash().increment(RedisUtil.getGoodsCollectMapKey(type), String.valueOf(id), 1);
        return Message.createBySuccess();
    }

    @Override
    public Message<String> deleteCollect(Long currentUserId, Long targetId, Integer type) {
        ProductCollectRel productCollectRel = new ProductCollectRel();
        productCollectRel.setType(type);
        productCollectRel.setProductId(targetId);
        productCollectRel.setUserId(currentUserId);
        productCollectRel.setState(2);
        List<ProductCollectRel> productCollectRels = productCollectRelDao.selectEntryList(productCollectRel);
        if(productCollectRels == null || productCollectRels.isEmpty()) return Message.createByError("未找到相关的收藏记录,请刷新后重试");

        ProductCollectRel condition = productCollectRels.get(0);
        condition.setState(1);
        productCollectRelDao.updateByKey(condition);
        return Message.createBySuccess();
    }

    @Override
    public Message<Integer> deleteAddCollect(Long currentUserIdFromCookie) {
        Integer total = productCollectRelDao.deleteAllCollectByUserId(currentUserIdFromCookie);
        return Message.createBySuccess(total);
    }

    @Override
    public Message<Page<ProductAndDemandDto>> list(Integer pageSize, Integer currentPage, Long currentUserIdFromCookie) {
        Page<ProductCollectRel> page = new Page<>();
        ProductCollectRel condition = new ProductCollectRel();
        condition.setState(2);
        condition.setOrderField("create_time");
        condition.setOrderFieldType("DESC");
        condition.setUserId(currentUserIdFromCookie);
        page.setPageSize(pageSize < 1 ? 10 : pageSize);
        page.setCurrentPage(currentPage < 1 ? 1: currentPage);
        page = this.selectPage(condition, page);

        List<ProductCollectRel> data = page.getData();

        ArrayList<ProductAndDemandDto> productInfos = new ArrayList<>();
        if(data != null && !data.isEmpty()){
            data.stream().forEach(productCollectRel -> {
                if(productCollectRel.getType() == 1){
                    Long productId = productCollectRel.getProductId();
                    ProductInfo productBriefInfo = productInfoService.getProductBriefInfoById(productId);
                    productInfos.add(new ProductAndDemandDto(productBriefInfo));
                }else{
                    Long productId = productCollectRel.getProductId();
                    List<ProductDemandInfo> infos = productDemandInfoService.selectEntryList(productId);
                    if(infos != null && !infos.isEmpty()) productInfos.add(new ProductAndDemandDto(infos.get(0)));
                }
            });
        }
        Page<ProductAndDemandDto> resPage = new Page<>();
        resPage.setData(productInfos);
        resPage.setTotalCount(page.getTotalCount());
        return Message.createBySuccess(resPage);
    }

    @Override
    public Message<Integer> getCollectStateById(Long userId, Long id, Integer type) {
        ProductCollectRel productCollectRel = new ProductCollectRel();
        productCollectRel.setUserId(userId);
        productCollectRel.setProductId(id);
        productCollectRel.setState(2);
        productCollectRel.setType(type);
        Integer count = productCollectRelDao.selectEntryListCount(productCollectRel);
        return Message.createBySuccess(count);
    }

    @Override
    public Message<String> deleteCollect(Long id, Long currentUserId) {
        Long userId = productCollectRelDao.selectUserIdById(id);
        if(userId == null) return Message.createByError("未找到相关的收藏记录,请刷新后重试");
        if(!currentUserId.equals(userId)) {
            log.warn("-------->用户id为{}的用户试图删除不属于自己的收藏,收藏id为{}",currentUserId, id);
            return Message.createByError("非法操作,当前操作已被保存,如果是无意,请立刻刷新后重试");
        }
        ProductCollectRel condition = new ProductCollectRel();
        condition.setId(id);
        condition.setState(1);
        productCollectRelDao.updateByKey(condition);
        return Message.createBySuccess();
    }
}