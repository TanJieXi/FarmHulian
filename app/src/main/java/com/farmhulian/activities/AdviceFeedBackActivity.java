package com.farmhulian.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.farmhulian.R;
import com.farmhulian.base.BaseActivity;
import com.farmhulian.utils.CommonUtils;
import com.farmhulian.utils.ToastUtils;

/**
 * 意见反馈的Actiivity
 *
 * @author 谭杰栖
 */
public class AdviceFeedBackActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_advice, et_phone;  //意见框,电话号码
    private Button btn_advice;  //提交建议
    private ImageView iv_advice_back; //返回键
    private String advice;
    private String phone;
    private boolean isCorrectPhone;


    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_advice_feed_back);
    }

    @Override
    public void initFindView() {
        et_advice = (EditText) findViewById(R.id.et_advice);
        et_phone = (EditText) findViewById(R.id.et_phone);
        btn_advice = (Button) findViewById(R.id.btn_advice);
        iv_advice_back = (ImageView) findViewById(R.id.iv_advice_back);
    }

    @Override
    public void achieveProgress() {
        iv_advice_back.setOnClickListener(this);
        btn_advice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_advice:  //提交建议
                uploadAdvice();
                break;
            case R.id.iv_advice_back:  //返回键
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 提交建议
     */
    private void uploadAdvice() {
        advice = et_advice.getText().toString();
        phone = et_phone.getText().toString();
        isCorrectPhone = CommonUtils.isMobilePhone(phone);
        if (TextUtils.isEmpty(advice)) {
            ToastUtils.showShort(AdviceFeedBackActivity.this, "提交内容不能为空");
            et_advice.requestFocus();
        } else if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort(AdviceFeedBackActivity.this, "请你填写电话，方便我们与您联系");
            et_phone.requestFocus();
        } else if (!isCorrectPhone) {
            ToastUtils.showShort(AdviceFeedBackActivity.this, "你输入的号码不正确，请重新输入");
            et_phone.setText("");
            et_phone.requestFocus();
        } else{
            ToastUtils.showShort(AdviceFeedBackActivity.this,"内容：" + advice + ",电话：" + phone);
        }
    }
}
