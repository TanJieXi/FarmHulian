package com.farmhulian.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.farmhulian.R;
import com.farmhulian.base.BaseActivity;
import com.farmhulian.utils.CommonUtils;
import com.farmhulian.utils.ToastUtils;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

import static android.text.TextUtils.isEmpty;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity {
    private AllListener listener = new AllListener();
    private TabLayout login_tablayout;
    private Button btn_login,btn_right_login;//左边登录键 和 右边登录键
    private ImageView login_qq, login_weibo, login_wechat;
    private RelativeLayout layout_Login_Left, layout_Login_Right;  //一个左边一个右边
    private String[] tabs = {"账号密码登录", "手机号快速登录"};
    private TabLayout.Tab tab;
    private TextView tv_agree_protocal;  //下面加横线
    private ImageView login_back;  //返回键
    private TextView tv_register;  //左边注册键
    private EditText et_left_account, et_left_pwd;//左边账号密码登录的账号和密码
    private String account, pwd;
    private EditText et_right_phone,et_right_ma;  //右边手机号快速登录的手机号码和密码
    private String phone,ma; //右边的手机号码和验证码
    private ImageView iv_right_send_ma;  //发送验证码


    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initFindView() {
        login_tablayout = (TabLayout) findViewById(R.id.login_tablayout);
        login_qq = (ImageView) findViewById(R.id.login_qq);
        login_weibo = (ImageView) findViewById(R.id.login_weibo);
        login_wechat = (ImageView) findViewById(R.id.login_wechat);
        layout_Login_Left = (RelativeLayout) findViewById(R.id.layout_Login_Left);
        layout_Login_Right = (RelativeLayout) findViewById(R.id.layout_Login_Right);
        tv_agree_protocal = (TextView) findViewById(R.id.tv_agree_protocal);
        //加下横线
        tv_agree_protocal.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        login_back = (ImageView) findViewById(R.id.login_back);
        tv_register = (TextView) findViewById(R.id.tv_register);

        //左边登录界面的账号和密码
        btn_login = (Button) findViewById(R.id.btn_login);
        et_left_account = (EditText) findViewById(R.id.et_left_account);
        et_left_pwd = (EditText) findViewById(R.id.et_left_pwd);
        //右边登录界面的手机号码和验证码
        et_right_phone = (EditText) findViewById(R.id.et_right_phone);
        et_right_ma = (EditText) findViewById(R.id.et_right_ma);
        btn_right_login = (Button) findViewById(R.id.btn_right_login);

        //右边的发送验证码按钮
        iv_right_send_ma = (ImageView) findViewById(R.id.iv_right_send_ma);
    }

    @Override
    public void achieveProgress() {

        setAllListener();
        initTabLayout();

        //设置一个初始值
        tab = login_tablayout.getTabAt(0);
        if (tab != null) {
            tab.select();
        }

    }

    /**
     * 设置所有监听
     */
    private void setAllListener() {
        btn_login.setOnClickListener(listener);
        login_tablayout.addOnTabSelectedListener(listener);
        login_qq.setOnClickListener(listener);
        login_wechat.setOnClickListener(listener);
        login_weibo.setOnClickListener(listener);
        login_back.setOnClickListener(listener);
        tv_register.setOnClickListener(listener);
        btn_right_login.setOnClickListener(listener);
        iv_right_send_ma.setOnClickListener(listener);
    }

    /**
     * 添加tablayout的数据
     */
    private void initTabLayout() {
        login_tablayout.setSelectedTabIndicatorColor(Color.rgb(8, 170, 59));
        login_tablayout.setTabTextColors(Color.rgb(201, 201, 201), Color.rgb(8, 170, 59));
        login_tablayout.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0, len = tabs.length; i < len; i++) {
            tab = login_tablayout.newTab();
            tab.setText(tabs[i]);
            login_tablayout.addTab(tab);
        }

    }

    /**
     * QQ三方登录
     */
    private void loginWithQQ() {
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        if (qq.isValid()) {
            qq.removeAccount(true);
        }
        qq.SSOSetting(false);  //设置false表示使用SSO授权方式
        qq.setPlatformActionListener(listener); // 设置分享事件回调
        qq.showUser(null);
    }

    /**
     * 微博登录
     */
    private void loginWithWeibo() {
        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        if (weibo.isValid()) {
            weibo.removeAccount(true);
        }
        weibo.SSOSetting(false);  //设置false表示使用SSO授权方式
        weibo.setPlatformActionListener(listener); // 设置分享事件回调
        weibo.showUser(null);
    }

    /**
     * 微信登录
     */
    private void loginWithWechat() {

    }

    /**
     * 登录键的功能
     */
    private void achieveLogin() {
        account = et_left_account.getText().toString();
        pwd = et_left_pwd.getText().toString();
        if (isEmpty(account)) {
            ToastUtils.showShort(LoginActivity.this, "账号不能为空");
            et_left_account.requestFocus();

        } else if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showShort(LoginActivity.this, "请输入密码");
            et_left_pwd.requestFocus();
        } else if (!CommonUtils.isCorrectPassword(pwd, false)) {
            ToastUtils.showShort(LoginActivity.this, "密码长度在6-16，请重新输入");
            et_left_pwd.setText("");
            et_left_pwd.requestFocus();
        }else{
            ToastUtils.showShort(LoginActivity.this, "账号："  + account + ",密码：" + pwd);
            //TODO 登录
        }
    }

    /**
     * 右边登录键
     */
    private void achieveRightLogin() {
        phone = et_right_phone.getText().toString();
        ma = et_right_ma.getText().toString();
        if(TextUtils.isEmpty(phone)){
            ToastUtils.showShort(LoginActivity.this, "请输入电话");
            et_right_phone.requestFocus();
        }else if(!CommonUtils.isMobilePhone(phone)){
            ToastUtils.showShort(LoginActivity.this, "电话格式不对，重新输入");
            et_right_phone.setText("");
            et_right_phone.requestFocus();
        }else if(TextUtils.isEmpty(ma)){
            ToastUtils.showShort(LoginActivity.this, "请输入验证码");
            et_right_ma.requestFocus();
        }else{
            ToastUtils.showShort(LoginActivity.this,"电话：" + phone + "，验证码：" + ma);
            //TODO 右边登录
        }
    }

    class AllListener implements View.OnClickListener, TabLayout.OnTabSelectedListener, PlatformActionListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_login: //左边登录键
                    achieveLogin();
                    break;
                case R.id.btn_right_login:  //右边登录键
                    achieveRightLogin();
                    break;
                case R.id.login_qq:  //QQ登录
                    loginWithQQ();
                    break;
                case R.id.login_wechat: //微信登录
                    loginWithWechat();
                    break;
                case R.id.login_weibo:  //微博登录
                    loginWithWeibo();
                    break;
                case R.id.login_back:  //返回
                    finish();
                    break;
                case R.id.tv_register:  //注册
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                    break;
                case R.id.iv_right_send_ma:  //右边的发送验证码
                    ToastUtils.showShort(LoginActivity.this,"发送验证码");
                    break;
                default:
                    break;
            }
        }

        /**
         * Tablayout的Tab监听事件
         */
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()) {
                case 0:
                    layout_Login_Left.setVisibility(View.VISIBLE);
                    layout_Login_Right.setVisibility(View.GONE);
                    break;
                case 1:
                    layout_Login_Left.setVisibility(View.GONE);
                    layout_Login_Right.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }

        ///////////////////////////////////Tab监听事件结束/////////////////////////////////////////
        ///////////////////////////////////三方授权的结果回调/////////////////////////////////////////
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            if (i == Platform.ACTION_USER_INFOR) {
                PlatformDb platDB = platform.getDb();//获取数平台数据DB
                //通过DB获取各种数据
                platDB.getToken();
                platDB.getUserGender();
                String icon = platDB.getUserIcon();
                platDB.getUserId();
                String name2 = platDB.getUserName();
                Log.i("info", "icon===" + icon);
                Log.i("info", "name===" + name2);
            }
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(Platform platform, int i) {

        }
        /////////////////
    }


}
