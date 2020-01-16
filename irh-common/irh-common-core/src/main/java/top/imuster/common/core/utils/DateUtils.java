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

    public static Date current() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_FORMAT);
        String now = now();
        return simpleDateFormat.parse(now);
    }

    /**
     * @Description: 解析时间
     * @Author: hmr
     * @Date: 2019/12/23 22:00
     * @param time
     * @reture: java.time.LocalDate
     **/
    public static Date parse(String time) throws ParseException {
       return new SimpleDateFormat(DEFAULT_FORMAT).parse(time);
    }

    public static Date parse() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
        String format = sdf.format(now());
        return sdf.parse(format);
    }

    public static void main(String[] args) throws ParseException {
        String now = now();
        System.out.println(now);
        Date parse = parse("2019-12-30 16:00:30");
        System.out.println(parse);
    }


}
