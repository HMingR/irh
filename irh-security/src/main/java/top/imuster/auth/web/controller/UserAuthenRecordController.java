package top.imuster.auth.web.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.imuster.auth.service.UserAuthenRecordInfoService;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.security.api.pojo.UserAuthenRecordInfo;

import javax.annotation.Resource;

/**
 * @ClassName: UserAuthenRecordController
 * @Description: 用户认证管理
 * @author: hmr
 * @date: 2020/3/29 15:49
 */
@RestController
@RequestMapping("/admin/authen")
public class UserAuthenRecordController  extends BaseController {

    @Resource
    private UserAuthenRecordInfoService userAuthenRecordInfoService;

    @ApiOperation("管理员分页查看认证记录")
    @NeedLogin
    @PostMapping
    public Message<Page<UserAuthenRecordInfo>> getPage(@RequestBody Page<UserAuthenRecordInfo> page){
        UserAuthenRecordInfo searchCondition = page.getSearchCondition();
        if(searchCondition == null){
            UserAuthenRecordInfo recordInfo = new UserAuthenRecordInfo();
            recordInfo.setOrderField("create_time");
            recordInfo.setOrderFieldType("DESC");
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

    /**
     * @Author hmr
     * @Description 人工认证
     * @Date: 2020/5/30 19:57
     * @param userAuthenRecordInfo
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @PostMapping("/approve")
    public Message<String> authen(@RequestBody UserAuthenRecordInfo userAuthenRecordInfo){
        userAuthenRecordInfo.setApproveId(getCurrentUserIdFromCookie());
        return userAuthenRecordInfoService.authen(userAuthenRecordInfo);
    }
}
