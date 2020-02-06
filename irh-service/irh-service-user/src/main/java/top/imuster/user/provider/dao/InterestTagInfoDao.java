package top.imuster.user.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.user.api.pojo.InterestTagInfo;

/**
 * InterestTagInfoDao 接口
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
public interface InterestTagInfoDao extends BaseDao<InterestTagInfo, Long> {
    //自定义扩展
    /**
     * @Author hmr
     * @Description 根据id获得兴趣标签的名字
     * @Date: 2020/2/6 17:40
     * @param id
     * @reture: java.lang.String
     **/
    String selectTagNameById(Long id);

}