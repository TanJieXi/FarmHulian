package com.farmhulian.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.farmhulian.ContrantsF;
import com.farmhulian.R;
import com.farmhulian.adapters.KindLeftListViewAdapter;
import com.farmhulian.adapters.KindRightGridViewAdapter;
import com.farmhulian.base.BaseActivity;
import com.farmhulian.beans.KindRightBean;
import com.farmhulian.utils.ToastUtils;
import com.farmhulian.views.NoScrollGridView;
import com.farmhulian.views.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类Activity
 *
 * @author 谭杰栖
 */
public class KindActivity extends BaseActivity {
    private ImageView kind_title_back;  // 返回键
    private ImageView kind_iv_title_erwei;  //二维码键
    private AllListener listener = new AllListener();
    private TextView kind_tv_title_search;  //搜索键
    private NoScrollListView left_listView;  //左边的ListView的导航栏
    private KindLeftListViewAdapter leftAdapter; //左边的适配器
    private List<String> leftNames = new ArrayList<>();
    private String[] names = {"粮    油", "鲜    果", "蔬    菜", "水    产", "牧    场", "野    生", "特    产"};
    private String[] absolutName = {"生态粮油","生态鲜果","绿色蔬菜","水产海鲜","散养牧场","天然野生","名优特产"};
    private KindRightGridViewAdapter rightAdapter; //右边的GridView的适配器
    private List<KindRightBean> kindRightBeanList = new ArrayList<>();
    private List<KindRightBean> needKindRight;
    private KindRightBean kindRightBean;
    private NoScrollGridView right_gridView;  //右边的禁止滚动的gridView
    private String[] rNames = {"有机认证", "绿色认证", "无公害", "其他认证", "优产", "优选"};
    private int[] rImageIds = {R.mipmap.youjichanpin_img, R.mipmap.lvserenzheng_img, R.mipmap.wugonghai_img, R.mipmap.qitarenzheng_img, R.mipmap.youchan_img, R.mipmap.youxuan_img};
    private String leftMark = "粮    油";  //左边标记    判断是粮油，鲜果，蔬菜，其中的哪一个
    private String rightMark = "有机认证";    //判断右边的是哪个字
    private Intent gridIntent;
    private int leftMarkPotion = 0;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_kind);
    }

    @Override
    public void initFindView() {
        kind_title_back = (ImageView) findViewById(R.id.kind_title_back);
        kind_iv_title_erwei = (ImageView) findViewById(R.id.kind_iv_title_erwei);
        kind_tv_title_search = (TextView) findViewById(R.id.kind_tv_title_search);
        left_listView = (NoScrollListView) findViewById(R.id.left_listView);
        right_gridView = (NoScrollGridView) findViewById(R.id.right_gridView);
    }

    @Override
    public void achieveProgress() {


        setAllListener();

        leftAdapter = new KindLeftListViewAdapter(this, leftNames);
        left_listView.setAdapter(leftAdapter);

        rightAdapter = new KindRightGridViewAdapter(this, kindRightBeanList);
        right_gridView.setAdapter(rightAdapter);


        setLeftData();
        setRightData(rNames.length);


        leftAdapter.setSelectedPosition(0);
    }

    /**
     * 给右边的适配器加东西
     */
    private void setRightData(int count) {
        needKindRight = new ArrayList<>();
        if (kindRightBeanList.size() != 0) {
            kindRightBeanList.clear();
        }
        for (int i = 0; i < count; i++) {
            kindRightBean = new KindRightBean(rNames[i], rImageIds[i]);
            needKindRight.add(kindRightBean);
        }
        kindRightBeanList.addAll(needKindRight);
        rightAdapter.notifyDataSetChanged();
    }

    /**
     * 来给左边的适配器加东西
     */
    private void setLeftData() {

        //来数据
        for (int i = 0, len = names.length; i < len; i++) {
            leftNames.add(names[i]);
        }
        leftAdapter.notifyDataSetChanged();

    }

    /**
     * 添加所有的监听事件
     */
    private void setAllListener() {
        kind_title_back.setOnClickListener(listener);
        kind_iv_title_erwei.setOnClickListener(listener);
        kind_tv_title_search.setOnClickListener(listener);
        left_listView.setOnItemClickListener(listener);
        right_gridView.setOnItemClickListener(listener);
    }

    class AllListener implements View.OnClickListener, AdapterView.OnItemClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.kind_title_back: //返回键
                    finish();
                    break;
                case R.id.kind_iv_title_erwei:  //二维码
                    ToastUtils.showShort(KindActivity.this, "二维码");
                    break;
                case R.id.kind_tv_title_search:  //搜索
                    ToastUtils.showShort(KindActivity.this, "搜索键");
                    break;

            }
        }

        /**
         * ListView的点击事件
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (left_listView == parent) {
                //ToastUtils.showShort(KindActivity.this, "当前点击位置是:" + position);
                leftAdapter.setSelectedPosition(position);
                //leftAdapter.setLeftLinesColor(position);
                leftAdapter.notifyDataSetInvalidated();
                leftMark = names[position];  //左边标记
                //setRightData(position);
                leftMarkPotion = position;
            } else if (right_gridView == parent) {
                rightMark = rNames[position];
                //ToastUtils.showShort(KindActivity.this,"当前点击的是"+leftMark+"界面的"+ rNames[position] +"部分");
                //这个值是判断是Home界面七个粮油等中的哪一个
                gridIntent = new Intent(KindActivity.this,OilActivity.class);
                //这个是判断tab中的哪一个
                gridIntent.putExtra(ContrantsF.HOME_SEVEN_MARK, absolutName[leftMarkPotion]);
                //TODO 这里要将rightMark传递过去，然后让哪个对应的tab 被选中
                gridIntent.putExtra(ContrantsF.KIND_RIGHT_TEXT_PART,rightMark);
                startActivity(gridIntent);
            }
        }
    }

}
