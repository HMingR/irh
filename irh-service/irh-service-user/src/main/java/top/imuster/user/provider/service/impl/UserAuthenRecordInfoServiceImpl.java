package top.imuster.user.provider.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.SendAuthenRecordDto;
import top.imuster.common.core.dto.SendUserCenterDto;
import top.imuster.common.core.utils.DateUtil;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.user.api.pojo.UserAuthenRecordInfo;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.provider.dao.UserAuthenRecordInfoDao;
import top.imuster.user.provider.service.UserAuthenRecordInfoService;
import top.imuster.user.provider.service.UserInfoService;

import javax.annotation.Resource;

/**
 * UserAuthenRecordInfoService 实现类
 * @author 黄明人
 * @since 2020-03-27 15:53:30
 */
@Service("userAuthenRecordInfoService")
public class UserAuthenRecordInfoServiceImpl extends BaseServiceImpl<UserAuthenRecordInfo, Long> implements UserAuthenRecordInfoService {

    @Resource
    private UserAuthenRecordInfoDao userAuthenRecordInfoDao;

    @Resource
    private UserInfoService userInfoService;

    @Autowired
    private GenerateSendMessageService generateSendMessageService;

    @Override
    public BaseDao<UserAuthenRecordInfo, Long> getDao() {
        return this.userAuthenRecordInfoDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Message<String> authen(UserAuthenRecordInfo userAuthenRecordInfo) {
        userAuthenRecordInfoDao.updateByKey(userAuthenRecordInfo);
        Integer state = userAuthenRecordInfo.getState();
        Long userId = userAuthenRecordInfo.getUserId();
        String content;
        if(state == 2){
            UserInfo userInfo = new UserInfo();
            userInfo.setId(userId);
            userInfo.setState(40);
            userInfoService.updateByKey(userInfo);
            content = "您已经通过了实名认证";
        }else {
            content = "对不起,通过AI和人工审核,您提交的信息未能通过认证,请仔细核对个人信息和提供的照片上的信息是否一致";
        }

        //发送消息
        SendUserCenterDto message = new SendUserCenterDto();
        message.setContent(content);
        message.setDate(DateUtil.now());
        message.setFromId(0L);
        message.setToId(userId);
        generateSendMessageService.sendToMq(message);
        return Message.createBySuccess();
    }

    @Override
    public void writeFromMQ2DB(SendAuthenRecordDto recordDto) {
        UserAuthenRecordInfo recordInfo = new UserAuthenRecordInfo();
        recordInfo.setUserId(recordDto.getUserId());
        recordInfo.setInputName(recordDto.getInputName());
        recordInfo.setUserId(recordDto.getUserId());
        userAuthenRecordInfoDao.insertEntry(recordInfo);
    }


}