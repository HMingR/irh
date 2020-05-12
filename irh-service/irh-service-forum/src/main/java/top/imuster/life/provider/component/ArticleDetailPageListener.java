package top.imuster.life.provider.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.SendDetailPageDto;
import top.imuster.file.api.service.FileServiceFeignApi;
import top.imuster.life.api.pojo.ArticleInfo;
import top.imuster.life.provider.service.ArticleInfoService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;

/**
 * @ClassName: ArticleDetailPageListener
 * @Description: 文章详情页队列的监听器
 * @author: hmr
 * @date: 2020/4/12 9:05
 */
@Component
public class ArticleDetailPageListener {

    private static final String templateLocation = "static/article.ftl";

    @Autowired
    private Configuration configuration;

    @Autowired
    private ObjectMapper objectMapper;

    private Template template;

    @Autowired
    FileServiceFeignApi fileServiceFeignApi;

    @Resource
    ArticleInfoService articleInfoService;

    @PostConstruct
    public void createTemplate() throws IOException {
        template = configuration.getTemplate(templateLocation, "UTF-8");
    }

    @RabbitListener(queues = "queue_inform_detail")
    public void generate(String body) throws Exception {
        SendDetailPageDto entry = objectMapper.readValue(body, SendDetailPageDto.class);
        String content = entry.getEntry();
        HashMap<String, String> param = new HashMap<>();
        param.put("context", content);
        byte[] bytes = FreeMarkerTemplateUtils.processTemplateIntoString(template, param).getBytes();
        Message<String> msg = fileServiceFeignApi.uploadByBytes(bytes);
        ArticleInfo condition = new ArticleInfo();
        condition.setId(entry.getTargetId());
        if(msg == null || msg.getCode() != 200){
            condition.setState(3);
            condition.setContent(content);
            articleInfoService.updateByKey(condition);
        }else {
            condition.setState(2);
            condition.setDetailPage(msg.getText());
            articleInfoService.updateByKey(condition);
        }
    }

}
