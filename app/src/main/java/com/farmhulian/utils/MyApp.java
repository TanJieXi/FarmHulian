package com.farmhulian.utils;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import cn.sharesdk.framework.ShareSDK;

/**
 *  一个基础类
 *   @author 谭杰栖
 */
public class MyApp extends Application {
    //public static List<Activity> activityList;
    //在自己的Application中添加如下代码,收集内存泄漏
    private RefWatcher refWatcher;
    //在自己的Application中添加如下代码
    public static RefWatcher getRefWatcher(Context context) {
        MyApp application = (MyApp) context
                .getApplicationContext();
        return application.refWatcher;
    }



    @Override
    public void onCreate() {
        super.onCreate();

        //activityList = new ArrayList<>();
        ShareSDK.initSDK(getApplicationContext());
        ViewUtils.init(getApplicationContext());
        refWatcher = LeakCanary.install(this);
    }

   /* public static void exit(){
        for(Activity a : activityList){
            if(a != null) {
                a.finish();
            }
        }
    }*/
}
