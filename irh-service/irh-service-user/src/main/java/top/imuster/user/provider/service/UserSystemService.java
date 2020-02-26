package top.imuster.user.provider.service;

import top.imuster.common.base.wrapper.Message;
import top.imuster.user.api.dto.UserTrendDto;

/**
 * @ClassName: UserSystemService
 * @Description: UserSystemService
 * @author: hmr
 * @date: 2020/2/26 10:08
 */
public interface UserSystemService {

    /**
     * @Author hmr
     * @Description 计算用户趋势图
     * @Date: 2020/2/26 10:14
     * @param type
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.user.api.dto.UserTrendDto>
     **/
    Message<UserTrendDto> userTrend(Integer type);
}
