package top.imuster.forum.provider.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.BrowserTimesAnnotation;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.dto.UserDto;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.forum.api.pojo.ArticleInfo;
import top.imuster.forum.provider.exception.ForumException;
import top.imuster.forum.provider.service.ArticleInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: ForumController
 * @Description: ForumController
 * @author: hmr
 * @date: 2020/2/1 15:09
 */
@RestController
@RequestMapping("/forum/article")
public class ArticleInfoController extends BaseController {

    @Resource
    ArticleInfoService articleInfoService;

    /**
     * @Author hmr
     * @Description 用户发布帖子
     * @Date: 2020/2/1 19:32
     * @param articleInfo
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    // todo 用户可能上传封面,需要保存到ftp服务器
    @ApiOperation("用户发布帖子")
    @NeedLogin
    @PostMapping
    public Message<String> releaseArticle(@RequestBody ArticleInfo articleInfo){
        UserDto currentUser = getCurrentUserFromCookie();
        if(currentUser == null){
            throw new ForumException("用户身份过期,请重新登录");
        }
        articleInfoService.release(currentUser, articleInfo);
        return Message.createBySuccess();
    }

    /**
     * @Author hmr
     * @Description 用户删除自己的帖子
     * @Date: 2020/2/1 19:34
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation("用户删除自己的帖子")
    @NeedLogin
    @DeleteMapping("/{id}")
    public Message<String> deleteArticle(@PathVariable("id")Long id){
        ArticleInfo condition = new ArticleInfo();
        condition.setId(id);
        condition.setState(1);
        articleInfoService.updateByKey(condition);
        return Message.createBySuccess();
    }

    /**
     * @Author hmr
     * @Description 用户查看自己发布的帖子,按照发布时间降序排列
     * @Date: 2020/2/1 19:40
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.forum.api.pojo.ArticleInfo>>
     **/
    @ApiOperation(("用户条件查看自己发布的帖子(列表,没有帖子的内容和图片),按照发布时间降序排列"))
    @PostMapping("/list")
    @NeedLogin
    public Message<Page<ArticleInfo>> list(@RequestBody Page<ArticleInfo> page){
        ArticleInfo condition = page.getSearchCondition();
        if(condition == null){
            condition = new ArticleInfo();
        }
        condition.setUserId(getcurrentUserIdFromCookie());
        condition.setOrderField("create_time");
        condition.setOrderFieldType("DESC");
        condition.setState(2);
        List<ArticleInfo> list = articleInfoService.list(page);
        page.setResult(list);
        return Message.createBySuccess(page);
    }

    @ApiOperation("根据id查看帖子的所有信息")
    @GetMapping("/{id}")
    @BrowserTimesAnnotation(browserType = BrowserType.FORUM)
    public Message<ArticleInfo> getArticleInfoById(@PathVariable("id") Long id){
        ArticleInfo articleInfo = articleInfoService.selectEntryList(id).get(0);
        return Message.createBySuccess(articleInfo);
    }
}
