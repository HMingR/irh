package top.imuster.user.provider.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.SendMessageDto;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.user.api.dto.CheckValidDto;
import top.imuster.user.api.pojo.ConsumerInfo;
import top.imuster.user.api.pojo.ReportFeedbackInfo;
import top.imuster.user.provider.exception.UserException;
import top.imuster.user.provider.service.ConsumerInfoService;
import top.imuster.user.provider.service.ReportFeedbackInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: CustomerController
 * @Description: customer的控制器
 * @author: hmr
 * @date: 2019/12/18 19:11
 */
@Api(tags = "用户controller,这个控制器主要是对自己信息的一些操作")
@RestController
@RequestMapping("/consumer")
public class CustomerController extends BaseController {
    @Resource
    ReportFeedbackInfoService reportFeedbackInfoService;

    @Resource
    ConsumerInfoService consumerInfoService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @ApiOperation(value = "登录，成功返回token", httpMethod = "POST")
    @PostMapping("/login")
    public Message managementLogin(@Validated(ValidateGroup.loginGroup.class) @RequestBody ConsumerInfo consumerInfo, BindingResult result){
        validData(result);
        try{
            String token = consumerInfoService.login(consumerInfo.getEmail(), consumerInfo.getPassword());
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            tokenMap.put("tokenHead", GlobalConstant.JWT_TOKEN_HEAD);
            return Message.createBySuccess(tokenMap);
        }catch (Exception e){
            logger.error(GlobalConstant.getErrorLog("会员登录失败,{}"), e.getMessage(), consumerInfo);
            return Message.createByError(e.getMessage());
        }
    }

    @ApiOperation(value = "发送email验证码,type标识(1-注册验证码  2-重置密码验证码),当type为2时，email可取任意值",httpMethod = "GET")
    @GetMapping("/sendCode/{type}/{email}")
    public Message getCode(HttpServletRequest request, @PathVariable("type") Integer type, @PathVariable("email") String email, SendMessageDto sendMessageDto) throws Exception {
        if(type == 1){
            consumerInfoService.getCode(sendMessageDto, email, type);
        }
        if(type == 2){
            Long userId = getIdByToken(request);
            ConsumerInfo consumerInfo = consumerInfoService.selectEntryList(userId).get(0);
            consumerInfoService.getCode(sendMessageDto, consumerInfo.getEmail(), type);
        }
        return Message.createBySuccess();
    }

    /**
     * @Description: 会员注册
     * @Author: hmr
     * @Date: 2019/12/26 19:29
     * @param consumerInfo
     * @param bindingResult
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "会员注册,code为发送的验证码", httpMethod = "POST")
    @PostMapping("/register/{code}")
    public Message register(@RequestBody @Validated({ValidateGroup.register.class}) ConsumerInfo consumerInfo, BindingResult bindingResult, @PathVariable String code) throws Exception {
        validData(bindingResult);
        consumerInfoService.register(consumerInfo, code);
        return Message.createBySuccess("注册成功,请完善后续必要的信息才能正常使用");
    }

    /**
     * @Description 用户在注册的时候需要校验各种参数
     * @Author hmr
     * @Date 12:53 2020/1/14
     * @Param checkValidDto
     * @param bindingResult
     * @return top.imuster.common.base.wrapper.Message 
     **/
    @ApiOperation(value = "用户在注册的时候需要校验各种参数(用户名、邮箱、手机号等)必须唯一",httpMethod = "POST")
    @PostMapping("/check")
    public Message checkValid(@RequestBody CheckValidDto checkValidDto, BindingResult bindingResult) throws Exception {
        validData(bindingResult);
        boolean flag = consumerInfoService.checkValid(checkValidDto);
        if(flag){
            return Message.createBySuccess();
        }
        return Message.createByError(checkValidDto.getType().getTypeName() + "已经存在");
    }

    /**
     * @Description: 修改会员的个人信息
     * @Author: hmr
     * @Date: 2019/12/26 19:37
     * @param consumerInfo
     * @param bindingResult
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @PostMapping("/edit")
    @ApiOperation(value = "修改会员的个人信息(先校验一些信息是否存在)", httpMethod = "POST")
    public Message editInfo(@RequestBody @Validated(ValidateGroup.editGroup.class) ConsumerInfo consumerInfo, BindingResult bindingResult){
        validData(bindingResult);
        try{
            consumerInfoService.updateByKey(consumerInfo);
            return Message.createBySuccess("修改个人信息成功");
        }catch (Exception e){
            logger.error("修改用户个人信息失败",e, e.getMessage());
            throw new UserException(e.getMessage());
        }
    }

    /**
     * @Description: 用户举报
     * @Author: hmr
     * @Date: 2020/1/11 12:18
     * @param type
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @GetMapping("/report/{type}/{id}")
    @ApiOperation(value = "用户举报(type可选择 1-商品举报 2-留言举报 3-评价举报 4-帖子举报),id则为举报对象的id", httpMethod = "GET")
    public Message reportFeedback(@PathVariable("type") Integer type, @PathVariable("id") Long id, HttpServletRequest request) throws Exception {
        Long userId = getIdByToken(request);
        ReportFeedbackInfo reportFeedbackInfo = new ReportFeedbackInfo();
        reportFeedbackInfo.setCustomerId(userId);
        reportFeedbackInfo.setType(type);
        reportFeedbackInfo.setTargetId(id);
        reportFeedbackInfo.setState(2);
        reportFeedbackInfoService.insertEntry(reportFeedbackInfo);
        return Message.createBySuccess("反馈成功,我们会尽快处理");
    }
}
