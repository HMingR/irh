package top.imuster.user.provider.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.validate.ValidateGroup;
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
    @NeedLogin(validate = true)
    public Message<Page<AuthInfo>> authList(Page<AuthInfo> page){
        Page<AuthInfo> authInfoPage = authInfoService.selectPage(page.getSearchCondition(), page);
        return Message.createBySuccess(authInfoPage);
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
    @NeedLogin(validate = true)
    public Message<String> deleteAuth(@ApiParam(value = "auth主键", required = true) @PathVariable(value = "authId") Long authId){
        AuthInfo authInfo = new AuthInfo();
        authInfo.setState(1);
        authInfo.setId(authId);
        authInfoService.updateByKey(authInfo);
        return Message.createBySuccess();
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
    @NeedLogin(validate = true)
    public Message<String> addAuth(@ApiParam("AuthInfo实体类") @Validated(ValidateGroup.addGroup.class) @RequestBody AuthInfo authInfo){
        authInfoService.insertEntry(authInfo);
        return Message.createBySuccess();
    }

    @ApiOperation(value = "根据id修改权限信息", httpMethod = "GET")
    @GetMapping("/authId")
    @NeedLogin(validate = true)
    public Message toEdit(@ApiParam(value = "权限id", required = true) @PathVariable("authId")Long authId){
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
    @NeedLogin(validate = true)
    public Message<String> editAuth(@ApiParam("AuthInfo实体类") @RequestBody AuthInfo authInfo){
        authInfoService.updateByKey(authInfo);
        return Message.createBySuccess();
    }


}
