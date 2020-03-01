package top.imuster.common.core.dto;

/**
 * @ClassName: SendUserCenterDto
 * @Description: 发送给用户中心的
 * @author: hmr
 * @date: 2020/3/1 16:30
 */
public class SendUserCenterDto extends Send2MQ{
    private Long fromId;

    private Long toId;

    private String date;

    private String content;
}
