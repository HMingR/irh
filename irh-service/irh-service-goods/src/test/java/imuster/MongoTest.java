package imuster;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import top.imuster.goods.GoodsProviderApplication;
import top.imuster.goods.api.dto.ProductRecommendDto;
import top.imuster.goods.dao.ProductRecommendDao;
import top.imuster.goods.service.impl.ProductRecommendService;

/**
 * @ClassName: MongoTest
 * @Description: TODO
 * @author: hmr
 * @date: 2020/5/1 15:51
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GoodsProviderApplication.class)
public class MongoTest {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ProductRecommendDao productRecommendDao;

    @Autowired
    ProductRecommendService productRecommendService;

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Test
    public void test() throws JsonProcessingException {
        ProductRecommendDto one = productRecommendService.findOne(58556);
        System.out.println(objectMapper.writeValueAsString(one));
    }

    public void test02(){
        //sqlSessionTemplate.insert(statement, parameter);
    }
}
