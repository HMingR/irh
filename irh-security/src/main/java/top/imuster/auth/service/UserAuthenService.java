package top.imuster.auth.service;

import top.imuster.common.base.wrapper.Message;
import top.imuster.security.api.dto.UserAuthenDto;

/**
 * @ClassName: UserAuthenServiceImpl
 * @Description: UserAuthenServiceImpl
 * @author: hmr
 * @date: 2020/3/29 11:13
 */
public interface UserAuthenService {

    /**
     * @Author hmr
     * @Description 一卡通认证
     * @Date: 2020/3/27 16:03
     * @param file
     * @param inputName
     * @param userId
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> oneCardSolution(UserAuthenDto userAuthenDto) throws Exception;


    /**
     * @Author hmr
     * @Description 用户的实名认证
     * @Date: 2020/3/27 14:57
     * @param userId 当前用户
     * @param picUri 服务器中图片的uri
     * @param inputName 输入的名字
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> realNameAuthentication(UserAuthenDto userAuthenDto);


}
