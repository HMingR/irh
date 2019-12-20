package top.imuster.order.provider.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.imuster.common.base.controller.BaseController;

/**
 * @ClassName: OrderController
 * @Description: TODO
 * @author: lpf
 * @date: 2019/12/18 18:03
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController{

    @GetMapping("/test")
    public String test() {

        return "test";
    }
}
