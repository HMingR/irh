package top.imuster;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import top.imuster.user.provider.UserProviderApplication;

/**
 * @ClassName: test
 * @Description: test
 * @author: hmr
 * @date: 2020/4/21 9:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserProviderApplication.class)
public class test {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void test(){
        String encode = passwordEncoder.encode("irhWebApp");
        System.out.println(encode);
    }
}
