package top.imuster.user.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.user.api.pojo.ConsumerInfo;

/**
 * ConsumerInfoDao 接口
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
public interface ConsumerInfoDao extends BaseDao<ConsumerInfo, Long> {
    //自定义扩展

    /**
     * @Description: 校验用户注册时的某些信息是否唯一
     * @Author: hmr
     * @Date: 2020/1/13 10:31
     * @param consumerInfo
     * @reture: int
     **/
    int checkInfo(ConsumerInfo consumerInfo);

    String selectEmailById(Long id);

}