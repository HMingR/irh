package top.imuster.life.provider.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.provider.service.ErrandOrderService;

import javax.annotation.Resource;

/**
 * @ClassName: ErrandOrderController
 * @Description: 跑腿订单controller
 * @author: hmr
 * @date: 2020/2/11 20:14
 */
@RestController
@RequestMapping("/order")
public class ErrandOrderController {

    @Resource
    ErrandOrderService errandOrderService;

    @ApiOperation("接单")
    @GetMapping("/{id}")
    public Message<String> orderReceive(@PathVariable("id") Long id){
        return null;
    }
}
