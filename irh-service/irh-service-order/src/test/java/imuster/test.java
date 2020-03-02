package imuster;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.imuster.order.provider.IrhOrderApplication;
import top.imuster.order.provider.dao.OrderInfoDao;

import javax.annotation.Resource;
import java.util.HashMap;


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
        HashMap<String, String> param = new HashMap<>();
        param.put("start", "2020-01-02");
        param.put("end", "2020-03-02");
        Long aLong = orderInfoDao.selectAmountIncrementTotal(param);
        System.out.println("------->" + aLong);

        Long l = orderInfoDao.selectOrderAmountTotalByCreateTime("2020-01-02");
        System.out.println("------------->" + l.longValue() + aLong);

        long l1 = orderInfoDao.selectOrderTotalByCreateTime("2020-01-02");
        System.out.println("------------->" + l1);

    }

}
