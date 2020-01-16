package top.imuster.user.provider.controller;

import io.swagger.annotations.ApiOperation;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.SendMessageDto;
import top.imuster.common.core.enums.MqTypeEnum;
import top.imuster.common.core.utils.DateUtils;
import top.imuster.user.api.enums.FeedbackEnum;
import top.imuster.user.api.pojo.ReportFeedbackInfo;
import top.imuster.user.provider.exception.UserException;
import top.imuster.user.provider.service.ReportFeedbackInfoService;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
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
    public Message reportFeedbackList(Page<ReportFeedbackInfo> page){
        try{
            ReportFeedbackInfo searchCondition = page.getSearchCondition();
            Page<ReportFeedbackInfo> feedbackInfoPage = reportFeedbackInfoService.selectPage(searchCondition, page);
            return Message.createBySuccess(feedbackInfoPage);
        }catch (Exception e){
            logger.error("管理员分页条件查询用户举报反馈失败,错误信息为{}",e.getMessage(), e);
            throw new UserException("分页条件查询用户举报反馈失败");
        }
    }

    @ApiOperation("高级查询,统计被举报的目标的总次数或被举报人的总次数(当要查询被举报目标的总次数或者举报人举报次数时，将其中的targetId或者customerId置为-1即可)")
    @PostMapping("/statisic")
    public Message statistics(@RequestBody Page<ReportFeedbackInfo> page){
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
    public Message getStatisticsById(@PathVariable("type") Integer type, @PathVariable("id")Long id){
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
    public Message getReportById(@PathVariable("id")Long id){
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
    public Message processReport(@RequestBody ReportFeedbackInfo reportFeedbackInfo, BindingResult bindingResult) throws ParseException {
        validData(bindingResult);
        ReportFeedbackInfo info = reportFeedbackInfoService.selectEntryList(reportFeedbackInfo.getId()).get(0);
        reportFeedbackInfoService.processReport(reportFeedbackInfo);
        if(reportFeedbackInfo.getResult() == 3 || info.getResult() == 5){
            SendMessageDto sendMessageDto = new SendMessageDto();
            sendMessageDto.setTopic("警告");
            sendMessageDto.setBody("您在irh中发布的" + FeedbackEnum.getNameByType(info.getType()) + "被人举报，经过核实，举报属实。如果再次发现类似情况，您的账号将被冻结");
            reportFeedbackInfoService.generateSendMessage(sendMessageDto, reportFeedbackInfo);
        }
        if(reportFeedbackInfo.getResult() == 4){
            ArrayList<SendMessageDto> sendMessageDtos = new ArrayList<>();
            SendMessageDto customerMessage = new SendMessageDto();
            SendMessageDto targetMessage = new SendMessageDto();
            customerMessage.setBody("您于" + info.getCreateTime() + "举报的关于" + info.getTargetId() + "的信息已经被管理员成功处理，已经将相关账号进行冻结。感谢您的及时反馈");
            targetMessage.setBody("由于您多次违反irh平台的相关规定或多次被用户举报并核实，您的账号已经被冻结。请联系管理员取消冻结");
            sendMessageDtos.add(customerMessage);
            sendMessageDtos.add(targetMessage);
            reportFeedbackInfoService.generateSendMessage(sendMessageDtos);
        }
        return Message.createBySuccess("处理成功");
    }
}
