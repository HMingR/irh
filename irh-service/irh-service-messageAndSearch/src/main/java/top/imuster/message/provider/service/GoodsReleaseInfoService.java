package top.imuster.message.provider.service;

import top.imuster.common.core.enums.OperationType;
import top.imuster.goods.api.pojo.ProductInfo;

/**
 * @ClassName: ReleaseInfoService
 * @Description: ReleaseInfoService
 * @author: hmr
 * @date: 2020/4/24 11:32
 */
public interface GoodsReleaseInfoService{

    /**
     * @Author hmr
     * @Description 根据 OperationType(更新，删除，新增) 将商品信息同步到ES中
     * @Date: 2020/5/3 9:37
     * @param productInfo
     * @param operationType
     * @reture: void
     **/
    void synchGoodsFromDB2ESByType(ProductInfo productInfo, OperationType operationType);

}
