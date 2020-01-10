package top.imuster.file.provider.exception;

import top.imuster.common.core.exception.GlobalException;

/**
 * @ClassName: FileException
 * @Description: 文件模块的异常
 * @author: hmr
 * @date: 2020/1/10 20:22
 */
public class FileException extends GlobalException {

    public FileException(){

    }

    public FileException(String msg){
        super(msg);
    }


}
