package com.farmhulian.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.farmhulian.ContrantsF;
import com.farmhulian.R;
import com.farmhulian.adapters.OilVFragmentAdapter;
import com.farmhulian.base.BaseActivity;
import com.farmhulian.fragment.CollectionLeftFragment;
import com.farmhulian.fragment.CollectionRightFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 我的收藏
 */
public class CollectionActivity extends BaseActivity {
    private ImageView iv_collection_back;  //返回键
    private AllListener listener = new AllListener();
    private TabLayout collection_tablayout;  //tablayout
    private ViewPager collection_viewPager;  //viewPgaer
    private String[] titles = {"宝贝收藏","店铺收藏"};
    private CollectionLeftFragment leftFragment;
    private CollectionRightFragment rightFragment;
    private List<Fragment> fragmentList;
    private OilVFragmentAdapter vAdapter;
    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_collection);
    }

    @Override
    public void initFindView() {
        collection_tablayout = (TabLayout) findViewById(R.id.collection_tablayout);
        collection_viewPager = (ViewPager) findViewById(R.id.collection_viewPager);
        iv_collection_back = (ImageView) findViewById(R.id.iv_collection_back);
    }

    @Override
    public void achieveProgress() {
        setAllListener();
        initTablayout();
        initViewPagerFragmentData();
    }

    /**
     * 初始化碎片内容绑定适配器
     */
    private void initViewPagerFragmentData() {
        fragmentList = new ArrayList<>();
        leftFragment = new CollectionLeftFragment();
        rightFragment = new CollectionRightFragment();
        Collections.addAll(fragmentList,leftFragment,rightFragment);
        vAdapter = new OilVFragmentAdapter(getSupportFragmentManager(),fragmentList,titles);
        collection_viewPager.setAdapter(vAdapter);
        collection_viewPager.setOffscreenPageLimit(ContrantsF.VIEWPAGER_LIMIT_NUM);
        collection_tablayout.setupWithViewPager(collection_viewPager);
    }

    /**
     * 初始化tablayout
     */
    private void initTablayout() {
        collection_tablayout.setTabTextColors(Color.rgb(255,255,255),Color.rgb(253,120,43));
        collection_tablayout.setTabMode(TabLayout.MODE_FIXED);
        collection_tablayout.setSelectedTabIndicatorColor(Color.rgb(253,120,43));
    }

    /**
     * 所有的监听器
     */
    private void setAllListener() {
        iv_collection_back.setOnClickListener(listener);
    }

    class AllListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_collection_back:  //返回键
                    finish();
                    break;
                default:
                    break;
            }
        }
    }

}
