package top.imuster.life.provider.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.pojo.ArticleInfo;
import top.imuster.life.api.pojo.ForumHotTopicInfo;
import top.imuster.life.provider.service.ForumHotTopicService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: ForumHotTopicController
 * @Description: 文章热搜控制器
 * @author: hmr
 * @date: 2020/2/14 12:16
 */
@RestController
@RequestMapping("/hot")
@Api
public class ForumHotTopicController {

    @Value("${hot.topic.total}")
    private int topic;

    @Resource
    ForumHotTopicService forumHotTopicService;

    @ApiOperation("累计总榜")
    @GetMapping("/total/{pageSize}/{currentPage}")
    public Message<List<ForumHotTopicInfo>> totalHotTopicList(@PathVariable("pageSize") Long pageSize, @PathVariable("currentPage") Long currentPage){
        return forumHotTopicService.totalHotTopicList(pageSize, currentPage);
    }

    @ApiOperation("实时总榜")
    @GetMapping("/current/{pageSize}/{currentPage}")
    public Message<List<ArticleInfo>> currentHotTopicList(@PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage){
        return forumHotTopicService.currentHotTopicList(topic, pageSize, currentPage);
    }
}
