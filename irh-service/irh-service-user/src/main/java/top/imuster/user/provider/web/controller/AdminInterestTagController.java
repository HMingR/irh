package top.imuster.user.provider.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.user.api.pojo.InterestTagInfo;
import top.imuster.user.provider.service.InterestTagInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: AdminInterestTagController
 * @Description: AdminInterestTagController
 * @author: hmr
 * @date: 2020/2/6 12:40
 */
@RestController
@RequestMapping("/interest")
@Api("用户兴趣处理器")
public class AdminInterestTagController extends BaseController {

    @Resource
    InterestTagInfoService interestTagInfoService;

    /**
     * @Author hmr
     * @Description 用户选择兴趣标签
     * @Date: 2020/2/6 15:23
     * @param
     * @reture: top.imuster.common.base.wrapper.Message<java.util.List<top.imuster.user.api.pojo.InterestTagInfo>>
     **/
    @ApiOperation(value = "查询所有的兴趣标签,用户选择兴趣标签,提供给用户的", httpMethod = "GET")
    @GetMapping
    public Message<List<InterestTagInfo>> list(){
        List<InterestTagInfo> res = interestTagInfoService.userTaglist(getCurrentUserIdFromCookie(false));
        return Message.createBySuccess(res);
    }

    /**
     * @Author hmr
     * @Description 根据id查询兴趣标签的名字
     * @Date: 2020/2/6 17:47
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation("根据id查询兴趣标签的名字")
    @GetMapping("/name/{id}")
    public Message<String> getTagNameById(@PathVariable("id") Long id){
        String tagName = interestTagInfoService.getTagNameById(id);
        if(tagName == null){
            tagName = String.valueOf(id);
        }
        return Message.createBySuccess(tagName);
    }

    /**
     * @Author hmr
     * @Description 分页条件查询兴趣标签
     * @Date: 2020/2/6 15:18
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.user.api.pojo.InterestTagInfo>>
     **/
    @ApiOperation(value = "分页条件查询兴趣标签,提供给管理员的", httpMethod = "POST")
    @PostMapping("/admin")
    public Message<Page<InterestTagInfo>> tagList(@RequestBody Page<InterestTagInfo> page){
        Page<InterestTagInfo> interestTagInfoPage = interestTagInfoService.list(page);
        return Message.createBySuccess(interestTagInfoPage);
    }

    /**
     * @Author hmr
     * @Description 根据id获得兴趣标签
     * @Date: 2020/2/6 15:18
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.user.api.pojo.InterestTagInfo>
     **/
    @ApiOperation(value = "根据id获得兴趣标签信息", httpMethod = "GET")
    @GetMapping("/admin/{id}")
    public Message<InterestTagInfo> getInfoById(@PathVariable("id") Long id){
        InterestTagInfo interestTagInfo = interestTagInfoService.selectEntryList(id).get(0);
        return Message.createBySuccess(interestTagInfo);
    }

    /**
     * @Author hmr
     * @Description 修改标签内容
     * @Date: 2020/2/6 15:18
     * @param interestTagInfo
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation(value = "修改标签内容", httpMethod = "PUT")
    @PutMapping("/admin")
    public Message<String> editTarInfo(@RequestBody InterestTagInfo interestTagInfo){
        interestTagInfoService.updateByKey(interestTagInfo);
        return Message.createBySuccess();
    }


    /**
     * @Author hmr
     * @Description 根据id删除,需要提醒管理员会把所有用户引用该标签的记录都删掉
     * @Date: 2020/2/6 15:18
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation(value = "根据id删除,需要提醒管理员会把所有用户引用该标签的记录都删掉", httpMethod = "DELETE")
    @DeleteMapping("/admin/{id}")
    public Message<String> deleteById(@PathVariable("id") Long id){
        interestTagInfoService.deleteById(id);
        return Message.createBySuccess();
    }

    @ApiOperation("根据用户id获得用户关注的兴趣标签id列表")
    @GetMapping("/owner")
    @NeedLogin
    public Message<List<Long>> getUserTag(){
        Long userId = getCurrentUserIdFromCookie();
        return interestTagInfoService.getUserTagByUserId(userId);
    }

    @ApiOperation("关注和取消关注 type:1-取消关注   2-关注")
    @GetMapping("/follow/{type}/{id}")
    public Message<String> follow(@PathVariable("type") Integer type, @PathVariable("id") Long tagId){
        return interestTagInfoService.follow(type, tagId, getCurrentUserIdFromCookie());
    }
}
