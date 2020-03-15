package top.imuster.life.provider.service;


import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.pojo.ErrandInfo;

/**
 * ErrandInfoService接口
 * @author 黄明人
 * @since 2020-02-11 17:49:35
 */
public interface ErrandInfoService extends BaseService<ErrandInfo, Long> {

    /**
     * @Author hmr
     * @Description 查看自己发布的跑腿服务列表
     * @Date: 2020/2/11 19:47
     * @param searchCondition
     * @reture: java.util.List<top.imuster.life.api.pojo.ErrandInfo>
     **/
    Message<Page<ErrandInfo>> getListByCondition(Page<ErrandInfo> page, Long userId);

    /**
     * @Author hmr
     * @Description 根据id查看当前跑腿是否还能接单
     * @Date: 2020/2/12 11:19
     * @param id
     * @reture: boolean
     **/
    boolean isAvailable(Long id);

    /**
     * @Author hmr
     * @Description 用户删除自己发布的跑腿
     * @Date: 2020/3/15 15:49
     * @param id
     * @param currentUserIdFromCookie
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> deleteErrandById(Long id, Long currentUserIdFromCookie);
}