package top.imuster.goods.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.goods.api.pojo.ProductCollectRel;

/**
 * ProductCollectRelDao 接口
 * @author 黄明人
 * @since 2020-04-26 15:48:44
 */
public interface ProductCollectRelDao extends BaseDao<ProductCollectRel, Long> {
    //自定义扩展

    /**
     * @Author hmr
     * @Description 根据id查看收藏id的人
     * @Date: 2020/5/9 8:44
     * @param id
     * @reture: java.lang.Long
     **/
    Long selectUserIdById(Long id);

    /**
     * @Author hmr
     * @Description 清空收藏
     * @Date: 2020/5/9 8:54
     * @param currentUserIdFromCookie
     * @reture: java.lang.Integer
     **/
    Integer deleteAllCollectByUserId(Long currentUserIdFromCookie);
}