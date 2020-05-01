package top.imuster.goods.dao;

import java.util.List;

/**
 * @ClassName: ProductRecommendDao
 * @Description: ProductRecommendDao
 * @author: hmr
 * @date: 2020/5/1 14:19
 */
public interface ProductRecommendDao {

    List<?> getListByUserId(Integer pageSize, Integer currentPage, Long userId);
}
