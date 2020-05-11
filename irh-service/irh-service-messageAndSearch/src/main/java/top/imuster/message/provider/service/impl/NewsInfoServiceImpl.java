package top.imuster.message.provider.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.SendUserCenterDto;
import top.imuster.message.pojo.NewsInfo;
import top.imuster.message.provider.dao.NewsInfoDao;
import top.imuster.message.provider.service.NewsInfoService;

import javax.annotation.Resource;
import java.util.List;

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
    public void writeFromMq(SendUserCenterDto sendMessageDto) throws JsonProcessingException {
        boolean flag = checkMessage(sendMessageDto);
        if(!flag) return;
        NewsInfo newsInfo = new NewsInfo();
        newsInfo.setContent(sendMessageDto.getContent());
        newsInfo.setSenderId(sendMessageDto.getFromId());
        newsInfo.setReceiverId(sendMessageDto.getToId());
        newsInfo.setTargetId(sendMessageDto.getTargetId());
        newsInfo.setResourceId(sendMessageDto.getResourceId());
        newsInfo.setNewsType(sendMessageDto.getNewsType());
        newsInfoDao.insertEntry(newsInfo);
    }

    @Override
    public Message<Page<NewsInfo>> getAtMeMessage(Long userId, Integer pageSize, Integer currentPage) {
        NewsInfo newsInfo = new NewsInfo();
        newsInfo.setReceiverId(userId);
        newsInfo.setStartIndex((currentPage - 1) * pageSize);
        newsInfo.setEndIndex(pageSize);
        Integer totalCount = newsInfoDao.selectAtMeTotal(newsInfo);
        List<NewsInfo> res = newsInfoDao.selectAtMeMessage(newsInfo);
        Page<NewsInfo> page = new Page<>();
        page.setData(res);
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        page.setTotalCount(totalCount);
        return Message.createBySuccess(page);
    }

    @Override
    public Message<String> updateMessageState(Long id, Integer type, Long currentUserId) {
        Long userId = newsInfoDao.selectReceiverIdById(id);
        if(userId == null) return Message.createByError("操作失败,没有找到相关的消息,请刷新后重试");
        if(!userId.equals(currentUserId)){
            log.error("----->用户id为{}的用户试图删除不属于自己的消息,消息id为{}", currentUserId, id);
            return Message.createByError("惭怍失败,该操作属于非法操作,我们已经记录下您当前的操作,请刷新后重试");
        }
        NewsInfo newsInfo = new NewsInfo();
        newsInfo.setId(id);
        newsInfo.setState(type);
        newsInfoDao.updateByKey(newsInfo);
        return Message.createBySuccess();
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