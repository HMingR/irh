package top.imuster.life.provider.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.forum.api.pojo.ArticleInfo;
import top.imuster.forum.api.pojo.ForumHotTopic;
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
public class ForumHotTopicController {

    @Value("${hot.topic}")
    private int topic;

    @Resource
    ForumHotTopicService forumHotTopicService;

    @ApiOperation("累计总榜")
    @GetMapping("/total")
    public Message<List<ForumHotTopic>> totalHotTopicList(){
        return forumHotTopicService.totalHotTopicList(topic);
    }

    @ApiOperation("实时总榜")
    @GetMapping
    public Message<List<ArticleInfo>> currentHotTopicList(){
        return forumHotTopicService.currentHotTopicList(topic);
    }
}
