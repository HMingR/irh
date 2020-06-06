package imuster;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import top.imuster.goods.GoodsProviderApplication;
import top.imuster.goods.api.dto.ProductContentRecommendDto;
import top.imuster.goods.api.dto.ProductRecommendDto;
import top.imuster.goods.dao.ProductContentRecommendRepository;
import top.imuster.goods.dao.ProductRecommendDao;
import top.imuster.goods.service.impl.ProductContentRecommendServiceImpl;
import top.imuster.goods.service.impl.ProductRecommendService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

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

    @Resource
    ProductContentRecommendRepository productContentRecommendRepository;

    @Resource
    ProductContentRecommendServiceImpl productContentRecommendService;

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    RedisCommands commands;

    @Test
    public void test05(){
      //  AsyncCommand getbit =(AsyncCommand) commands.setbit("irh::product::browse::record::bitmap::key::11", 5L, 1);
        Long getbit1 = commands.getbit("irh::product::browse::record::bitmap::key::11".getBytes(), 5L);
        //System.out.println(getbit);
        System.out.println(getbit1);
    }

    @Test
    public void test() throws JsonProcessingException {
        ProductRecommendDto one = productRecommendService.findOne(5L);
        System.out.println(objectMapper.writeValueAsString(one));
    }


    @Test
    public void test02() throws JsonProcessingException {
        ProductContentRecommendDto byProductId = productContentRecommendRepository.findByProductId(2L);
        String asString = objectMapper.writeValueAsString(byProductId);
        long count = productContentRecommendRepository.count();
        ProductRecommendDto productRecommendDtoByUserId = productRecommendDao.findProductRecommendDtoByUserId(1L);
        System.out.println(objectMapper.writeValueAsString(productRecommendDtoByUserId));

        Optional<ProductRecommendDto> byId = productRecommendDao.findById("5eb7db3ab5bd1f2f70c7be82");
        System.out.println(objectMapper.writeValueAsString(byId.get()));
        System.out.println(asString);
        System.out.println(count);
    }

    @Test
    public void test03(){
        List<ProductContentRecommendDto> all = productContentRecommendRepository.findAll();
        System.out.println(all.toArray());
    }


    @Test
    public void test04(){
        List<ProductRecommendDto> all = productRecommendDao.findAll();
        System.out.println(all.size());
        long count = productRecommendDao.count();
        System.out.println(count);
    }
}
