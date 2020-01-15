package top.imuster.user.provider.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.user.api.pojo.AuthInfo;
import top.imuster.user.provider.service.AuthInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: AuthController
 * @Description: 权限控制器
 * @author: hmr
 * @date: 2019/12/18 10:14
 */
@RestController
@RequestMapping("admin/auth")
public class AuthController extends BaseController {
    @Resource
    AuthInfoService authInfoService;

    /**
     * @Description: 分页查询权限列表
     * @Author: hmr
     * @Date: 2019/12/17 20:10
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(httpMethod = "POST", value = "分页查询权限列表")
    @PostMapping("/list")
    public Message authList(Page<AuthInfo> page, AuthInfo authInfo){
        try{
            Page<AuthInfo> authInfoPage = authInfoService.selectPage(authInfo, page);
            return Message.createBySuccess(authInfoPage);
        }catch (Exception e){
            logger.error("获得权限列表失败", e.getMessage(), e);
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
    @ApiOperation(httpMethod = "DELETE", value = "按主键删除权限")
    @DeleteMapping("/{authId}")
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
    @ApiOperation(value = "添加权限", httpMethod = "POST")
    @PostMapping("/add")
    public Message addAuth(@RequestBody AuthInfo authInfo){
        try{
            authInfoService.insertEntry(authInfo);
            return Message.createBySuccess();
        }catch (Exception e){
            logger.error("添加权限失败:{};权限信息为:{}",e.getMessage(),authInfo);
            return Message.createByError();
        }
    }

    @ApiOperation(value = "根据id修改权限信息", httpMethod = "GET")
    @GetMapping("/authId")
    public Message toEdit(@PathVariable("authId")Long authId){
        AuthInfo authInfo = authInfoService.selectEntryList(authId).get(0);
        return Message.createBySuccess(authInfo);
    }

    /**
     * @Description: 修改权限信息
     * @Author: hmr
     * @Date: 2019/12/17 21:11
     * @param authInfo
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "修改权限信息", httpMethod = "PUT")
    @PutMapping("/edit")
    public Message editAuth(@RequestBody AuthInfo authInfo){
        try{
            authInfoService.updateByKey(authInfo);
            return Message.createBySuccess();
        }catch (Exception e){
            logger.error("更新权限信息失败,报错{};权限信息为{}", e.getMessage(), authInfo);
            return Message.createByError();
        }
    }


}
