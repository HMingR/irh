package top.imuster.goods.web.controller;

import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.controller.BaseController;
import top.imuster.goods.api.pojo.ProductDemandInfo;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.service.RecommendProductService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName: RecommendProductController
 * @Description: 个人推荐控制
 * @author: hmr
 * @date: 2020/5/1 14:02
 */
@RestController
@RequestMapping("/recommend")
public class RecommendController extends BaseController {

    @Resource
    RecommendProductService recommendProductService;

    /**
     * @Author hmr
     * @Description 离线推荐
     * @Date: 2020/5/1 14:06
     * @param pageSize
     * @param currentPage
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductInfo>>
     **/
    @GetMapping("/offline/{pageSize}/{currentPage}")
    public Message<Page<ProductInfo>> getOfflineRecommendByUserId(@PathVariable("pageSize") Integer pageSize,
                                                           @PathVariable("currentPage") Integer currentPage){
        return recommendProductService.getOfflineRecommendListByUserId(pageSize, currentPage, getCurrentUserIdFromCookie(false));
    }

    /**
     * @Author hmr
     * @Description 实时推荐
     * @Date: 2020/5/14 9:20
     * @param
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductInfo>>
     **/
    @GetMapping("/realtime/{pageSize}/{currentPage}")
    public Message<Page<ProductInfo>> getRealTimeRecommend(@PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage){
        return recommendProductService.getRealtimeRecommend(pageSize, currentPage, getCurrentUserIdFromCookie(false));
    }


    /**
     * @Author hmr
     * @Description 在查看商品的时候推荐和该商品相关的商品
     * @Date: 2020/5/14 9:41
     * @param pageSize
     * @param currentPage
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductInfo>>
     **/
    @GetMapping("/content/{pageSize}/{currentPage}/{productId}")
    public Message<Page<ProductInfo>> getContentRecommend(@PathVariable("productId") Long productId, @PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage){
        return recommendProductService.getContentRecommend(pageSize, currentPage, productId);
    }

    /**
     * @Author hmr
     * @Description 根据text的内容推荐标签
     * @Date: 2020/5/8 9:32
     * @param text
     * @reture: top.imuster.common.base.wrapper.Message<java.util.List<java.lang.Object>>
     **/
    @PostMapping("/tag")
    public Message<List<Object>> recommendTagName(@RequestParam("text") String text) throws IOException {
        return recommendProductService.recommendTagNames(text);
    }

    @GetMapping("/demand/{pageSize}/{currentPage}")
    public Message<Page<ProductDemandInfo>> getRecommendDemand(@PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage){
        Long userId = getCurrentUserIdFromCookie(false);
        return recommendProductService.getDemandRecommend(userId, pageSize, currentPage);
    }
}
