package top.imuster.common.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;

/**
 * @ClassName: DateUtils
 * @Description: 日期工具类
 * @author: hmr
 * @date: 2019/12/23 21:50
 */
public class DateUtils {

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
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:dd");
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

    /**
     * @Description: 解析时间
     * @Author: hmr
     * @Date: 2019/12/23 22:00
     * @param time
     * @reture: java.time.LocalDate
     **/
    public static Date parse(String time) throws ParseException {
       return new SimpleDateFormat("yyyy-MM-dd HH:mm:dd").parse(time);
    }

    public static Date parse() throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:dd").parse(now());
    }

    public static void main(String[] args) throws ParseException {
        String now = now();
        System.out.println(now);
        Date parse = parse("2019-12-30 16:00:30");
        System.out.println(parse);
    }


}
