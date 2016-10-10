package com.farmhulian.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.farmhulian.R;
import com.farmhulian.adapters.RestGridViewAdapter;
import com.farmhulian.base.BaseActivity;
import com.farmhulian.utils.ToastUtils;
import com.farmhulian.views.NavTopView;
import com.farmhulian.views.NoScrollGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * 生态餐厅
 *  @author 谭杰栖
 */
public class ResturantActivity extends BaseActivity {
    private NavTopView rest_topView; //题目
    private TextView tv_share;  //分享
    private ImageView iv_back;  //返回
    private TextView tv_rest_title;   //题目
    private AllListener listener = new AllListener();
    private RelativeLayout rl_rest_nongchang;  //农场直供原材料的更多
    private NoScrollGridView rest_gridView;
    private RestGridViewAdapter gridViewAdapter;  //适配器
    private List<String> datas = new ArrayList<>();
    private ImageView iv_rest_titleView;  //最上面的广告

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_resturant);
    }

    @Override
    public void initFindView() {
        rest_topView = (NavTopView) findViewById(R.id.rest_topView);
        tv_share = rest_topView.getRightTextView();
        tv_share.setVisibility(View.VISIBLE);
        iv_back = rest_topView.getLeftView();
        tv_rest_title = rest_topView.getTitleView();
        rl_rest_nongchang = (RelativeLayout) findViewById(R.id.rl_rest_nongchang);
        rest_gridView = (NoScrollGridView) findViewById(R.id.rest_gridView);
        iv_rest_titleView = (ImageView) findViewById(R.id.iv_rest_titleView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        iv_rest_titleView.setFocusable(true);
        iv_rest_titleView.setFocusableInTouchMode(true);
        iv_rest_titleView.requestFocus();
    }

    @Override
    public void achieveProgress() {
        tv_rest_title.setText("生态餐厅");
        setAllListener();


        //模拟下数据
        gridViewAdapter = new RestGridViewAdapter(this,datas);
        rest_gridView.setAdapter(gridViewAdapter);

        for(int i = 0 ; i < 5 ; i++){
            datas.add("" + i);
        }
        gridViewAdapter.notifyDataSetChanged();
    }

    /**
     * 设置所有的监听事件
     */
    private void setAllListener() {
        tv_share.setOnClickListener(listener);
        iv_back.setOnClickListener(listener);
        rl_rest_nongchang.setOnClickListener(listener);
        rest_gridView.setOnItemClickListener(listener);
        iv_rest_titleView.setOnClickListener(listener);
    }

    /**
     * 完成所有事件的监听类
     */
    class AllListener implements View.OnClickListener, AdapterView.OnItemClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.nav_top_right_tv:  //分享键  对应tv_share
                    showShare();
                    break;
                case R.id.nav_top_left:  //返回键
                    finish();
                    break;
                case R.id.rl_rest_nongchang:  //农场直供原材料  的更多
                    ToastUtils.showShort(ResturantActivity.this,"农场直供原材料");
                    break;
                case R.id.iv_rest_titleView:  //最上面的广告
                    ToastUtils.showShort(ResturantActivity.this,"广告");
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
             ToastUtils.showShort(ResturantActivity.this,"当前点击位置："  + position);
        }
    }


}
