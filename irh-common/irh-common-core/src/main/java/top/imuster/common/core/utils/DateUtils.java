package top.imuster.common.core.utils;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @ClassName: DateUtils
 * @Description: 日期工具类
 * @author: hmr
 * @date: 2019/12/23 21:50
 */
public class DateUtils {

    protected static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:dd";

    private DateUtils(){}

    /**
     * @Description: 获得当前时间，该方法是线程安全的，默认是yyyy-MM-dd HH:mm:dd格式
     * @Author: hmr
     * @Date: 2019/12/23 21:52
     * @param
     * @reture: java.lang.String
     **/
    public static String now(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern(DEFAULT_FORMAT);
        return now.format(format);
    }

    /**
     * @Description: 获得当前时间，该方法是线程安全的
     * @Author: hmr
     * @Date: 2019/12/23 21:55
     * @param pattern
     * @reture: java.lang.String
     **/
    public static String now(String pattern){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        return now.format(format);
    }

    public static Date current(){
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_FORMAT);
            String now = now();
            return simpleDateFormat.parse(now);
        }catch (Exception e){
            logger.error("获得当前时间错误");
            return null;
        }
    }

    /**
     * @Description: 解析时间
     * @Author: hmr
     * @Date: 2019/12/23 22:00
     * @param time
     * @reture: java.time.LocalDate
     **/
    public static Date parse(String time) {
        try {
            return new SimpleDateFormat(DEFAULT_FORMAT).parse(time);
        } catch (ParseException e) {
            logger.error("解析日期类型错误");
            return null;
        }
    }



    public static Date parse(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
        String format = sdf.format(date);
        try {
            return sdf.parse(format);
        } catch (ParseException e) {
            logger.error("解析日期类型错误");
            return null;
        }
    }

    public static String takeDate2String(Date date){
        SimpleDateFormat sDateFormat = new SimpleDateFormat(DEFAULT_FORMAT);
        return sDateFormat.format(date);
    }

    public static String takeDate2String(Date date, String pattern){
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        return sDateFormat.format(date);
    }


    public static Date parse(String data, String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(data);
        } catch (ParseException e) {
        }
        return null;
    }


    /**
     * 获取第x天之前的时间
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }


    /**
     * 获取过去x天内的日期字符串数组
     * @param intervals      intervals天内
     * @return              日期数组
     */
    public static ArrayList<String> getDays(int intervals) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        for (int i = intervals -1; i >= 0; i--) {
            pastDaysList.add(getPastDate(i));
        }
        return pastDaysList;
    }


    /**
     * 将时间转化为Calendar形式
     * @param date
     * @return
     */
    public static Calendar parseCalendar(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }


    /**
     * 获得过去一周的开始时间和结束时间
     * @return
     */
    public static ArrayList<String> getWeekStartTimeAndEndTime(){
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> days = getDays(7);
        result.add(takeDate2String(parse(days.get(0), "yyyy-MM-dd")));
        String endTime = takeDate2String(current());
        result.add(endTime);
        return result;
    }

    /**
     * 获得两个日期类型的时间之间的所有日期
     * @param start
     * @param end
     * @return
     */
    public static List<Date> getBetweenDates(Date start, Date end) {
        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.DAY_OF_YEAR, 1);
        result.add(start);
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        result.add(end);
        return result;
    }


    /**
     * 得到一个时间的当天的开始时间 例如:2010-10-8 11:04:05则返回时间设置2010-10-8 00:00:00
     *
     * @param date
     * @return
     */
    public static Date getTheDateOfStartTime(Date date) {
        Calendar c = parseCalendar(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static String getTheDateOfStartTime(String date){
        Date theDateOfStartTime = getTheDateOfStartTime(DateUtil.parse(date, DEFAULT_FORMAT));
        return takeDate2String(theDateOfStartTime);
    }

    public static String getTheDateOfStartTime(String strDate, String pattern){
        Date theDateOfStartTime = getTheDateOfStartTime(DateUtil.parse(strDate, pattern));
        return takeDate2String(theDateOfStartTime, pattern);
    }

    /**
     * 得到一个时间的当天的最后时间 例如:2010-10-8 11:04:05则返回时间设置2010-10-8 23:59:59
     *
     * @param date
     * @return
     */
    public static Date getTheDateOfEndTime(Date date) {
        Calendar c = parseCalendar(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    /**
     * 重载getTheDateOfEndTime方法
     * @param date
     * @return
     */
    public static String getTheDateOfEndTime(String date){
        Date parse = parse(date, DEFAULT_FORMAT);
        return takeDate2String(getTheDateOfEndTime(parse));
    }

    public static String getTheDateOfEndTime(String date, String pattern){
        Date parse = parse(date, pattern);
        return takeDate2String(getTheDateOfEndTime(parse));
    }

    /**
     * 获得两个字符串类型的时间之间的所有日期
     * @param startTime
     * @param entTime
     * @return
     */
    public static List<String> getBetweenDates(String startTime, String entTime){
        ArrayList<String> result = new ArrayList<>();
        Date start = parse(startTime, "yyyy-MM-dd");
        Date end = parse(entTime, "yyyy-MM-dd");
        List<Date> betweenDates = getBetweenDates(start, end);
        Iterator<Date> iterator = betweenDates.iterator();
        while (iterator.hasNext()){
            result.add(takeDate2String(iterator.next(), "yyyy-MM-dd"));
        }
        return result;
    }


    /**
     * 获得任意时间所在月份的最小日期
     * @param repeatDate
     * @return
     */
    public static String getMinMonthDate(String repeatDate){
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
        Calendar calendar = Calendar.getInstance();
        try {
            if(StringUtils.isNotBlank(repeatDate) && !"null".equals(repeatDate)){
                calendar.setTime(dft.parse(repeatDate));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return getTheDateOfStartTime(dft.format(calendar.getTime()));
    }

    /**
     * 获取任意时间的下一个月的开始时间
     * @param repeatDate
     * @return
     */
    public static String getPreMonth(String repeatDate) {
        String lastMonth = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
        int year = Integer.parseInt(repeatDate.substring(0, 4));
        String monthsString = repeatDate.substring(5, 7);
        int month;
        if ("0".equals(monthsString.substring(0, 1))) {
            month = Integer.parseInt(monthsString.substring(1, 2));
        } else {
            month = Integer.parseInt(monthsString.substring(0, 2));
        }
        cal.set(year,month,Calendar.DATE);
        lastMonth = dft.format(cal.getTime());
        return getMinMonthDate(lastMonth);
    }

    public static void main(String[] args) {
        String s = "2019-03-01 00:00:01";
        String substring = s.substring(0, 7);
        System.out.println(substring);
    }


}
