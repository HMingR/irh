package top.imuster.goods.service.impl;

import org.springframework.stereotype.Service;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.service.RecommendProductService;

/**
 * @ClassName: RecommendProductServiceImpl
 * @Description: RecommendProductServiceImpl
 * @author: hmr
 * @date: 2020/5/1 14:04
 */
@Service("recommendProductService")
public class RecommendProductServiceImpl implements RecommendProductService {

    @Override
    public Message<Page<ProductInfo>> getRecommendListByUserId(Integer pageSize, Integer currentPage, Long userId) {
        return null;
    }
}
