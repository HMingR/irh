package top.imuster.user.provider.service;


import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.user.api.pojo.InterestTagInfo;

import java.util.List;

/**
 * InterestTagInfoService接口
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
public interface InterestTagInfoService extends BaseService<InterestTagInfo, Long> {

    /**
     * @Author hmr
     * @Description 获得有效的兴趣标签,按照使用的多少排序
     * @Date: 2020/2/6 12:43
     * @param
     * @reture: top.imuster.user.api.pojo.InterestTagInfo
     **/
    Page<InterestTagInfo> list(Page<InterestTagInfo> page);

    /**
     * @Author hmr
     * @Description 根据id删除兴趣标签
     * @Date: 2020/2/6 12:56
     * @param id
     * @reture: void
     **/
    void deleteById(Long id);

    /**
     * @Author hmr
     * @Description 根据id获得兴趣标签的名字
     * @Date: 2020/2/6 17:39
     * @param id
     * @reture: java.lang.String
     **/
    String getTagNameById(Long id);

    /**
     * @Author hmr
     * @Description 根据用户id获得用户选择的标签
     * @Date: 2020/3/28 16:07
     * @param userId
     * @reture: top.imuster.common.base.wrapper.Message<java.util.List<top.imuster.user.api.pojo.InterestTagInfo>>
     **/
    Message<List<Long>> getUserTagByUserId(Long userId);
}