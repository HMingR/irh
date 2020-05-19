package top.imuster.common.base.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

/**
 * JwtToken生成的工具类
 * JWT token的格式：header.payload.signature
 * header的格式（算法、token的类型）：
 * {"alg": "HS512","typ": "JWT"}
 * payload的格式（用户名、创建时间、生成时间）：
 * {"sub":"wang","created":1489079981393,"exp":1489684781}
 * signature的生成算法：
 * HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 *
 * 取消了时间，就通过用户名生成token
 */
public class JwtTokenUtil {

    private static final String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgaVB+OnTTnhG8/plZ2dihsA6KXnL3BuDTByPlIbCw6dz7aB2PU+/YsdQKig8HWQFq/cNpUkWBU0+d2VQwTQp/9uqdp9nK3VMSzzHkcZkFTpOK51tzFqmvP6CH2FWkVh/bJo+vfkm32XFY9L6C+NYKGJdC7FpcBIs3132d+lbRbOp4j/6keq8BaqNWwbOU+2I/UeAGA7GlHp1FSe/0e4OT/t62jwqVLXQhkTSYhcoSaal+zAr3kVveQnLXqhAeQe1n0WnhSodQpLeKrU49mqt0ciz6oXxTsZglsh/RbCv76/5tpAAxSu+N92G7P+pvlxuLo+wku6Q7IsFhR0IIS12KwIDAQAB-----END PUBLIC KEY-----";

    public static Long getUserId(String token){
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));
        //拿到jwt令牌中自定义的内容
        String claims = jwt.getClaims();
        JSONObject jsonObject = JSONObject.parseObject(claims);
        String userId = jsonObject.getString("userId");
        return Long.parseLong(userId);
    }

}
