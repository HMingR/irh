package top.imuster.goods.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.goods.api.pojo.ProductRotationImgInfo;

import java.util.List;

/**
 * ProductRotationImgInfoDao 接口
 * @author 黄明人
 * @since 2020-06-01 15:45:21
 */
public interface ProductRotationImgInfoDao extends BaseDao<ProductRotationImgInfo, Long> {
    //自定义扩展
    /**
     * @Author hmr
     * @Description
     * @Date: 2020/6/1 16:17
     * @param res
     * @reture: java.lang.Integer
     **/
    Integer updateClickTotal(List<BrowserTimesDto> res);

}