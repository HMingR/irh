package top.imuster.message.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.forum.api.pojo.ArticleInfo;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.message.dto.ForumSearchParam;
import top.imuster.message.dto.GoodsSearchParam;
import top.imuster.message.provider.service.impl.ForumSearchService;
import top.imuster.message.provider.service.impl.GoodsSearchService;

/**
 * @ClassName: GoodsSearchController
 * @Description: GoodsSearchController
 * @author: hmr
 * @date: 2020/1/31 10:36
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    GoodsSearchService goodsSearchService;

    @Autowired
    ForumSearchService forumSearchService;


    @GetMapping(value="/goods/list/{page}/{size}")
    public Message<Page<ProductInfo>> list(@PathVariable("page") int page, @PathVariable("type")Integer type, @PathVariable("size") int size, GoodsSearchParam courseSearchParam) {
        return goodsSearchService.goodsList(page,size,courseSearchParam);
    }

    @GetMapping(value="/forum/list/{page}/{size}")
    public Message<Page<ArticleInfo>> forumList(@PathVariable("page") int page, @PathVariable("size") int size, ForumSearchParam forumSearchParam) {
        return forumSearchService.forumList(page,size,forumSearchParam);
    }

}
