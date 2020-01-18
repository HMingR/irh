package top.imuster.message.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.common.core.dto.SendMessageDto;
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
    void writeFromMq(SendMessageDto sendMessageDto);
}