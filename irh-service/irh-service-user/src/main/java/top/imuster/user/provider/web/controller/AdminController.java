package top.imuster.user.provider.web.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.api.pojo.UserRoleRel;
import top.imuster.user.provider.service.UserInfoService;
import top.imuster.user.provider.service.UserRoleRelService;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @ClassName: UserController
 * @Description: 管理人员的controller(包括用户和会员)
 * @author: hmr
 * @date: 2019/12/1 18:59
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "adminController", description = "操作用户的权限角色等")
public class AdminController extends BaseController {

    @Resource
    UserInfoService userInfoService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Resource
    UserRoleRelService userRoleRelService;

    @ApiOperation(value = "查看所有的用户", httpMethod = "POST")
    @PostMapping("/list")
    public Message<Page<UserInfo>> UserList(@ApiParam @RequestBody Page<UserInfo> page){
        if(page.getSearchCondition() == null){
            page.setSearchCondition(new UserInfo());
        }
        Page<UserInfo> consumerInfoPage = userInfoService.selectPage(page.getSearchCondition(), page);
        return Message.createBySuccess(consumerInfoPage);
    }

    /**
     * @Description: 添加用户
     * @Author: hmr
     * @Date: 2019/12/19 11:14
     * @param userInfo
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "添加用户", httpMethod = "POST")
    @PostMapping
    public Message<String> addUser(@ApiParam("UserInfo实体") @RequestBody UserInfo userInfo) throws IOException {
        String real_pwd = passwordEncoder.encode(userInfo.getPassword());
        userInfo.setPassword(real_pwd);
        userInfoService.insertEntry(userInfo);
        return Message.createBySuccess();
    }

    @ApiOperation(value = "根据id获得用户信息", httpMethod = "GET")
    @GetMapping("/{id}")
    public Message<UserInfo> toEdit(@ApiParam("用户id") @PathVariable("id") Long id){
        UserInfo condition = new UserInfo();
        condition.setId(id);
        UserInfo userInfo = userInfoService.selectEntryList(condition).get(0);
        if(null == userInfo){
            return Message.createByError("未找到对应的用户信息，请刷新后重试");
        }
        return Message.createBySuccess(condition);
    }


    /**
     * @Description: 修改用户的角色
     * @Author: hmr
     * @Date: 2019/12/19 20:09
     * @param
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("提交修改用户的角色")
    @PutMapping("/adminRole")
    public Message<String> editUserRole(Long UserId, String roleIds) throws Exception {
        userInfoService.editUserRoleById(UserId, roleIds);
        return Message.createBySuccess("修改成功");
    }

    @ApiOperation(value = "修改用户信息(修改基本信息，包括删除)", httpMethod = "PUT")
    @PutMapping
    public Message<String> editUser(@ApiParam("UserInfo实体") @Validated(value = ValidateGroup.editGroup.class) @RequestBody UserInfo userInfo, BindingResult bindingResult) throws IOException {
        validData(bindingResult);
        userInfoService.updateByKey(userInfo);
        return Message.createBySuccess();
    }

    /**
     * @Author hmr
     * @Description 根据用户id删除指定的角色
     * @Date: 2020/1/21 14:11
     * @param id
     * @param roleId
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation("根据用户id删除指定的角色")
    @DeleteMapping("/{userId}/{roleId}")
    public Message<String> deleteUserRole(@PathVariable("userId")Long id, @PathVariable("roleId")Long roleId){
        UserRoleRel condition = new UserRoleRel();
        condition.setRoleId(roleId);
        condition.setStaffId(id);
        userRoleRelService.deleteByCondtion(condition);
        return Message.createBySuccess();
    }

    /**
     * @Author hmr
     * @Description 根据用户的id改变用户的状态
     * @Date: 2020/1/22 13:29
     * @param id
     * @param state
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation("根据用户的id改变用户的状态")
    @DeleteMapping("/{id}/{state}")
    public Message<String> deleteUser(@ApiParam("用户id")@PathVariable Long id, @ApiParam("需要改变的状态10:注销 20:锁定 30:审核中 40:审核通过")@PathVariable("state")Integer state){
        UserInfo condition = new UserInfo();
        condition.setId(id);
        condition.setState(state);
        userInfoService.updateByKey(condition);
        return Message.createBySuccess();
    }
}
