package top.imuster.goods.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import top.imuster.goods.api.dto.ProductUserTagRecommendDto;

/**
 * @ClassName: ProductUserTagRecommendTRepository
 * @Description: ProductUserTagRecommendTRepository
 * @author: hmr
 * @date: 2020/5/24 20:14
 */
public interface ProductUserTagRecommendTRepository extends MongoRepository<ProductUserTagRecommendDto, String> {
    ProductUserTagRecommendDto findByUserId(Long userId);
}
