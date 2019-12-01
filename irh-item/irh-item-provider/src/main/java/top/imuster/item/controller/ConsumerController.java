package top.imuster.item.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.Controller.BaseController;
import top.imuster.item.domain.ConsumerInfo;
import top.imuster.item.service.ConsumerInfoService;
import top.imuster.wrapper.Message;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: ConsumerController
 * @Description: 会员控制器
 * @author: hmr
 * @date: 2019/11/26 10:37
 */

@RestController
public class ConsumerController extends BaseController {

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

    @GetMapping("/list")
    Message testList(){
        try{
            List<ConsumerInfo> consumerInfos = consumerInfoService.selectEntryList(new ConsumerInfo());
            return Message.createBySuccess(consumerInfos);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Message.createByError("失败");
    }
}
