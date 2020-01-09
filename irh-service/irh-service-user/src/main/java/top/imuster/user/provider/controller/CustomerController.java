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
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.goods.api.pojo.ProductEvaluateInfo;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.api.service.OrderServiceFeignApi;
import top.imuster.user.api.pojo.ConsumerInfo;
import top.imuster.user.provider.exception.UserException;
import top.imuster.user.provider.service.ConsumerInfoService;

import javax.annotation.Resource;
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

    @Autowired
    OrderServiceFeignApi orderServiceFeignApi;

    @Resource
    ConsumerInfoService consumerInfoService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @ApiOperation("登录，成功返回token")
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

    /**
     * @Description: 会员注册
     * @Author: hmr
     * @Date: 2019/12/26 19:29
     * @param consumerInfo
     * @param bindingResult
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @PostMapping("/register")
    public Message register(@RequestBody @Validated({ValidateGroup.register.class}) ConsumerInfo consumerInfo, BindingResult bindingResult){
        validData(bindingResult);
        try{
            consumerInfo.setState(30);
            consumerInfoService.insertEntry(consumerInfo);
            return Message.createBySuccess("注册成功,请完善后续必要的信息才能正常使用");
        }catch (Exception e){
            logger.error("会员注册失败", e.getMessage(), e);
            throw new UserException(e.getMessage());
        }
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
    @ApiOperation(value = "修改会员的个人信息", httpMethod = "POST")
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
     * @Description: 用户根据条件分页查看自己的账单
     * @Author: hmr
     * @Date: 2019/12/27 15:42
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @PostMapping("/order")
    public Message orderList(@RequestBody Page<OrderInfo> page){
        try{
            return orderServiceFeignApi.list(page);
        }catch (Exception e){
            logger.error("会员查询账单失败",e.getMessage(),e);
            throw new UserException(e.getMessage());
        }
    }
}
