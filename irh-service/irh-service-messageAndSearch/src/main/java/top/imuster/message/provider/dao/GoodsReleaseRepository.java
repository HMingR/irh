package top.imuster.message.provider.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import top.imuster.goods.api.pojo.ProductInfo;

/**
 * @ClassName: GoodsReleaseRepository
 * @Description: GoodsReleaseRepository
 * @author: hmr
 * @date: 2020/5/3 9:34
 */
@Repository("goodsReleaseRepository")
public interface GoodsReleaseRepository extends ElasticsearchRepository<ProductInfo, String> {
}
