package top.imuster.goods.dao;


import org.apache.ibatis.annotations.Param;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.goods.api.pojo.ProductDemandInfo;
import top.imuster.goods.api.pojo.ProductInfo;

import java.util.List;

/**
 * ProductDemandInfoDao 接口
 * @author 黄明人
 * @since 2020-01-16 10:19:41
 */
public interface ProductDemandInfoDao extends BaseDao<ProductDemandInfo, Long> {
    //自定义扩展
    /**
     * @Author hmr
     * @Description 根据id更新browserTimes
     * @Date: 2020/4/22 03
     * @param update
     * @reture: void
     **/
    void updateBrowserTimesByCondition(@Param("update") List<ProductInfo> update);

    /**
     * @Author hmr
     * @Description 根据主键id查询发布者id
     * @Date: 2020/5/3 16:18
     * @param id
     * @reture: java.lang.Long
     **/
    Long selectUserIdByDemandId(Long id);
}