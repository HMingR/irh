package top.imuster.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import top.imuster.controller.BaseController;
import top.imuster.user.pojo.ManagementInfo;
import top.imuster.user.service.ManagementInfoService;
import top.imuster.wrapper.Message;

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

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Resource
    ManagementInfoService managementInfoService;

    @ApiOperation("查看所有的管理员")
    @GetMapping("/list")
    public Message managementList(){
        try{
            List<ManagementInfo> managementInfos = managementInfoService.selectEntryList(new ManagementInfo());
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
            tokenMap.put("tokenHead", tokenHead);
            return Message.createBySuccess(tokenMap);
        }catch (Exception e){
            logger.error("管理人员登录失败:{}", e.getMessage());
        }
        return Message.createByError();
    }

}
