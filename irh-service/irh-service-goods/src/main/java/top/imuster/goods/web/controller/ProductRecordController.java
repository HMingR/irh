package top.imuster.goods.web.controller;

import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.BrowseRecordAnnotation;
import top.imuster.common.core.annotation.BrowserAnnotation;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.dto.BrowseRecordDto;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.service.ProductRecordService;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @ClassName: ProductRecordController
 * @Description: 商品浏览记录查询
 * @author: hmr
 * @date: 2020/4/26 15:55
 */
@RestController
@RequestMapping("/record")
public class ProductRecordController extends BaseController {

    @Resource
    ProductRecordService productRecordService;

    /**
     * @Author hmr
     * @Description 用户查看自己的浏览记录
     * @Date: 2020/4/26 16:01
     * @param pageSize
     * @param currentPage
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductInfo>>
     **/
    @GetMapping("/{pageSize}/{currentPage}")
    public Message<Page<ProductInfo>> recordList(@PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage) throws IOException {
        return productRecordService.getUserRecordList(pageSize, currentPage, getCurrentUserIdFromCookie());
    }

    @DeleteMapping("/{targetId}")
    public Message<String> deleteByIndex(@PathVariable("targetId") Long targetId){
        return productRecordService.deleteByIndex(targetId, getCurrentUserIdFromCookie());
    }

    @DeleteMapping
    public Message<String> deleteAll(){
        return productRecordService.deleteAll(getCurrentUserIdFromCookie());
    }

    /**
     * @Author hmr
     * @Description 记录浏览和浏览次数
     * @Date: 2020/4/22 9:04
     * @param targetId
     * @reture: void
     **/
    @GetMapping("/browse/{targetId}")
    @BrowseRecordAnnotation(browserType = BrowserType.ES_SELL_PRODUCT, value = "#p1")
    @BrowserAnnotation(browserType = BrowserType.ES_SELL_PRODUCT, value = "#p0")
    public Message<String> browser(@PathVariable("targetId") Long targetId, BrowseRecordDto recordDto){
        Long userId = getCurrentUserIdFromCookie(false);
        recordDto.setUserId(userId);
        recordDto.setTargetId(targetId);
        recordDto.setBrowserType(BrowserType.ES_SELL_PRODUCT);
        //埋点日志 userId|productId|score
        log.info("埋点日志=>用户浏览商品");
        log.info("PRODUCT_RATING_PREFIX" + ":" + userId + "|" + targetId + "|"+ "3" );    //浏览商品得3分
        return Message.createBySuccess();
    }
}
