package top.imuster.common.core.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: CusThreadLocal
 * @Description: 自定义本地线程
 * @author: hmr
 * @date: 2019/12/15 16:10
 */
public class CusThreadLocal {

    private static ThreadLocal<Map<String, Object>> mapThreadLocal = new MapThreadLocal();

    public static void put(String key, Object value){
        mapThreadLocal.get().put(key, value);
    }

    /**
     * @Description: 根据key获得一个对象
     * @Author: hmr
     * @Date: 2019/12/15 16:21
     * @param key
     * @reture: java.lang.Object
     **/
    public static Object get(String key){
        return mapThreadLocal.get().get(key);
    }

    /**
     * @Description: 根据key删除线程中的对象
     * @Author: hmr
     * @Date: 2019/12/15 16:20
     * @param key
     * @reture: java.lang.Object
     **/
    public static Object remove(String key){
        return mapThreadLocal.get().remove(key);
    }

    /**
     * @Description: 清除线程中所有的对象
     * @Author: hmr
     * @Date: 2019/12/15 16:20
     * @param
     * @reture: void
     **/
    public static void clear(){
        mapThreadLocal.get().clear();
    }

    private static class MapThreadLocal extends ThreadLocal<Map<String, Object>> {

        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>(8) {
                private static final long serialVersionUID = -5871556411091439769L;

                @Override
                public Object put(String key, Object value) {
                    return super.put(key, value);
                }
            };
        }
    }

}
