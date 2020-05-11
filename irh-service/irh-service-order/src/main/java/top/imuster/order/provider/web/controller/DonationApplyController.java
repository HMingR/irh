package top.imuster.order.provider.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.order.api.pojo.ProductDonationApplyInfo;
import top.imuster.order.provider.service.ProductDonationApplyInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: DonationApplyController
 * @Description: 对外提供的捐赠订单查询接口
 * @author: hmr
 * @date: 2020/4/16 8:49
 */
@RestController
@RequestMapping("/donation")
public class DonationApplyController {

    @Resource
    ProductDonationApplyInfoService productDonationApplyInfoService;

    /**
     * @Author hmr
     * @Description 分页查看已经转账了的申请
     * @Date: 2020/4/16 8:51
     * @param
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.order.api.pojo.ProductDonationApplyInfo>>
     **/
    @GetMapping("/finish/{pageSize}/{currentPage}")
    public Message<Page<ProductDonationApplyInfo>> finishApplyList(@PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage){
        return productDonationApplyInfoService.finishApplyList(pageSize, currentPage);
    }

    @GetMapping("/unfinish/{pageSize}/{currentPage}")
    public Message<Page<ProductDonationApplyInfo>> unfinishApplyList(@PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage){
        return productDonationApplyInfoService.unfinishApplyList(pageSize, currentPage);
    }

    @GetMapping("/detail/{type}/{id}")
    public Message<ProductDonationApplyInfo> getApplyInfoById(@PathVariable("type") Integer type, @PathVariable("id") Long applyId){
        if(type != 5 && type != 2) return Message.createByError("参数异常");
        return productDonationApplyInfoService.getApplyInfoById(type, applyId);
    }

    /**
     * @Author hmr
     * @Description 获得最新的5个已经发放资金的申请
     * @Date: 2020/4/18 10:42
     * @param
     * @reture: top.imuster.common.base.wrapper.Message<java.util.List<top.imuster.order.api.pojo.ProductDonationApplyInfo>>
     **/
    @GetMapping
    public Message<List<ProductDonationApplyInfo>> getNewestApply(){
        return productDonationApplyInfoService.getNewestApply();
    }

    /**
     * @Author hmr
     * @Description 用户给申请提供自己的态度
     * @Date: 2020/4/27 8:53
     * @param type 1-不同意  2-同意
     * @param targetId 申请表的主键id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @NeedLogin
    @GetMapping("/attribute/{type}/{targetId}")
    public Message<String> upOrDown(@PathVariable("type") Integer type, @PathVariable("targetId") Long targetId){
        if(type != 1 && type != 2) return Message.createByError("参数错误");
        return productDonationApplyInfoService.upOrDownApply(type, targetId);
    }
}
