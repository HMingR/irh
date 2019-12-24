package top.imuster.user.provider.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.user.api.pojo.ManagementInfo;
import top.imuster.user.api.pojo.ManagementRoleRel;
import top.imuster.user.provider.service.ManagementInfoService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ManagementController
 * @Description: 管理人员的controller
 * @author: hmr
 * @date: 2019/12/1 18:59
 */
@RestController
@RequestMapping("/management")
@Api(tags = "ManagementController", description = "后台管理页面")
public class UserController extends BaseController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Resource
    ManagementInfoService managementInfoService;

    @ApiOperation("查看所有的管理员")
    @GetMapping("/list")
    @NeedLogin(validate = true)
    public Message managementList(/*Page<ManagementInfo> page*/){
        try{
            //Page<ManagementInfo> managementInfoPage = managementInfoService.selectPage(new ManagementInfo(), page);
            List<ManagementInfo> managementInfos = managementInfoService.selectEntryList(new ManagementInfo());

            /*if(null != managementInfoPage){
                //将密码全都设置成空
                managementInfoPage.getResult().stream().forEach(managementInfo -> managementInfo.setPassword(""));
            }*/
            return Message.createBySuccess(managementInfos);
        }catch (Exception e){
            logger.error("查看管理员列表失败:{}", e.getMessage());
            return Message.createByError();
        }
    }

    /**
     * @Description: 添加管理员
     * @Author: hmr
     * @Date: 2019/12/19 11:14
     * @param managementInfo
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("添加管理员")
    @PostMapping("/")
    public Message addManagement(@RequestBody ManagementInfo managementInfo){
        try{
            String real_pwd = passwordEncoder.encode(managementInfo.getPassword());
            managementInfo.setPassword(real_pwd);
            managementInfoService.insertEntry(managementInfo);
            return Message.createBySuccess();
        }catch (Exception e){
            logger.error(GlobalConstant.getErrorLog("添加管理员"), e.getMessage(), managementInfo);
            return Message.createByError();
        }
    }

    @ApiOperation("修改管理员信息(修改基本信息，包括删除)")
    @PutMapping("/")
    public Message editManagement(@Validated(value = ValidateGroup.editGroup.class) @RequestBody ManagementInfo managementInfo, BindingResult bindingResult){
        validData(bindingResult);
        try{
            managementInfoService.updateByKey(managementInfo);
            return Message.createBySuccess();
        }catch (Exception e){
            logger.error(GlobalConstant.getErrorLog("修改管理员信息"), e.getMessage(), managementInfo);
            return Message.createByError();
        }
    }


    @ApiOperation("登录成功返回token")
    @PostMapping("/login")
    public Message managementLogin(@Validated(ValidateGroup.loginGroup.class) @RequestBody ManagementInfo managementInfo, BindingResult result){
        validData(result);
        try{
            String token = managementInfoService.login(managementInfo.getName(), managementInfo.getPassword());
            if(StringUtils.isEmpty(token)){
                return Message.createByError("用户名或密码错误");
            }
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            tokenMap.put("tokenHead", GlobalConstant.JWT_TOKEN_HEAD);
            return Message.createBySuccess(tokenMap);
        }catch (Exception e){
            logger.error(GlobalConstant.getErrorLog("管理员登录失败,{}"), e.getMessage(), managementInfo);
            return Message.createByError(e.getMessage());
        }
    }



    /**
     * @Description: 修改管理员的角色
     * @Author: hmr
     * @Date: 2019/12/19 20:09
     * @param
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @PostMapping("/managementRole")
    public Message editManagementRole(ManagementRoleRel managementRoleRel){
        try{

        }catch (Exception e){
            logger.error(GlobalConstant.getErrorLog("修改管理员角色"), e.getMessage() );
        }
        return null;
    }
}
