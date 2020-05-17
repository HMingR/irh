package top.imuster.common.core.exception;

/**
 * @ClassName: NeedLoginException
 * @Description: 用户没有登录，需要抛这个异常
 * @author: hmr
 * @date: 2019/12/20 20:16
 */
public class NeedLoginException extends RuntimeException{

    public NeedLoginException(){
        super();
    }

    public NeedLoginException(String message){
        super(message);
    }
}
