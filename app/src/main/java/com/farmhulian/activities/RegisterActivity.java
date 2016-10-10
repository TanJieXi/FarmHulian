package com.farmhulian.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.farmhulian.R;
import com.farmhulian.base.BaseActivity;
import com.farmhulian.utils.CommonUtils;
import com.farmhulian.utils.ToastUtils;

/**
 * 注册的Activity
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private ImageView register_back;  //返回键
    private EditText et_register_phone, et_register_ma, et_pwd, et_twice_pwd;  //电话，验证码，密码，再次确定密码
    private String phone, ma, pwd, twice_pwd;
    private Button btn_register; //注册键
    private TextView tv_send_yzm;  //发送验证码

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initFindView() {
        register_back = (ImageView) findViewById(R.id.register_back);
        et_register_phone = (EditText) findViewById(R.id.et_register_phone);
        et_register_ma = (EditText) findViewById(R.id.et_register_ma);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_twice_pwd = (EditText) findViewById(R.id.et_twice_pwd);
        btn_register = (Button) findViewById(R.id.btn_register);
        tv_send_yzm = (TextView) findViewById(R.id.tv_send_yzm);
    }

    @Override
    public void achieveProgress() {
        register_back.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        tv_send_yzm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_back: //返回键
                finish();
                break;
            case R.id.btn_register: //注册键
                achieveRegister();
                break;
            case R.id.tv_send_yzm:  //发送验证
                ToastUtils.showShort(RegisterActivity.this,"发送验证码");
                break;
            default:
                break;

        }
    }

    /**
     * 注册界面
     */
    private void achieveRegister() {
        phone = et_register_phone.getText().toString();
        ma = et_register_ma.getText().toString();
        pwd = et_pwd.getText().toString();
        twice_pwd = et_twice_pwd.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort(RegisterActivity.this, "请填写电话");
            et_register_phone.requestFocus();
        } else if (!CommonUtils.isMobilePhone(phone)) {
            ToastUtils.showShort(RegisterActivity.this, "电话格式不对，请重新输入");
            et_register_phone.setText("");
            et_register_phone.requestFocus();
        } else if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showShort(RegisterActivity.this, "请填写密码");
            et_pwd.requestFocus();
        } else if (!CommonUtils.isCorrectPassword(pwd, false)) {
            ToastUtils.showShort(RegisterActivity.this, "密码长度在6-16,请重新输入");
            et_pwd.setText("");
            et_pwd.requestFocus();
        } else if (TextUtils.isEmpty(twice_pwd)) {
            ToastUtils.showShort(RegisterActivity.this, "请再次确认密码");
            et_twice_pwd.requestFocus();
        } else if (!twice_pwd.equals(pwd)) {
            ToastUtils.showShort(RegisterActivity.this, " 两次密码不一致，请再次输入");
            et_twice_pwd.setText("");
            et_twice_pwd.requestFocus();
        } else {
            ToastUtils.showShort(RegisterActivity.this, "电话：" + phone + ",验证码：" + ma + "，密码：" + pwd + ",再次确认密码:" + twice_pwd);
            finish();
        }

    }
}
