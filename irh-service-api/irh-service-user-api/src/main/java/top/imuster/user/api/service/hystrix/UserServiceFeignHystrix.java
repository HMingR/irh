package top.imuster.user.api.service.hystrix;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.imuster.common.base.wrapper.Message;
import top.imuster.user.api.pojo.ExamineRecordInfo;
import top.imuster.user.api.pojo.RoleInfo;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.api.service.UserServiceFeignApi;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: UserServiceFeignHystrix
 * @Description: UserServiceFeignHystrix
 * @author: hmr
 * @date: 2020/1/27 16:34
 */
@Component
public class UserServiceFeignHystrix implements FallbackFactory<UserServiceFeignApi> {

    private static final Logger log = LoggerFactory.getLogger(UserServiceFeignHystrix.class);

    @Override
    public UserServiceFeignApi create(Throwable throwable) {
        return new UserServiceFeignApi() {
            @Override
            public UserInfo loadUserInfoByEmail(String email) {
                log.error("登录失败");
                return null;
            }

            @Override
            public List<RoleInfo> getAllRoleAndAuth() {
                return null;
            }

            @Override
            public List<String> getRoleByUserName(String loginName) {
                return null;
            }

            @Override
            public Message<String> updateUserState(Long userId, Integer state) {
                return null;
            }

            @Override
            public Map<String, String> getUserAddressAndPhoneById(Long userId) {
                return null;
            }

            @Override
            public String getUserEmailById(Long holderId) {
                return null;
            }

            @Override
            public UserInfo getInfoByWxOpenId(String openId) {
                return null;
            }

            @Override
            public boolean saveExamineRecord2DB(ExamineRecordInfo examineRecordInfo) {
                return false;
            }
        };
    }
}
