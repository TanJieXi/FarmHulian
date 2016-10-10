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
 * @author 谭杰栖
 *         添加地址的Activity
 */
public class AddAddressActivity extends BaseActivity {
    private ImageView iv_addAddress_back;//返回键
    private AllListener listener = new AllListener();
    //收货人姓名，手机号码，收货地区，街道地址，邮编
    private EditText et_add_name, et_add_phone, et_add_address, et_add_street_address, et_add_postcode;
    private Button btn_addAddress;  //确定添加
    private String addName, addPhone, addPostCode, addAddress;  //最终输入的姓名，电话，右边，详细地址

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_address);
    }

    @Override
    public void initFindView() {
        iv_addAddress_back = (ImageView) findViewById(R.id.iv_addAddress_back);
        //收货人姓名，手机号码，收货地区，街道地址，邮编
        et_add_name = (EditText) findViewById(R.id.et_add_name);
        et_add_phone = (EditText) findViewById(R.id.et_add_phone);
        et_add_address = (EditText) findViewById(R.id.et_add_address);
        et_add_street_address = (EditText) findViewById(R.id.et_add_street_address);
        et_add_postcode = (EditText) findViewById(R.id.et_add_postcode);
        btn_addAddress = (Button) findViewById(R.id.btn_addAddress);
    }

    @Override
    public void achieveProgress() {
        addAllListener();
    }


    /**
     * 点击按钮之之后做的事情
     */
    private void addAddress() {

        addName = et_add_name.getText().toString();
        addPhone = et_add_phone.getText().toString();
        addPostCode = et_add_postcode.getText().toString();
        addAddress = et_add_address.getText().toString();

        if (TextUtils.isEmpty(addName)) {
            ToastUtils.showShort(AddAddressActivity.this, "请填写收货人姓名");
            et_add_name.requestFocus();
        } else if (TextUtils.isEmpty(addPhone)) {
            ToastUtils.showShort(AddAddressActivity.this, "请填写收货人手机号码");
            et_add_phone.requestFocus();
        } else if (!CommonUtils.isMobilePhone(addPhone)) {
            ToastUtils.showShort(AddAddressActivity.this, "你输入的手机号码不正确，请重新输入");
            et_add_phone.setText("");
            et_add_phone.requestFocus();
        } else if (TextUtils.isEmpty(addPostCode)) {
            ToastUtils.showShort(AddAddressActivity.this, "请输入邮箱，如果不知道请填写000000");
            et_add_postcode.requestFocus();
        } else if (TextUtils.isEmpty(addAddress)) {
            ToastUtils.showShort(AddAddressActivity.this, "请输入你的详细收货地址");
            et_add_address.requestFocus();
        } else {
            ToastUtils.showShort(this, "收货人姓名是：" + addName + ",电话号码是：" + addPhone
                    + ",详细收货地址是：" + addAddress +
                    ",邮编是：" + addPostCode);
            finish();
        }

    }


    /**
     * 添加所有的监听事件
     */
    private void addAllListener() {
        iv_addAddress_back.setOnClickListener(listener);
        btn_addAddress.setOnClickListener(listener);
    }

    class AllListener implements View.OnClickListener {

        /**
         * 点击事件
         */
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_addAddress_back:
                    finish();
                    break;
                case R.id.btn_addAddress:  //确定添加
                    addAddress();
                    break;
                default:
                    break;
            }
        }
    }


}
