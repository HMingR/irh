package top.imuster.message.provider.controller;

import cn.hutool.core.lang.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public Message systemNews(@PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage, Page<NewsInfo> page){
        NewsInfo newsInfo = new NewsInfo();
        newsInfo.setReceiverId(getCurrentUserIdFromCookie());
        newsInfo.setOrderField("state");
        newsInfo.setOrderFieldType("DESC");
        page.setCurrentPage(currentPage < 1 ? 1 : currentPage);
        page.setPageSize(pageSize);
        page = newsInfoService.selectPage(newsInfo, page);
        return Message.createBySuccess(page);
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
        return newsInfoService.getAtMeMessage(getCurrentUserIdFromCookie(), pageSize, currentPage);
    }


    @ApiOperation("更新消息状态,type为10-删除 20-已读")
    @GetMapping("/{type}/{id}")
    public Message<String> updateById(@PathVariable("id") Long id, @PathVariable("type") Integer type){
        Assert.isTrue(type == 10 || type == 20, "参数异常");
        return newsInfoService.updateMessageState(id, type, getCurrentUserIdFromCookie());
    }


}
