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
import top.imuster.goods.api.pojo.ProductCollectRel;
import top.imuster.goods.dao.ProductCollectRelDao;
import top.imuster.goods.service.ProductCollectRelService;

import javax.annotation.Resource;

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
        productCollectRelDao.insertEntry(collectRel);
        redisTemplate.opsForHash().increment(RedisUtil.getGoodsCollectMapKey(type), id, 1);
        return Message.createBySuccess();
    }

    @Override
    public Message<String> deleteCollect(Long currentUserId, Long id) {
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

    @Override
    public Message<Integer> deleteAddCollect(Long currentUserIdFromCookie) {
        Integer total = productCollectRelDao.deleteAllCollectByUserId(currentUserIdFromCookie);
        return Message.createBySuccess(total);
    }

    @Override
    public Message<Page<ProductCollectRel>> list(Integer pageSize, Integer currentPage, Long currentUserIdFromCookie) {
        Page<ProductCollectRel> page = new Page<>();
        ProductCollectRel condition = new ProductCollectRel();
        condition.setState(2);
        condition.setOrderField("create_time");
        condition.setOrderFieldType("DESC");
        condition.setUserId(currentUserIdFromCookie);
        page.setPageSize(pageSize < 1 ? 10 : pageSize);
        page.setCurrentPage(currentPage < 1 ? 1: currentPage);
        page = this.selectPage(condition, page);
        return Message.createBySuccess(page);
    }

    @Override
    public Message<Integer> getCollectStateById(Long userId, Long id) {
        ProductCollectRel productCollectRel = new ProductCollectRel();
        productCollectRel.setUserId(userId);
        productCollectRel.setProductId(id);
        productCollectRel.setState(2);
        Integer count = productCollectRelDao.selectEntryListCount(productCollectRel);
        return Message.createBySuccess(count);
    }
}