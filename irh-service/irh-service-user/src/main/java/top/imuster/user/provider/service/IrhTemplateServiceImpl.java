package top.imuster.user.provider.service;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.user.api.pojo.IrhTemplate;
import top.imuster.user.provider.dao.IrhTemplateDao;
import top.imuster.user.provider.exception.UserException;

import javax.annotation.Resource;

/**
 * IrhTemplateService 实现类
 * @author 黄明人
 * @since 2020-02-18 19:44:30
 */
@Service("irhTemplateService")
public class IrhTemplateServiceImpl extends BaseServiceImpl<IrhTemplate, Long> implements IrhTemplateService {

    @Resource
    private IrhTemplateDao irhTemplateDao;

    @Override
    public BaseDao<IrhTemplate, Long> getDao() {
        return this.irhTemplateDao;
    }

    @Override
    public void insert(IrhTemplate irhTemplate) {
        Integer type = irhTemplate.getType();
        IrhTemplate condition = new IrhTemplate();
        condition.setState(2);
        condition.setType(type);
        Integer count = irhTemplateDao.selectEntryListCount(condition);
        if(count != 0)  throw new UserException("当前类型的模板已经存在,请删除原有模板再添加");
        irhTemplateDao.insertEntry(irhTemplate);
    }
}