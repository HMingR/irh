package imuster;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;
import top.imuster.goods.GoodsProviderApplication;
import top.imuster.goods.api.dto.ProductRecommendDto;
import top.imuster.goods.dao.ProductRecommendDao;
import top.imuster.goods.service.impl.ProductRecommendService;

import java.util.List;

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

    @Test
    public void test() throws JsonProcessingException {
/*        MongoCollection<Document> test = mongoTemplate.getCollection("test");
        BasicDBObject basicQuery = new BasicDBObject();
        Document document = new Document();
        document.append("userId", 123.0);
        FindIterable<Document> documents = test.find(document);
        System.out.println(objectMapper.writeValueAsString(documents.first()));*/

        ProductRecommendDto one = productRecommendService.findOne(58556);
        System.out.println(objectMapper.writeValueAsString(one));

    }
}
