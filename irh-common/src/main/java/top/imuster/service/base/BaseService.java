package top.imuster.service.base;

import top.imuster.domain.base.Page;
import java.io.Serializable;
import java.util.List;

/**
 * @Description: 基本的CRUD
 * @Author: hmr
 * @Date: 2019/12/1 10:03
 **/
public interface BaseService<T,KEY extends Serializable> {

	/**
	 * 添加对象
	 * @param t
	 * @return
	 */
	int insertEntry(T... t) throws Exception;
	
	/**
	 * 添加对象并且设置主键ID值(需要事务支持)
	 * @param t
	 * @return
	 */
	//Long insertEntryCreateId(T t);
	
	/**
	 * 删除对象,主键
	 * @param key 主键数组
	 * @return 影响条数
	 */
	//int deleteByKey (KEY... key);
	
	/**
	 * 按条件删除对象
	 * @param condtion
	 * @return 影响条数
	 */
	int deleteByCondtion(T condtion) throws Exception;
	
	/**
	 * 更新对象,条件主键Id
	 * @param condtion 更新对象
	 * @return 影响条数
	 */
	int updateByKey(T condtion) throws Exception;
	
	/**
	 * 保存或更新对象(条件主键Id)
	 * @param t 需更新的对象
	 * @return 影响条数
	 */
	int saveOrUpdate(T t) throws Exception;
	
	int saveOrUpdateByKey(T t, String idMethodName) throws Exception;
	
	/**
	 * 查询对象,条件主键
	 * @param key
	 * @return 实体对象
	 */
	//T selectEntry (KEY key);
	
	/**
	 * 查询对象列表,主键数组
	 * @param key
	 * @return 对象列表
	 */
	List<T> selectEntryList(KEY... key) throws Exception;
	
	/**
	 * 查询对象,只要不为NULL与空则为条件
	 * @param condtion 查询条件
	 * @return 对象列表
	 */
	List<T> selectEntryList(T condtion) throws Exception;
	
	/**
	 * 分页查询
	 * @param condtion 查询条件
	 * @return 分页对象
	 */
	Page<T> selectPage(T condtion, Page<T> page) throws Exception;

    /**
     * 查询记录数量,add by luojinsong@jd.com 20150221;
     * @param condition;
     * @return 记录数量;
     */
    Integer selectEntryListCount(T condition) throws Exception;

}
