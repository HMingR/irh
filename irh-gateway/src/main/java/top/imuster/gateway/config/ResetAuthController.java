package top.imuster.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.gateway.config.security.ZuulUrlFilterInvocationSecurityMetadataSource;

/**
 * @ClassName: ResetAuthController
 * @Description: ResetAuthController
 * @author: hmr
 * @date: 2020/6/3 14:11
 */
@RestController
@RequestMapping("/reset")
public class ResetAuthController {

    @Autowired
    ZuulUrlFilterInvocationSecurityMetadataSource zuulUrlFilterInvocationSecurityMetadataSource;

    @GetMapping
    public Message<String> reset(){
        zuulUrlFilterInvocationSecurityMetadataSource.resetMap();
        return Message.createBySuccess();
    }
}
