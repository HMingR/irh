package top.imuster.user.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.user.api.pojo.UserInterestTagRel;

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
}