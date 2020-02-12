package top.imuster.life.provider.exception;

import top.imuster.common.core.exception.GlobalException;

/**
 * @ClassName: LifeException
 * @Description: LifeException
 * @author: hmr
 * @date: 2020/2/12 11:43
 */
public class LifeException extends GlobalException {
    public LifeException(){
        super();
    }

    public LifeException(String msg){
        super(msg);
    }
}
