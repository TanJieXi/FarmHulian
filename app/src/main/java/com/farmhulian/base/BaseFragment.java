package com.farmhulian.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.farmhulian.R;
import com.farmhulian.utils.MyApp;
import com.squareup.leakcanary.RefWatcher;

/**
 * 一个父类Fragment
 *  @author 谭杰栖
 */
public abstract class BaseFragment extends Fragment{
    private View mView;

    public static ProgressDialog myDialog;// 加载时的进度条

    public static float ScreenW;// 用于保存屏幕的宽度
    public static float ScreenH;// 用于保存屏幕的高度


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        DisplayMetrics dm = new DisplayMetrics();// 初始化一个系统屏幕对象
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);// 通过调用系统服务给系统屏幕对象赋值
        ScreenW = dm.widthPixels;// 得到系统屏幕的宽度值
        ScreenH = dm.heightPixels;// 得到系统屏幕的高度值


        mView = initContentView(inflater,container,savedInstanceState);
        initFindView();
        achieveProgress();
        return mView;
    }


    /**
     * 初始化碎片布局
     * @return 碎片View对象
     */
    protected abstract View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 找到碎片布局上面的控件
     */
    protected abstract void initFindView();

    /**
     * 完成具体过程
     */
    protected abstract void achieveProgress();


    /**
     * 检测内存泄漏
     */
    @Override public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApp.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }


    public void showProgressDialog(String message, Context context)
    {
        myDialog = new ProgressDialog(context);// 创建ProgressDialog对象,关联本当前Activity
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
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)// dialog:该密钥该密钥已经被派往对话已经被派往对话;keyCode:对于被按下的物理键的代码;event:包含完整的信息关于这个事件的keyEvent的对象

            {
                switch (keyCode)
                {
                    case KeyEvent.KEYCODE_BACK:// 返回键
                        Log.i("aaa", "KEYCODE_BACK");
                        myDialog.dismiss();
                        getActivity().finish();
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



}
