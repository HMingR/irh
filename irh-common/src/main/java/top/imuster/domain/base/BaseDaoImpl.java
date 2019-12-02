package top.imuster.domain.base;

import java.io.Serializable;
import java.util.List;


public abstract class BaseDaoImpl<T,KEY extends Serializable> extends MyBatisSupport implements BaseDao<T,KEY>{
    private static final String DEFAULT_INSERT_KEY = "insertEntry";
    //private static final String DEFAULT_INSERT_LAST_SEQUENCE_KEY = "lastSequence";
    private static final String DEFAULT_DELETE_ARRAY_KEY = "deleteByArrayKey";
    private static final String DEFAULT_DELETE_CONDTION = "deleteByCondition";
    private static final String DEFAULT_UPDATE_KEY = "updateByKey";
    private static final String DEFAULT_SELECT_ARRAY_KEY = "selectEntryArray";
    private static final String DEFAULT_SELECT_CONDTION = "selectEntryList";
    private static final String DEFAULT_SELECT_CONDTION_COUNT = "selectEntryListCount";
    /**
     * 获取命名空前前缀
     * @param statement
     * @return
     */
    public abstract String getNameSpace(String statement);

    public int insertEntry(T...t) throws Exception {
        int result = 0;
        if (t == null || t.length <= 0) {return result;}
        for (T o : t) {
            if(o != null) {
                result += this.insert(getNameSpace(DEFAULT_INSERT_KEY), o);
            }
        }
        return result;
    }

    public int deleteByKey(KEY...key) throws Exception {
        return this.delete(getNameSpace(DEFAULT_DELETE_ARRAY_KEY), key);
    }

    public int deleteByKey(T t) throws Exception {
        return this.delete(getNameSpace(DEFAULT_DELETE_CONDTION), t);
    }

    public int updateByKey(T t) throws Exception {
        return this.update(getNameSpace(DEFAULT_UPDATE_KEY), t);
    }

    public T selectEntry(KEY key) throws Exception {
        @SuppressWarnings("unchecked")
        List<T> list = this.selectEntryList(key);
        if(list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public List<T> selectEntryList(KEY...key) throws Exception {
        if (key == null || key.length <= 0) {return null;}
        return this.selectList(getNameSpace(DEFAULT_SELECT_ARRAY_KEY), key);
    }

    public List<T> selectEntryList(T t) throws Exception {
        return this.selectList(getNameSpace(DEFAULT_SELECT_CONDTION), t);
    }

    public Integer selectEntryListCount(T t) throws Exception {
        return this.select(getNameSpace(DEFAULT_SELECT_CONDTION_COUNT), t);
    }
}
