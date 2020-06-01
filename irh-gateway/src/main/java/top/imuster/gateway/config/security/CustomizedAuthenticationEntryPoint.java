package top.imuster.gateway.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import top.imuster.common.base.config.MessageCode;
import top.imuster.common.base.wrapper.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: CustomizedAuthenticationEntryPoint
 * @Description: CustomizedAuthenticationEntryPoint
 * @author: hmr
 * @date: 2020/5/25 14:57
 */
@Component
public class CustomizedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        Message<String> byCustom = Message.createByCustom(MessageCode.UNAUTHORIZED);
        //按照系统自定义结构返回授权失败
        response.getWriter().write(objectMapper.writeValueAsString(byCustom));
    }
}
