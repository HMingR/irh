package top.imuster.goods.service.impl;

import org.springframework.stereotype.Service;
import top.imuster.goods.api.dto.ProductRealtimeRecommendDto;
import top.imuster.goods.dao.ProductRealtimeRecommendRepository;
import top.imuster.goods.service.ProductRealtimeRecommendService;

import javax.annotation.Resource;

/**
 * @ClassName: ProductRealtimeRecommendServiceImpl
 * @Description: ProductRealtimeRecommendServiceImpl
 * @author: hmr
 * @date: 2020/5/14 16:03
 */
@Service("productRealtimeRecommendService")
public class ProductRealtimeRecommendServiceImpl implements ProductRealtimeRecommendService {

    @Resource
    ProductRealtimeRecommendRepository productRealtimeRecommendRepository;

    public ProductRealtimeRecommendDto getInfoByUserId(Long userId){
        return productRealtimeRecommendRepository.findByUserId(userId);
    }

}
