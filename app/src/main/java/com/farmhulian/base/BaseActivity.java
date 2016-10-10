package com.farmhulian.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.farmhulian.R;
import com.farmhulian.utils.MyApp;
import com.squareup.leakcanary.RefWatcher;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


/**
 * 一个基础父类
 *
 * @author 谭杰栖
 */
public abstract class BaseActivity extends AppCompatActivity {

    Context context;
    Activity activity;

    public static ProgressDialog myDialog;// 加载时的进度条


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideActionBar(true);// 设置应用不显示标题栏

        context = this;
        activity = this;
        //MyApp.activityList.add(this);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initContentView(savedInstanceState);
        initFindView();
        achieveProgress();

    }

    /**
     * 检测内存泄漏
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApp.getRefWatcher(this);
        refWatcher.watch(this);
    }

    /**
     * 隐藏标题栏
     */
    public void hideActionBar(boolean isHide) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            Log.i("info", "可能手机版本太低，导致无法显示ActionBar");
            return;
        }
        if (isHide) {
            actionBar.hide();
        } else {
            actionBar.show();
        }
    }

    /**
     * 绑定布局
     */
    public abstract void initContentView(Bundle savedInstanceState);

    /**
     * 找到布局中的控件
     */
    public abstract void initFindView();

    /**
     * 实现过程
     */
    public abstract void achieveProgress();

    /****************************************************************/
    public void showProgressDialog(String message) {
        myDialog = new ProgressDialog(this);// 创建ProgressDialog对象,关联本当前Activity
        myDialog.setMessage(message);// 设置ProgressDialog提示信息
        myDialog.setIcon(R.mipmap.ic_launcher);// 设置标题图标；
        myDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        myDialog.setIndeterminate(true);// 设置ProgressDialog的进度条是否不明确；
        // 这个属性对于ProgressDailog默认的转轮模式没有实际意义，
        // 默认下设置为true，它仅仅对带有ProgressBar的Dialog有作用。
        // 修改这个属性为false后可以实时更新进度条的进度。
        myDialog.setCancelable(true);// 设置ProgressDialog 是否可以按返回键取消；
        myDialog.setCanceledOnTouchOutside(false);// 按对话框以外的地方起作用。按返回键不起作用

        /** 设置透明度 */
        Window window = myDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.5f;// 透明度
        lp.dimAmount = 0.5f;// 黑暗度
        window.setAttributes(lp);
        myDialog.show();// 让ProgressDialog显示

        // 会报错：java.lang.IllegalArgumentException: Window type can not be
        // changed
        // after the window is added.
        // myDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);//屏蔽Home键,不过在这里貌似没有什么用

        myDialog.setOnKeyListener(new DialogInterface.OnKeyListener()// 为按键设置监听
        {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event)// dialog:该密钥该密钥已经被派往对话已经被派往对话;keyCode:对于被按下的物理键的代码;event:包含完整的信息关于这个事件的keyEvent的对象

            {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK:// 返回键
                        Log.i("aaa", "KEYCODE_BACK");
                        myDialog.dismiss();
                        finish();
                        return true;
                }
                return false;
            }
        });

    }


    public void dismissProgressDialog() {
        if (myDialog != null) {
            myDialog.dismiss();
        }
    }


    /****************************************************************/

    /**
     * 通过类名启动Activity，没有Bundle数据
     *
     * @param pClass
     */
    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);

    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param pbundle
     */
    protected void openActivity(Class<?> pClass, Bundle pbundle) {
        Intent intent = new Intent(this, pClass);
        if (pbundle != null) {
            intent.putExtras(pbundle);
        }
        startActivity(intent);
    }

    /**
     * 通过Action启动Activity，不含有Bundle数据
     *
     * @param pAction
     */
    protected void openActivity(String pAction) {
        openActivity(pAction, null);
    }

    /**
     * 通过Action启动Activity，并且含有Bundle数据
     *
     * @param pAction
     * @param pbundle
     */
    protected void openActivity(String pAction, Bundle pbundle) {
        Intent intent = new Intent(pAction);
        if (pbundle != null) {
            intent.putExtras(pbundle);
        }
        startActivity(intent);

    }

    /****************************************************************/
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();


    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

    }


    //mob分享代码
    public void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("题目");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }


}
