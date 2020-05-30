package top.imuster.goods.service;


import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.goods.api.dto.GoodsForwardDto;
import top.imuster.goods.api.pojo.ProductDemandInfo;

import java.util.List;

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

    /**
     * @Author hmr
     * @Description 将浏览记录从redis保存到DB
     * @Date: 2020/4/22 9:58
     * @param browserTimesDtos
     * @reture: void
     **/
    void transBrowserTimesFromRedis2DB(List<BrowserTimesDto> browserTimesDtos);

    /**
     * @Author hmr
     * @Description 根据demand的id删除自己发布的需求
     * @Date: 2020/5/3 16:17
     * @param id
     * @param userId
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> deleteById(Long id, Long userId);

    /**
     * @Author hmr
     * @Description 用户发布
     * @Date: 2020/5/6 12:02
     * @param productDemandInfo
     * @param userId
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> releaseDemand(ProductDemandInfo productDemandInfo, Long userId);

    void updateDemandCollectTotal(List<GoodsForwardDto> list);

    /**
     * @Author hmr
     * @Description 根据发布时间查看最新的信息
     * @Date: 2020/5/24 15:36
     * @param pageSize
     * @param currentPage
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductDemandInfo>>
     **/
    Message<Page<ProductDemandInfo>> list(Integer pageSize, Integer currentPage);

    /**
     * @Author hmr
     * @Description
     * @Date: 2020/5/24 16:05
     * @param res
     * @reture: java.util.List<top.imuster.goods.api.pojo.ProductDemandInfo>
     **/
    List<ProductDemandInfo> getInfoByIds(List<Long> res);

    /**
     * @Author hmr
     * @Description 根据id查看详情
     * @Date: 2020/5/30 15:45
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.goods.api.pojo.ProductDemandInfo>
     **/
    Message<ProductDemandInfo> detailById(Long id);
}