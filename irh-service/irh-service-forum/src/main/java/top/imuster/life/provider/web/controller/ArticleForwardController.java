package top.imuster.life.provider.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.BrowserTimesAnnotation;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.life.api.pojo.ArticleForwardInfo;
import top.imuster.life.provider.service.ArticleForwardInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: ArticleForwardController
 * @Description: 文章转发表
 * @author: hmr
 * @date: 2020/2/21 17:27
 */
@RestController
@RequestMapping("/forum/forward")
@Api
public class ArticleForwardController extends BaseController {

    @Resource
    private ArticleForwardInfoService articleForwardInfoService;

    @ApiOperation("查看自己的转发记录")
    @NeedLogin
    @GetMapping("/list/{currentPage}")
    public Message<Page<ArticleForwardInfo>> list(@PathVariable("currentPage") Integer currentPage){
        return articleForwardInfoService.getPageByUserId(getCurrentUserIdFromCookie(), currentPage);
    }

    @ApiOperation("转发")
    @NeedLogin
    @BrowserTimesAnnotation(browserType = BrowserType.FORUM, value = "p0.id")
    @PostMapping
    public Message<String> forward(@RequestBody ArticleForwardInfo articleForwardInfo){
        Long userId = getCurrentUserIdFromCookie();
        articleForwardInfo.setUserId(userId);
        return articleForwardInfoService.forward(articleForwardInfo);
    }

    @ApiOperation("删除转发的内容")
    @NeedLogin
    @DeleteMapping("/{id}")
    public Message<String> delete(@PathVariable("id") Long id){
        ArticleForwardInfo articleForwardInfo = new ArticleForwardInfo();
        articleForwardInfo.setUserId(getCurrentUserIdFromCookie());
        articleForwardInfo.setId(id);
        articleForwardInfo.setState(2);
        articleForwardInfoService.updateByKey(articleForwardInfo);
        return Message.createBySuccess();
    }

}
