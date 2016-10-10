package com.farmhulian.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shuibian on 2016/9/7.
 */
public class DateUtils {


    private static SimpleDateFormat sdf;



    /*获取系统时间 格式为："yyyy/MM/dd "*/
    public static String getCurrentDate() {
        Date d = new Date();
        sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(d);
        }

    /*时间戳转换成字符串*/
    public static String getDateToString(long time) {
        Date d = new Date(time);
        sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(d);
        }


    /*将字符串转为时间戳*/
    public static long getStringToDate(String time) {

        sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        try {
            date = sdf.parse(time);
            } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            }
        return date.getTime();
        }

    public static String formatTime(long time) {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(time);
    }


}
