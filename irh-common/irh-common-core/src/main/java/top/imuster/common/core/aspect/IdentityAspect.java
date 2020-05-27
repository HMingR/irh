package top.imuster.common.core.aspect;

import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import top.imuster.common.core.exception.GlobalException;

import java.util.Collection;

/**
 * @ClassName: IdentityAspect
 * @Description: IdentityAspect
 * @author: hmr
 * @date: 2020/5/27 12:59
 */
@Aspect
@Component
public class IdentityAspect {

    @Pointcut("@annotation(top.imuster.common.core.annotation.CheckIdentityAnnotation)")
    private void pointCut(){}

    @Before("pointCut()")
    public void before(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if(CollectionUtils.isEmpty(authorities) || !authorities.contains("identified")){
            throw new GlobalException("未实名认证,无法访问");
        }
    }
}
