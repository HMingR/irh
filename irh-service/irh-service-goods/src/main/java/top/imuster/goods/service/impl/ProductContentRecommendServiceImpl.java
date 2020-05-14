package top.imuster.goods.service.impl;

import org.springframework.stereotype.Service;
import top.imuster.goods.api.dto.ProductContentRecommendDto;
import top.imuster.goods.dao.ProductContentRecommendRepository;

import javax.annotation.Resource;

/**
 * @ClassName: ProductContentRecommendService
 * @Description: ProductContentRecommendService
 * @author: hmr
 * @date: 2020/5/14 9:52
 */
@Service("productContentRecommendService")
public class ProductContentRecommendServiceImpl {

    @Resource
    ProductContentRecommendRepository productContentRecommendRepository;

    public ProductContentRecommendDto findByUserId(Long userId){
        return productContentRecommendRepository.findByProductId(userId);
    }

}
