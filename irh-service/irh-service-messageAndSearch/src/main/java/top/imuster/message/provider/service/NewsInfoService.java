package top.imuster.message.provider.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.rabbitMq.SendUserCenterDto;
import top.imuster.message.pojo.NewsInfo;

/**
 * NewsInfoService接口
 * @author 黄明人
 * @since 2020-01-17 17:13:09
 */
public interface NewsInfoService extends BaseService<NewsInfo, Long> {

    /**
     * @Author hmr
     * @Description 从消息队列中监听信息并写入数据库
     * @Date: 2020/1/18 11:01
     * @param sendMessageDto
     * @reture: void
     **/
    void writeFromMq(SendUserCenterDto sendMessageDto) throws JsonProcessingException;

    /**
     * @Author hmr
     * @Description 获得回复消息或者评价消息
     * @Date: 2020/5/8 15:20
     * @param currentUserIdFromCookie
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.message.pojo.NewsInfo>>
     **/
    Message<Page<NewsInfo>> getAtMeMessage(Long userId, Integer pageSize, Integer currentPage);

    /**
     * @Author hmr
     * @Description 更新消息的状态
     * @Date: 2020/5/8 16:13
     * @param id
     * @param type
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> updateMessageState(Long id, Integer type, Long currentPage);
}