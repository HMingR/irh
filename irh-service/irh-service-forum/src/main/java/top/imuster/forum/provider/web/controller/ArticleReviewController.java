package top.imuster.forum.provider.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.controller.BaseController;
import top.imuster.forum.api.pojo.ArticleReview;
import top.imuster.forum.provider.service.ArticleReviewService;

import javax.annotation.Resource;

/**
 * @ClassName: ArticleReviewController
 * @Description: ArticleReviewController
 * @author: hmr
 * @date: 2020/2/1 19:59
 */
@RestController
@RequestMapping("/forum/review")
public class ArticleReviewController extends BaseController {

    @Resource
    ArticleReviewService articleReviewService;

    @ApiOperation("根据一级留言id获得其对应的所有留言或回复")
    @GetMapping("/{id}")
    public Message<ArticleReview> reviewDetails(@PathVariable("id") Long id){
        return null;
    }

}
