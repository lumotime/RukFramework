package com.j3dream.core.util;

import com.j3dream.core.constant.Constants;
import com.j3dream.core.constant.TimeDataConstants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * <p>文件名称: TimeDateUtils </p>
 * <p>所属包名: com.bloodsport.lib.core.utils</p>
 * <p>描述: 时间日期工具类, 用于获取和操作日期和时间 </p>
 * <p>创建时间: 2020/3/13 09:32 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class TimeDateUtils implements TimeDataConstants {

    private static final ThreadLocal<SimpleDateFormat> SDF_THREAD_LOCAL = new ThreadLocal<>();

    private TimeDateUtils() {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORT_CREATE_INSTANTIATE);
    }

    /**
     * 获取当前系统的时间戳
     *
     * @return 当前系统的时间戳
     */
    public static long getCurTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 获取系统当前时间戳
     *
     * @return 当前系统时间戳(String)
     */
    public static String getCurTimestampForString() {
        return String.valueOf(getCurTimestamp());
    }

    /**
     * 获取当前时间的格式化时间字符串
     *
     * @return 当前时间的格式化时间
     */
    public static String timestamp2FormatTimeString() {
        return timestamp2FormatTimeString(getCurTimestamp(), getDefaultFormat());
    }

    /**
     * 时间戳转换字符串时间格式
     *
     * @param pattern 时间格式字符串 例如: yyyy/MM/dd HH:mm
     * @return 格式化的字符串时间
     */
    public static String timestamp2FormatTimeString(final String pattern) {
        return timestamp2FormatTimeString(getCurTimestamp(), pattern);
    }

    /**
     * 时间戳转换字符串时间格式
     *
     * @param timestamp 待转换的时间戳
     * @return 格式化的字符串时间
     */
    public static String timestamp2FormatTimeString(long timestamp) {
        return timestamp2FormatTimeString(timestamp, getDefaultFormat());
    }

    /**
     * 时间戳转换字符串时间格式
     *
     * @param timestamp 待转换的时间戳
     * @param pattern   时间格式字符串 例如: yyyy/MM/dd HH:mm
     * @return 格式化的字符串时间
     */
    public static String timestamp2FormatTimeString(long timestamp, final String pattern) {
        return timestamp2FormatTimeString(timestamp, pattern2Format(pattern));
    }

    /**
     * 时间戳转换字符串时间格式
     *
     * @param timestamp  要转换的时间戳
     * @param dateFormat 转换时间戳转换字符串格式化时间的格式化器
     * @return 格式化的字符串时间
     */
    public static String timestamp2FormatTimeString(final long timestamp, final DateFormat dateFormat) {
        return dateFormat.format(new Date(timestamp));
    }

    /**
     * 将字符串日期转换成长整型
     *
     * @param date 字符串时间
     * @return 日期时间长整型
     */
    public static long timeString2FormatTimestamp(String date) {
        return timeString2FormatTimestamp(date, DEFAULT_TIME_PATTERN);
    }

    /**
     * 将字符串日期转换成长整型
     *
     * @param date   字符串时间
     * @param format 格式化
     * @return 日期时间长整型
     */
    public static long timeString2FormatTimestamp(String date, String format) {
        try {
            if (!(date == null || date.length() == 0)) {
                if (isEmpty(format)) {
                    format = DEFAULT_TIME_PATTERN;
                }
                SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
                return formatter.parse(date).getTime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据时间格式字符获取一个格式化器
     *
     * @param pattern 时间格式字符串
     * @return 时间格式化器
     */
    public static SimpleDateFormat pattern2Format(String pattern) {
        SimpleDateFormat simpleDateFormat = SDF_THREAD_LOCAL.get();
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
            SDF_THREAD_LOCAL.set(simpleDateFormat);
        } else {
            simpleDateFormat.applyPattern(pattern);
        }
        return simpleDateFormat;
    }

    /**
     * 日期时间是否为本周
     *
     * @param time 待确定的日期时间
     * @return 是否属于本周
     */
    public static boolean isThisWeek(Date time) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(time);
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        return paramWeek == currentWeek;
    }

    /**
     * 判断日期是否在今天
     *
     * @param time 确定的日期时间
     * @return 是否在今天
     */
    public static boolean isToday(Date time) {
        return isThisTime(time, FORMAT_YYYY_MM_DD);
    }

    /**
     * 判断日期是否在本周
     *
     * @param time 确定的日期时间
     * @return 是否在本周
     */
    public static boolean isThisMonth(Date time) {
        return isThisTime(time, FORMAT_YYYY_MM);
    }

    /**
     * 判断日期是否在今年
     *
     * @param time 确定的日期时间
     * @return 是否在今年
     */
    public static boolean isThisYear(Date time) {
        return isThisTime(time, FORMAT_YYYY);
    }

    /**
     * 获取某年某月有多少天
     *
     * @param year  年份
     * @param month 月份
     * @return 改月有多少天
     */
    public static int getDayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, 0);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取两个时间相差的天数
     *
     * @param time1 第一个时间
     * @param time2 第二个时间
     * @return 相差天数
     */
    public static int getDayOffset(long time1, long time2) {
        long offsetTime;
        if (time1 > time2) {
            offsetTime = time1 - getDayStartTime(getCalendar(time2)).getTimeInMillis();
        } else {
            offsetTime = getDayStartTime(getCalendar(time1)).getTimeInMillis() - time2;
        }
        return (int) (offsetTime / DAY_TIMESTAMP);
    }

    /**
     * 根据时间获取日历管理器
     *
     * @param time 时间
     * @return 日历管理器
     */
    public static Calendar getCalendar(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar;
    }

    /**
     * 获取当前时间是周几
     *
     * @return 今天是周几
     */
    public static String curWeekOfDate() {
        return curWeekOfDate(DEFAULT_WEEK_DAY);
    }

    /**
     * 获取当前时间是周几
     *
     * @param weekDays 时间日期数组
     * @return 今天是周几
     */
    public static String curWeekOfDate(String[] weekDays) {
        return getWeekOfDate(new Date(), weekDays);
    }

    /**
     * 获取指定时间是周几
     *
     * @param dt       指定时间
     * @param weekDays 时间日期数组
     * @return 在星期几
     */
    public static String getWeekOfDate(Date dt, String[] weekDays) {
        if (weekDays.length != 7) {
            throw new RuntimeException();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static String millis2FitTimeSpan(long millis, int precision) {
        if (precision <= 0) return null;
        precision = Math.min(precision, 5);
        String[] units = {"天", "小时", "分钟", "秒", "毫秒"};
        if (millis == 0) return 0 + units[precision - 1];
        StringBuilder sb = new StringBuilder();
        if (millis < 0) {
            sb.append("-");
            millis = -millis;
        }
        int[] unitLen = {86400000, 3600000, 60000, 1000, 1};
        for (int i = 0; i < precision; i++) {
            if (millis >= unitLen[i]) {
                long mode = millis / unitLen[i];
                millis -= mode * unitLen[i];
                sb.append(mode).append(units[i]);
            }
        }
        return sb.toString();
    }

    private static Calendar getDayStartTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    private static boolean isThisTime(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(date);
        String now = sdf.format(new Date());
        return param.equals(now);
    }

    private static DateFormat getDefaultFormat() {
        return pattern2Format(DEFAULT_TIME_PATTERN);
    }

    private static boolean isEmpty(CharSequence charSequence) {
        return StringUtils.isEmpty(charSequence);
    }
}