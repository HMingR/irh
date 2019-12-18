package top.imuster.goods.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.imuster.common.base.controller.BaseController;

/**
 * @ClassName: ProductController
 * @Description: 处理商品的controller
 * @author: hmr
 * @date: 2019/12/1 14:53
 */
@Controller
@RequestMapping("product")
public class ProductController extends BaseController {

    @GetMapping("test")
    public String test() {

        return "test";
    }

}
