package top.imuster.forum.provider.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.BrowserTimesAnnotation;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.dto.UserDto;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.file.api.service.FileServiceFeignApi;
import top.imuster.forum.api.pojo.ArticleInfo;
import top.imuster.forum.provider.service.ArticleInfoService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ForumController
 * @Description: ForumController
 * @author: hmr
 * @date: 2020/2/1 15:09
 */
@RestController
@RequestMapping("/forum/article")
@Api("论坛帖子控制器")
public class ArticleInfoController extends BaseController {

    private List<String> types;

    @Resource
    ArticleInfoService articleInfoService;

    @Autowired
    FileServiceFeignApi fileServiceFeignApi;

    @PostConstruct
    private void setTypes(){
        types = new ArrayList<>();
        types.add("jpg");
        types.add("png");
    }

    /**
     * @Author hmr
     * @Description 用户发布帖子
     * @Date: 2020/2/1 19:32
     * @param articleInfo
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation("用户发布帖子")
    @NeedLogin
    @PostMapping
    public Message<String> releaseArticle(@RequestParam("file")MultipartFile file, ArticleInfo articleInfo) throws Exception {
        if(!file.isEmpty()){
            int last = file.getOriginalFilename().length();
            String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), last);
            if(!types.contains(fileType)){
                return Message.createByError("图片格式不正确,请更换图片格式");
            }
            String url = fileServiceFeignApi.upload(file).getText();
            articleInfo.setMainPicture(url);
        }
        UserDto currentUser = getCurrentUserFromCookie();
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
    @ApiOperation(("用户条件查看自己发布的帖子(列表,没有帖子的内容),按照发布时间降序排列"))
    @PostMapping("/list")
    @NeedLogin
    public Message<Page<ArticleInfo>> list(@RequestBody Page<ArticleInfo> page){
        ArticleInfo condition = page.getSearchCondition();
        if(condition == null){
            condition = new ArticleInfo();
        }
        condition.setUserId(getCurrentUserIdFromCookie());
        condition.setOrderField("create_time");
        condition.setOrderFieldType("DESC");
        condition.setState(2);
        List<ArticleInfo> list = articleInfoService.list(page);
        page.setResult(list);
        return Message.createBySuccess(page);
    }

    /**
     * @Author hmr
     * @Description 根据id查看帖子的详细信息(包括一级留言)，
     * @Date: 2020/2/2 10:56
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.forum.api.pojo.ArticleInfo>
     **/
    @ApiOperation("根据id查看帖子的所有信息")
    @GetMapping("/{id}")
    @BrowserTimesAnnotation(browserType = BrowserType.FORUM)
    public Message<ArticleInfo> getArticleInfoById(@PathVariable("id") Long id){
        ArticleInfo articleDetail = articleInfoService.getArticleDetailById(id);
        return Message.createBySuccess(articleDetail);
    }

    /**
     * @Author hmr
     * @Description 根据id获得文章的简略信息
     * @Date: 2020/2/11 16:21
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.forum.api.pojo.ArticleInfo>
     **/
    @ApiOperation("根据id获得文章的简略信息(文章标题,图片url,留言总数,点赞总数)")
    @GetMapping("/brief/{id}}")
    public Message<ArticleInfo> getTitleAndUrl(@PathVariable("id") Long id){
        ArticleInfo res = articleInfoService.getBriefById(id);
        return Message.createBySuccess(res);
    }
}
