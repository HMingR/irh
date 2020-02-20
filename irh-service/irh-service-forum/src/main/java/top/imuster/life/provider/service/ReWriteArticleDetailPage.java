package top.imuster.life.provider.service;

import top.imuster.life.api.pojo.ArticleInfo;

/**
 * @ClassName: ReWriteArticleDetailPage
 * @Description: 实现文章的页面静态化接口
 * @author: hmr
 * @date: 2020/2/9 15:31
 */
public interface ReWriteArticleDetailPage {

    /**
     * @Author hmr
     * @Description 根据发布的信息生成静态页面
     * @Date: 2020/2/9 15:32
     * @param articleInfo
     * @reture: void
     **/
    void createHtml(ArticleInfo articleInfo);
}
