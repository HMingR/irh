package top.imuster.order.provider.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.UserDto;
import top.imuster.order.api.pojo.ProductDonationApplyInfo;

import java.io.IOException;

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
     * @param applyInfo
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

    /**
     * @Author hmr
     * @Description 计算方法金额使用的订单
     * @Date: 2020/4/15 9:08
     * @param operatorId 操作者
     * @param applyId  申请表的主键id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>   返回自动选择的订单总金额，需要用户确定
     **/
    Message<String> grant(Long operatorId, Long applyId) throws JsonProcessingException;

    /**
     * @Author hmr
     * @Description 确定转账
     * @Date: 2020/4/15 16:02
     * @param applyId
     * @param currentUserIdFromCookie
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> determine(Long applyId, Long operatorId) throws IOException;
}