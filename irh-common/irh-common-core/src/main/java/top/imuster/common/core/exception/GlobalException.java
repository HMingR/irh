package top.imuster.common.core.exception;

/**
 * @ClassName: GlobalException
 * @Description: 全局自定义异常
 * @author: hmr
 * @date: 2019/12/19 15:23
 */
public class GlobalException extends RuntimeException {

    public GlobalException(){
        super();
    }

    public GlobalException(String message){
        super(message);
    }
}
