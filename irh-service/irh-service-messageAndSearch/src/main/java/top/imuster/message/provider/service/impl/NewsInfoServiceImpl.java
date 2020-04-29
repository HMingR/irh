package top.imuster.message.provider.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.core.dto.SendUserCenterDto;
import top.imuster.message.pojo.NewsInfo;
import top.imuster.message.provider.dao.NewsInfoDao;
import top.imuster.message.provider.service.NewsInfoService;

import javax.annotation.Resource;

/**
 * NewsInfoService 实现类
 * @author 黄明人
 * @since 2020-01-17 17:13:09
 */
@Service("newsInfoService")
public class NewsInfoServiceImpl extends BaseServiceImpl<NewsInfo, Long> implements NewsInfoService {
    private static final Logger log = LoggerFactory.getLogger(NewsInfoServiceImpl.class);

    @Resource
    private NewsInfoDao newsInfoDao;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public BaseDao<NewsInfo, Long> getDao() {
        return this.newsInfoDao;
    }

    @Override
    public void writeFromMq(@ApiParam SendUserCenterDto sendMessageDto) throws JsonProcessingException {
        boolean flag = checkMessage(sendMessageDto);
        if(!flag) return;
        NewsInfo newsInfo = new NewsInfo();
        newsInfo.setContent(sendMessageDto.getContent());
        newsInfo.setSenderId(sendMessageDto.getFromId());
        newsInfo.setReceiverId(sendMessageDto.getToId());
        newsInfo.setTargetId(sendMessageDto.getResourceId());
        newsInfo.setNewsType(sendMessageDto.getNewsType());
        newsInfoDao.insertEntry(newsInfo);
    }

    /**
     * @Author hmr
     * @Description 校验参数是否正常
     * @Date: 2020/4/29 10:40
     * @param message
     * @reture: boolean
     **/
    private boolean checkMessage(SendUserCenterDto message) throws JsonProcessingException {
        if(message == null
                || message.getToId() == null
                || message.getFromId() == null
                || message.getNewsType() == null
                || message.getContent() == null
                || StringUtils.isBlank(message.getContent())){
            log.error("从消息队列中解析得到的发送到个人中心的消息参数有异常,消息实体类信息为{}", objectMapper.writeValueAsString(message));
            return false;
        }
        return true;
    }
}