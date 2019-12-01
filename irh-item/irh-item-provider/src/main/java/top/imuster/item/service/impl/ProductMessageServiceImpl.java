package top.imuster.item.service.impl;

import org.springframework.stereotype.Service;
import top.imuster.domain.base.BaseDao;
import top.imuster.item.dao.ProductMessageDao;
import top.imuster.item.domain.ProductMessage;
import top.imuster.item.service.ProductMessageService;
import top.imuster.service.base.BaseServiceImpl;

import javax.annotation.Resource;

/**
 * ProductMessageService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("productMessageService")
public class ProductMessageServiceImpl extends BaseServiceImpl<ProductMessage, Long> implements ProductMessageService {

    @Resource
    private ProductMessageDao productMessageDao;

    @Override
    public BaseDao<ProductMessage, Long> getDao() {
        return this.productMessageDao;
    }
}