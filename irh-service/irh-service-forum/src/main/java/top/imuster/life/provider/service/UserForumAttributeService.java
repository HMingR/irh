package top.imuster.life.provider.service;


import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.pojo.UserForumAttribute;

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
     * @Description 将redis中的热搜储存到mysql中
     * @Date: 2020/2/14 10:18
     * @param
     * @reture: void
     **/
    void transHotTopicFromRedis2DB(Long topic);

    /**
     * @Author hmr
     * @Description 将点赞的总数更新
     * @Date: 2020/2/8 19:36
     * @param
     * @reture: void
     **/
    void transUpCountFromRedis2DB();

    /**
     * @Author hmr
     * @Description 根据id和type获得点赞总数
     * @Date: 2020/2/9 10:34
     * @param id
     * @param type
     * @param upTotalKey
     * @reture: java.lang.Long
     **/
    Long getUpTotalByTypeAndId(Long id, Integer type, String upTotalKey);

    /**
     * @Author hmr
     * @Description 查看用户在forum模块的点赞记录
     * @Date: 2020/2/11 15:49
     * @param page
     * @param currentUserIdFromCookie
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<UserForumAttribute>>
     **/
    Message<Page<UserForumAttribute>> getUpList(Page<UserForumAttribute> page, Long userId);
}