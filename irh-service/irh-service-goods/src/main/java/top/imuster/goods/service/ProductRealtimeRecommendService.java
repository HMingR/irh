package top.imuster.goods.service;

import top.imuster.goods.api.dto.ProductRealtimeRecommendDto;

/**
 * @ClassName: ProductRealtimeRecommendService
 * @Description: ProductRealtimeRecommendService
 * @author: hmr
 * @date: 2020/5/14 16:03
 */
public interface ProductRealtimeRecommendService {

    ProductRealtimeRecommendDto getInfoByUserId(Long userId);

}
