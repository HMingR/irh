package top.imuster.goods.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.goods.api.pojo.ProductMessageInfo;

/**
 * ProductMessageDao 接口
 * @author 黄明人
 * @since 2019-11-24 16:31:58
 */
public interface ProductMessageDao extends BaseDao<ProductMessageInfo, Long> {
    //自定义扩展
    /**
     * @Author hmr
     * @Description 插入信息，返回生成的id
     * @Date: 2020/1/21 10:54
     * @param productMessageInfo
     * @reture: java.lang.Long
     **/
    Long insertReturnId(ProductMessageInfo productMessageInfo);

    /**
     * @Author hmr
     * @Description 根据父id获得父id对应的消息的发送者的id
     * @Date: 2020/1/21 11:21
     * @param parentId
     * @reture: java.lang.String
     **/
    Long selectSalerIdByMessageParentId(Long parentId);

    /**
     * @Author hmr
     * @Description 根据一级留言id查看该留言下的回复数量
     * @Date: 2020/5/6 19:49
     * @param id
     * @reture: java.lang.Integer
     **/
    Integer selectReplyTotalById(Long id);

    /**
     * @Author hmr
     * @Description 根据留言id获得父级留言的作者
     * @Date: 2020/5/7 8:40
     * @param pid
     * @reture: java.lang.Long
     **/
    Long selectParentWriterIdById(Long pid);
}