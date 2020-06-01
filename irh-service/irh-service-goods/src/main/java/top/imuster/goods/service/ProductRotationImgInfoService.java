package top.imuster.goods.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.goods.api.pojo.ProductRotationImgInfo;

import java.util.List;

/**
 * ProductRotationImgInfoService接口
 * @author 黄明人
 * @since 2020-06-01 15:45:21
 */
public interface ProductRotationImgInfoService extends BaseService<ProductRotationImgInfo, Long> {

    /**
     * @Author hmr
     * @Description 将点击次数保存到DB中
     * @Date: 2020/6/1 16:17
     * @param res
     * @reture: java.lang.Integer
     **/
    Integer saveClick2DB(List<BrowserTimesDto> res);
}