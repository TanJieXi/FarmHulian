package com.farmhulian.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 一个吐司的工具类
 */
public class ToastUtils {
    private ToastUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;

    /**
     * 显示短时间的Toat
     *
     * @param context 上下文
     * @param message 显示的信息
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param context 上下文
     * @param message 显示的信息
     */
    public static void showShort(Context context, int message) {
        if (isShow) {
            Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context 上下文
     * @param message 显示的信息
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context 上下文
     * @param message 显示的信息
     */
    public static void showLong(Context context, int message) {
        if (isShow) {
            Toast.makeText(context, message+"", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 自定义吐司显示时间
     * @param context 上下文
     * @param message 显示的信息
     * @param duration 吐司持续时间
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow) {
            Toast.makeText(context, message, duration).show();
        }
    }

    /**
     * 自定义吐司显示时间
     * @param context 上下文
     * @param message 显示的信息
     * @param duration 吐司持续时间
     */
    public static void show(Context context, int message, int duration) {
        if (isShow) {
            Toast.makeText(context, message+"", duration).show();
        }
    }

}
