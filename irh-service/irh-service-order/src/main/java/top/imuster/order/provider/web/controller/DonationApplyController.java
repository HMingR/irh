package top.imuster.order.provider.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.order.api.pojo.ProductDonationApplyInfo;
import top.imuster.order.provider.service.ProductDonationApplyInfoService;

import javax.annotation.Resource;

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
}
