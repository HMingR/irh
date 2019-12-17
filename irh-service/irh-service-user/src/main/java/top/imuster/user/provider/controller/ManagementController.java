package top.imuster.user.provider.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.UserDto;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.user.api.pojo.AuthInfo;
import top.imuster.user.api.pojo.ManagementInfo;
import top.imuster.user.api.pojo.RoleInfo;
import top.imuster.user.provider.service.AuthInfoService;
import top.imuster.user.provider.service.ManagementInfoService;
import top.imuster.user.provider.service.RoleInfoService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ManagementController
 * @Description: 管理人员的controller
 * @author: hmr
 * @date: 2019/12/1 18:59
 */
@RestController
@RequestMapping("/management")
@Api(tags = "ManagementController", description = "后台管理页面")
public class ManagementController extends BaseController {

    @Autowired
    RedisTemplate redisTemplate;


    @Resource
    ManagementInfoService managementInfoService;

    @Resource
    RoleInfoService roleInfoService;

    @Resource
    AuthInfoService authInfoService;

    @ApiOperation("查看所有的管理员")
    @GetMapping("/managementList")
    public Message managementList(@RequestBody Page<ManagementInfo> page){
        try{
            Page<ManagementInfo> managementInfoPage = managementInfoService.selectPage(new ManagementInfo(), page);

            if(null != managementInfoPage){
                //将密码全都设置成空
                managementInfoPage.getResult().stream().forEach(managementInfo -> managementInfo.setPassword(""));
            }
            return Message.createBySuccess(managementInfoPage);
        }catch (Exception e){
            logger.error("查看管理员列表失败:{}", e.getMessage());
            return Message.createByError();
        }
    }


    @ApiOperation("登录成功返回token")
    @PostMapping("/login")
    public Message managementLogin(@RequestBody ManagementInfo managementInfo){
        try{
            String token = managementInfoService.login(managementInfo.getName(), managementInfo.getPassword());
            if(StringUtils.isEmpty(token)){
                return Message.createByError("用户名或密码错误");
            }
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            tokenMap.put("tokenHead", GlobalConstant.JWT_TOKEN_HEAD);
            redisTemplate.opsForValue().set(RedisUtil.getAccessToken(token), new UserDto(managementInfo.getId(), managementInfo.getName(), 40));
            return Message.createBySuccess(tokenMap);
        }catch (Exception e){
            logger.error("管理人员登录失败:{}", e.getMessage());
            return Message.createByError(e.getMessage());
        }
    }


    /**
     * @Description: 分页查询角色列表
     * @Author: hmr
     * @Date: 2019/12/17 20:14
     * @param
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(httpMethod = "Get", value = "分页查询角色列表")
    @GetMapping("/role")
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
     * @Description: 分页查询权限列表
     * @Author: hmr
     * @Date: 2019/12/17 20:10
     * @param type 1表示查询无效的权限列表     2:表示查询有效的权限列表
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(httpMethod = "Get", value = "分页查询权限列表")
    @GetMapping("/auth/{type}")
    public Message authList(@PathVariable(value = "type", required = true) Integer type,@RequestBody Page<AuthInfo> page){
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
     * @Description: 按权限的主键删除权限
     * @Author: hmr
     * @Date: 2019/12/17 20:34
     * @param authId
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(httpMethod = "Delete", value = "按权限的主键删除权限")
    @DeleteMapping("/auth/{authId}")
    public Message deleteAuth(@ApiParam(value = "authId", required = true) @PathVariable(value = "authId", required = true) Long authId){
        try{
            authInfoService.deleteAuthById(authId);
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
    @PutMapping("/auth")
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
