package top.imuster.goods.service;


import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.dto.ProductAndDemandDto;
import top.imuster.goods.api.pojo.ProductCollectRel;

/**
 * ProductCollectRelService接口
 * @author 黄明人
 * @since 2020-04-26 15:48:44
 */
public interface ProductCollectRelService extends BaseService<ProductCollectRel, Long> {

    /**
     * @Author hmr
     * @Description 收藏
     * @Date: 2020/5/9 8:33
     * @param userId
     * @param type 1-收藏商品  2-收藏需求
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> collect(Long userId, Integer type, Long id);

    /**
     * @Author hmr
     * @Description 删除收藏
     * @Date: 2020/5/9 8:43
     * @param currentUserIdFromCookie
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> deleteCollect(Long currentUserIdFromCookie, Long id);

    /**
     * @Author hmr
     * @Description 清空收藏
     * @Date: 2020/5/9 8:53
     * @param currentUserIdFromCookie
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<Integer> deleteAddCollect(Long currentUserIdFromCookie);

    /**
     * @Author hmr
     * @Description 分页搜索
     * @Date: 2020/5/12 19:21
     * @param pageSize
     * @param currentPage
     * @param currentUserIdFromCookie
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.dto.ProductAndDemandDto>>
     **/
    Message<Page<ProductAndDemandDto>> list(Integer pageSize, Integer currentPage, Long currentUserIdFromCookie);

    /**
     * @Author hmr
     * @Description 查看是否收藏
     * @Date: 2020/5/11 14:20
     * @param userId
     * @param id
     * @param type
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<Integer> getCollectStateById(Long userId, Long id, Integer type);
}