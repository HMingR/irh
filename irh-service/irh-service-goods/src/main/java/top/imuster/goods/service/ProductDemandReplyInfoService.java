package top.imuster.goods.service;


import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductDemandReplyInfo;

/**
 * ProductDemandReplyInfoService接口
 * @author 黄明人
 * @since 2020-05-03 15:01:34
 */
public interface ProductDemandReplyInfoService extends BaseService<ProductDemandReplyInfo, Long> {

    /**
     * @Author hmr
     * @Description 根据id分页获得一级回复列表
     * @Date: 2020/5/3 15:33
     * @param pageSize
     * @param currentPage
     * @param demandId
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductDemandReplyInfo>>
     **/
    Message<Page<ProductDemandReplyInfo>> getFirstClassReplyListByPage(Integer pageSize, Integer currentPage, Long demandId);

    /**
     * @Author hmr
     * @Description 根据demand的id查询回复总数
     * @Date: 2020/5/4 19:24
     * @param id
     * @reture: java.lang.Integer
     **/
    Integer getReplyTotalByDemandId(Long id);
}