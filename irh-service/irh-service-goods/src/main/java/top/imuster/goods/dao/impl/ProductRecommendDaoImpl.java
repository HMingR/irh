package top.imuster.goods.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import top.imuster.goods.api.dto.ProductRecommendDto;
import top.imuster.goods.dao.ProductRecommendDao;

import java.util.List;

/**
 * @ClassName: ProductRecommendDaoImpl
 * @Description: ProductRecommendDaoImpl
 * @author: hmr
 * @date: 2020/5/1 14:19
 */
@Repository("productRecommendDao")
public class ProductRecommendDaoImpl implements ProductRecommendDao {

    private static final Logger log = LoggerFactory.getLogger(ProductRecommendDaoImpl.class);


    //mongodb中的集合名称
    private static final String COLLECT_NAME = "UserRecs";

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private ObjectMapper objectMapper;


    public List<?> getListByUserId(Integer pageSize, Integer currentPage, Long userId){
        List<ProductRecommendDto> res = mongoTemplate.find(new Query(Criteria.where("userId").is(userId)), ProductRecommendDto.class);
        try {
            log.debug(objectMapper.writeValueAsString(res));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }



}
