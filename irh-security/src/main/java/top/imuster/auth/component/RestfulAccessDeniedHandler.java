package top.imuster.auth.component;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import top.imuster.common.base.config.MessageCode;
import top.imuster.common.base.wrapper.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: RestfulAccessDeniedHandler
 * @Description: 当访问接口没有权限时，自定义的返回结果
 * @author: hmr
 * @date: 2019/12/6 22:40
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException {
        response.resetBuffer();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(Message.createByError(MessageCode.FORBID)));
        response.flushBuffer();
    }
}
