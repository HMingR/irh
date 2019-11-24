package top.imuster.domain.base;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T,KEY extends Serializable> {
    /**
     * 添加对象
     * @param t
     * @return 影响条数
     */
    int insertEntry(T... t) throws Exception;

    /**
     * 删除对象,主键
     * @param key
     * @return 影响条数
     */
    //int deleteByKey (KEY... key);

    /**
     * 删除对象,条件
     * @param condtion
     * @return 影响条数
     */
    int deleteByKey(T condtion) throws Exception;

    /**
     * 更新对象,条件主键ID
     * @param t
     * @return 影响条数
     */
    int updateByKey(T t) throws Exception;

    /**
     * 查询对象,条件主键
     * @param key
     * @return
     */
    //T selectEntry (KEY key);

    /**
     * 查询对象,条件主键数组
     * @param key
     * @return
     */
    List<T> selectEntryList(KEY... key) throws Exception;

    /**
     * 查询对象,只要不为NULL与空则为条件
     * @param t
     * @return
     */
    List<T> selectEntryList(T t) throws Exception;

    /**
     * 查询对象总数
     * @param t
     * @return
     */
    Integer selectEntryListCount(T t) throws Exception;
}
