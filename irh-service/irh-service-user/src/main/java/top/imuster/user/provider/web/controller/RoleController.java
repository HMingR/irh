package top.imuster.user.provider.web.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.user.api.pojo.AuthRoleRel;
import top.imuster.user.api.pojo.RoleInfo;
import top.imuster.user.provider.service.AuthRoleRelService;
import top.imuster.user.provider.service.RoleInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: RoleController
 * @Description: 角色控制器
 * @author: hmr
 * @date: 2019/12/18 10:13
 */
@RestController
@RequestMapping("admin/role")
@Api("角色控制器")
public class RoleController extends BaseController {

    @Resource
    RoleInfoService roleInfoService;

    @Resource
    AuthRoleRelService authRoleRelService;

    /**
     * @Description: 分页条件查询角色列表
     * @Author: hmr
     * @Date: 2019/12/17 20:14
     * @param
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(httpMethod = "POST", value = "分页查询角色列表")
    @PostMapping("/list")
    public Message<Page<RoleInfo>> roleList(@ApiParam("page实体类") @RequestBody Page<RoleInfo> page){
        Page<RoleInfo> roleInfoPage = roleInfoService.selectPage(page.getSearchCondition(), page);
        return Message.createBySuccess(roleInfoPage);
    }


    @ApiOperation(value = "根据id获得角色信息", httpMethod = "GET")
    @GetMapping("/{roleId}")
    public Message<RoleInfo> toEditRoleInfo(@ApiParam("角色id") @PathVariable("roleId") Long roleId){
        RoleInfo roleInfo = roleInfoService.selectEntryList(roleId).get(0);
        return Message.createBySuccess(roleInfo);
    }

    /**
     * @Description: 按主键修改角色信息
     * @Author: hmr
     * @Date: 2019/12/18 10:21
     * @param roleInfo
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "按主键修改角色信息", httpMethod = "POST")
    @PostMapping("/edit")
    public Message<String> editRoleInfo(@ApiParam("RoleInfo实体类")@Validated(ValidateGroup.editGroup.class) @RequestBody RoleInfo roleInfo, BindingResult bindingResult){
        validData(bindingResult);
        roleInfoService.updateByKey(roleInfo);
        return Message.createBySuccess();
    }

    /**
     * @Description: 根据主键删除角色
     * @Author: hmr
     * @Date: 2019/12/18 10:32
     * @param roleId
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "根据主键删除角色", httpMethod = "DELETE")
    @DeleteMapping("/{roleId}")
    public Message<String> deleteById(@ApiParam("角色id") @PathVariable Long roleId){
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setId(roleId);
        roleInfo.setState(1);
        int i = roleInfoService.updateByKey(roleInfo);
        if(i != 1){
            return Message.createByError("操作失败,请刷新后重试");
        }
        return Message.createBySuccess();
    }

    /**
     * @Description: 添加角色
     * @Author: hmr
     * @Date: 2019/12/18 10:35
     * @param roleInfo
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "添加角色", httpMethod = "POST")
    @PostMapping("/")
    public Message<String> insertRole(@ApiParam("RoleInfo实体类") @Validated(ValidateGroup.addGroup.class) @RequestBody RoleInfo roleInfo, BindingResult bindingResult){
        validData(bindingResult);
        roleInfoService.insertEntry(roleInfo);
        return Message.createBySuccess();
    }


    /**
     * @Description: 根据角色id获得角色的所有权限
     * @Author: hmr
     * @Date: 2019/12/18 15:47
     * @param roleId
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "根据角色id获得角色的所有权限", httpMethod = "GET")
    @GetMapping("/ra/{roleId}")
    public Message<RoleInfo> toEditRoleAuth(@ApiParam("角色id") @PathVariable(value = "roleId")Long roleId){
        RoleInfo roleInfo = roleInfoService.getRoleAndAuthByRoleId(roleId);
        return Message.createBySuccess(roleInfo);
    }

    /**
     * @Description: 修改角色的权限
     * @Author: hmr
     * @Date: 2019/12/18 12:18
     * @param
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "添加角色的权限", httpMethod = "PUT")
    @PutMapping("/editRoleAuth")
    public Message<String> editRoleAuth(@ApiParam("AuthRoleRel实体类") @Validated({AuthRoleRel.editGroup.class}) @RequestBody AuthRoleRel authRoleRel, BindingResult bindingResult){
        validData(bindingResult);
        authRoleRelService.insertEntry(authRoleRel);
        return Message.createBySuccess();
    }

    /**
     * @Author hmr
     * @Description 根据管理员id获得该管理员没有拥有的角色
     * @Date: 2020/1/22 10:08
     * @param adminId
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "根据管理员id获得该管理员没有拥有的角色", httpMethod = "GET")
    @GetMapping("/other/{adminId}")
    public Message getOtherRoleByAdminId(@ApiParam("管理员id") @PathVariable("adminId") Long adminId){
        List<RoleInfo> roleInfos =  roleInfoService.getOtherRoleByAdminId(adminId);
        return Message.createBySuccess(roleInfos);
    }
}
