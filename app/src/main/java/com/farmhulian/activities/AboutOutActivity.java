package com.farmhulian.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.farmhulian.R;
import com.farmhulian.base.BaseActivity;

/**
 * 关于我们
 */
public class AboutOutActivity extends BaseActivity {
    private ImageView iv_about_back;
    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about_out);
    }

    @Override
    public void initFindView() {
        iv_about_back = (ImageView) findViewById(R.id.iv_about_back);
    }

    @Override
    public void achieveProgress() {
        iv_about_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
