package com.farmhulian;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.farmhulian.adapters.VPFragmentAdapter;
import com.farmhulian.base.BaseActivity;
import com.farmhulian.fragment.BuyStoreFragment;
import com.farmhulian.fragment.ChoiceFragment;
import com.farmhulian.fragment.HomeFragment;
import com.farmhulian.fragment.MeFragment;
import com.farmhulian.fragment.NearByFragment;
import com.farmhulian.utils.ToastUtils;
import com.farmhulian.views.ScrollViewPager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 主界面，程序主接口
 *  @author 谭杰栖
 */

public class MainActivity extends BaseActivity {
    private ScrollViewPager main_viewPager; //主界面的ViewPager
    private RadioGroup main_rg;   //主界面的底部导航栏
    private RadioButton rb;


    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initFindView() {
        main_viewPager = (ScrollViewPager) findViewById(R.id.main_viewPager);
        main_rg = (RadioGroup) findViewById(R.id.main_rg);
    }

    @Override
    public void achieveProgress() {
        main_viewPager.setScanScroll(false);
        hideActionBar(true);
        initViewPagerDada();
        initListener();

        //设置一个初始值
        setDefaultRB();
    }

    /**
     * 设置第一个为默认
     */
    private void setDefaultRB() {
        rb = (RadioButton) main_rg.getChildAt(0);
        if(rb != null){
            rb.setChecked(true);
        }
    }


    /**
     * 初始化ViewPager里面碎片信息,并且绑定适配器
     */
    private void initViewPagerDada() {
        List<Fragment> fragmentList = new ArrayList<>();
        //以下分别是几个碎片
        HomeFragment homeFragment = new HomeFragment();
        NearByFragment nearByFragment = new NearByFragment();
        ChoiceFragment choiceFragment = new ChoiceFragment();
        BuyStoreFragment buyStoreFragment = new BuyStoreFragment();
        MeFragment meFragment = new MeFragment();
        //将数据加入到集合中去然后去VP进行绑定
        Collections.addAll(fragmentList,homeFragment,nearByFragment,choiceFragment,buyStoreFragment,meFragment);
        //碎片的适配器
        VPFragmentAdapter vpAdapter = new VPFragmentAdapter(getSupportFragmentManager(),fragmentList);
        main_viewPager.setAdapter(vpAdapter);
        main_viewPager.setOffscreenPageLimit(ContrantsF.VIEWPAGER_LIMIT_NUM);

    }

    /**
     * 写RadioGroup和ViewPager的监听事件，实现联动
     */
    private void initListener() {
        VP_RGListener listener = new VP_RGListener();
        main_viewPager.addOnPageChangeListener(listener);
        main_rg.setOnCheckedChangeListener(listener);
    }

    ///////////////////////////////////////////ViewPager和RadioButton的监听器完成联动///////////////////////////////
    class VP_RGListener extends ViewPager.SimpleOnPageChangeListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            for(int j = 0 , len = radioGroup.getChildCount() ; j < len; j++){
                rb = (RadioButton) radioGroup.getChildAt(j);
                if(rb.isChecked()){
                    main_viewPager.setCurrentItem(j);

                }
            }
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            rb = (RadioButton) main_rg.getChildAt(position);
            if(rb != null){
                rb.setChecked(true);
            }
        }
    }

    private long curTime =  0;
    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - curTime > 2000){
            ToastUtils.showShort(this,"双击退出");
            curTime = System.currentTimeMillis();
        }else {
            super.onBackPressed();
        }
    }
}
