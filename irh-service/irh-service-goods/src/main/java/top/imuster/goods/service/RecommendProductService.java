package top.imuster.goods.service;

import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductInfo;

/**
 * @ClassName: RecommendProductService
 * @Description:
 * @author: hmr
 * @date: 2020/5/1 14:03
 */
public interface RecommendProductService {

    /**
     * @Author hmr
     * @Description 分页获得推荐的商品信息
     * @Date: 2020/5/1 14:11
     * @param pageSize
     * @param currentPage
     * @param userId
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductInfo>>
     **/
    Message<Page<ProductInfo>> getRecommendListByUserId(Integer pageSize, Integer currentPage, Long userId);
}
