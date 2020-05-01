package top.imuster.goods.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.imuster.goods.api.dto.ProductRecommendDto;

/**
 * @ClassName: ProductRecommendDao
 * @Description: ProductRecommendDao
 * @author: hmr
 * @date: 2020/5/1 15:53
 */
@Repository
public interface ProductRecommendDao extends MongoRepository<ProductRecommendDto, String> {

    ProductRecommendDto findByUserId(Integer userId);
}
