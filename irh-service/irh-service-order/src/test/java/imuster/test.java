package imuster;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.imuster.order.provider.IrhOrderApplication;
import top.imuster.order.provider.dao.OrderInfoDao;
import top.imuster.user.api.service.UserServiceFeignApi;

import javax.annotation.Resource;
import java.util.Map;


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

    @Autowired
    UserServiceFeignApi userServiceFeignApi;

    @Test
    public void test(){
        Map<String, String> user = userServiceFeignApi.getUserAddressAndPhoneById(5L);
        System.out.println(user.get("address"));
    }

}
