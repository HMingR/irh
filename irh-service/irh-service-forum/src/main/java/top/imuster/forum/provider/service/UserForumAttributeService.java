package top.imuster.forum.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.forum.api.pojo.UserForumAttribute;

/**
 * UserForumAttributeService接口
 * @author 黄明人
 * @since 2020-02-08 15:27:10
 */
public interface UserForumAttributeService extends BaseService<UserForumAttribute, Long> {

    /**
     * @Author hmr
     * @Description 将redis中的点赞记录保存到数据库中
     * @Date: 2020/2/8 19:36
     * @param
     * @reture: void
     **/
    void transUpFromRedis2DB();

    /**
     * @Author hmr
     * @Description 将点赞的总数更新
     * @Date: 2020/2/8 19:36
     * @param
     * @reture: void
     **/
    void transUpCountFromRedis2DB();
}