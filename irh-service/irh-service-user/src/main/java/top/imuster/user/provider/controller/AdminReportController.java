package top.imuster.user.provider.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.user.api.pojo.ReportFeedbackInfo;
import top.imuster.user.provider.exception.UserException;
import top.imuster.user.provider.service.ReportFeedbackInfoService;

import javax.annotation.Resource;

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
    @ApiOperation(value = "管理员分页条件查询用户举报反馈失败",httpMethod = "POST")
    @PostMapping
    public Message reportFeedbackList(Page<ReportFeedbackInfo> page){
        try{
            ReportFeedbackInfo searchCondition = page.getSearchCondition();
            Page<ReportFeedbackInfo> feedbackInfoPage = reportFeedbackInfoService.selectPage(searchCondition, page);
            return Message.createBySuccess(feedbackInfoPage);
        }catch (Exception e){
            logger.error("管理员分页条件查询用户举报反馈失败",e.getMessage(), e);
            throw new UserException("分页条件查询用户举报反馈失败");
        }
    }

    /**
     * @Description: 根据id查询用户反馈
     * @Author: hmr
     * @Date: 2020/1/11 16:17
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("根据id查询用户反馈")
    @GetMapping("/{id}")
    public Message getReportById(@PathVariable("id")Long id){
        try{
            ReportFeedbackInfo search = reportFeedbackInfoService.selectEntryList(id).get(0);
            return Message.createBySuccess(search);
        }catch (Exception e){
            logger.error("根据id查询用户反馈失败",e.getMessage(), e);
            throw new UserException("查询失败,请刷新后重试或联系超级管理员");
        }
    }

    /**
     * @Description: 处理反馈
     * @Author: hmr
     * @Date: 2020/1/11 16:18
     * @param
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("处理用户提交的举报")
    @PostMapping("/process")
    public Message processReport(@RequestBody ReportFeedbackInfo reportFeedbackInfo, BindingResult bindingResult){
        validData(bindingResult);
        try{
            reportFeedbackInfoService.processReport(reportFeedbackInfo);
            return Message.createBySuccess("处理成功");
        }catch (Exception e){
            logger.error("处理用户提交的举报失败",e.getMessage(), e);
            throw new UserException("处理失败");
        }
    }
}
