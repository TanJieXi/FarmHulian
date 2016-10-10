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
import com.farmhulian.fragment.OrderFragment;

import com.farmhulian.views.NavTopView;

import java.util.ArrayList;
import java.util.List;

/**
 * 全部订单，包括全部，待发货，待收货，待评价
 */
public class AllOrdersActivity extends BaseActivity {
    private NavTopView orders_titleView;  //标题
    private ImageView orders_back;  //返回键
    private AllListener listener = new AllListener();  //所有事件的监听事件
    private TabLayout orders_tablayout;  //标题栏的tablayout导航
    private OilVFragmentAdapter viewPagerAdapter;
    private ViewPager order_viewPager;
    private OrderFragment orderFragment;
    private List<Fragment> fragmentList;
    private String[] titles = {"全部","待付款","待发货","待收货","待评价"};

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_all_orders);
    }

    @Override
    public void initFindView() {
        orders_titleView = (NavTopView) findViewById(R.id.orders_titleView);
        orders_titleView.getTitleView().setText("已买到的货品");
        orders_back = orders_titleView.getLeftView();
        orders_tablayout = (TabLayout) findViewById(R.id.orders_tablayout);
        order_viewPager = (ViewPager) findViewById(R.id.order_viewPager);
    }

    @Override
    public void achieveProgress() {
        setAllListener();
        initTablayout();
        initViewPagersFragmentData();
    }
    /**
     * 添加碎片
     */
    private void initViewPagersFragmentData() {
        fragmentList = new ArrayList<>();
        Bundle bundle;
        for(int i = 0 , len = titles.length ; i < len ; i++){
            orderFragment = new OrderFragment();
            bundle = new Bundle();
            bundle.putString(ContrantsF.ORDERS_WHICH_TAB_S,titles[i]);
            orderFragment.setArguments(bundle);
            fragmentList.add(orderFragment);
        }

        viewPagerAdapter = new OilVFragmentAdapter(getSupportFragmentManager(),fragmentList,titles);
        order_viewPager.setAdapter(viewPagerAdapter);
        orders_tablayout.setupWithViewPager(order_viewPager);
        order_viewPager.setOffscreenPageLimit(ContrantsF.VIEWPAGER_LIMIT_NUM);
    }

    /**
     * 初始化Tablayout的数据
     */
    private void initTablayout() {
        orders_tablayout.setTabTextColors(Color.rgb(0,0,0),Color.rgb(253,120,43));
        orders_tablayout.setTabMode(TabLayout.MODE_FIXED);
        orders_tablayout.setSelectedTabIndicatorColor(Color.rgb(253,120,43));
    }

    /**
     * 设置所有的监听事件
     */
    private void setAllListener() {
        orders_back.setOnClickListener(listener);
    }

    class AllListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.nav_top_left:   //返回键
                    finish();
                    break;
                default:
                    break;
            }
        }
    }


}
