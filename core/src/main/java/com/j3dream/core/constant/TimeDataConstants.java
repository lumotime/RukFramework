package com.j3dream.core.constant;

/**
 * <p>文件名称: TimeDataConstants </p>
 * <p>所属包名: com.lumotime.core.constant</p>
 * <p>描述: 时间日期常量列表 </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/7/9 10:22 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public interface TimeDataConstants extends Constants {
    long DAY_TIMESTAMP = 1000 * 60 * 60 * 24;
    String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    String[] DEFAULT_WEEK_DAY = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    String FORMAT_WEEK_YYYY_MM_DD = "YYYY-MM-dd";
    String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    String FORMAT_YYYY_MM = "yyyy-MM";
    String FORMAT_YYYY = "yyyy";
}
