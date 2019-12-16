package top.imuster.common.core.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: LogTypeEnum
 * @Description: 操作日志的类型
 * @author: hmr
 * @date: 2019/12/14 14:59
 */
public enum LogTypeEnum {
    OPERATION_LOG(10, "操作日志"),
    LOGIN_LOG(20, "登录日志"),
    EXCEPTION_LOG(30, "异常日志");

    private Integer type;
    private String name;

    LogTypeEnum(Integer type, String name){
        this.name = name;
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getName(Integer type) {
        for (LogTypeEnum ele : LogTypeEnum.values()) {
            if (ele.getType() == type) {
                return ele.getName();
            }
        }
        return null;
    }

    public static LogTypeEnum getEnum(String type) {
        for (LogTypeEnum ele : LogTypeEnum.values()) {
            if (type.equals(ele.getType())) {
                return ele;
            }
        }
        return LogTypeEnum.OPERATION_LOG;
    }

    public static List<Map<String, Object>> getList() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (LogTypeEnum ele : LogTypeEnum.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("key", ele.getType());
            map.put("value", ele.getName());
            list.add(map);
        }
        return list;
    }

}
