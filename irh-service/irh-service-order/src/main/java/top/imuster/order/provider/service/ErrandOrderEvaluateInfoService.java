package top.imuster.order.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.order.api.pojo.ErrandOrderEvaluateInfo;

/**
 * ErrandOrderEvaluateInfoService接口
 * @author 黄明人
 * @since 2020-06-19 19:54:16
 */
public interface ErrandOrderEvaluateInfoService extends BaseService<ErrandOrderEvaluateInfo, Long> {

    /**
     * @Author hmr
     * @Description 发布者写评价
     * @Date: 2020/6/19 19:59
     * @param errandOrderEvaluateInfo
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> writeEvaluate(ErrandOrderEvaluateInfo errandOrderEvaluateInfo);

    /**
     * @Author hmr
     * @Description
     * @Date: 2020/6/19 20:21
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.order.api.pojo.ErrandOrderEvaluateInfo>
     **/
    Message<ErrandOrderEvaluateInfo> getDetailById(Long id);
}