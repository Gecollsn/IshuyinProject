package com.ishuyin.gecollsn.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by gecollsn on 2016/3/25 15:05.
 * <p/>
 * Company: zjp-company
 * Email: cs@zhijiepay.com
 * TEL: 0592-5101911
 * <p/>
 * Declare: 处理和日期相关的辅助工具
 */
public class DateUtil extends DatePUtil {

    public static final String DATE_STANDER = "yyyy/MM/dd";
    public static final String DATE_STANDER_TIME = "yyyy/MM/dd HH:mm:ss";

    public static final String DATE_NONE = "yyyyMMdd";
    public static final String DATE_NONE_TIME = "yyyyMMddHHmmss";

    public static final String DATE_CH = "yyyy年MM月dd";
    public static final String DATE_CH_TIME = "yyyy年MM月dd HH:mm:ss";

    public static final String DATE_ULINE = "yyyy_MM_dd";
    public static final String DATE_ULINE_TIME = "yyyy_MM_dd HH:mm:ss";
    public static final String DATE_ULINE_TIME_NO_SECOND = "yyyy_MM_dd HH:mm";

    public static final String DATE_CLINE = "yyyy-MM-dd";
    public static final String DATE_CLINE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_CLINE_TIME_NO_SECOND = "yyyy-MM-dd HH:mm";

    public static final String DATE_DOT = "yyyy.MM.dd";
    public static final String DATE_DOT_TIME = "yyyy.MM.dd HH:mm:ss";

    public static final String TIME_STANDER = "HH:mm:ss";

    private static final SimpleDateFormat sdf = new SimpleDateFormat(DATE_STANDER);

    /**
     * 返回当前日期的年份
     *
     * @return
     */
    public static String year() {
        return Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
    }

    /**
     * 返回当前日期的月份
     *
     * @return
     */
    public static String month() {
        return Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1);
    }

    /**
     * 返回当前日期的天数
     *
     * @return
     */
    public static String day() {
        return Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 以标准格式返回当前日期
     *
     * @return
     */
    public static String date() {
        return date(new Date(), DATE_STANDER_TIME);
    }

    /**
     * 以指定格式返回当前日期
     *
     * @param regex    格式
     * @return
     */
    public static String date(String regex) {
        return date(new Date(), regex);
    }

    /**
     * 以指定的日期和格式返回日期
     *
     * @param date
     * @param regex
     */
    public static String date(Date date, String regex) {
        sdf.applyPattern(regex);
        return sdf.format(date);
    }

    /**
     * 以指定的时间戳和格式返回日期
     *
     * @param date
     * @param regex
     */
    public static String date(long date, String regex) {
        sdf.applyPattern(regex);
        return sdf.format(new Date(date));
    }


    /**
     * 某个日期的开始时间
     *
     * @param to    与当前日期的比较
     * @param regex
     */
    public static String date_start(int to, String regex){
        sdf.applyPattern(regex);
        final long l = timestamp_day_start(to);
        return sdf.format(new Date(l));
    }

    /**
     * 某个日期的结束时间
     *
     * @param to
     * @param regex
     */
    public static String date_end(int to, String regex){
        sdf.applyPattern(regex);
        final long l = timestamp_day_end(to);
        return sdf.format(new Date(l));
    }

    /**
     * 以给定的格式返回昨天的日期
     *
     * @param regex
     */
    public static String yesterday(String regex) {
        return addDays(regex, -1);
    }

    /**
     * 获取一个相对于今天的日期
     *
     * @param regex
     * @param day   距离今天的天数，可为负数，如昨天为-1
     */
    public static String addDays(String regex, int day){
        Date specified = addDays(new Date(), day);
        sdf.applyPattern(regex);
        return sdf.format(specified);
    }

    /**
     * 获取一个相对于指定日期的日期
     *
     * @param speDate
     * @param speDateRegex
     * @param finalRegex
     * @param day 距离指定日期的天数，可为负数
     */
    public static String addDays(String speDate, String speDateRegex, String finalRegex, int day) {
        Date temp;
        try {
            temp = parseDate(speDate, speDateRegex);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Dateformat Exception!!");
        }
        Date specified = addDays(temp, day);
        sdf.applyPattern(finalRegex);
        return sdf.format(specified);
    }

    /**
     * 获取时间戳
     *
     * @param truncate 是否设置单位为秒
     * @return
     */
    public static long timestamp(boolean truncate) {
        return truncate ? System.currentTimeMillis() / 1000 : System.currentTimeMillis();
    }

    /**
     * 获取时间戳
     *
     * @param time
     * @param regex
     *
     * @throws ParseException
     */
    public static long timestamp(String time, String regex){
        return timestamp(time, regex, false);
    }

    /**
     * 获取时间戳
     *
     * @param time
     * @param regex
     * @param truncate 是否仅精确到秒
     *
     * @throws ParseException
     */
    public static long timestamp(String time, String regex, boolean truncate){
        long stamp = 0;
        try {
            stamp = parseDate(time, regex).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (truncate) stamp = stamp / 1000;
        return stamp;
    }

    /**
     * 一天开始的时间戳
     *
     * @param to    与今天相差的天数
     * @return
     */
    public static long timestamp_day_start(int to){
        final Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.add(Calendar.DAY_OF_MONTH, to);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    /**
     * 一天结束的时间戳
     *
     * @param to    与今天相差的天数
     * @return
     */
    public static long timestamp_day_end(int to){
        final Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.add(Calendar.DAY_OF_MONTH, to);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTimeInMillis();
    }

    /**
     * 一周开始的时间
     *
     * @param to 与这周相差的周数
     */
    public static long timestamp_week_start(int to){
        final Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.add(Calendar.WEEK_OF_MONTH, to);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    /**
     * 一个月的开始
     *
     * @param to 与这个月相差的月数
     */
    public static long timestamp_month_start(int to){
        final Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.add(Calendar.MONTH, to);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    /**
     * 转换日期格式
     *
     * @param oDate
     */
    public static String formatDate(String oDate) {
        char s = '-';
        char c = ':';
        String year = oDate.substring(0, 4);
        String month = oDate.substring(4, 6);
        String day = oDate.substring(6, 8);
        switch (oDate.length()) {
            case 14:
                String hour = oDate.substring(8, 10);
                String minute = oDate.substring(10, 12);
                String second = oDate.substring(12, 14);

                oDate = year + s + month + s + day + " " + hour + c + minute + c + second;
                break;
            case 8:
                oDate = year + s + month + s + day;
                break;
            default:
                break;
        }

        return oDate;
    }
}
