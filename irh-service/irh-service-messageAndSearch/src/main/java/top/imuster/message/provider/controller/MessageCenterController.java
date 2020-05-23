package top.imuster.message.provider.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.message.pojo.NewsInfo;
import top.imuster.message.provider.service.NewsInfoService;

import javax.annotation.Resource;

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

    @Resource
    NewsInfoService newsInfoService;

    @ApiOperation("分页查询系统通知，未读的排在前面")
    @GetMapping("/systemNews/{pageSize}/{currentPage}")
    public Message<Page<NewsInfo>> systemNews(@PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage){
        if(pageSize < 1 || currentPage < 1) return Message.createByError("参数错误");
        Long userId = getCurrentUserIdFromCookie();
        return newsInfoService.selectSystemNews(pageSize, currentPage, userId);
    }

    /**
     * @Author hmr
     * @Description 留言回复的消息或者评价消息
     * @Date: 2020/5/8 15:19
     * @param
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.message.pojo.NewsInfo>>
     **/
    @GetMapping("/atMe/{pageSize}/{currentPage}")
    @NeedLogin
    public Message<Page<NewsInfo>> atMe(@PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage){
        if(pageSize< 1 || currentPage< 1) return Message.createByError("参数错误");
        return newsInfoService.getAtMeMessage(getCurrentUserIdFromCookie(), pageSize, currentPage);
    }


    @ApiOperation("更新消息状态,type为10-删除 20-已读")
    @GetMapping("/{type}/{id}")
    public Message<String> updateById(@PathVariable("id") Long id, @PathVariable("type") Integer type){
        if(type != 20 && type != 10) return Message.createByError("参数错误");
        return newsInfoService.updateMessageState(id, type, getCurrentUserIdFromCookie());
    }

    /**
     * @Author hmr
     * @Description 根据sourceId标记已读
     * @Date: 2020/5/22 9:53
     * @param sourceId
     * @param type
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @DeleteMapping("/{type}/{sourceId}")
    public Message<String> updateBySourceId(@PathVariable("sourceId") Long sourceId, @PathVariable("type") Integer type){
        return newsInfoService.updateMessageStateBySourceId(type, sourceId);
    }

    /**
     * @Author hmr
     * @Description 获得系统通知和消息通知未读的数量   格式为:系统通知|atMe
     * @Date: 2020/5/22 10:03
     * @param
     * @reture: top.imuster.common.base.wrapper.Message<java.util.Map<java.lang.String,java.lang.Integer>>
     **/
    @GetMapping("/unread")
    public Message<String> getUnreadTotal(){
        Long userId = getCurrentUserIdFromCookie();
        return newsInfoService.getUnreadTotal(userId);
    }

    /**
     * @Author hmr
     * @Description 根据type更新所有的状态  type取值  1-系统通知   2-at我的
     * @Date: 2020/5/22 10:26
     * @param
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @GetMapping("/readAll/{type}")
    public Message<String> readAll(@PathVariable("type") Integer type){
        if(type != 1 && type != 2) return Message.createByError("参数错误");
        Long userId = getCurrentUserIdFromCookie();
        return newsInfoService.readAll(type, userId);
    }
}

