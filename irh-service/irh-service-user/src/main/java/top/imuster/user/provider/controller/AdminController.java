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
import top.imuster.goods.api.dto.ProductInfoDto;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.api.service.GoodsServiceFeignApi;
import top.imuster.user.api.pojo.ConsumerInfo;
import top.imuster.user.api.pojo.ManagementInfo;
import top.imuster.user.api.pojo.ManagementRoleRel;
import top.imuster.user.provider.exception.UserException;
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
@RequestMapping("/admin")
@Api(tags = "adminController", description = "后台管理页面")
public class AdminController extends BaseController {

    @Autowired
    GoodsServiceFeignApi goodsServiceFeignApi;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Resource
    ManagementInfoService managementInfoService;

    @ApiOperation("查看所有的管理员")
    @PostMapping("/list")
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

    /**
     * @Description: 管理二手商品，按条件分页查询
     * @Author: hmr
     * @Date: 2019/12/27 12:07
     * @param productInfo
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("查看二手商品，按条件分页查询")
    @PostMapping("/goods/es")
    public Message goodsList(@RequestBody ProductInfoDto productInfoDto){
        try{
            return goodsServiceFeignApi.list(productInfoDto);
        }catch (Exception e){
            logger.error("远程调用goods模块出现异常", e.getMessage(), e);
            throw new UserException("远程调用goods模块出现异常" + e.getMessage());
        }
    }

    /**
     * @Description: 管理员根据id下架二手商品
     * @Author: hmr
     * @Date: 2019/12/27 15:10
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @DeleteMapping("/goods/{id}")
    @ApiOperation("管理员根据id下架二手商品")
    public Message delGoodsById(@PathVariable("id") Long id) throws UserException{
        return goodsServiceFeignApi.delProduct(id);
    }

}
