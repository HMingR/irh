package top.imuster.message.dto;

/**
 * @ClassName: ChatMessageDto
 * @Description: ChatMessageDto
 * @author: hmr
 * @date: 2020/7/15 11:37
 */
public class ChatMessageDto {

    private Long from;

    private Long to;

    private String msg;

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
