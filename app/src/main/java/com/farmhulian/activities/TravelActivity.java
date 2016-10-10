package com.farmhulian.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.farmhulian.R;
import com.farmhulian.adapters.TravelGridViewAdapter;
import com.farmhulian.base.BaseActivity;
import com.farmhulian.utils.ToastUtils;
import com.farmhulian.views.NavTopView;
import com.farmhulian.views.NoScrollGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * 生态旅游
 *  @author 谭杰栖
 */
public class TravelActivity extends BaseActivity {
    private NavTopView travel_topView;
    private TextView tv_share;  //分享
    private ImageView iv_back;  //返回
    private AllListener listener = new AllListener();
    private TextView tv_travel_title;   //题目
    private RelativeLayout rl_travle_yongbao; //拥抱大自然的更多
    private NoScrollGridView travel_gridView;  //生态旅游的GridView
    private List<String> datas = new ArrayList<>();
    private TravelGridViewAdapter gridViewAdapter;
    private ImageView iv_titleView;  //顶部广告

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_travel);
    }

    @Override
    public void initFindView() {
        travel_topView = (NavTopView) findViewById(R.id.travel_topView);
        travel_gridView = (NoScrollGridView) findViewById(R.id.travel_gridView);
        tv_share = travel_topView.getRightTextView();
        tv_share.setVisibility(View.VISIBLE);
        iv_back = travel_topView.getLeftView();
        tv_travel_title = travel_topView.getTitleView();
        rl_travle_yongbao = (RelativeLayout) findViewById(R.id.rl_travle_yongbao);
        iv_titleView = (ImageView) findViewById(R.id.iv_titleView);
    }

    @Override
    public void achieveProgress() {
        tv_travel_title.setText("生态旅游");
        setAllListener();

        //GridView绑定适配器
        gridViewAdapter = new TravelGridViewAdapter(this,datas);
        travel_gridView.setAdapter(gridViewAdapter);

        //模拟网络下数据
        for(int i = 0 ; i < 4 ; i++){
            datas.add("数据" + i);
        }
        gridViewAdapter.notifyDataSetChanged();

    }

    /**
     * 添加全部的监听器
     */
    private void setAllListener() {
        tv_share.setOnClickListener(listener);
        rl_travle_yongbao.setOnClickListener(listener);
        iv_back.setOnClickListener(listener);
        iv_titleView.setOnClickListener(listener);
        travel_gridView.setOnItemClickListener(listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        iv_titleView.setFocusableInTouchMode(true);
        iv_titleView.setFocusable(true);
        iv_titleView.requestFocus();
    }

    /**
     * 一个全部监听事件的类
     */
    class AllListener implements View.OnClickListener, AdapterView.OnItemClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.nav_top_right_tv:  //分享键  对应tv_share
                    ToastUtils.showShort(TravelActivity.this,"分享");
                    showShare();
                    break;
                case R.id.nav_top_left:  //返回键
                    finish();
                    break;
                case R.id.rl_travle_yongbao:  //拥抱大自然
                    ToastUtils.showShort(TravelActivity.this,"拥抱大自然");
                    break;
                case R.id.iv_titleView:  //顶部广告
                    ToastUtils.showShort(TravelActivity.this,"顶部广告");
                    break;
                default:
                    break;
            }
        }

        /**
         * GridView的点击事件
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ToastUtils.showShort(TravelActivity.this,"当前点击的位置：" + position);
        }
    }
}
