package top.imuster.common.core.exception;

/**
 * @ClassName: BusyOperationException
 * @Description: BusyOperationException  操作繁忙异常类
 * @author: hmr
 * @date: 2020/5/20 14:59
 */
public class BusyOperationException extends GlobalException {

    public BusyOperationException(){
        super();
    }

    public BusyOperationException(String msg){
        super(msg);
    }
}
