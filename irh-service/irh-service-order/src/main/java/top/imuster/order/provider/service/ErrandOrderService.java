package top.imuster.order.provider.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.order.api.pojo.ErrandOrder;

/**
 * ErrandOrderService接口
 * @author 黄明人
 * @since 2020-02-11 17:49:36
 */
public interface ErrandOrderService extends BaseService<ErrandOrder, Long> {

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
     * @param currentUserIdFromCookie
     * @reture: void
     **/
    String receiveOrder(Long id, Long currentUserIdFromCookie) throws JsonProcessingException;

    /**
     * @Author hmr
     * @Description 根据订单编号查看该订单是否有效
     * @Date: 2020/2/12 11:50
     * @param code
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> getOrderStateByCode(String code);
}