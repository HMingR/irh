package top.imuster.auth.component;

import cn.hutool.json.JSONUtil;
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
 * @ClassName: RestAuthenticationEntryPoint
 * @Description: 当未登录或者token失效访问接口时，自定义的返回结果
 * @author: hmr
 * @date: 2019/12/6 22:48
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(Message.createByCustom(MessageCode.UNAUTHORIZED)));
        response.getWriter().flush();
    }
}
