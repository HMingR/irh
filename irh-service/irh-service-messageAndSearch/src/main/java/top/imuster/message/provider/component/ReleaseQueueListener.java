package top.imuster.message.provider.component;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.imuster.common.core.dto.rabbitMq.SendReleaseDto;
import top.imuster.common.core.enums.OperationType;
import top.imuster.common.core.enums.ReleaseType;
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

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * @Author hmr
     * @Description 获得发布商品的信息
     * @Date: 2020/4/24 11:07
     * @param msg
     * @reture: void
     **/
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue_inform_release"),
            exchange = @Exchange(value = "exchange_topics_inform",type = "topic"),
            key = {"info.release.#"}
    ))
    @RabbitHandler
    public void GoodsReleaseListener(String msg) throws JsonProcessingException {
        log.info("发布商品到ES中");
        SendReleaseDto releaseDto = null;
        try {
            JSONObject jsonObject = JSONObject.parseObject(msg);
            releaseDto = objectMapper.readValue(msg, SendReleaseDto.class);
            ReleaseType releaseType = releaseDto.getReleaseType();
            if(ReleaseType.GOODS.equals(releaseType) || releaseType.DEMAND.equals(releaseType)){
                ESProductDto targetInfo = jsonObject.getObject("targetInfo", ESProductDto.class);
                releaseDto.setTargetInfo(targetInfo);
                OperationType operationType = releaseDto.getOperationType();
                ESProductDto releaseInfo = (ESProductDto)releaseDto.getTargetInfo();
                esOperationService.execute(objectMapper.writeValueAsString(releaseInfo), String.valueOf(releaseInfo.getId()), operationType, "goods");
            }else{
                EsArticleDto targetInfo = jsonObject.getObject("targetInfo", EsArticleDto.class);
                releaseDto = objectMapper.readValue(msg, SendReleaseDto.class);
                releaseDto.setTargetInfo(targetInfo);
                OperationType operationType = releaseDto.getOperationType();
                EsArticleDto releaseInfo = (EsArticleDto)releaseDto.getTargetInfo();
                esOperationService.execute(objectMapper.writeValueAsString(releaseInfo), String.valueOf(releaseInfo.getId()), operationType, "article");
            }
        } catch (IOException e) {
            log.error("------Product-解析消息队列中的信息失败,消息队列中的信息为{},错误信息为{}", msg, e.getMessage());
        }


    }
}
