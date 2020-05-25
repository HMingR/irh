package top.imuster.user.provider.web.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.user.api.pojo.ReportFeedbackInfo;
import top.imuster.user.api.pojo.ReportTypeInfo;
import top.imuster.user.provider.service.ReportFeedbackInfoService;
import top.imuster.user.provider.service.ReportTypeInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: ReportController
 * @Description: ReportController  举报相关的控制器
 * @author: hmr
 * @date: 2020/5/25 10:05
 */
@RestController
@RequestMapping("/report")
public class ReportController extends BaseController {

    @Resource
    ReportTypeInfoService reportTypeInfoService;

    @Resource
    ReportFeedbackInfoService reportFeedbackInfoService;

    @GetMapping
    public Message<Page<ReportTypeInfo>> getList(){
        ReportTypeInfo reportTypeInfo = new ReportTypeInfo();
        reportTypeInfo.setState(2);
        Page<ReportTypeInfo> page = new Page<>();
        page = reportTypeInfoService.selectPage(reportTypeInfo, page);
        return Message.createBySuccess(page);
    }

    /**
     * @Description: 用户举报
     * @Author: hmr
     * @Date: 2020/1/11 12:18
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @PostMapping
    @ApiOperation(value = "用户举报(type可选择 1-商品举报 2-留言举报 3-评价举报 4-帖子举报),id则为举报对象的id", httpMethod = "GET")
    public Message<String> reportFeedback(@RequestBody ReportFeedbackInfo reportFeedbackInfo) throws Exception {
        Long userId = getCurrentUserIdFromCookie();
        reportFeedbackInfo.setCustomerId(userId);
        reportFeedbackInfo.setState(2);
        reportFeedbackInfoService.insertEntry(reportFeedbackInfo);
        return Message.createBySuccess("反馈成功,我们会尽快处理");
    }


    /**
     * @Description: 管理员分页条件查询用户举报反馈失败
     * @Author: hmr
     * @Date: 2020/1/11 16:06
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "管理员分页条件查询用户举报反馈",httpMethod = "POST")
    @PostMapping("/admin")
    public Message<Page<ReportFeedbackInfo>> reportFeedbackList(@ApiParam @RequestBody Page<ReportFeedbackInfo> page){
        Page<ReportFeedbackInfo> list = reportFeedbackInfoService.list(page);
        return Message.createBySuccess(list);
    }

    @ApiOperation("根据targetId获得所有举报的信息")
    @GetMapping("/admin/detail/{targetId}/{type}")
    public Message<List<ReportFeedbackInfo>> getReportDetailByTargetId(@PathVariable("targetId") Long targetId, @PathVariable("type") Integer type){
        return reportFeedbackInfoService.getDetailsByTargetId(targetId, type);
    }

    /**
     * @Author hmr
     * @Description 获得某一个的具体
     * @Date: 2020/1/16 18:50
     * @param type
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("根据id高级查询，type的取值(1-被举报的目标  2-举报人)，该id不是主键id，是被举报目标id或者举报人id")
    @GetMapping("/admin/statisic/{type}/{id}")
    public Message<List<ReportFeedbackInfo>> getStatisticsById(@ApiParam("1-被举报的目标  2-举报人") @PathVariable("type") Integer type,@ApiParam("该id不是主键id，是被举报目标id或者举报人id") @PathVariable("id")Long id){
        ReportFeedbackInfo reportFeedbackInfo = new ReportFeedbackInfo();
        if(type == 1) reportFeedbackInfo.setTargetId(id);
        if(type == 2) reportFeedbackInfo.setCustomerId(id);
        reportFeedbackInfo.setState(2);
        List<ReportFeedbackInfo> reportFeedbackInfos = reportFeedbackInfoService.selectEntryList(reportFeedbackInfo);
        return Message.createBySuccess(reportFeedbackInfos);
    }

    /**
     * @Description: 根据id查询用户反馈
     * @Author: hmr
     * @Date: 2020/1/11 16:17
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "根据id查询用户反馈", httpMethod = "GET")
    @GetMapping("/admin/{id}")
    public Message<ReportFeedbackInfo> getReportById(@ApiParam("反馈id") @PathVariable("id")Long id){
        ReportFeedbackInfo search = reportFeedbackInfoService.selectEntryList(id).get(0);
        return Message.createBySuccess(search);
    }


    /**
     * @Description: 处理反馈
     * @Author: hmr
     * @Date: 2020/1/11 16:18
     * @param
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "处理用户提交的举报", httpMethod = "POST")
    @PostMapping("/admin/process")
    public Message<String> processReport(@ApiParam("ReportFeedbackInfo实体类") @RequestBody ReportFeedbackInfo reportFeedbackInfo, BindingResult bindingResult) throws Exception {
        validData(bindingResult);
        Long userId = getCurrentUserIdFromCookie();
        reportFeedbackInfoService.processReport(reportFeedbackInfo, userId);
        return Message.createBySuccess("处理成功");
    }

    @GetMapping("/admin/process/{targetId}/{result}/{remark}")
    @NeedLogin
    public Message<String> processByTargetId(@PathVariable("targetId")Long targetId, @PathVariable("result") Integer result, @PathVariable("remark")String remark){
        Long userId = getCurrentUserIdFromCookie();
        return reportFeedbackInfoService.processReportByTargetId(targetId, result, remark, userId);
    }
}
