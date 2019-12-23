package top.imuster.goods.config;

import top.imuster.common.core.exception.GlobalException;

/**
 * @ClassName: GoodsException
 * @Description: goods模块的异常
 * @author: hmr
 * @date: 2019/12/22 10:51
 */
public class GoodsException extends GlobalException {
    public GoodsException(){
        super();
    }

    public GoodsException(String message){
        super(message);
    }
}
