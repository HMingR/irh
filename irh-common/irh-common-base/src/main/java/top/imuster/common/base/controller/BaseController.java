package top.imuster.common.base.controller;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.utils.JwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

/**
 * @ClassName: BaseController
 * @Description: controller层一些共有的
 * @author: hmr
 * @date: 2019/12/1 10:36
 */
public class BaseController {
    protected  final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected final static ObjectMapper objectMapper = new ObjectMapper();

    protected void validData(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuffer sb = new StringBuffer();
            for (ObjectError error : bindingResult.getAllErrors()) {
                sb.append(error.getDefaultMessage());
            }
            throw new ValidationException(sb.toString());
        }
    }

    /**
     * @Description: 从token中解析得到当前用户的用户名
     * @Author: hmr
     * @Date: 2020/1/10 16:00
     * @param request
     * @reture: java.lang.String
     **/
    protected String getNameByToken(HttpServletRequest request) throws Exception{
        String token = StringUtils.substringAfter(request.getHeader(GlobalConstant.JWT_TOKEN_HEADER), GlobalConstant.JWT_TOKEN_HEAD);
        return JwtTokenUtil.getUserNameFromToken(token);
    }

    /**
     * @Description: 从token中解析当前用户的id
     * @Author: hmr
     * @Date: 2020/1/10 16:00
     * @param request
     * @reture: java.lang.Long
     **/
    protected Long getIdByToken(HttpServletRequest request)throws Exception{
        String token = StringUtils.substringAfter(request.getHeader(GlobalConstant.JWT_TOKEN_HEADER), GlobalConstant.JWT_TOKEN_HEAD);
        return JwtTokenUtil.getUserIdFromToken(token);
    }


}
