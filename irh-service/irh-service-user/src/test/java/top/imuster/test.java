package top.imuster;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
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

    //校验jwt令牌
    @Test
    public void testVerify(){
        //公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgaVB+OnTTnhG8/plZ2dihsA6KXnL3BuDTByPlIbCw6dz7aB2PU+/YsdQKig8HWQFq/cNpUkWBU0+d2VQwTQp/9uqdp9nK3VMSzzHkcZkFTpOK51tzFqmvP6CH2FWkVh/bJo+vfkm32XFY9L6C+NYKGJdC7FpcBIs3132d+lbRbOp4j/6keq8BaqNWwbOU+2I/UeAGA7GlHp1FSe/0e4OT/t62jwqVLXQhkTSYhcoSaal+zAr3kVveQnLXqhAeQe1n0WnhSodQpLeKrU49mqt0ciz6oXxTsZglsh/RbCv76/5tpAAxSu+N92G7P+pvlxuLo+wku6Q7IsFhR0IIS12KwIDAQAB-----END PUBLIC KEY-----";
        //jwt令牌
        String jwtString = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySW5mbyI6eyJjcmVhdGVUaW1lIjpudWxsLCJ1cGRhdGVUaW1lIjpudWxsLCJzdGF0ZSI6bnVsbCwic2VhcmNoU3RhcnRUaW1lIjpudWxsLCJzZWFyY2hFbmRUaW1lIjpudWxsLCJ1c2VySWQiOjUsIm5pY2tuYW1lIjoibWFzdGVyIiwicGljIjpudWxsLCJsb2dpbk5hbWUiOiIxOTc4NzczNDY1QHFxLmNvbSIsInVzZXJUeXBlIjoiTUFOQUdFTUVOVCIsImFjYWRlbXlOYW1lIjpudWxsLCJzaWduYXR1cmUiOm51bGx9LCJleHAiOjE1ODk3MjgzNTUsImF1dGhvcml0aWVzIjpbInN1cGVyX2FkbWluIl0sImp0aSI6IjEwYjkwOWJkLWY3YTQtNDVhMi05Y2M0LTE5MWQwZDc0MjQwOSIsImNsaWVudF9pZCI6ImlyaFdlYkFwcCIsInNjb3BlIjpbImFwcCJdfQ.Syhum0AZIWiPR-emV1DduQRyWVHNm350bvllsMrUfLZ2hqe8anyRO-saUNMilRA-vq6jolWsB1H6OqMtsMoylDSbvGZcTHqLXdd7NCF9hte0GRgZpoA3SeEj79cHDFgWZPlFglq78YzDvoodcyXEgMBX44_e3JcIPQf6WQF-_TUa3ZAHv0-98JRdjwtlYsd4sv0mWNMDP4f_BNw7twUaEGqz3S9TnEsmtZgGLzOrdrg_qYz-2KmBjz74Y_TFs5GoXxzUrw8jst9BtFcQajKy3TqH72q3MjGZ_I_QSOHBCpwaxJoUEjmVbrT2kuYbuFlYH8BG7auZ0a1qbt_w2vaekA";
        //校验jwt令牌
        Jwt jwt = JwtHelper.decodeAndVerify(jwtString, new RsaVerifier(publickey));
        //拿到jwt令牌中自定义的内容
        String claims = jwt.getClaims();
        System.out.println(claims);
    }
}
