package top.imuster.gateway.config.security;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Optional;

/**
 * @ClassName: FeignClientInterceptor
 * @Description: FeignClientInterceptor,作用就是在微服务之间进行调用的时候，传递从前端传来的jwt令牌
 * @author: hmr
 * @date: 2020/1/31 8:50
 */
public class FeignClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        Optional<ServletRequestAttributes> optional = Optional.ofNullable(servletRequestAttributes);
        if(optional.isPresent()){
            HttpServletRequest request = servletRequestAttributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            while(headerNames.hasMoreElements()){
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                requestTemplate.header(headerName, headerValue);
            }
        }
    }
}
