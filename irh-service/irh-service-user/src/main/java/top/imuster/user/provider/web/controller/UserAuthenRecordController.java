package top.imuster.user.provider.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.user.api.pojo.UserAuthenRecordInfo;
import top.imuster.user.provider.service.UserAuthenRecordInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: UserAuthenRecordController
 * @Description: 用户认证管理
 * @author: hmr
 * @date: 2020/3/29 15:49
 */
@RestController
@RequestMapping("/authen")
public class UserAuthenRecordController {

    @Resource
    private UserAuthenRecordInfoService userAuthenRecordInfoService;

    @ApiOperation("管理员分页查看认证记录")
    @NeedLogin
    @PostMapping
    public Message<Page<UserAuthenRecordInfo>> getPage(@RequestParam Page<UserAuthenRecordInfo> page){
        UserAuthenRecordInfo searchCondition = page.getSearchCondition();
        if(searchCondition == null){
            UserAuthenRecordInfo recordInfo = new UserAuthenRecordInfo();
            recordInfo.setOrderField("create_time");
            recordInfo.setOrderFieldType("ASC");
            page.setSearchCondition(recordInfo);
        }
        Page<UserAuthenRecordInfo> res = userAuthenRecordInfoService.selectPage(page.getSearchCondition(), page);
        return Message.createBySuccess(res);
    }

    @GetMapping("/{id}")
    public Message<UserAuthenRecordInfo> getDetail(@PathVariable("id") Long id){
        UserAuthenRecordInfo recordInfo = userAuthenRecordInfoService.selectEntryList(id).get(0);
        return Message.createBySuccess(recordInfo);
    }

    @PostMapping("/authen")
    public Message<String> authen(@RequestParam UserAuthenRecordInfo userAuthenRecordInfo){
        return userAuthenRecordInfoService.authen(userAuthenRecordInfo);
    }
}
