package top.imuster.user.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: CustomerController
 * @Description: customer的控制器
 * @author: hmr
 * @date: 2019/12/18 19:11
 */
@RestController
@RequestMapping("/user")
public class CustomerController {

    @GetMapping("/test")
    public void testFeign(){
        System.out.println("asdf");
    }
}
