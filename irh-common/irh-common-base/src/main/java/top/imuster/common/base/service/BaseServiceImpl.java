package top.imuster.common.base.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 实现类
 * @Author: hmr
 * @Date: 2019/12/1 10:04
 **/
public abstract class BaseServiceImpl<T, KEY extends Serializable> implements BaseService<T, KEY> {
	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	/**
	 * 获取DAO操作类
	 */
	public abstract BaseDao<T, KEY> getDao();
	
	public int insertEntry(T...t) throws Exception {
		return getDao().insertEntry(t);
	}

	public int deleteByCondtion(T condtion) throws Exception {
		return getDao().deleteByKey(condtion);
	}
	
	public int updateByKey(T condtion) throws Exception {
		return getDao().updateByKey(condtion);
	}
	
	@SuppressWarnings("unchecked")
	public int saveOrUpdate(T t) throws Exception {
		KEY id = null;
		try {
			Class<?> clz = t.getClass();
			id = (KEY)clz.getMethod("getId").invoke(t);
		} catch (Exception e) {
			LOGGER.warn("获取对象主键值失败!");
			e.printStackTrace();
		}
		if(id != null) {
			return this.updateByKey(t);
		} 
		return this.insertEntry(t);
	}
	
	@SuppressWarnings("unchecked")
	public int saveOrUpdateByKey(T t ,String idMethodName) throws Exception {
	        KEY id = null;
	        try {
	            Class<?> clz = t.getClass();
	            id = (KEY)clz.getMethod(idMethodName).invoke(t);
	        } catch (Exception e) {
	            LOGGER.warn("获取对象主键值失败!");
	            e.printStackTrace();
	        }
	        if(id != null) {
	            return this.updateByKey(t);
	        } 
	        return this.insertEntry(t);
	    }
	
	public List<T> selectEntryList(T condtion) throws Exception {
		return getDao().selectEntryList(condtion);
	}

	/**
	 * @Description: 分页查询
	 * @Author: hmr
	 * @Date: 2019/11/26 10:56
	 * @param condition
	 * @param page
	 * @reture: top.imuster.pojo.base.Page<T>
	 **/
	public Page<T> selectPage(T condition, Page<T> page) throws Exception {
		try {
			Class<?> clz = condition.getClass();
			clz.getMethod("setStartIndex", Integer.class).invoke(condition, page.getStartIndex());
			clz.getMethod("setEndIndex", Integer.class).invoke(condition, page.getEndIndex());
		} catch (Exception e) {
			LOGGER.error("设置分页设置失败",e);
		}
		Integer size = getDao().selectEntryListCount(condition);
		if(size == null || size <= 0) {
			return page;
		}
		page.setTotalCount(size);
		page.setResult(this.selectEntryList(condition));
		return page;
	}

    /**
     * 查询记录数量;
     * @param condition;
     * @return
     */
    public Integer selectEntryListCount(T condition) throws Exception {
        Integer size = getDao().selectEntryListCount(condition);
        return size;
    }

    public List<T> selectEntryList (KEY... key) throws Exception {
    	return getDao().selectEntryList(key);
	}

}
