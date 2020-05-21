package top.imuster.user.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.rabbitMq.SendAuthenRecordDto;
import top.imuster.user.api.pojo.UserAuthenRecordInfo;

/**
 * UserAuthenRecordInfoService接口
 * @author 黄明人
 * @since 2020-03-27 15:53:30
 */
public interface UserAuthenRecordInfoService extends BaseService<UserAuthenRecordInfo, Long> {

    /**
     * @Author hmr
     * @Description 人工认证
     * @Date: 2020/3/29 15:56
     * @param userAuthenRecordInfo
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> authen(UserAuthenRecordInfo userAuthenRecordInfo);

    /**
     * @Author hmr
     * @Description 将认证服务器无法认证的信息从MQ保存到DB
     * @Date: 2020/3/29 16:14
     * @param recordDto
     * @reture: void
     **/
    void writeFromMQ2DB(SendAuthenRecordDto recordDto);
}