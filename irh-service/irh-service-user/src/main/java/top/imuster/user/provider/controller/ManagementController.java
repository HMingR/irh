package top.imuster.user.provider.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.user.api.pojo.ManagementInfo;
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
@RequestMapping("/management")
@Api(tags = "ManagementController", description = "后台管理页面")
public class ManagementController extends BaseController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Resource
    ManagementInfoService managementInfoService;

    @ApiOperation("查看所有的管理员")
    @GetMapping("/list")
    public Message managementList(){
        try{
            ManagementInfo managementInfo = new ManagementInfo();
            List<ManagementInfo> managementInfos = managementInfoService.selectEntryList(managementInfo);
            return Message.createBySuccess(managementInfos);
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
            redisTemplate.opsForValue().set("123", "2345");
            stringRedisTemplate.opsForValue().set(managementInfo.getName(), token);
            return Message.createBySuccess(tokenMap);
        }catch (Exception e){
            logger.error("管理人员登录失败:{}", e.getMessage());
        }
        return Message.createByError();
    }

}
