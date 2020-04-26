package imuster;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.imuster.order.provider.IrhOrderApplication;
import top.imuster.order.provider.dao.OrderInfoDao;

import javax.annotation.Resource;


/**
 * @ClassName: test
 * @Description: test
 * @author: hmr
 * @date: 2020/3/2 19:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IrhOrderApplication.class)
public class test {

    @Resource
    public OrderInfoDao orderInfoDao;

    @Test
    public void test(){
        String s = DigestUtils.sha1Hex("123456");
        System.out.println(s);
    }

}
