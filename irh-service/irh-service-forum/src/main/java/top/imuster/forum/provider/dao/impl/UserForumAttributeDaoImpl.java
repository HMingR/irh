package top.imuster.forum.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.forum.api.pojo.UserForumAttribute;
import top.imuster.forum.provider.dao.UserForumAttributeDao;

import java.util.List;

/**
 * UserForumAttributeDao 实现类
 * @author 黄明人
 * @since 2020-02-08 15:27:10
 */
@Repository("userForumAttributeDao")
public class UserForumAttributeDaoImpl extends BaseDaoImpl<UserForumAttribute, Long> implements UserForumAttributeDao {
	private final static String NAMESPACE = "top.imuster.user.api.pojo.dao.UserForumAttributeDao.";
	private final static String SELECT_UP_LIST_BY_CONDITION = "selectUpListByCondition";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public List<UserForumAttribute> selectUpListByCondition(UserForumAttribute condition) {
		return this.selectList(getNameSpace(SELECT_UP_LIST_BY_CONDITION), condition);
	}
}