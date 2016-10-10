package com.farmhulian.utils;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Map;

/**
 * 简单的AsyncHttpUtils
 */
public class AsyncHttpUtils {
    public static final String TAG = AsyncHttpUtils.class.getSimpleName();
    public static final int SOCKET_TIMEOUT = 10 * 1000;//默认超时时间

    private static AsyncHttpUtils httpInstance = new AsyncHttpUtils();

    // 实例话对象
    private static AsyncHttpClient client;

    private AsyncHttpUtils() {}

    public static AsyncHttpUtils getInstance() {
        if (client == null){
            client = new AsyncHttpClient();
        }
        return httpInstance;
    }

    /**
     * 用一个完整url获取一个string对象
     * @param url
     * @param res
     */
    public static void getToString(String url, AsyncHttpResponseHandler res) {
        client.get(url, res);
    }
    /**
     * get方法带参数
     */
    public static void getToString(String url, RequestParams params, AsyncHttpResponseHandler res) {
        client.get(url,params,res);
    }

    /**
     * 不带参数，获取json对象或者数组
     * @param url
     * @param res
     */
    public static void getToJson(String url, JsonHttpResponseHandler res) {
        client.get(url, res);
    }

    /**
     * 带参数，获取json对象或者数组
     * @param url
     * @param params
     * @param res
     */
    public static void getToJson(String url, RequestParams params, JsonHttpResponseHandler res) {
        client.get(url, params,res);
    }

    /**
     * 下载数据使用，会返回byte数据.
     * 如果要带进度重写onProgress()方法(super()不能删)
     * @param url
     * @param bHandler
     */
    public static void getToByte(String url, BinaryHttpResponseHandler bHandler) {
        client.get(url, bHandler);
    }

    /**
     * 上传文件
     * 如果要带进度，重写onProgress()方法(super()不能删)
     * @param url
     * @param params
     * @param pHandler
     */
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler pHandler){
        client.post(url,params,pHandler);
    }


    /**
     * 传进Map数组，并添加到RequestParams中
     * @param map
     * @return
     */
    public static RequestParams getPostParamsForMap (Map<String,String> map){
        RequestParams params = new RequestParams();
        if (map == null){
            return params;
        }
        for (String key : map.keySet()) {
            params.put(key,map.get(key));
        }
        return params;
    }

    /**
     * 取消所有请求
     *
     * @param context
     */
    public void cancelAllRequests(Context context) {
        if (client != null) {
            Log.e(TAG, "cancel");
            client.cancelRequests(context, true); //取消请求
        }
    }

    /**
     * 取消请求
     * @param context
     */
    public void cancelRequests(Context context) {
        if (client != null){
            client.cancelRequests(context, true);
        }
    }
}
