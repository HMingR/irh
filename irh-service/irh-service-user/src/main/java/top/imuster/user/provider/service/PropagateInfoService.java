package top.imuster.user.provider.service;


import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.user.api.pojo.PropagateInfo;

import java.util.List;

/**
 * PropagateInfoService接口
 * @author 黄明人
 * @since 2020-05-16 10:05:59
 */
public interface PropagateInfoService extends BaseService<PropagateInfo, Long> {

    /**
     * @Author hmr
     * @Description 发布广告或者通知
     * @Date: 2020/5/16 10:16
     * @param propagateInfo
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> release(PropagateInfo propagateInfo);

    /**
     * @Author hmr
     * @Description 获得简略信息列表
     * @Date: 2020/5/25 10:22
     * @param pageSize
     * @param currentPage
     * @param type
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.user.api.pojo.PropagateInfo>>
     **/
    Message<Page<PropagateInfo>> getBriefList(Integer pageSize, Integer currentPage, Integer type);

    /**
     * @Author hmr
     * @Description 执行attribute的统计任务
     * @Date: 2020/5/31 15:07
     * @param
     * @reture: void
     **/
    void trans2DB();

    /**
     * @Author hmr
     * @Description 保存浏览总数到db
     * @Date: 2020/6/1 20:38
     * @param res
     * @reture: java.lang.Integer
     **/
    Integer saveBrowseTimes2DB(List<BrowserTimesDto> res);
}