package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

/**
 * Created by chenxin on 2017/9/7.
 */

public class TimeUtil {

    public static Date getDateByString(String dateString) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
    }

    /**
     * 返回当前时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * 返回当前时间 对文件进行命名
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentTimeToFile() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }


    /**
     * 返回适用的当前时间
     *
     * @return yyyy-MM-dd'T'HH:mm:ss
     */
    public static String getPCCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
    }

    /**
     * 时间格式化
     *
     * @param date
     * @return
     */
    public static String getTimeFormat(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
    /**
     * 时间格式化，精准到秒
     */
    public static String getTimeFormatParse(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
    /**
     * 时间格式化
     *
     * @param date
     * @return
     */
    public static Date getTimeParse(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(date);

    }

    /**
     * 获取
     * @param totalSeconds
     * @return
     */
    public static String getFormatRecordTime(int totalSeconds) {
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        Formatter mFormatter = new Formatter();
        if (hours > 10){
            return mFormatter.format("%02d:%02d:%02d", hours, minutes, seconds).toString();
        } else if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

}
