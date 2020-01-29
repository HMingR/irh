package top.imuster.user.provider.web.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import top.imuster.user.api.pojo.ManagementInfo;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.api.pojo.UserRoleRel;
import top.imuster.user.provider.exception.UserException;
import top.imuster.user.provider.service.ManagementInfoService;
import top.imuster.user.provider.service.UserInfoService;
import top.imuster.user.provider.service.UserRoleRelService;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @ClassName: ManagementController
 * @Description: 管理人员的controller(包括管理员和会员)
 * @author: hmr
 * @date: 2019/12/1 18:59
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "adminController", description = "操作管理员的权限角色等")
public class AdminController extends BaseController {

    @Resource
    UserInfoService userInfoService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Resource
    ManagementInfoService managementInfoService;

    @Resource
    UserRoleRelService userRoleRelService;


    @ApiOperation(value = "查看所有的管理员", httpMethod = "POST")
    @NeedLogin(validate = true)
    @PostMapping("/list/1")
    @PreAuthorize("hasAuthority('login')")
    public Message<Page<ManagementInfo>> managementList(@ApiParam @RequestBody Page<ManagementInfo> page){
        Page<ManagementInfo> managementInfoPage = managementInfoService.selectPage(page.getSearchCondition(), page);
        if(null != managementInfoPage){
            //将密码全都设置成空
            managementInfoPage.getResult().stream().forEach(mi -> mi.setPassword(""));
        }
        return Message.createBySuccess(managementInfoPage);
    }

   /**
    * @Author hmr
    * @Description 分页条件查询所有的会员
    * @Date: 2020/1/19 16:46
    * @param page
    * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.user.api.pojo.UserInfo>>
    **/
    @ApiOperation(value = "分页条件查询所有的会员", httpMethod = "POST")
    @PostMapping("/list/2")
    @NeedLogin(validate = true)
    @PreAuthorize("hasAuthority('login1')")
    public Message<Page<UserInfo>> list(@ApiParam @RequestBody Page<UserInfo> page){
        Page<UserInfo> consumerInfoPage = userInfoService.selectPage(page.getSearchCondition(), page);
        return Message.createBySuccess(consumerInfoPage);
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
    @NeedLogin(validate = true)
    public Message<String> addManagement(@ApiParam("ManagementInfo实体") @RequestBody ManagementInfo managementInfo) throws IOException {
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

    @ApiOperation(value = "根据id获得管理员信息", httpMethod = "GET")
    @GetMapping("/{id}")
    @NeedLogin(validate = true)
    public Message<ManagementInfo> toEdit(@ApiParam("管理员id") @PathVariable("id") Long id){
        ManagementInfo condition = new ManagementInfo();
        condition.setId(id);
        ManagementInfo managementInfo = managementInfoService.selectEntryList(condition).get(0);
        if(null == managementInfo){
            return Message.createByError("未找到对饮的管理员信息，请刷新后重试");
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
    //todo 修改逻辑需要修改
    @ApiOperation("提交修改管理员的角色")
    @PostMapping("/adminRole")
    @NeedLogin(validate = true)
    public Message<String> editManagementRole(Long managementId, String roleIds) throws Exception {
        managementInfoService.editManagementRoleById(managementId, roleIds);
        return Message.createBySuccess("修改成功");
    }

    @ApiOperation(value = "修改管理员信息(修改基本信息，包括删除)", httpMethod = "PUT")
    @NeedLogin(validate = true)
    @PutMapping
    public Message<String> editManagement(@ApiParam("ManagementInfo实体") @Validated(value = ValidateGroup.editGroup.class) @RequestBody ManagementInfo managementInfo, BindingResult bindingResult) throws IOException {
        validData(bindingResult);
        try{
            managementInfoService.updateByKey(managementInfo);
            return Message.createBySuccess();
        }catch (Exception e){
            logger.error(GlobalConstant.getErrorLog("修改管理员信息"), e.getMessage(), objectMapper.writeValueAsString(managementInfo));
            throw new UserException("修改管理员信息失败");
        }
    }

    /**
     * @Author hmr
     * @Description 根据管理员id删除指定的角色
     * @Date: 2020/1/21 14:11
     * @param id
     * @param roleId
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation("根据管理员id删除指定的角色")
    @DeleteMapping("/{userId}/{roleId}")
    @NeedLogin(validate = true)
    public Message<String> deleteManagementRole(@PathVariable("userId")Long id, @PathVariable("roleId")Long roleId){
        UserRoleRel condition = new UserRoleRel();
        condition.setRoleId(roleId);
        condition.setStaffId(id);
        userRoleRelService.deleteByCondtion(condition);
        return Message.createBySuccess();
    }

    /**
     * @Author hmr
     * @Description 根据管理员的id改变管理员的状态
     * @Date: 2020/1/22 13:29
     * @param id
     * @param state
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation("根据管理员的id改变管理员的状态")
    @NeedLogin(validate = true)
    @DeleteMapping("/{id}/{state}")
    public Message<String> deleteManagement(@ApiParam("管理员id")@PathVariable Long id, @ApiParam("需要改变的状态10:注销 20:锁定 30:审核中 40:审核通过")@PathVariable("state")Integer state){
        ManagementInfo managementInfo = new ManagementInfo();
        managementInfo.setId(id);
        managementInfo.setState(state);
        managementInfoService.updateByKey(managementInfo);
        return Message.createBySuccess();
    }
}
