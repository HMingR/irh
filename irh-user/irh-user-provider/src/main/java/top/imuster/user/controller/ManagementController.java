package top.imuster.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.controller.BaseController;
import top.imuster.user.pojo.ManagementInfo;
import top.imuster.user.service.ManagementInfoService;
import top.imuster.wrapper.Message;

import javax.annotation.Resource;

/**
 * @ClassName: ManagementController
 * @Description: 管理人员的controller
 * @author: hmr
 * @date: 2019/12/1 18:59
 */
@RestController
public class ManagementController extends BaseController {

    @Resource
    ManagementInfoService managementInfoService;

    @GetMapping("/management")
    public Message test(){
        try{
           /* String encode = passwordEncoder.encode("123456");
            this.logger.info("加密之后的密码是:" + encode);
            this.logger.info("对比:" + passwordEncoder.matches("123456", "$2a$10$QDGDZDkNan7AKACYqjMoxOrJoHKs4HfzxfFQStzyQtfuyRMMZUsu2"));
            ManagementInfo managementInfo = new ManagementInfo("hmr", "123456", null);
            managementInfo.setName("hmr");
            managementInfo.setState(2);
            return Message.createBySuccess(managementInfoService.getManagementRoleByCondition(managementInfo));*/
        }catch (Exception e){
            e.printStackTrace();
        }
        return Message.createByError();
    }

    @GetMapping("/login")
    public Message managementLogin(){
        try{
            ManagementInfo managementInfo = new ManagementInfo("hmr", "123456", null);
            managementInfo.setName("hmr");
            managementInfo.setState(2);
            return Message.createBySuccess(managementInfoService.getManagementRoleByCondition(managementInfo));
        }catch (Exception e){
            logger.info("登录失败" + e);
        }
        return null;
    }

}
