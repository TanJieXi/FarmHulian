package com.farmhulian.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.farmhulian.R;
import com.farmhulian.base.BaseActivity;

/**
 * 加盟合作
 * @author 谭杰栖
 */
public class JoinActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;  //返回键
    private ImageView btn_join,btn_join1,btn_join2;
    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_join);
    }

    @Override
    public void initFindView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        btn_join = (ImageView) findViewById(R.id.btn_join);
        btn_join1 = (ImageView) findViewById(R.id.btn_join1);
        btn_join2 = (ImageView) findViewById(R.id.btn_join2);
    }

    @Override
    public void achieveProgress() {
        iv_back.setOnClickListener(this);
        btn_join.setOnClickListener(this);
        btn_join1.setOnClickListener(this);
        btn_join2.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:  //返回键
                finish();
                break;
            case R.id.btn_join:
            case R.id.btn_join1:
            case R.id.btn_join2:
                startActivity(new Intent(JoinActivity.this,ClientJoinActivity.class));
                break;

            default:
                break;

        }
    }
}
