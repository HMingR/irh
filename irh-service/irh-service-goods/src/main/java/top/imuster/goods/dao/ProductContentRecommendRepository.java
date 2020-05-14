package top.imuster.goods.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.imuster.goods.api.dto.ProductContentRecommendDto;

/**
 * @ClassName: ProductContentRecommendRepository
 * @Description: ProductContentRecommendRepository
 * @author: hmr
 * @date: 2020/5/14 9:53
 */
@Repository("productContentRecommendRepository")
public interface ProductContentRecommendRepository extends MongoRepository<ProductContentRecommendDto, String> {
    ProductContentRecommendDto findByProductId(Long productId);
}
