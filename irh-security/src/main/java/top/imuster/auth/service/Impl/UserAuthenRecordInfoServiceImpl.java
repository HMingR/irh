package top.imuster.auth.service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.imuster.auth.dao.UserAuthenRecordInfoDao;
import top.imuster.auth.service.UserAuthenInfoService;
import top.imuster.auth.service.UserAuthenRecordInfoService;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.rabbitMq.SendAuthenRecordDto;
import top.imuster.common.core.dto.rabbitMq.SendUserCenterDto;
import top.imuster.common.core.utils.DateUtil;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.user.api.dto.UserAuthenResultDto;
import top.imuster.security.api.pojo.UserAuthenInfo;
import top.imuster.security.api.pojo.UserAuthenRecordInfo;
import top.imuster.user.api.service.UserServiceFeignApi;

import javax.annotation.Resource;

/**
 * UserAuthenRecordInfoService 实现类
 * @author 黄明人
 * @since 2020-03-27 15:53:30
 */
@Service("userAuthenRecordInfoService")
public class UserAuthenRecordInfoServiceImpl extends BaseServiceImpl<UserAuthenRecordInfo, Long> implements UserAuthenRecordInfoService {

    @Resource
    UserAuthenInfoService userAuthenInfoService;

    @Resource
    private UserAuthenRecordInfoDao userAuthenRecordInfoDao;

    @Autowired
    private UserServiceFeignApi userServiceFeignApi;

    @Autowired
    private GenerateSendMessageService generateSendMessageService;

    @Override
    public BaseDao<UserAuthenRecordInfo, Long> getDao() {
        return this.userAuthenRecordInfoDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Message<String> authen(UserAuthenRecordInfo userAuthenRecordInfo) {
        userAuthenRecordInfoDao.updateStateByUserId(userAuthenRecordInfo);
        Integer result = userAuthenRecordInfo.getResult();
        Long userId = userAuthenRecordInfo.getUserId();
        String content;
        if(result == 2){
            UserAuthenResultDto userAuthenResultDto = new UserAuthenResultDto();
            userAuthenResultDto.setUserId(userId);
            userAuthenResultDto.setCertificateNum(userAuthenRecordInfo.getInputCardNo());
            userServiceFeignApi.userAuthenSuccess(userAuthenResultDto);

            UserAuthenInfo userAuthenInfo = new UserAuthenInfo();
            userAuthenInfo.setCertificateNum(userAuthenRecordInfo.getInputCardNo());
            userAuthenInfo.setRealName(userAuthenRecordInfo.getInputName());
            userAuthenInfo.setUserId(userAuthenRecordInfo.getUserId());
            userAuthenInfo.setType(userAuthenRecordInfo.getType());
            userAuthenInfoService.insertEntry(userAuthenInfo);

            content = "您已经通过了实名认证";
        }else {
            content = "对不起,通过AI和人工审核,您提交的信息未能通过认证,请仔细核对个人信息和提供的照片上的信息是否一致";
        }

        //发送消息
        SendUserCenterDto message = new SendUserCenterDto();
        message.setContent(content);
        message.setDate(DateUtil.now());
        message.setFromId(-1L);
        message.setToId(userId);
        message.setNewsType(70);
        generateSendMessageService.sendToMq(message);
        return Message.createBySuccess();
    }

    @Override
    public void writeFromMQ2DB(SendAuthenRecordDto recordDto) {
        UserAuthenRecordInfo recordInfo = new UserAuthenRecordInfo();
        recordInfo.setUserId(recordDto.getUserId());
        recordInfo.setInputName(recordDto.getInputName());
        recordInfo.setUserId(recordDto.getUserId());
        recordInfo.setInputCardNo(recordDto.getInputNum());
        recordInfo.setResult(recordDto.getResult());
        recordInfo.setType(recordDto.getAuthenType());
        userAuthenRecordInfoDao.insertEntry(recordInfo);

        if(recordInfo.getResult() == 2){
            UserAuthenInfo userAuthenInfo = new UserAuthenInfo();
            userAuthenInfo.setRealName(recordDto.getInputName());
            userAuthenInfo.setCertificateNum(recordDto.getInputNum());
            userAuthenInfo.setType(recordDto.getAuthenType());
            userAuthenInfoService.insertEntry(userAuthenInfo);
        }
    }
}