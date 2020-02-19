package top.imuster.auth.exception;

import top.imuster.common.core.exception.GlobalException;

/**
 * @ClassName: CustomSecurityException
 * @Description: CustomSecurityException
 * @author: hmr
 * @date: 2020/1/27 16:02
 */
public class CustomSecurityException extends GlobalException {

    public CustomSecurityException(){
        super();
    }

    public CustomSecurityException(String msg){
        super(msg);
    }

}
