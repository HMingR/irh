package top.imuster.common.base.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.ValidationException;

/**
 * @ClassName: BaseController
 * @Description: controller层一些共有的
 * @author: hmr
 * @date: 2019/12/1 10:36
 */
public class BaseController {
    protected  final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected void validData(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuffer sb = new StringBuffer();
            for (ObjectError error : bindingResult.getAllErrors()) {
                sb.append(error.getDefaultMessage());
            }
            throw new ValidationException(sb.toString());
        }
    }
}
