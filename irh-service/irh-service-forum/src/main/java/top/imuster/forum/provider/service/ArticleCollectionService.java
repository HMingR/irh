package top.imuster.forum.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.forum.api.pojo.ArticleCollection;

/**
 * ArticleCollectionService接口
 * @author 黄明人
 * @since 2020-02-08 15:27:10
 */
public interface ArticleCollectionService extends BaseService<ArticleCollection, Long> {

    /**
     * @Author hmr
     * @Description 收藏文章
     * @Date: 2020/2/9 10:59
     * @param userId
     * @param id
     * @reture: void
     **/
    Message<String> collect(Long userId, Long id);

    /**
     * @Author hmr
     * @Description 取消收藏
     * @Date: 2020/2/9 11:04
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> unCollect(Long id);

    /**
     * @Author hmr
     * @Description 从reids中获得收藏信息,并更新到每个文章表中
     * @Date: 2020/2/9 11:20
     * @param
     * @reture: void
     **/
    void transCollectCountFromRedis2Db();
}