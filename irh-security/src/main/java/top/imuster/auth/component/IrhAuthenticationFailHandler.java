package top.imuster.auth.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import top.imuster.common.base.wrapper.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: IrhAuthenticationSuccessHandler
 * @Description: 认证失败的处理逻辑
 * @author: hmr
 * @date: 2020/4/30 20:14
 */
public class IrhAuthenticationFailHandler implements AuthenticationFailureHandler {

    private static final Logger log = LoggerFactory.getLogger(IrhAuthenticationFailHandler.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        Message<String> msg = Message.createByError(exception.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(msg));
    }
}
