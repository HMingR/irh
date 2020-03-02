package top.imuster.user.provider.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.wrapper.Message;
import top.imuster.user.api.dto.UserTrendDto;
import top.imuster.user.provider.service.UserSystemService;

import javax.annotation.Resource;

/**
 * @ClassName: UserSystemController
 * @Description: 用户模块系统控制器
 * @author: hmr
 * @date: 2020/2/25 9:56
 */
@RestController
@RequestMapping("/system")
public class UserSystemController {

    @Resource
    UserSystemService userSystemService;

    @ApiOperation("获得当前在线的用户总数")
    @GetMapping("/currentUser")
    public Message currentUserTotal(){
        return null;
    }

    @ApiOperation("搜索一个时间段内的用户注册数量趋势 1-最近一周  2-一个月 3-半年 4-一年")
    @GetMapping("/userTrend/{type}")
    public Message<UserTrendDto> userTrend(@PathVariable("type") Integer type){
        return userSystemService.userTrend(type);
    }
}
