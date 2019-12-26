package top.imuster.user.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.user.api.bo.ConsumerDetails;
import top.imuster.user.api.pojo.ConsumerInfo;

/**
 * ConsumerInfoService接口
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
public interface ConsumerInfoService extends BaseService<ConsumerInfo, Long> {
    /**
     * @Description: 会员登录使用
     * @Author: hmr
     * @Date: 2019/12/26 15:54
     * @param email
     * @param password
     * @reture: java.lang.String 返回token
     **/
    String login(String email, String password);

    /**
     * @Description:
     * @Author: hmr
     * @Date: 2019/12/26 15:54
     * @param userName
     * @reture: top.imuster.user.api.bo.ConsumerDetails
     **/
    ConsumerDetails loadConsumerDetailsByName(String userName);
}