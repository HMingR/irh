package top.imuster.message.provider.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.imuster.common.core.dto.SendReleaseDto;
import top.imuster.goods.api.pojo.ProductDemandInfo;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.life.api.pojo.ArticleInfo;
import top.imuster.message.provider.service.ArticleReleaseInfoService;
import top.imuster.message.provider.service.DemandReleaseInfoService;
import top.imuster.message.provider.service.GoodsReleaseInfoService;

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
    GoodsReleaseInfoService goodsReleaseInfoService;

    @Resource
    ArticleReleaseInfoService articleReleaseInfoService;

    @Resource
    DemandReleaseInfoService demandReleaseInfoService;

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
                                            key = "info.1.release.1"))
    public void GoodsReleaseListener(String msg){
        SendReleaseDto releaseDto = null;
        try {
            releaseDto = objectMapper.readValue(msg, SendReleaseDto.class);
        } catch (IOException e) {
            log.error("------Product-解析消息队列中的信息失败,消息队列中的信息为{},错误信息为{}", msg, e.getMessage());
        }
        ProductInfo releaseInfo = (ProductInfo)releaseDto.getTargetInfo();
        goodsReleaseInfoService.save(releaseInfo);

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
    public void articleReleaseListener(String msg){
        SendReleaseDto releaseDto = null;
        try {
            releaseDto = objectMapper.readValue(msg, SendReleaseDto.class);
        } catch (IOException e) {
            log.error("------Article-解析消息队列中的信息失败,消息队列中的信息为{},错误信息为{}", msg, e.getMessage());
        }
        ArticleInfo releaseInfo = (ArticleInfo)releaseDto.getTargetInfo();
        articleReleaseInfoService.save(releaseInfo);
    }


    /**
     * @Author hmr
     * @Description 需求
     * @Date: 2020/4/24 11:26
     * @param msg
     * @reture: void
     **/
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "queue_info_release"),
            exchange = @Exchange(name="exchange_topics_inform", type = "topic"),
            key = "info.3.release.3"))
    public void DemandReleaseListener(String msg){
        SendReleaseDto releaseDto = null;
        try {
            releaseDto = objectMapper.readValue(msg, SendReleaseDto.class);
        } catch (IOException e) {
            log.error("------Demand-解析消息队列中的信息失败,消息队列中的信息为{},错误信息为{}", msg, e.getMessage());
        }
        ProductDemandInfo releaseInfo = (ProductDemandInfo)releaseDto.getTargetInfo();
        demandReleaseInfoService.save(releaseInfo);
    }
}
