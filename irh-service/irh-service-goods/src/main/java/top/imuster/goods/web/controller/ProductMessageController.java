package top.imuster.goods.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.goods.api.pojo.ProductMessageInfo;
import top.imuster.goods.service.ProductMessageService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName: ProductMessageController
 * @Description: 商品留言控制器
 * @author: hmr
 * @date: 2020/1/9 15:18
 */
@Api("商品留言控制器")
@RestController
@RequestMapping("/goods/msg")
public class ProductMessageController extends BaseController {

    @Resource
    ProductMessageService productMessageService;

    /**
     * @Description: 根据商品的id查询商品的留言信息
     * @Author: hmr
     * @Date: 2020/1/9 15:43
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("根据商品的id查询商品的留言信息")
    @GetMapping("/{pageSize}/{currentPage}/{goodsId}/{parentId}/{firstClassId}")
    public Message<Page<ProductMessageInfo>> list(@PathVariable("pageSize") Integer pageSize,
                                                  @PathVariable("currentPage") Integer currentPage,
                                                  @PathVariable("goodsId") Long goodsId,
                                                  @PathVariable("parentId") Long parentId,
                                                  @PathVariable("firstClassId") Long firstClassId){
        if(pageSize < 0 || currentPage < 0) return Message.createByError("参数异常,请刷新后重试");
        return productMessageService.getMessagePage(pageSize, currentPage, goodsId, parentId, firstClassId);
    }

    @ApiOperation("根据商品id和留言父id写留言信息")
    @PostMapping("/write")
    public Message<String> writeMessage(@ApiParam("在写留言信息的时候，留言的商品id、parentId、内容不能为空") @Validated(ValidateGroup.addGroup.class) @RequestBody ProductMessageInfo productMessageInfo,
                                BindingResult bindingResult) throws Exception{
        validData(bindingResult);
        Long userId = getCurrentUserIdFromCookie();
        productMessageInfo.setConsumerId(userId);
        productMessageService.generateSendMessage(productMessageInfo);
        //埋点日志 userId|productId|score
        log.info("埋点日志=>用户留言商品");
        log.info("PRODUCT_RATING_PREFIX" + ":" + userId + "|" + productMessageInfo.getProductId() + "|"+ "3" );    //留言商品得3分
        return Message.createBySuccess();
    }

    @ApiOperation("根据留言id删除自己的留言信息")
    @DeleteMapping("/{id}")
    public Message<String> deleteMsg(@ApiParam("留言的id") @PathVariable("id") Long id, HttpServletRequest request){
        Long userId = getCurrentUserIdFromCookie();
        ProductMessageInfo condition = new ProductMessageInfo();
        condition.setConsumerId(userId);
        condition.setId(id);
        condition.setState(1);
        productMessageService.updateByKey(condition);
        return Message.createBySuccess();
    }


    /**
     * @Author hmr
     * @Description 根据id获得留言信息详情
     * @Date: 2020/5/12 8:45
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.goods.api.pojo.ProductMessageInfo>
     **/
    @GetMapping("/detail/{id}")
    public Message<ProductMessageInfo> getDetail(@PathVariable("id") Long id){
        List<ProductMessageInfo> list = productMessageService.selectEntryList(id);
        if(list == null || list.isEmpty()) return Message.createBySuccess();
        return Message.createBySuccess(list.get(0));
    }

}
