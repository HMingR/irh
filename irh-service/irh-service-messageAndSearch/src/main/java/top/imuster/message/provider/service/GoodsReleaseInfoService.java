package top.imuster.message.provider.service;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;
import top.imuster.goods.api.pojo.ProductInfo;

/**
 * @ClassName: ReleaseInfoService
 * @Description: ReleaseInfoService
 * @author: hmr
 * @date: 2020/4/24 11:32
 */
@Service("goodsReleaseInfoService")
public interface GoodsReleaseInfoService extends ElasticsearchRepository<ProductInfo, String> {
}
