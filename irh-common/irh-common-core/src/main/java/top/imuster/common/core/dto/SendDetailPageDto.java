package top.imuster.common.core.dto;

import top.imuster.common.core.enums.TemplateEnum;

import java.io.Serializable;

/**
 * @ClassName: SendDetailPageDto
 * @Description: 生成详情页
 * @author: hmr
 * @date: 2020/3/2 11:30
 */
public class SendDetailPageDto extends Send2MQ implements Serializable {
    private static final long serialVersionUID = 4445650992745522723L;

    private String entry;

    private Long targetId;

    private TemplateEnum templateEnum;

    public SendDetailPageDto() {
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public TemplateEnum getTemplateEnum() {
        return templateEnum;
    }

    public void setTemplateEnum(TemplateEnum templateEnum) {
        this.templateEnum = templateEnum;
    }
}
