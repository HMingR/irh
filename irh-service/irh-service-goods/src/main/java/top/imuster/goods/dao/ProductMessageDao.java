package top.imuster.goods.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.goods.api.pojo.ProductMessage;

/**
 * ProductMessageDao 接口
 * @author 黄明人
 * @since 2019-11-24 16:31:58
 */
public interface ProductMessageDao extends BaseDao<ProductMessage, Long> {
    //自定义扩展
    /**
     * @Author hmr
     * @Description 插入信息，返回生成的id
     * @Date: 2020/1/21 10:54
     * @param productMessage
     * @reture: java.lang.Long
     **/
    Long insertReturnId(ProductMessage productMessage);

    /**
     * @Author hmr
     * @Description 根据父id获得父id对应的消息的发送者的email
     * @Date: 2020/1/21 11:21
     * @param parentId
     * @reture: java.lang.String
     **/
    String selectProductEmailByMessageParentId(Long parentId);

}