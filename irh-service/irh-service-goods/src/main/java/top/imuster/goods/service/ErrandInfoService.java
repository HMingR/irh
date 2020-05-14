package top.imuster.goods.service;


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
     * @param type
     * @param state
     * @reture: java.util.List<top.imuster.life.api.pojo.ErrandInfo>
     **/
    Message<Page<ErrandInfo>> getListByCondition(Integer pageSize, Integer currentPage, Long userId, Integer state);

    /**
     * @Author hmr
     * @Description 根据id查看当前跑腿是否还能接单
     * @Date: 2020/2/12 11:19
     * @param id
     * @param errandVersion
     * @reture: boolean
     **/
    boolean isAvailable(Long id, Integer errandVersion);

    /**
     * @Author hmr
     * @Description 用户删除自己发布的跑腿
     * @Date: 2020/3/15 15:49
     * @param id
     * @param currentUserIdFromCookie
     * @param version
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> deleteErrandById(Long id, Long currentUserIdFromCookie, Integer version);

    /**
     * @Author hmr
     * @Description 根据id和版本更新
     * @Date: 2020/4/29 11:47
     * @param id
     * @param errandVersion 版本
     * @reture: void
     **/
    boolean updateStateByIdAndVersion(Long id, Integer errandVersion, Integer state);

    /**
     * @Author hmr
     * @Description 发布跑腿
     * @Date: 2020/5/9 14:39
     * @param errandInfo
     * @param currentUserIdFromCookie
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> release(ErrandInfo errandInfo, Long currentUserIdFromCookie);

    /**
     * @Author hmr
     * @Description 根据id获得errand的id
     * @Date: 2020/5/9 16:08
     * @param errandId
     * @reture: top.imuster.life.api.pojo.ErrandInfo
     **/
    ErrandInfo getAddAndPhoneById(Long errandId);

    /**
     * @Author hmr
     * @Description 展示所有可用的跑腿
     * @Date: 2020/5/10 15:42
     * @param pageSize
     * @param currentPage
     * @param type
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.life.api.pojo.ErrandInfo>>
     **/
    Message<Page<ErrandInfo>> listByType(Integer pageSize, Integer currentPage, Integer type);

    /**
     * @Author hmr
     * @Description 根据id获得版本信息
     * @Date: 2020/5/13 19:59
     * @param errandId
     * @reture: java.lang.Integer
     **/
    Integer getErrandVersionById(Long errandId);
}