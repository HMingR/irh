package top.imuster.user.provider.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.user.api.pojo.AuthInfo;
import top.imuster.user.api.pojo.AuthRoleRel;
import top.imuster.user.provider.service.AuthInfoService;
import top.imuster.user.provider.service.AuthRoleRelService;

import javax.annotation.Resource;

/**
 * @ClassName: AuthController
 * @Description: 权限控制器
 * @author: hmr
 * @date: 2019/12/18 10:14
 */
@RestController("/")
public class AuthController extends BaseController {

    @Resource
    AuthInfoService authInfoService;

    @Resource
    AuthRoleRelService authRoleRelService;

    /**
     * @Description: 分页查询权限列表
     * @Author: hmr
     * @Date: 2019/12/17 20:10
     * @param type 1表示查询无效的权限列表     2:表示查询有效的权限列表
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(httpMethod = "Get", value = "分页查询权限列表")
    @GetMapping("/auth/list")
    public Message authList(Integer type, Page<AuthInfo> page){
        try{
            if(type != 1 && type != 2){
                return Message.createByError("参数错误");
            }
            AuthInfo authInfo = new AuthInfo();
            authInfo.setState(type);
            Page<AuthInfo> authInfoPage = authInfoService.selectPage(authInfo, page);
            return Message.createBySuccess(authInfoPage);
        }catch (Exception e){
            logger.error("获得权限列表失败", e.getMessage());
            return Message.createByError();
        }
    }


    /**
     * @Description: 按主键删除权限
     * @Author: hmr
     * @Date: 2019/12/17 20:34
     * @param authId
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(httpMethod = "Delete", value = "按主键删除权限")
    @DeleteMapping("/auth/{authId}")
    public Message deleteAuth(@ApiParam(value = "authId", required = true) @PathVariable(value = "authId", required = true) Long authId){
        try{
            AuthInfo authInfo = new AuthInfo();
            authInfo.setState(1);
            authInfo.setId(authId);
            authInfoService.updateByKey(authInfo);
            return Message.createBySuccess();
        }catch (Exception e){
            logger.error("删除权限失败,原因{},权限id{}",e.getMessage(), authId);
            return Message.createByError();
        }
    }


    /**
     * @Description: 添加权限
     * @Author: hmr
     * @Date: 2019/12/17 20:57
     * @param authInfo
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "添加权限", httpMethod = "Put")
    @PostMapping("/auth")
    public Message addAuth(@RequestBody AuthInfo authInfo){
        try{
            authInfoService.insertEntry(authInfo);
            return Message.createBySuccess();
        }catch (Exception e){
            logger.error("添加权限失败:{};权限信息为:{}",e.getMessage(),authInfo);
            return Message.createByError();
        }
    }

    /**
     * @Description: 更新权限信息
     * @Author: hmr
     * @Date: 2019/12/17 21:11
     * @param authInfo
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "更新权限信息", httpMethod = "Put")
    @PutMapping("/auth/edit")
    public Message editAuth(@RequestBody AuthInfo authInfo){
        try{
            authInfoService.updateByKey(authInfo);
            return Message.createBySuccess();
        }catch (Exception e){
            logger.error("更新权限信息失败,报错{};权限信息为{}", e.getMessage(), authInfo);
            return Message.createByError();
        }
    }

    /**
     * @Description: 修改角色的权限信息
     * @Author: hmr
     * @Date: 2019/12/18 12:18
     * @param
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "修改角色的权限信息", httpMethod = "PUT")
    @PutMapping("/auth/editRoleAuth")
    public Message editRoleAuth(@RequestBody AuthRoleRel authRoleRel){
        try{
            authRoleRelService.insertEntry(authRoleRel);
            return Message.createBySuccess();
        }catch (Exception e){
            logger.error("给角色添加新的权限失败,错误信息为{},");
            return Message.createByError();
        }
    }
}
