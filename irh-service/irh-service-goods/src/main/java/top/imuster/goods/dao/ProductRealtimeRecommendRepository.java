package top.imuster.goods.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.imuster.goods.api.dto.ProductRealtimeRecommendDto;

/**
 * @ClassName: ProductRealtimeRecommendRepository
 * @Description: ProductRealtimeRecommendRepository
 * @author: hmr
 * @date: 2020/5/14 16:01
 */
@Repository("productRealtimeRecommendRepository")
public interface ProductRealtimeRecommendRepository extends MongoRepository<ProductRealtimeRecommendDto, String> {

    ProductRealtimeRecommendDto findByUserId(Long userId);
}
