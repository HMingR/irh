package top.imuster.user.provider.web.controller;


import io.swagger.annotations.ApiOperation;
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

/**
 * @ClassName: RoleController
 * @Description: 角色控制器
 * @author: hmr
 * @date: 2019/12/18 10:13
 */
@RestController("/role")
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
    @ApiOperation(httpMethod = "GET", value = "分页查询角色列表")
    @GetMapping("/list")
    public Message roleList(Page<RoleInfo> page, RoleInfo roleInfo){
        try{
            Page<RoleInfo> roleInfoPage = roleInfoService.selectPage(roleInfo, page);
            return Message.createBySuccess(roleInfoPage);
        }catch (Exception e){
            logger.error("获得用户角色失败", e.getMessage());
            return Message.createByError();
        }
    }


    /**
     * @Description: 按主键修改角色信息
     * @Author: hmr
     * @Date: 2019/12/18 10:21
     * @param roleInfo
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "按主键修改角色信息", httpMethod = "PUT")
    @PutMapping("/")
    public Message editRoleInfo(@Validated(ValidateGroup.editGroup.class) @RequestBody RoleInfo roleInfo, BindingResult bindingResult){
        validData(bindingResult);
        try{
            roleInfoService.updateByKey(roleInfo);
            return Message.createBySuccess();
        }catch (Exception e){
            logger.error("修改权限信息失败{},角色信息为{}",e.getMessage(),roleInfo);
            return Message.createByError();
        }
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
    public Message deleteById(@PathVariable Long roleId){
        try{
            RoleInfo roleInfo = new RoleInfo();
            roleInfo.setId(roleId);
            roleInfo.setState(1);
            int i = roleInfoService.updateByKey(roleInfo);
            if(i != 1){
                return Message.createByError("没有该记录");
            }
            return Message.createBySuccess();
        }catch (Exception e){
            logger.error("删除角色失败,报错:{},角色id为{}",e.getMessage(),roleId);
            return Message.createByError();
        }
    }

    /**
     * @Description: 添加角色
     * @Author: hmr
     * @Date: 2019/12/18 10:35
     * @param roleInfo
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("添加角色")
    @PostMapping("/")
    public Message insertRole(@Validated(ValidateGroup.addGroup.class) @RequestBody RoleInfo roleInfo, BindingResult bindingResult){
        validData(bindingResult);
        try{
            roleInfoService.insertEntry(roleInfo);
            return Message.createBySuccess();
        }catch (Exception e){
            logger.error("添加角色失败,错误信息为{},角色信息为{}", e.getMessage(), roleInfo);
            return Message.createByError();
        }
    }


    /**
     * @Description: 根据角色id获得角色的所有权限
     * @Author: hmr
     * @Date: 2019/12/18 15:47
     * @param roleId
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "根据角色id获得角色的所有权限")
    @GetMapping("/{roleId}")
    public Message toEditRoleAuth(@PathVariable(value = "roleId")Long roleId){
        try{
            RoleInfo roleInfo = roleInfoService.getRoleAndAuthByRoleId(roleId);
            return Message.createBySuccess(roleInfo);
        }catch (Exception e){
            logger.error("进入角色权限列表页面失败,报错{}",e.getMessage());
            return Message.createByError();
        }
    }

    /**
     * @Description: 修改角色的权限
     * @Author: hmr
     * @Date: 2019/12/18 12:18
     * @param
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "修改角色的权限", httpMethod = "PUT")
    @PutMapping("/editAuth")
    public Message editRoleAuth(@Validated({AuthRoleRel.editGroup.class}) @RequestBody AuthRoleRel authRoleRel, BindingResult bindingResult){
        validData(bindingResult);
        try{
            authRoleRelService.insertEntry(authRoleRel);
            return Message.createBySuccess();
        }catch (Exception e){
            logger.error("给角色添加新的权限失败,错误信息为{},");
            return Message.createByError();
        }
    }
}
