package top.imuster.order.provider.web.rpc;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.dto.UserDto;
import top.imuster.order.api.pojo.ProductDonationApplyInfo;
import top.imuster.order.api.service.DonationApplyServiceFeignApi;
import top.imuster.order.provider.service.ProductDonationApplyInfoService;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @ClassName: DonationApplyController
 * @Description: 公益基金申请
 * @author: hmr
 * @date: 2020/4/14 16:48
 */
@RestController
@RequestMapping("/feign/order/donation")
public class DonationApplyServiceFeignClient extends BaseController implements DonationApplyServiceFeignApi  {

    @Resource
    ProductDonationApplyInfoService productDonationApplyInfoService;

    @Override
    @PostMapping
    public Message<String> apply(@RequestBody ProductDonationApplyInfo applyInfo){
        UserDto userInfo = getCurrentUserFromCookie();
        return productDonationApplyInfoService.apply(userInfo, applyInfo);
    }

    @Override
    @PostMapping("/approve")
    public Message<String> approve(@RequestBody ProductDonationApplyInfo approveInfo) {
        return productDonationApplyInfoService.approve(approveInfo);
    }

    @PostMapping("/grant/{operatorId}/{id}")
    public Message<String> grantMoney(@PathVariable("id") Long applyId, @PathVariable("operatorId") Long operatorId) throws JsonProcessingException {
        return productDonationApplyInfoService.grant(applyId, operatorId);
    }

    @GetMapping("/determine/{operatorId}/{applyId}")
    public Message<String> determineGrant(@PathVariable("applyId") Long applyId, @PathVariable("operatorId") Long operatorId) throws IOException {
        return productDonationApplyInfoService.determine(applyId, operatorId);
    }



}
