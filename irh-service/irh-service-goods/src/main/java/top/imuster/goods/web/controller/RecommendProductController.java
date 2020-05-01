package top.imuster.goods.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.service.RecommendProductService;

import javax.annotation.Resource;

/**
 * @ClassName: RecommendProductController
 * @Description: 个人推荐控制哎
 * @author: hmr
 * @date: 2020/5/1 14:02
 */
@RestController
@RequestMapping("/recommend")
public class RecommendProductController {

    @Resource
    RecommendProductService recommendProductService;

    /**
     * @Author hmr
     * @Description 根据用户id获得推荐给用户的商品
     * @Date: 2020/5/1 14:06
     * @param pageSize
     * @param currentPage
     * @param userId
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductInfo>>
     **/
    @GetMapping("/{pageSize}/{currentPage}/{userId}")
    public Message<Page<ProductInfo>> getRecommendByUserId(@PathVariable("pageSize") Integer pageSize,
                                                           @PathVariable("currentPage") Integer currentPage,
                                                           @PathVariable("userId") Long userId){
        return recommendProductService.getRecommendListByUserId(pageSize, currentPage, userId);
    }

}
