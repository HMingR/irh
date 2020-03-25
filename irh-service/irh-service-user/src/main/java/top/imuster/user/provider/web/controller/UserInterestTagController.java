package top.imuster.user.provider.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.controller.BaseController;
import top.imuster.user.api.pojo.InterestTagInfo;
import top.imuster.user.provider.service.InterestTagInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: UserInterestTagController
 * @Description: 用户兴趣标签
 * @author: hmr
 * @date: 2020/3/25 16:12
 */
@RestController
@RequestMapping("/interest")
public class UserInterestTagController extends BaseController {

    @Resource
    InterestTagInfoService interestTagInfoService;

    @ApiOperation("获得标签列表")
    @GetMapping
    public Message<List<InterestTagInfo>> getTagList(){
        InterestTagInfo condition = new InterestTagInfo();
        condition.setState(2);
        List<InterestTagInfo> interestTagInfos = interestTagInfoService.selectEntryList(condition);
        return Message.createBySuccess(interestTagInfos);
    }

    @ApiOperation("根据id获得简略信息")
    @GetMapping("/{id}")
    public Message<InterestTagInfo> getDetailById(@PathVariable("id") Long id){
        InterestTagInfo interestTagInfo = interestTagInfoService.selectEntryList(id).get(0);
        return Message.createBySuccess(interestTagInfo);
    }

    @DeleteMapping("/{id}")
    public Message<String> delete(@PathVariable("id") Long id){
        InterestTagInfo condition = new InterestTagInfo();
        condition.setState(1);
        condition.setId(id);
        interestTagInfoService.updateByKey(condition);
        return Message.createBySuccess();
    }

    @PutMapping
    public Message<String> add(@RequestBody InterestTagInfo interestTagInfo){
        interestTagInfoService.insertEntry(interestTagInfo);
        return Message.createBySuccess();
    }

    @PostMapping
    public Message<String> edit(@RequestBody InterestTagInfo interestTagInfo){
        interestTagInfoService.insertEntry(interestTagInfo);
        return Message.createBySuccess();
    }
}
