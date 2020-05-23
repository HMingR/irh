package top.imuster.order.provider.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.order.api.pojo.ErrandOrderInfo;

/**
 * ErrandOrderService接口
 * @author 黄明人
 * @since 2020-02-11 17:49:36
 */
public interface ErrandOrderService extends BaseService<ErrandOrderInfo, Long> {

    /**
     * @Author hmr
     * @Description 删除跑腿服务的订单
     * @Date: 2020/2/12 10:09
     * @param id 订单编号
     * @param userId 当前登录者
     * @param type
     * @reture: void
     *
     * @return*/
    Message<String> delete(Long id, Long userId, Integer type);

    /**
     * @Author hmr
     * @Description 下单操作,使用rabbitmq
     * @Date: 2020/2/12 11:00
     * @param id
     * @param version
     * @param currentUserIdFromCookie
     * @reture: void
     **/
    Message<String> receiveOrder(Long id, Integer version, Long currentUserIdFromCookie) throws JsonProcessingException;

    /**
     * @Author hmr
     * @Description 根据订单编号查看该订单是否有效
     * @Date: 2020/2/12 11:50
     * @param code
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> getOrderStateByCode(String code);

    /**
     * @Author hmr
     * @Description 将订单的状态b
     * @Date: \ 11:10
     * @param order
     * @reture: boolean
     **/
    ErrandOrderInfo acceptErrand(ErrandOrderInfo order);

    /**
     * @Author hmr
     * @Description 根据订单id查看详情
     * @Date: 2020/5/9 16:01
     * @param userId
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.order.api.pojo.ErrandOrderInfo>
     **/
    Message<ErrandOrderInfo> getOrderInfoById(Long userId, Long id);

    /**
     * @Author hmr
     * @Description 查看自己的订单
     * @Date: 2020/5/10 19:59
     * @param pageSize
     * @param currentPage
     * @param type
     * @param state
     * @param currentUserIdFromCookie
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.order.api.pojo.ErrandOrderInfo>>
     **/
    Message<Page<ErrandOrderInfo>> list(Integer pageSize, Integer currentPage, Integer type, Integer state, Long userId);

    /**
     * @Author hmr
     * @Description 发布者完成订单
     * @Date: 2020/5/13 19:48
     * @param userId
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> finishOrder(Long userId, Long id);
}