package top.imuster.goods.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.base.utils.JwtTokenUtil;
import top.imuster.common.core.dto.SendMessageDto;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.api.pojo.ProductMessage;
import top.imuster.goods.exception.GoodsException;
import top.imuster.goods.service.ProductInfoService;
import top.imuster.goods.service.ProductMessageService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    @Resource
    ProductInfoService productInfoService;

    /**
     * @Description: 根据商品的id查询商品的留言信息(树形结构)
     * @Author: hmr
     * @Date: 2020/1/9 15:43
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("根据商品的id查询商品的留言信息(树形结构)")
    @GetMapping("/{goodsId}")
    public Message list(@PathVariable("goodsId") Long id){
        List<ProductMessage> messageTree = productMessageService.generateMessageTree(id);
        return Message.createBySuccess(messageTree);
    }

    @ApiOperation("根据商品id和留言父id写留言信息(如果是新的留言,则parentId写成0)")
    @PostMapping("/write")
    public Message writeMessage(@RequestBody ProductMessage productMessage, HttpServletRequest request, SendMessageDto sendMessageDto){
        try{
            Long userId = getIdByToken(request);
            ProductInfo productInfo = productInfoService.selectEntryList(productMessage.getProductId()).get(0);
            //如果parentId是0，则给卖家发送消息；如果不是，则给卖家和parentId对应的会员发送消息
            sendMessageDto.setTargetId(productInfo.getConsumerId());
            productMessageService.generateSendMessage(sendMessageDto, productMessage.getParentId());
            return Message.createBySuccess("留言成功");
        }catch (Exception e){
            logger.error("留言失败，出现异常为{}",e.getMessage(), e);
            throw new GoodsException("留言失败");
        }
    }

    @ApiOperation("根据留言id删除自己的留言信息")
    @DeleteMapping("/{id}")
    public Message deleteMsg(@PathVariable("id") Long id, HttpServletRequest request){
        try{
            Long userId = getIdByToken(request);
            ProductMessage productMessage = productMessageService.selectEntryList(id).get(0);
            if(null == productMessage || productMessage.getConsumerId()!= userId){
                logger.error("用户{}试图删除编号为{}的留言信息，但是这个留言信息不属于该用户发布",userId, id);
                return Message.createByError("只能删除自己的留言");
            }

            ProductMessage condition = new ProductMessage();
            condition.setId(id);
            condition.setState(1);
            int i = productMessageService.updateByKey(condition);
            if(i != 1){
                logger.error("更新的数量有误,一共更新了{}个数据",i);
                return Message.createByError("更新失败");
            }
            return Message.createBySuccess("更新成功");
        }catch (Exception e){
            logger.error("按照id删除留言信息失败，出现的异常为",e.getMessage(), e);
            throw new GoodsException("按照id删除留言信息失败");
        }
    }

    @GetMapping("/test")
    public Message test() throws Exception{
        List<SendMessageDto> sendMessageDtos = new ArrayList<>();
        productMessageService.test(sendMessageDtos);
        return Message.createBySuccess();
    }

}
