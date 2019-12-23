package top.imuster.goods.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.wrapper.Message;

/**
 * @ClassName: ProductController
 * @Description: 处理商品的controller
 * @author: hmr
 * @date: 2019/12/1 14:53
 */
@RestController
@RequestMapping("product")
public class ProductController extends BaseController {

    @GetMapping("test")
    public Message<String> test() {
        System.out.println("testing................");
        return Message.createBySuccess("测试Product成功", "测试数据");
    }

}
