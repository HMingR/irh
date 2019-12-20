package top.imuster.order.provider.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.wrapper.Message;

/**
 * @ClassName: OrderController
 * @Description: TODO
 * @author: lpf
 * @date: 2019/12/18 18:03
 */
@Controller
@RequestMapping
public class OrderController extends BaseController{

    @GetMapping("test")
    public Message<String> test() {
        System.out.println("testing................");
        return Message.createBySuccess("测试成功", "测试数据");
    }
}
