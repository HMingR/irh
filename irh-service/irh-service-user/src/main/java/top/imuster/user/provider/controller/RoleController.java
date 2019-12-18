package top.imuster.user.provider.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.user.api.pojo.RoleInfo;
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

    /**
     * @Description: 分页查询角色列表
     * @Author: hmr
     * @Date: 2019/12/17 20:14
     * @param
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(httpMethod = "GET", value = "分页查询角色列表")
    @GetMapping("/")
    public Message roleList(Page<RoleInfo> page){
        try{
            Page<RoleInfo> roleInfoPage = roleInfoService.selectPage(new RoleInfo(), page);
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
    public Message editRoleInfo(@RequestBody RoleInfo roleInfo){
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
    public Message deleteById(@PathVariable(required = true) Long roleId){
        try{
            RoleInfo roleInfo = new RoleInfo();
            roleInfo.setId(roleId);
            roleInfo.setState(1);
            roleInfoService.updateByKey(roleInfo);
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
    public Message insertRole(@RequestBody RoleInfo roleInfo){
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
    public Message toEditRoleAuth(@PathVariable(value = "roleId") Long roleId){
        try{
            RoleInfo roleInfo = roleInfoService.getRoleAndAuthByRoleId(roleId);
            return Message.createBySuccess(roleInfo);
        }catch (Exception e){
            logger.error("进入角色权限列表页面失败,报错{}",e.getMessage());
            return Message.createByError();
        }
    }
}
