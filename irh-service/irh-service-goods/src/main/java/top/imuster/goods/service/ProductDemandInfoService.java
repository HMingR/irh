package top.imuster.goods.service;


import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductDemandInfo;

/**
 * ProductDemandInfoService接口
 * @author 黄明人
 * @since 2020-01-16 10:19:41
 */
public interface ProductDemandInfoService extends BaseService<ProductDemandInfo, Long> {

    /**
     * @Author hmr
     * @Description 查看个人的发布需求的历史记录
     * @Date: 2020/4/12 18:27
     * @param userId
     * @param pageSize
     * @param currentPage
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductDemandInfo>>
     **/
    Message<Page<ProductDemandInfo>> list(Long userId, Integer pageSize, Integer currentPage);
}