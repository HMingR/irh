package top.imuster.mall.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.mall.domain.ConsumerInfo;
import top.imuster.mall.service.ConsumerInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: ConsumerController
 * @Description: 会员控制器
 * @author: hmr
 * @date: 2019/11/26 10:37
 */

@RestController
public class ConsumerController {

    @Resource
    private ConsumerInfoService consumerInfoService;

    @GetMapping("/test")
    public String test(){
        try{
            ConsumerInfo consumerInfo = new ConsumerInfo();
            consumerInfo.setAcademyName("信息工程学院");
            consumerInfo.setAge((short)18);
            consumerInfo.setMajorName("软件工程");
            consumerInfo.setPassword("123");
            consumerInfoService.insertEntry(consumerInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "测试";
    }
}
