package top.imuster.message.provider.service.impl;

import org.springframework.stereotype.Service;
import top.imuster.common.core.enums.OperationType;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.message.provider.dao.GoodsReleaseRepository;
import top.imuster.message.provider.service.GoodsReleaseInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: GoodsReleaseInfoServiceImpl
 * @Description: GoodsReleaseInfoServiceImpl
 * @author: hmr
 * @date: 2020/5/3 9:35
 */
@Service("goodsReleaseInfoService")
public class GoodsReleaseInfoServiceImpl implements GoodsReleaseInfoService {

    @Resource
    GoodsReleaseRepository goodsReleaseRepository;

    @Override
    public void synchGoodsFromDB2ESByType(ProductInfo productInfo, OperationType operationType) {
        if(OperationType.INSERT.equals(operationType)){
            insert2ES(productInfo);
        }else if(OperationType.UPDATE.equals(operationType)){
            update2ES(productInfo);
        }else{
            deleteFromES(productInfo);
        }
    }

    /**
     * @Author hmr
     * @Description 新增到ES中
     * @Date: 2020/5/3 9:39
     * @param productInfo
     * @reture: void
     **/
    private void insert2ES(ProductInfo productInfo){
        goodsReleaseRepository.save(productInfo);
    }

    /**
     * @Author hmr
     * @Description 更新到ES
     * @Date: 2020/5/3 9:40
     * @param productInfo
     * @reture: void
     **/
    private void update2ES(ProductInfo productInfo){
        //todo
        //goodsReleaseRepository.delete(productInfo);
    }

    /**
     * @Author hmr
     * @Description 从ES中删除
     * @Date: 2020/5/3 9:40
     * @param productInfo
     * @reture: void
     **/
    private void deleteFromES(ProductInfo productInfo){
        goodsReleaseRepository.delete(productInfo);
    }
}
