package top.imuster.user.provider.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.imuster.common.core.annotation.LogAnnotation;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.enums.LogTypeEnum;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.file.api.service.FileServiceFeignApi;
import top.imuster.user.api.dto.CheckValidDto;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.api.pojo.ReportFeedbackInfo;
import top.imuster.user.provider.service.UserInfoService;
import top.imuster.user.provider.service.ReportFeedbackInfoService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: CustomerController
 * @Description: customer的控制器
 * @author: hmr
 * @date: 2019/12/18 19:11
 */
@Api(tags = "用户controller,这个控制器主要是对自己信息的一些操作")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    private List<String> types;

    @Resource
    ReportFeedbackInfoService reportFeedbackInfoService;

    @Resource
    UserInfoService userInfoService;

    @Autowired
    FileServiceFeignApi fileServiceFeignApi;

    @PostConstruct
    private void createTypes(){
        types = new ArrayList<>();
        types.add("jpg");
        types.add("png");
        types.add("bmp");
    }

    /**
     * @Description 用户在注册的时候需要校验各种参数
     * @Author hmr
     * @Date 12:53 2020/1/14
     * @Param checkValidDto
     * @param bindingResult
     * @return top.imuster.common.base.wrapper.Message 
     **/
    @ApiOperation(value = "修改信息前需要校验各种参数(用户名、邮箱、手机号等)必须唯一",httpMethod = "POST")
    @PostMapping("/check")
    public Message<String> checkValid(@ApiParam("CheckValidDto实体类") @RequestBody CheckValidDto checkValidDto, BindingResult bindingResult) throws Exception {
        validData(bindingResult);
        boolean flag = userInfoService.checkValid(checkValidDto);
        if(flag){
            return Message.createBySuccess();
        }
        return Message.createByError(checkValidDto.getType().getTypeName() + "已经存在");
    }

    /**
     * @Description: 修改会员的个人信息
     * @Author: hmr
     * @Date: 2019/12/26 19:37
     * @param userInfo
     * @param bindingResult
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @PostMapping("/edit")
    @ApiOperation(value = "修改会员的个人信息(先校验一些信息是否存在),以表单的形式上传,不是用json,其中表单中各个标签的按钮name必须和实体类保持一致", httpMethod = "POST")
    public Message<String> editInfo(@ApiParam("上传的头像,前端按钮的name属性必须为file") @RequestParam("file") MultipartFile file, @ApiParam("ConsumerInfo实体类") @Validated(ValidateGroup.editGroup.class) UserInfo userInfo, BindingResult bindingResult) throws Exception{
        validData(bindingResult);

        //头像不为空
        if(!file.isEmpty()){
            int last = file.getOriginalFilename().length();
            String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), last);
            if(!types.contains(fileType)){
                return Message.createByError("图片格式不正确,请更换图片格式");
            }
            String url = fileServiceFeignApi.upload(file);
            userInfo.setPortrait(url);
        }
        userInfoService.updateByKey(userInfo);
        return Message.createBySuccess();
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
    public Message<String> reportFeedback(@ApiParam("1-商品举报 2-留言举报 3-评价举报 4-帖子举报")@PathVariable("type") Integer type, @ApiParam("举报对象的id") @PathVariable("id") Long id, HttpServletRequest request) throws Exception {
        Long userId = getIdByToken(request);
        ReportFeedbackInfo reportFeedbackInfo = new ReportFeedbackInfo();
        reportFeedbackInfo.setCustomerId(userId);
        reportFeedbackInfo.setType(type);
        reportFeedbackInfo.setTargetId(id);
        reportFeedbackInfo.setState(2);
        reportFeedbackInfoService.insertEntry(reportFeedbackInfo);
        return Message.createBySuccess("反馈成功,我们会尽快处理");
    }

    @ApiOperation(value = "根据用户id获得用户昵称", httpMethod = "GET")
    @GetMapping("/{id}")
    public Message<String> getUserNameById(@PathVariable("id") Long id){
        return Message.createBySuccess(userInfoService.getUserNameById(id));
    }

        //todo
    public Message<String> editInterestTag(){

        return null;
    }
}
