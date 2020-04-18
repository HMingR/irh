package top.imuster.goods.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductCategoryInfo;
import top.imuster.goods.api.pojo.ProductCategoryRel;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.exception.GoodsException;
import top.imuster.goods.dao.ProductCategoryInfoDao;
import top.imuster.goods.service.ProductCategoryInfoService;
import top.imuster.goods.service.ProductCategoryRelService;
import top.imuster.goods.service.ProductInfoService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * ProductCategoryInfoService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Slf4j
@Service("productCategoryInfoService")
public class ProductCategoryInfoServiceImpl extends BaseServiceImpl<ProductCategoryInfo, Long> implements ProductCategoryInfoService {

    @Resource
    private ProductCategoryInfoDao productCategoryInfoDao;

    @Resource
    ProductInfoService productInfoService;

    @Resource
    ProductCategoryRelService productCategoryRelService;

    @Override
    public BaseDao<ProductCategoryInfo, Long> getDao() {
        return this.productCategoryInfoDao;
    }

    @Override
    public Message<List<ProductCategoryInfo>> getCategoryTree() throws GoodsException {
        try{
            List<ProductCategoryInfo> allCategory =productCategoryInfoDao.selectAllCategory();
            return Message.createBySuccess(generateTree(allCategory));
        }catch (Exception e){
            throw new GoodsException("获得商品分类数据失败");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer delCategoryById(Long id) throws GoodsException {
        Long parentId = productCategoryInfoDao.selectEntryList(id).get(0).getParentId();
        if(0 == parentId){
            ProductCategoryInfo condition = new ProductCategoryInfo();
            condition.setParentId(parentId);
            condition.setState(2);
            Integer count = productCategoryInfoDao.selectEntryListCount(condition);
            if(count != 0){
                throw new GoodsException("删除的是根分类,请先将根节点的商品全部修改或删除再重试");
            }
        }

        //更新分类
        ProductCategoryInfo productCategoryInfo = new ProductCategoryInfo();
        productCategoryInfo.setId(id);
        productCategoryInfo.setState(1);
        productCategoryInfoDao.updateByKey(productCategoryInfo);

        //删除商品关系表中的数据
        ProductCategoryRel productCategoryRel = new ProductCategoryRel();
        productCategoryRel.setCategoryId(id);
        productCategoryRelService.deleteByCondtion(productCategoryRel);

        //更新商品中的分类
        ProductInfo productInfo = new ProductInfo();
        productInfo.setCategoryId(id);
        productInfo.setNewCategoryId(parentId);
        return productInfoService.updateProductCategoryByCondition(productInfo);
    }

    private List<ProductCategoryInfo> generateTree(List<ProductCategoryInfo> list){
        List<ProductCategoryInfo> result = new ArrayList<>();
        // 1、获取第一级节点
        for (ProductCategoryInfo ProductCategoryInfo : list) {
            if(0 == ProductCategoryInfo.getParentId()) {
                result.add(ProductCategoryInfo);
            }
        }
        // 2、递归获取子节点
        for (ProductCategoryInfo parent : result) {
            parent = recursiveTree(parent, list);
        }
        return result;
    }

    private ProductCategoryInfo recursiveTree(ProductCategoryInfo parent, List<ProductCategoryInfo> list) {
        for (ProductCategoryInfo productCategoryInfo : list) {
            if(parent.getId() == productCategoryInfo.getParentId()) {
                productCategoryInfo = recursiveTree(productCategoryInfo, list);
                parent.getChilds().add(productCategoryInfo);
            }
        }
        return parent;
    }
}