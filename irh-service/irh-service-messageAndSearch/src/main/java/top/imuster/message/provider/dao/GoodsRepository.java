package top.imuster.message.provider.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import top.imuster.goods.api.dto.ESProductDto;

/**
 * @ClassName: GoodsRepository
 * @Description: TODO
 * @author: hmr
 * @date: 2020/5/6 15:24
 */
@Repository
public interface GoodsRepository extends ElasticsearchRepository<ESProductDto, Long> {
}
