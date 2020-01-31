package top.imuster.message.provider.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.SendMessageDto;
import top.imuster.common.core.enums.MqTypeEnum;
import top.imuster.common.core.exception.GlobalException;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.goods.api.pojo.ProductEvaluateInfo;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.api.service.GoodsServiceFeignApi;
import top.imuster.message.pojo.NewsInfo;
import top.imuster.message.provider.service.NewsInfoService;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.api.service.OrderServiceFeignApi;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;

/**
 * @ClassName: MessageCenterController
 * @Description: 消息中心控制器
 * @author: hmr
 * @date: 2020/1/17 18:03
 */
@RestController
@RequestMapping("/msg")
@Api("消息中心控制器")
public class MessageCenterController extends BaseController {

    @Autowired
    JavaMailSenderImpl mailSender;

    @Autowired
    GenerateSendMessageService generateSendMessageService;

    @Resource
    NewsInfoService newsInfoService;

    @Autowired
    GoodsServiceFeignApi goodsServiceFeignApi;

    @Autowired
    OrderServiceFeignApi orderServiceFeignApi;

    @ApiOperation("分页查询消息，未读的排在前面")
    @GetMapping
    public Message list(HttpServletRequest request, Page<NewsInfo> page) throws Exception{
        NewsInfo newsInfo = new NewsInfo();
        Long userId = getIdByToken(request);
        newsInfo.setReceiverId(userId);
        newsInfo.setOrderField("state");
        newsInfo.setOrderFieldType("DESC");
        Page<NewsInfo> newsInfoPage = newsInfoService.selectPage(newsInfo, page);
        return Message.createBySuccess(newsInfoPage);
    }


    @ApiOperation("更新消息状态,type为10-删除 20-已读")
    @GetMapping("/type/{id}")
    public Message updateById(@PathVariable("id") Long id, @PathVariable("type") Integer type){
        NewsInfo newsInfo = new NewsInfo();
        newsInfo.setId(id);
        newsInfo.setState(type);
        newsInfoService.updateByKey(newsInfo);
        return Message.createBySuccess();
    }

    @ApiOperation("根据消息的类型获得定位到消息的具体位置,根据不同的type有不同的响实体类信息")
    @GetMapping("/{newsType}/{targetId}")
    public Message newsDetail(@ApiParam("消息类型 10-订单(响应OrderInfo)  20-商品留言(ProductMessage)  30-商品评价(ProductEvaluate)") @PathVariable("newsType")Long newsType, @PathVariable("targetId")Long targetId){
        if(newsType == 10){
            OrderInfo orderById = orderServiceFeignApi.getOrderById(targetId);
            return Message.createBySuccess(orderById);
        }else if(newsType == 20){
            ProductInfo productInfo = goodsServiceFeignApi.getProductInfoByProductMessage(targetId);
            return Message.createBySuccess(productInfo);
        }else if(newsType == 30){
            ProductEvaluateInfo info = goodsServiceFeignApi.getProductEvaluateInfoByEvaluateId(targetId);
            return Message.createBySuccess(info);
        }
        throw new GlobalException("参数错误,请刷新后重试");
    }

}
