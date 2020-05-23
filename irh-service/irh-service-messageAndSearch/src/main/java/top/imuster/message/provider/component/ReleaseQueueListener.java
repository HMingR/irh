package top.imuster.message.provider.component;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.imuster.common.core.dto.rabbitMq.SendReleaseDto;
import top.imuster.common.core.enums.OperationType;
import top.imuster.goods.api.dto.ESProductDto;
import top.imuster.life.api.dto.EsArticleDto;
import top.imuster.message.provider.service.impl.EsOperationService;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @ClassName: ReleaseQueueListener
 * @Description: 发布消息队列的监听器   每个不同种类信息的key可以查看   ReleaseType类
 * @author: hmr
 * @date: 2020/4/24 10:52
 */
@Component
public class ReleaseQueueListener {
    private static final Logger log = LoggerFactory.getLogger(ReleaseQueueListener.class);


    @Resource
    EsOperationService esOperationService;

    @Resource

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * @Author hmr
     * @Description 获得发布商品的信息
     * @Date: 2020/4/24 11:07
     * @param msg
     * @reture: void
     **/
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "queue_info_release"),
                                            exchange = @Exchange(name="exchange_topics_inform", type = "topic"),
                                            key = {"info.1.release.1", "info.3.release.3"}))
    public void GoodsReleaseListener(String msg) throws JsonProcessingException {
        SendReleaseDto releaseDto = null;
        try {
            JSONObject jsonObject = JSONObject.parseObject(msg);
            ESProductDto targetInfo = jsonObject.getObject("targetInfo", ESProductDto.class);
            releaseDto = objectMapper.readValue(msg, SendReleaseDto.class);
            releaseDto.setTargetInfo(targetInfo);
        } catch (IOException e) {
            log.error("------Product-解析消息队列中的信息失败,消息队列中的信息为{},错误信息为{}", msg, e.getMessage());
        }
        OperationType operationType = releaseDto.getOperationType();
        ESProductDto releaseInfo = (ESProductDto)releaseDto.getTargetInfo();
        esOperationService.execute(objectMapper.writeValueAsString(releaseInfo), String.valueOf(releaseInfo.getId()), operationType, "goods");
    }


    /**
     * @Author hmr
     * @Description 文章
     * @Date: 2020/4/24 11:26
     * @param msg
     * @reture: void
     **/
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "queue_info_release"),
            exchange = @Exchange(name="exchange_topics_inform", type = "topic"),
            key = "info.2.release.2"))
    public void articleReleaseListener(String msg) throws JsonProcessingException {
        SendReleaseDto releaseDto = null;
        try {
            JSONObject jsonObject = JSONObject.parseObject(msg);
            EsArticleDto targetInfo = jsonObject.getObject("targetInfo", EsArticleDto.class);
            releaseDto = objectMapper.readValue(msg, SendReleaseDto.class);
            releaseDto.setTargetInfo(targetInfo);
        } catch (IOException e) {
            log.error("------Article-解析消息队列中的信息失败,消息队列中的信息为{},错误信息为{}", msg, e.getMessage());
        }
        OperationType operationType = releaseDto.getOperationType();
        EsArticleDto releaseInfo = (EsArticleDto)releaseDto.getTargetInfo();
        esOperationService.execute(objectMapper.writeValueAsString(releaseInfo), String.valueOf(releaseInfo.getId()), operationType, "article");
    }
}
