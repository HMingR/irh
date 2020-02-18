package top.imuster.user.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.user.api.pojo.IrhTemplate;

/**
 * IrhTemplateService接口
 * @author 黄明人
 * @since 2020-02-18 19:44:30
 */
public interface IrhTemplateService extends BaseService<IrhTemplate, Long> {

    /**
     * @Author hmr
     * @Description 新增模板
     * @Date: 2020/2/18 19:58
     * @param irhTemplate
     * @reture: void
     **/
    void insert(IrhTemplate irhTemplate);
}