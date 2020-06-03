package top.imuster.goods.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.imuster.common.core.dto.ExamineResultDetail;
import top.imuster.common.core.dto.ExamineResultDto;
import top.imuster.common.core.dto.rabbitMq.SendExamineDto;
import top.imuster.common.core.dto.rabbitMq.SendUserCenterDto;
import top.imuster.common.core.utils.DateUtil;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.common.core.utils.examine.HuaweiModerationImageUtil;
import top.imuster.common.core.utils.examine.HuaweiModerationTextContentUtil;
import top.imuster.goods.api.dto.ESProductDto;
import top.imuster.goods.api.pojo.ProductDemandInfo;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.service.ProductDemandInfoService;
import top.imuster.goods.service.ProductInfoService;
import top.imuster.user.api.pojo.ExamineRecordInfo;
import top.imuster.user.api.service.UserServiceFeignApi;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName: ExamineQueueListener
 * @Description: ExamineQueueListener
 * @author: hmr
 * @date: 2020/5/21 15:16
 */
@Component
public class ExamineQueueListener {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ProductInfoService productInfoService;

    @Resource
    private ProductDemandInfoService productDemandInfoService;

    @Autowired
    UserServiceFeignApi userServiceFeignApi;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    HuaweiModerationImageUtil huaweiModerationImageUtil;

    @Autowired
    HuaweiModerationTextContentUtil huaweiModerationTextContentUtil;

    @Autowired
    GenerateSendMessageService generateSendMessageService;

    @Autowired
    Trans2ES trans2ES;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "queue_inform_examine"),
            exchange = @Exchange(name="exchange_topics_inform", type = "topic"),
            key = {"info.examine.#"}))
    public void listen(String msg) throws IOException {
        log.debug("----->进入审核");
        SendExamineDto sendExamineDto = objectMapper.readValue(msg, SendExamineDto.class);

        List<String> imgUrl = sendExamineDto.getImgUrl();
        Long targetId = sendExamineDto.getTargetId();
        Integer type = sendExamineDto.getTargetType();
        String[] imgs = imgUrl.toArray(new String[imgUrl.size()]);
        ExamineRecordInfo examineRecordInfo = new ExamineRecordInfo();
        examineRecordInfo.setTargetId(targetId);
        examineRecordInfo.setType(type);
        examineRecordInfo.setReleaseUserId(sendExamineDto.getUserId());
        String res = huaweiModerationImageUtil.imageContentBatchCheck(imgs);
        if("NULL".equalsIgnoreCase(res)) {
            examineRecordInfo.setState(2);
            saveRecord2DB(examineRecordInfo, "AI检测出现问题，检测失败,请等待管理员审核");
            return;
        }
        else if(StringUtils.isBlank(res)) {
            //审核通过,可以发布
            String content = sendExamineDto.getContent();
            ExamineResultDto examineResultDto = huaweiModerationTextContentUtil.moderationTextContent(content);

            if(examineResultDto == null){
                examineRecordInfo.setState(2);
                saveRecord2DB(examineRecordInfo, "AI检测出现问题，检测失败");
            }else if("pass".equalsIgnoreCase(examineResultDto.getSuggestion())){
                examineRecordInfo.setState(1);
                saveRecord2DB(examineRecordInfo, null);
                getTargetInfoByIdAndType(type, targetId);
            }else{
                examineRecordInfo.setState(2);
                ExamineResultDetail detail = examineResultDto.getDetail();
                StringBuffer remark = new StringBuffer("内容审核不通过，原因:");

                List<String> abuse = detail.getAbuse();
                if(abuse != null &&  !abuse.isEmpty()){
                    remark.append("出现了以下带有谩骂语义的词:");
                    for (int i = 0; i < abuse.size(); i++) {
                        remark.append(abuse.get(i)).append("  ");
                    }
                    remark.append("; ");
                }

                List<String> contraband = detail.getContraband();
                if(contraband != null && !contraband.isEmpty()){
                    remark.append("出现了以下违禁品词汇: " );
                    for (int i = 0; i < contraband.size(); i++) {
                        remark.append(contraband.get(i)).append(" ");
                    }
                    remark.append("; ");
                }

                String flood = detail.getFlood();
                if(StringUtils.isNotBlank(flood)){
                    remark.append("以下段落有暴力语义:").append(flood).append("; ");
                }


                List<String> porn = detail.getPorn();
                if(porn != null && !porn.isEmpty()){
                    remark.append("出现了以下色情词汇:");
                    for (int i = 0; i < porn.size(); i++) {
                        remark.append(porn.get(i)).append(" ");
                    }
                    remark.append("; ");
                }

                List<String> politics = detail.getPolitics();
                if(politics != null && !politics.isEmpty()){
                    remark.append("出现了以下涉及政治的词汇:" );
                    for (int i = 0; i < politics.size(); i++) {
                        remark.append(politics.get(i)).append(" ");
                    }
                    remark.append("; ");
                }
                saveRecord2DB(examineRecordInfo, remark.toString());
                return;
            }
            examineRecordInfo.setState(1);
            saveRecord2DB(examineRecordInfo, null);
            getTargetInfoByIdAndType(type, targetId);
            return;
        } else{
            examineRecordInfo.setState(2);
            saveRecord2DB(examineRecordInfo, res);
            return;
        }
    }

    /**
     * @Author hmr
     * @Description 保存审核记录到DB中
     * @Date: 2020/5/21 19:21
     * @param
     * @reture: void
     **/
    private void saveRecord2DB(ExamineRecordInfo examineRecordInfo, String remark){
        SendUserCenterDto sendUserCenterDto = new SendUserCenterDto();
        examineRecordInfo.setRemark(remark);
        Integer state = examineRecordInfo.getState();
        String str = "";
        if(state == 1){
             str = "您发布的信息已经通过审核,快来看看吧";
        }else{
            str = new StringBuffer("您发布的信息未通过AI检测，请等待人工检测").append("原因为:").append(examineRecordInfo.getRemark()).toString();
        }
        sendUserCenterDto.setContent(str);
        sendUserCenterDto.setFromId(-1L);
        sendUserCenterDto.setToId(examineRecordInfo.getReleaseUserId());
        sendUserCenterDto.setDate(DateUtil.now());
        sendUserCenterDto.setNewsType(examineRecordInfo.getType() == 1 ? 80 : 85);
        sendUserCenterDto.setTargetId(examineRecordInfo.getTargetId());

        generateSendMessageService.sendToMq(sendUserCenterDto);
        userServiceFeignApi.saveExamineRecord2DB(examineRecordInfo);
    }

    private void getTargetInfoByIdAndType(Integer type, Long targetId){
        if(type == 1){
            //商品
            ProductInfo info = productInfoService.getProductBriefInfoById(targetId);
            if(info != null) trans2ES.save2ES(new ESProductDto(info));
        }else if(type == 2){
            //需求
            List<ProductDemandInfo> productDemandInfos = productDemandInfoService.selectEntryList(targetId);
            if(productDemandInfos != null && !productDemandInfos.isEmpty()){
                trans2ES.save2ES(new ESProductDto(productDemandInfos.get(0)));
            }
        }else{
            log.error("------>接受的消息错误,type={},targetId={}", type, targetId);
        }
    }


}
