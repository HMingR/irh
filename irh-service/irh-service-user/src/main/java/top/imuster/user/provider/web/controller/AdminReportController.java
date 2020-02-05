package top.imuster.user.provider.web.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.user.api.pojo.ReportFeedbackInfo;
import top.imuster.user.provider.service.ReportFeedbackInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: AdminController
 * @Description: 管理员管理会员的反馈的控制器
 * @author: hmr
 * @date: 2020/1/11 16:09
 */
@RestController
@RequestMapping("/admin/report")
public class AdminReportController extends BaseController {

    @Resource
    ReportFeedbackInfoService reportFeedbackInfoService;

    /**
     * @Description: 管理员分页条件查询用户举报反馈失败
     * @Author: hmr
     * @Date: 2020/1/11 16:06
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "管理员分页条件查询用户举报反馈",httpMethod = "POST")
    @PostMapping
    public Message<Page<ReportFeedbackInfo>> reportFeedbackList(@ApiParam Page<ReportFeedbackInfo> page){
        ReportFeedbackInfo searchCondition = page.getSearchCondition();
        Page<ReportFeedbackInfo> feedbackInfoPage = reportFeedbackInfoService.selectPage(searchCondition, page);
        return Message.createBySuccess(feedbackInfoPage);
    }

    @ApiOperation("高级查询,统计被举报的目标的总次数或被举报人的总次数(当要查询被举报目标的总次数或者举报人举报次数时，将其中的targetId或者customerId置为-1即可)")
    @PostMapping("/statisic")
    public Message<Page<ReportFeedbackInfo>> statistics(@ApiParam @RequestBody Page<ReportFeedbackInfo> page){
        Page<ReportFeedbackInfo> statistic = reportFeedbackInfoService.statistic(page);
        return Message.createBySuccess(statistic);
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
    @GetMapping("/statisic/{type}/{id}")
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
    @GetMapping("/{id}")
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
    @PostMapping("/process")
    public Message<String> processReport(@ApiParam("ReportFeedbackInfo实体类") @RequestBody ReportFeedbackInfo reportFeedbackInfo, BindingResult bindingResult) throws Exception {
        validData(bindingResult);
        Long userId = getCurrentUserIdFromCookie();
        reportFeedbackInfoService.processReport(reportFeedbackInfo, userId);
        return Message.createBySuccess("处理成功");
    }
}
