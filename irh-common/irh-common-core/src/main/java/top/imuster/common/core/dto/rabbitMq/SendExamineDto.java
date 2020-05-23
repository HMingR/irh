package top.imuster.common.core.dto.rabbitMq;

import top.imuster.common.core.enums.ExamineEnum;
import top.imuster.common.core.enums.MqTypeEnum;

import java.util.List;

/**
 * @ClassName: SendExamineDto
 * @Description: 审核内容和图片dto
 * @author: hmr
 * @date: 2020/5/21 14:56
 */
public class SendExamineDto extends Send2MQ {

    //1-二手商品  2-需求  3-文章
    private Integer targetType;

    //需要审核的图片
    private List<String> imgUrl;


    //需要审核的内容
    private String content;

    //目标id
    private Long targetId;

    private Long userId;

    public SendExamineDto(){
        setType(MqTypeEnum.EXAMINE_INFO);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
        if(targetType == 1 || targetType == 2){
            MqTypeEnum.EXAMINE_INFO.setRelease(ExamineEnum.GOODS);
        }else{
            MqTypeEnum.EXAMINE_INFO.setRelease(ExamineEnum.ARTICLE);
        }
    }

    public List<String> getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(List<String> imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }
}
