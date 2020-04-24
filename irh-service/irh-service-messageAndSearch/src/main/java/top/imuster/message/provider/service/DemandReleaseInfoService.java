package top.imuster.message.provider.service;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;
import top.imuster.goods.api.pojo.ProductDemandInfo;

/**
 * @ClassName: DemandReleaseInfoService
 * @Description: DemandReleaseInfoService
 * @author: hmr
 * @date: 2020/4/24 11:32
 */
@Service("demandReleaseInfoService")
public interface DemandReleaseInfoService extends ElasticsearchRepository<ProductDemandInfo, String> {
}
