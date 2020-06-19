package top.imuster.order.provider.web.controller;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.controller.BaseController;
import top.imuster.order.api.pojo.ErrandOrderEvaluateInfo;
import top.imuster.order.provider.service.ErrandOrderEvaluateInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: ErrandOrderEvaluateController
 * @Description: ErrandOrderEvaluateController 跑腿评价表
 * @author: hmr
 * @date: 2020/6/19 19:57
 */
@RestController
@RequestMapping("/errand/evaluate")
public class ErrandOrderEvaluateController extends BaseController {

    @Resource
    ErrandOrderEvaluateInfoService errandOrderEvaluateInfoService;

    @PostMapping
    public Message<String> write(@RequestBody ErrandOrderEvaluateInfo errandOrderEvaluateInfo){
        Long userId = getCurrentUserIdFromCookie();
        errandOrderEvaluateInfo.setUserId(userId);
        return errandOrderEvaluateInfoService.writeEvaluate(errandOrderEvaluateInfo);
    }

    /**
     * @Author hmr
     * @Description 根据id查看详情
     * @Date: 2020/6/19 20:51
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.order.api.pojo.ErrandOrderEvaluateInfo>
     **/
    @GetMapping("/detail/{id}")
    public Message<ErrandOrderEvaluateInfo> getDetailById(@PathVariable("id") Long id){
        ErrandOrderEvaluateInfo evaluateInfo = new ErrandOrderEvaluateInfo();
        evaluateInfo.setState(2);
        evaluateInfo.setId(id);
        List<ErrandOrderEvaluateInfo> infos = errandOrderEvaluateInfoService.selectEntryList(evaluateInfo);
        if(CollectionUtils.isEmpty(infos)) return Message.createByError("未找到相关信息");
        return Message.createBySuccess(infos.get(0));
    }

    /**
     * @Author hmr
     * @Description 根据orderId查看评价信息
     * @Date: 2020/6/19 20:33
     * @param orderId
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.order.api.pojo.ErrandOrderEvaluateInfo>
     **/
    @GetMapping("/{orderId}")
    public Message<ErrandOrderEvaluateInfo> getDetailByOrderId(@PathVariable("orderId") Long orderId){
        ErrandOrderEvaluateInfo evaluateInfo = new ErrandOrderEvaluateInfo();
        evaluateInfo.setErrandOrderId(orderId);
        evaluateInfo.setState(2);
        List<ErrandOrderEvaluateInfo> infos = errandOrderEvaluateInfoService.selectEntryList(evaluateInfo);
        if(CollectionUtils.isEmpty(infos)) return Message.createByError("未找到相关信息");
        return Message.createBySuccess(infos.get(0));
    }
}
