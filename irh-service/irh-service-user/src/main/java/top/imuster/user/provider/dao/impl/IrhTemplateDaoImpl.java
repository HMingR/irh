package top.imuster.user.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.user.api.pojo.IrhTemplate;
import top.imuster.user.provider.dao.IrhTemplateDao;

/**
 * IrhTemplateDao 实现类
 * @author 黄明人
 * @since 2020-02-18 19:44:30
 */
@Repository("irhTemplateDao")
public class IrhTemplateDaoImpl extends BaseDaoImpl<IrhTemplate, Long> implements IrhTemplateDao {
	private final static String NAMESPACE = "top.imuster.user.provider.dao.IrhTemplateDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}