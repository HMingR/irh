package top.imuster.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imuster.goods.api.dto.ProductRecommendDto;
import top.imuster.goods.dao.ProductRecommendDao;

/**
 * @ClassName: ProductRecommendService
 * @Description: ProductRecommendService
 * @author: hmr
 * @date: 2020/5/1 17:46
 */
@Service
public class ProductRecommendService {

    @Autowired
    ProductRecommendDao productRecommendDao;

    public ProductRecommendDto findOne(Integer userId){
        return productRecommendDao.findByUserId(userId);
    }

}
