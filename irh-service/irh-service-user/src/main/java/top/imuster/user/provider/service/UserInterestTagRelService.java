package top.imuster.user.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.user.api.pojo.UserInterestTagRel;

import java.util.List;

/**
 * ConsumerInterestTagRelService接口
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
public interface UserInterestTagRelService extends BaseService<UserInterestTagRel, Long> {

    /**
     * @Author hmr
     * @Description 根据标签id获得有多少个人使用了该标签
     * @Date: 2020/2/6 14:34
     * @param id
     * @reture: void
     **/
    Long getTagCountByTagId(Long id);

    /**
     * @Author hmr
     * @Description 根据用户id获得用户关注的tag id
     * @Date: 2020/3/28 16:13
     * @param userId
     * @reture: java.util.List<java.lang.Long>
     **/
    List<Long> getUserTagByUserId(Long userId);
}