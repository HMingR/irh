package top.imuster.order.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.UserDto;
import top.imuster.order.api.pojo.ProductDonationApplyInfo;

/**
 * ProductDonationApplyInfoService接口
 * @author 黄明人
 * @since 2020-04-14 16:45:13
 */
public interface ProductDonationApplyInfoService extends BaseService<ProductDonationApplyInfo, Long> {

    /**
     * @Author hmr
     * @Description 申请
     * @Date: 2020/4/14 16:54
     * @param userInfo
     * @param applyInfo
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> apply(UserDto userInfo, ProductDonationApplyInfo applyInfo);

    /**
     * @Author hmr
     * @Description 审核
     * @Date: 2020/4/14 17:21
     * @param id
     * @param approveDto
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> approve(ProductDonationApplyInfo approveDto);
}