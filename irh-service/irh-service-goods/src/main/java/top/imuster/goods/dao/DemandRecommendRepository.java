package top.imuster.goods.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import top.imuster.goods.api.dto.DemandRecommendDto;

/**
 * @ClassName: DemandRecommendRepository
 * @Description: DemandRecommendRepository
 * @author: hmr
 * @date: 2020/5/24 15:31
 */
@Repository("demandRecommendRepository")
public interface DemandRecommendRepository extends MongoRepository<DemandRecommendDto, String> {

    DemandRecommendDto findByUserId(Long userId);
}
