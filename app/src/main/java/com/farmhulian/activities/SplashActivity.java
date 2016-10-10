package com.farmhulian.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.farmhulian.ContrantsF;
import com.farmhulian.MainActivity;
import com.farmhulian.R;
import com.farmhulian.base.BaseActivity;
import com.farmhulian.utils.SharePreUtils;

/**
 * 闪屏界面,自动跳转
 *  @author 谭杰栖
 */
public class SplashActivity extends BaseActivity{
    private boolean isFirst;
    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash_layout);
    }

    @Override
    public void initFindView() {

    }

    //这里要判断一下是否是第一次安装软件，不然要跳转到引导界面，不然直接跳转到主界面
    @Override
    public void achieveProgress() {

        //这里要来判断一下
        isFirst = (boolean) SharePreUtils.get(this, ContrantsF.isFirst,false);
        if(!isFirst){ //第一次进程序
            startActivity(new Intent(this,GuideActivity.class));
            finish();
        }else{ // 不是第一次进程序
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        Thread.sleep(2000);
                        finish();
                    } catch (Exception e) {
                        Log.i("info","=SplashActivity===" + e.toString());
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }




}
