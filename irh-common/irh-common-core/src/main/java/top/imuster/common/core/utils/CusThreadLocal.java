package top.imuster.common.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: CusThreadLocal
 * @Description: 自定义本地线程
 * @author: hmr
 * @date: 2019/12/15 16:10
 */
@Slf4j
public class CusThreadLocal extends ThreadLocal<Map<String, Object>> {

    /**
     * The constant threadContext.
     */
    private final static ThreadLocal<Map<String, Object>> THREAD_CONTEXT = new CusThreadLocal();

    /**
     * Put.
     *
     * @param key   the key
     * @param value the value
     */
    public static void put(String key, Object value) {
        log.info("线程的值为====", THREAD_CONTEXT.toString());
        getContextMap().put(key, value);
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put(key, value);
        THREAD_CONTEXT.set(stringObjectHashMap);
    }

    /**
     * Remove object.
     *
     * @param key the key
     *
     * @return the object
     */
    public static Object remove(String key) {
        return getContextMap().remove(key);
    }

    /**
     * Get object.
     *
     * @param key the key
     *
     * @return the object
     */
    public static Object get(String key) {
        return getContextMap().get(key);
    }

    /*private static class MapThreadLocal extends ThreadLocal<Map<String, Object>> {
        *//**
         * Initial value map.
         *
         * @return the map
         *//*
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>(8) {

                private static final long serialVersionUID = 3637958959138295593L;

                @Override
                public Object put(String key, Object value) {
                    return super.put(key, value);
                }
            };
        }
    }*/

    /**
     * 取得thread context Map的实例。
     *
     * @return thread context Map的实例
     */
    private static Map<String, Object> getContextMap() {
        return THREAD_CONTEXT.get();
    }

    /**
     * 清理线程所有被hold住的对象。以便重用！
     */
    /*public static void remove() {
        getContextMap().clear();
    }*/


}
