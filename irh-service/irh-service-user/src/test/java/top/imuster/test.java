package top.imuster;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.test.context.junit4.SpringRunner;
import top.imuster.common.core.dto.UserDto;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.provider.UserProviderApplication;

import java.io.IOException;

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

    @Autowired
    ObjectMapper objectMapper;

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
        String jwtString = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySW5mbyI6eyJjcmVhdGVUaW1lIjpudWxsLCJ1cGRhdGVUaW1lIjpudWxsLCJzdGF0ZSI6bnVsbCwic2VhcmNoU3RhcnRUaW1lIjpudWxsLCJzZWFyY2hFbmRUaW1lIjpudWxsLCJ1c2VySWQiOjUsIm5pY2tuYW1lIjoibWFzdGVyIiwicGljIjpudWxsLCJsb2dpbk5hbWUiOiIxOTc4NzczNDY1QHFxLmNvbSIsInVzZXJUeXBlIjoiTUFOQUdFTUVOVCIsImFjYWRlbXlOYW1lIjpudWxsLCJzaWduYXR1cmUiOm51bGx9LCJleHAiOjE1ODk3NjA0MTQsImF1dGhvcml0aWVzIjpbInN1cGVyX2FkbWluIl0sImp0aSI6IjM0ZmE4N2JhLTc4NGYtNDlmZi05Y2YzLTMwZDhmNzYwMzNiOSIsImNsaWVudF9pZCI6ImlyaFdlYkFwcCIsInNjb3BlIjpbImFwcCJdfQ.VKASRr24osrcYsSXadM7ghpD6K8ziWysKE1AwdMddImNZXhxQV4FWJyPcA4BwyXjLxFgdrEyj9-LdRqB0MLd0PIAjDVlD3vzVxpTHIzzKHBkOwIEGn2sTq9WR0_-j5nbHHP2swbwWaMshYG7jHjBzOXbDovFoFBkHBL-DxCHcPjnbS04vPUGreaCjtRqJ857UMNWybqT3VlawW4I5DAihcRAES3dBPcuSh24tgXBycvg5GAvLW9CZ3LKRrDVA7w4OT8XPaB-eplS_DXJ2NqsvyjolYk1eoMHENPnNFqMnIPR0a5Nud3nuQHl2eWnIIS6Q33e-kaGHJOeO5GwsN_n_A";
        //校验jwt令牌
        Jwt jwt = JwtHelper.decodeAndVerify(jwtString, new RsaVerifier(publickey));
        //拿到jwt令牌中自定义的内容
        String claims = jwt.getClaims();
        System.out.println(claims);
    }

    @Test
    public void test03() throws IOException {
        String str = "{\"userInfo\":{\"createTime\":null,\"updateTime\":null,\"state\":null,\"searchStartTime\":null,\"searchEndTime\":null,\"userId\":5,\"nickname\":\"master\",\"pic\":null,\"loginName\":\"1978773465@qq.com\",\"userType\":\"MANAGEMENT\",\"academyName\":null,\"signature\":null},\"exp\":1589760414,\"authorities\":[\"super_admin\"],\"jti\":\"34fa87ba-784f-49ff-9cf3-30d8f76033b9\",\"client_id\":\"irhWebApp\",\"scope\":[\"app\"]}";
        JSONObject jsonObject = JSONObject.parseObject(str);
        String userInfo = jsonObject.getString("userInfo");
        System.out.println(userInfo);
        UserInfo userInfo1 = objectMapper.readValue(userInfo, UserInfo.class);
        System.out.println(userInfo1);
    }

    @Test
    public void test04() throws IOException {
        String str = "{\"createTime\":null,\"updateTime\":null,\"state\":null,\"searchStartTime\":null,\"searchEndTime\":null,\"userId\":5,\"nickname\":\"master\",\"pic\":null,\"loginName\":\"1978773465@qq.com\",\"userType\":\"MANAGEMENT\",\"academyName\":null,\"signature\":null}";
        UserDto userDto = objectMapper.readValue(str, UserDto.class);
        System.out.println(userDto);
    }
}
