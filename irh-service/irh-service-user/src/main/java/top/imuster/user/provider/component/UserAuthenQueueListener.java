package top.imuster.user.provider.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.imuster.common.core.dto.SendAuthenRecordDto;
import top.imuster.user.provider.service.UserAuthenRecordInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: UserAuthenQueueListener
 * @Description: UserAuthenQueueListener
 * @author: hmr
 * @date: 2020/3/29 16:11
 */
@Component
@Slf4j
public class UserAuthenQueueListener {

    @Resource
    private UserAuthenRecordInfoService userAuthenRecordInfoService;

    @Autowired
    ObjectMapper objectMapper;

    @RabbitListener(queues = "queue_info_authenRecord")
    public void listener(String msg){
        try{
            SendAuthenRecordDto recordDto = objectMapper.readValue(msg, SendAuthenRecordDto.class);
            userAuthenRecordInfoService.writeFromMQ2DB(recordDto);
        }catch (Exception e){
            log.error("------>无法将认证服务器发送到MQ中的数据序列化成对象,MQ中的数据为{},{}", msg, e.getStackTrace());
        }
    }

}
