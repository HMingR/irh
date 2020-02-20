package top.imuster.common.base.wrapper;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import top.imuster.common.base.config.MessageCode;

import java.io.Serializable;

/**
 * @ClassName: Message
 * @Description: 统一返回给前端的对象
 * @author: hmr
 * @date: 2019/12/1 9:11
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Message<T> implements Serializable {
    private static final long serialVersionUID = 7416903834444228788L;

    //信息
    private String text;

    //封装的数据
    private T data;

    //状态码
    private Integer code;

    public Message(){
        super();
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    private Message(Integer code){
        this.code = code;
    }

    private Message(Integer code, T data){
        this.code = code;
        this.data = data;
    }

    private Message(Integer code, String text, T data) {
        this.text = text;
        this.data = data;
        this.code = code;
    }

    private Message(Integer code, String text){
        this.code = code;
        this.text = text;
    }

    /**
     * @Description: 判断是否响应成功
     * @Author: hmr
     * @Date: 2019/12/1 9:32
     * @param
     * @reture: boolean
     **/
    @JsonIgnore
    public boolean isSuccess(){
        return this.code == MessageCode.SUCCESS.getCode();
    }

    @JsonIgnore
    public boolean isError(){
        return !isSuccess();
    }

    /*响应成功*/
    public static <T> Message<T> createBySuccess(){
        return new Message<>(MessageCode.SUCCESS.getCode(), MessageCode.SUCCESS.getText());
    }

    public static <T> Message<T> createBySuccess(String text){
        return new Message<>(MessageCode.SUCCESS.getCode(), text);
    }

    public static <T> Message<T> createBySuccess(T data){
        return new Message<>(MessageCode.SUCCESS.getCode(), data);
    }

    public static <T> Message<T> createBySuccess(String text, T data){
        return new Message<>(MessageCode.SUCCESS.getCode(), text, data);
    }

    /*响应失败*/
    public static <T> Message<T> createByError(){
        return new Message<>(MessageCode.ERROR.getCode());
    }

    public static <T> Message<T> createByError(String text){
        return new Message<>(MessageCode.ERROR.getCode(), text);
    }

    public static <T> Message<T> createByError(T data){
        return new Message<>(MessageCode.ERROR.getCode(), data);
    }

    public static <T> Message<T> createByError(String text, T data){
        return new Message<>(MessageCode.ERROR.getCode(), text, data);
    }

    /*自定义*/
    public static <T> Message<T> createByCustom(MessageCode messageCode){
        return new Message<>(messageCode.getCode(), messageCode.getText());
    }

    public static <T> Message<T> createByCustom(MessageCode messageCode, T data){
        return new Message<>(messageCode.getCode(), messageCode.getText(), data);
    }

    public static <T> Message<T> createByCustom(MessageCode messageCode, String text){
        return new Message<>(messageCode.getCode(), text);
    }

    public static <T> Message<T> createByCustom(MessageCode messageCode, String text, T data){
        return new Message<>(messageCode.getCode(), text, data);
    }
}
