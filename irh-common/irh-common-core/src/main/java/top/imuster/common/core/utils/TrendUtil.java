package top.imuster.common.core.utils;

import java.util.*;

/**
 * @ClassName: TrendUtil
 * @Description: 用来计算趋势图的横坐标时间间隔
 * @author: hmr
 * @date: 2020/3/2 15:45
 */
public class TrendUtil {

    /**
     * @Author hmr
     * @Description 获得一周内的所有天数的开始结束时间
     * @Date: 2020/3/2 15:47
     * @param
     * @reture: java.util.List<java.lang.String>
     **/
    public static Map<String, List<String>> getCurrentWeekTime(){
        List<String> startTimes = new ArrayList<>();
        List<String> endTimes = new ArrayList<>();
        //获得一周的开始时间和结束时间
        ArrayList<String> weekStartTimeAndEndTime = DateUtil.getWeekStartTimeAndEndTime();
        //获得一周7天的开始时间
        List<String> weekDayStartTime = DateUtil.getBetweenDates(weekStartTimeAndEndTime.get(0), weekStartTimeAndEndTime.get(1));
        int index = 0;
        for (String s : weekDayStartTime) {
            String start = DateUtil.getTheDateOfStartTime(weekDayStartTime.get(index), "yyyy-MM-dd");
            String end = DateUtil.getTheDateOfEndTime(weekDayStartTime.get(index), "yyyy-MM-dd");
            startTimes.add(start);
            endTimes.add(end);
            index++;
        }
        HashMap<String, List<String>> res = new HashMap<>();
        res.put("start", startTimes);
        res.put("end", endTimes);
        return res;
    }

    /**
     * @Author hmr
     * @Description 获得最近一个月的每天的开始时间和结束时间
     * @Date: 2020/3/2 15:52
     * @param
     * @reture: java.util.Map<java.lang.String,java.util.Set<java.lang.String>>
     **/
    public static Map<String, List<String>> getCurrentOneMonthTime(){
        List<String> startTimes = new ArrayList<>();
        List<String> endTimes = new ArrayList<>();
        String startTime = DateUtil.getPastDate(30);
        String endTime = DateUtil.now();
        List<String> days = DateUtil.getBetweenDates(startTime, endTime);
        days.stream().forEach(time -> {
            String start = DateUtil.getTheDateOfStartTime(time, "yyyy-MM-dd");
            String end = DateUtil.getTheDateOfEndTime(time, "yyyy-MM-dd");
            startTimes.add(start);
            endTimes.add(end);

        });
        HashMap<String, List<String>> res = new HashMap<>();
        res.put("start", startTimes);
        res.put("end", endTimes);
        return res;
    }

    /**
     * @Author hmr
     * @Description 获得最近半年的周开始的时间和结束时间
     * @Date: 2020/3/2 15:53
     * @param
     * @reture: java.util.Map<java.lang.String,java.util.Set<java.lang.String>>
     **/
    public static Map<String, List<String>> getSixMonthTime(){
        ArrayList<String> days = DateUtil.getDays(180);
        List<String> startTimes = new ArrayList<>(26);
        List<String> endTimes = new ArrayList<>();
        int m = 7;
        for (int j = 0; j < days.size() - 1;) {
            String start = days.get(j);
            String end = days.get(m - 1);
            if(m > days.size() - 7){
                m = days.size();
                j += 7;
            }else{
                j += 7;
                m = j + 7;
            }
            startTimes.add(start);
            endTimes.add(end);
        }
        HashMap<String, List<String>> res = new HashMap<>();
        res.put("start", startTimes);
        res.put("end", endTimes);
        return res;
    }

    public static Map<String, List<String>> getCurrentOneYearTime(){
        List<String> startTimes = new ArrayList<>(12);
        List<String> endTimes = new ArrayList<>(12);
        //最近一年
        String startTime = DateUtil.getPastDate(365);
        String endTime = DateUtil.getPreMonth(startTime);
        boolean flag = true;
        while (flag){
            //当目前的月份和搜索的时间的结束时间相同时，则再循环一次就可以退出循环
            if(DateUtil.getPreMonth(DateUtil.getMinMonthDate(DateUtil.now())).equalsIgnoreCase(endTime)){
                startTime = endTime;
                endTime = DateUtil.now();
                flag = false;
            }
            startTimes.add(startTime);
            endTimes.add(endTime);
            startTime = endTime;
            endTime = DateUtil.getPreMonth(endTime);
        }
        HashMap<String, List<String>> res = new HashMap<>();
        res.put("start", startTimes);
        res.put("end", endTimes);
        return res;
    }
}
