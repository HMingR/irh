package top.imuster.goods.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.controller.BaseController;
import top.imuster.goods.api.dto.ProductAndDemandDto;
import top.imuster.goods.service.ProductCollectRelService;

/**
 * @ClassName: ProductCollectController
 * @Description: ProductCollectController  商品收藏表
 * @author: hmr
 * @date: 2020/5/9 8:29
 */
@RestController
@RequestMapping("/collect")
public class ProductCollectController extends BaseController {

    @Autowired
    ProductCollectRelService productCollectRelService;


    /**
     * @Author hmr
     * @Description 收藏
     * @Date: 2020/5/9 8:31
     * @param type 1-收藏商品  2-收藏需求
     * @param id  被收藏的对象id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @GetMapping("/{type}/{id}")
    public Message<String> collect(@PathVariable("type") Integer type, @PathVariable("id") Long id){
        if(type != 1 && type != 2) return Message.createByError("参数错误");
        Long userId = getCurrentUserIdFromCookie();
        //埋点日志 userId|productId|score
        log.info("埋点日志=>用户收藏商品");
        log.info("PRODUCT_RATING_PREFIX" + ":" + userId + "|" + id + "|"+ "4" );    //收藏商品得4分
        return productCollectRelService.collect(userId, type, id);
    }

    @DeleteMapping("/{type}/{targetId}")
    public Message<String> deleteCollect(@PathVariable("type") Integer type, @PathVariable("targetId") Long targetId){
        return productCollectRelService.deleteCollect(getCurrentUserIdFromCookie(), targetId, type);
    }

    @DeleteMapping("/{id}")
    public Message<String> deleteCollect(@PathVariable("id") Long id){
        Long userId = getCurrentUserIdFromCookie();
        return productCollectRelService.deleteCollect(id, userId);
    }

    @DeleteMapping("/all")
    public Message<Integer> deleteAll(){
        return productCollectRelService.deleteAddCollect(getCurrentUserIdFromCookie());
    }

    @GetMapping("/list/{pageSize}/{currentPage}")
    public Message<Page<ProductAndDemandDto>> getList(@PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage){
        return productCollectRelService.list(pageSize, currentPage, getCurrentUserIdFromCookie());
    }

    /**
     * @Author hmr
     * @Description 根据id查看是否收藏
     * @Date: 2020/5/12 18:52
     * @param type1-收藏商品  2-收藏需求
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.Integer>
     **/
    @GetMapping("/state/{type}/{id}")
    public Message<Integer> getCollectState(@PathVariable("type") Integer type, @PathVariable("id") Long id){
        Long userId = getCurrentUserIdFromCookie();
        return productCollectRelService.getCollectStateById(userId, id, type);
    }
}
