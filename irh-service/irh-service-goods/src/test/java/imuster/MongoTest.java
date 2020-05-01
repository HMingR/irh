package imuster;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.imuster.goods.GoodsProviderApplication;
import top.imuster.goods.dao.ProductRecommendDao;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: MongoTest
 * @Description:
 * @author: hmr
 * @date: 2020/5/1 14:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GoodsProviderApplication.class)
public class MongoTest {

    @Resource
    ProductRecommendDao productRecommendDao;

    @Autowired
    ObjectMapper objectMapper;
    
    @Test
    public void test() throws JsonProcessingException {
        List<?> listByUserId = productRecommendDao.getListByUserId(10, 10, 58556L);
        String s = objectMapper.writeValueAsString(listByUserId);
        System.out.println(s);
    }

}
