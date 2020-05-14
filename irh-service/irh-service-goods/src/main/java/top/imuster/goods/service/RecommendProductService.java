package top.imuster.goods.service;

import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductInfo;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName: RecommendProductService
 * @Description:
 * @author: hmr
 * @date: 2020/5/1 14:03
 */
public interface RecommendProductService {

    /**
     * @Author hmr
     * @Description 根据内容分词
     * @Date: 2020/5/7 20:22
     * @param text
     * @reture: top.imuster.common.base.wrapper.Message<java.util.List<java.lang.String>>
     **/
    Message<List<Object>> recommendTagNames(String text) throws IOException;

    /**
     * @Author hmr
     * @Description 分页获得推荐的商品信息
     * @Date: 2020/5/1 14:11
     * @param pageSize
     * @param currentPage
     * @param userId
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductInfo>>
     **/
    Message<Page<ProductInfo>> getOfflineRecommendListByUserId(Integer pageSize, Integer currentPage, Long userId);


    /**
     * @Author hmr
     * @Description 获得实施推荐的商品
     * @Date: 2020/5/14 9:23
     * @param pageSize
     * @param currentPage
     * @param userId
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductInfo>>
     **/
    Message<Page<ProductInfo>> getRealtimeRecommend(Integer pageSize, Integer currentPage, Long userId);

    /**
     * @Author hmr
     * @Description 基于内容的推荐
     * @Date: 2020/5/14 9:43
     * @param pageSize
     * @param currentPage
     * @param productId
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductInfo>>
     **/
    Message<Page<ProductInfo>> getContentRecommend(Integer pageSize, Integer currentPage, Long productId);
}
