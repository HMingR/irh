package top.imuster.life.provider.exception;

import top.imuster.common.core.exception.GlobalException;

/**
 * @ClassName: ForumException
 * @Description: ForumException
 * @author: hmr
 * @date: 2020/2/1 17:41
 */
public class ForumException extends GlobalException {
    public ForumException(){
        super();
    }

    public ForumException(String msg){
        super(msg);
    }
}
