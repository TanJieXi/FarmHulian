package com.farmhulian.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.farmhulian.R;
import com.farmhulian.adapters.StoreDetaisListViewAdapter;
import com.farmhulian.base.BaseActivity;
import com.farmhulian.utils.ToastUtils;
import com.farmhulian.views.MyScrollView;
import com.farmhulian.views.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 门店详情
 * @author 谭杰栖
 */
public class StoreDetailsActivity extends BaseActivity {
    private String[] tabs = {"热销", "粮油", "生鲜", "蔬果"};
    private RadioGroup store_radioGroup;  //完成rb和viewPager
    private AllListener listener = new AllListener();
    private RadioButton rb;
    private NoScrollListView store_listView;  //ListView
    private StoreDetaisListViewAdapter adapter;
    private List<String> datas = new ArrayList<>();
    private List<String> needDatas;
    private String curTabName;  //用于判断当前是哪个tab数据，热销，粮油，生鲜，蔬果
    private MyScrollView store_scrollView;
    private RadioGroup store_top_radioGroup; //上面隐藏的RadioGroup
    private LinearLayout ll_top_radioGroup;  //上面隐藏部分，包含一根线
    private boolean isShowTopRb = false;
    private int bottom_rg_height;  //获取下面导航距离顶部位置

    //顶部三个按钮，返回，收藏，分享,店名
    private ImageView iv_store_back,iv_store_collection,iv_store_share;
    private TextView tv_store_name;  //店铺名

    //切换门店以及后面的门店名字
    private ImageView iv_store_change_store;
    private TextView iv_store_end_name;

    //门店位置以及后面的电话
    private TextView tv_store_address;
    private ImageView iv_store_callPhone;

    //探索详情
    private TextView tv_store_search;



    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_store_details);
    }

    @Override
    public void initFindView() {
        store_radioGroup = (RadioGroup) findViewById(R.id.store_radioGroup);
        store_listView = (NoScrollListView) findViewById(R.id.store_listView);
        store_scrollView = (MyScrollView) findViewById(R.id.store_scrollView);
        store_top_radioGroup = (RadioGroup) findViewById(R.id.store_top_radioGroup);
        iv_store_back = (ImageView) findViewById(R.id.iv_store_back);
        iv_store_collection = (ImageView) findViewById(R.id.iv_store_collection);
        tv_store_name = (TextView) findViewById(R.id.tv_store_name);
        iv_store_change_store = (ImageView) findViewById(R.id.iv_store_change_store);
        iv_store_end_name = (TextView) findViewById(R.id.iv_store_end_name);
        tv_store_address = (TextView) findViewById(R.id.tv_store_address);
        iv_store_callPhone = (ImageView) findViewById(R.id.iv_store_callPhone);
        tv_store_search = (TextView) findViewById(R.id.tv_store_search);
        ll_top_radioGroup = (LinearLayout) findViewById(R.id.ll_top_radioGroup);
        iv_store_share = (ImageView) findViewById(R.id.iv_store_share);
    }

    @Override
    public void achieveProgress() {
        setAllListener();

        //绑定数据
        adapter = new StoreDetaisListViewAdapter(this, datas);
        store_listView.setAdapter(adapter);


        //来一个默认
        ((RadioButton) store_radioGroup.getChildAt(0)).setChecked(true);
    }

    /**
     * 设置ListView数据
     */
    private void setListViewDatas(int curTab,int count) {
        if (datas.size() != 0) {
            datas.clear();
        }
        needDatas = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            needDatas.add(tabs[curTab]);
        }
        datas.addAll(needDatas);
        adapter.notifyDataSetChanged();
    }

    /**
     * 判断选中的是哪个tab，鲜果那些
     */
    private void judgeWhichTabChecked(int curRb) {
        //curTabName = curRb.getText().toString();
        if (0 == curRb) {
            setListViewDatas(0,2);
        } else if (1 == curRb) {
            setListViewDatas(1,3);
        } else if (2 == curRb) {
            setListViewDatas(2,4);
        } else if (3 == curRb) {
            setListViewDatas(3,5);
        }
    }

    /**
     * 绑定所有监听器
     */
    private void setAllListener() {
        store_radioGroup.setOnCheckedChangeListener(listener);
        store_listView.setOnItemClickListener(listener);
        store_top_radioGroup.setOnCheckedChangeListener(listener);
        iv_store_share.setOnClickListener(listener);
        store_scrollView.setScrollViewListener(listener);
        iv_store_back.setOnClickListener(listener);
        iv_store_collection.setOnClickListener(listener);
        iv_store_change_store.setOnClickListener(listener);
        iv_store_callPhone.setOnClickListener(listener);
        tv_store_search.setOnClickListener(listener);


    }


    /**
     * 所有事件的监听事件
     */
    class AllListener implements RadioGroup.OnCheckedChangeListener, AdapterView.OnItemClickListener, MyScrollView.ScrollViewListener, View.OnClickListener {
        /**
         * 这个是RadioGroup的监听事件
         */
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            for (int i = 0, len = group.getChildCount(); i < len; i++) {
                rb = (RadioButton) group.getChildAt(i);
                if (rb.isChecked()) {
                    curTabName = tabs[i];
                    ((RadioButton) (store_top_radioGroup.getChildAt(i))).setChecked(true);
                    ((RadioButton) (store_radioGroup.getChildAt(i))).setChecked(true);
                    judgeWhichTabChecked(i);
                    store_radioGroup.setFocusableInTouchMode(true);
                    store_radioGroup.setFocusable(true);
                    store_radioGroup.requestFocus();
                }


            }
        }

        /**
         * ListView的点击事件
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ToastUtils.showShort(StoreDetailsActivity.this, curTabName + "界面，位置是：" + position);
            startActivity(new Intent(StoreDetailsActivity.this,GoodDetailsActivity.class));
        }

        /**
         * ScrollView的滑动事件
         */
        @Override
        public void onScrollChanged(int x, int y, int oldx, int oldy) {

            bottom_rg_height = (int) (store_radioGroup.getY());
            //下面导航栏距离顶部距离，当滑动距离等于这个，让顶部的显示出来
            if (y <= bottom_rg_height) {
                isShowTopRb = false;
            } else {
                isShowTopRb = true;
            }
            //Log.i("info", "==isShowTopRb==" + isShowTopRb);

            if (isShowTopRb) {
                //store_top_radioGroup.setVisibility(View.VISIBLE);
                ll_top_radioGroup.setVisibility(View.VISIBLE);
                store_radioGroup.setVisibility(View.GONE);
            } else {
                ll_top_radioGroup.setVisibility(View.GONE);
                //store_top_radioGroup.setVisibility(View.GONE);
                store_radioGroup.setVisibility(View.VISIBLE);
            }
            //Log.i("info", "滑动了x = " + x + ",y = " + y + ", oldx = " + oldx + ", oldy" + oldy);
            //LogUtil.i("当前List-getY-->" + bottom_rg_height);
        }

        /**
         * 点击事件
         */
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_store_back:  //返回键
                    finish();
                    break;
                case R.id.iv_store_collection : //收藏键
                    ToastUtils.showShort(StoreDetailsActivity.this,"收藏");
                    break;
                case R.id.iv_store_share:  //分享
                    showShare();
                    break;
                case R.id.iv_store_change_store:  //切换门店
                    ToastUtils.showShort(StoreDetailsActivity.this,"切换门店");
                    break;
                case R.id.iv_store_callPhone:  //打电话
                    ToastUtils.showShort(StoreDetailsActivity.this,"打电话");
                    break;
                case R.id.tv_store_search:   //搜索键
                    ToastUtils.showShort(StoreDetailsActivity.this,"搜索键");
                    break;
                default:
                    break;
            }
        }
    }


}

