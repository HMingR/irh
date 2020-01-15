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
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.user.api.pojo.ConsumerInfo;
import top.imuster.user.api.pojo.ManagementInfo;
import top.imuster.user.provider.exception.UserException;
import top.imuster.user.provider.service.ConsumerInfoService;
import top.imuster.user.provider.service.ManagementInfoService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ManagementController
 * @Description: 管理人员的controller(包括管理员和会员)
 * @author: hmr
 * @date: 2019/12/1 18:59
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "adminController", description = "操作管理员的权限角色等")
public class AdminUserController extends BaseController {

    @Resource
    ConsumerInfoService consumerInfoService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Resource
    ManagementInfoService managementInfoService;

    @ApiOperation(value = "查看所有的管理员", httpMethod = "POST")
    @PostMapping("/list/1")
    @NeedLogin(validate = true)
    public Message managementList(@RequestBody Page<ManagementInfo> page, @RequestBody ManagementInfo managementInfo){
        try{
            Page<ManagementInfo> managementInfoPage = managementInfoService.selectPage(managementInfo, page);
            if(null != managementInfoPage){
                //将密码全都设置成空
                managementInfoPage.getResult().stream().forEach(mi -> mi.setPassword(""));
            }
            return Message.createBySuccess(managementInfoPage);
        }catch (Exception e){
            logger.error("查看管理员列表失败:{}", e.getMessage());
            return Message.createByError();
        }
    }

    /**
     * @Description: 分页条件查询所有的会员
     * @Author: hmr
     * @Date: 2019/12/26 19:43
     * @param page
     * @param consumerInfo
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "分页条件查询所有的会员", httpMethod = "POST")
    @PostMapping("/list/2")
    public Message list(@RequestBody Page<ConsumerInfo> page,@RequestBody ConsumerInfo consumerInfo){
        try{
            Page<ConsumerInfo> consumerInfoPage = consumerInfoService.selectPage(consumerInfo, page);
            return Message.createBySuccess(consumerInfoPage);
        }catch (Exception e){
            logger.error("分页条件查询所有的会员失败", e.getMessage(), e);
            throw new UserException(e.getMessage());
        }
    }

    /**
     * @Description: 添加管理员
     * @Author: hmr
     * @Date: 2019/12/19 11:14
     * @param managementInfo
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "添加管理员", httpMethod = "POST")
    @PostMapping
    public Message addManagement(@RequestBody ManagementInfo managementInfo) throws IOException {
        try{
            String real_pwd = passwordEncoder.encode(managementInfo.getPassword());
            managementInfo.setPassword(real_pwd);
            managementInfoService.insertEntry(managementInfo);
            return Message.createBySuccess();
        }catch (Exception e){
            logger.error(GlobalConstant.getErrorLog("添加管理员"), e.getMessage(), objectMapper.writeValueAsString(managementInfo));
            throw new UserException("添加管理员失败");
        }
    }

    @ApiOperation(value = "修改管理员信息(修改基本信息，包括删除)", httpMethod = "PUT")
    @PutMapping
    public Message editManagement(@Validated(value = ValidateGroup.editGroup.class) @RequestBody ManagementInfo managementInfo, BindingResult bindingResult) throws IOException {
        validData(bindingResult);
        try{
            managementInfoService.updateByKey(managementInfo);
            return Message.createBySuccess();
        }catch (Exception e){
            logger.error(GlobalConstant.getErrorLog("修改管理员信息"), e.getMessage(), objectMapper.writeValueAsString(managementInfo));
            throw new UserException("修改管理员信息失败");
        }
    }


    @ApiOperation(value = "登录成功返回token", httpMethod = "POST")
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

    @ApiOperation(value = "根据id获得管理员信息", httpMethod = "GET")
    @GetMapping("/{id}")
    public Message toEdit(@PathVariable("id") Long id){
        ManagementInfo condition = new ManagementInfo();
        condition.setId(id);
        ManagementInfo managementInfo = managementInfoService.selectEntryList(condition).get(0);
        if(null == managementInfo){
            return Message.createByError("修改管理员信息失败,请刷新后重试");
        }
        return Message.createBySuccess(managementInfo);
    }


    /**
     * @Description: 修改管理员的角色
     * @Author: hmr
     * @Date: 2019/12/19 20:09
     * @param
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("提交修改管理员的角色")
    @PostMapping("/adminRole")
    public Message editManagementRole(Long managementId, String roleIds){
        try{
            managementInfoService.editManagementRoleById(managementId, roleIds);
            return Message.createBySuccess("修改成功");
        }catch (Exception e){
            logger.error(GlobalConstant.getErrorLog("修改管理员角色失败"), e.getMessage());
            throw new UserException(e.getMessage());
        }
    }
}
