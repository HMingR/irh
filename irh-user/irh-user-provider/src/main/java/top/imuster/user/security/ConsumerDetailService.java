package top.imuster.user.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ConsumerDetailService
 * @Description: 会员登录认证
 * @author: hmr
 * @date: 2019/12/1 19:49
 */
@Component("consumerDetailService")
public class ConsumerDetailService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}


//spring io platform