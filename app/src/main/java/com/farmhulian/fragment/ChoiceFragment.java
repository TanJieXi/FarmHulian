package com.farmhulian.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.farmhulian.R;
import com.farmhulian.activities.GoodDetailsActivity;
import com.farmhulian.adapters.ChoiceGridAdapter;
import com.farmhulian.base.BaseFragment;
import com.farmhulian.utils.RefreshUtils;
import com.farmhulian.utils.ToastUtils;
import com.farmhulian.views.NavTopView;
import com.farmhulian.views.NoScrollGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 精选碎片
 *  @author 谭杰栖
 */
public class ChoiceFragment extends BaseFragment {
    private View curView;
    private NoScrollGridView choice_gridView;
    private RelativeLayout rl_choice_search;
    private ImageView choice_title_erwei;  //二维码
    private AllListener listener = new AllListener();
    private ChoiceGridAdapter gridAdapter;  //GridView的适配器
    private List<String> datas = new ArrayList<>();
    private PullToRefreshScrollView choice_myPullView;   //PulltoScrollView
    private TextView tv_zonghe, tv_xiaoliang, tv_jiage;  //综合排序，销量，价格的文本，更换颜色
    private RelativeLayout rl_zonghe, rl_xiaoliang, rl_jiage;  //综合，销量和价格排序的布局，点击要变后面箭头颜色
    private ImageView iv_xiangliang_order, iv_price_order; //分别是销量上下箭头，价格上下箭头，点击要切换
    private int clickXiliangNum = 0; //点击销量的次数，用于切换黑红箭头
    private int clickJiageNum = 0;  //点击价格的次数，用于切换黑红箭头
    private List<TextView> textViews;  //用于保存综合销量价格的字，要更改颜色
    private NavTopView buy_choice_titleView; //标题栏

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        curView = inflater.inflate(R.layout.fragment_main_choice, null);
        return curView;
    }

    @Override
    protected void initFindView() {
        //标题栏
        buy_choice_titleView = (NavTopView) curView.findViewById(R.id.buy_choice_titleView);
        buy_choice_titleView.getLeftView().setVisibility(View.GONE);
        buy_choice_titleView.getTitleView().setText("精选");

        choice_gridView = (NoScrollGridView) curView.findViewById(R.id.choice_gridView);
        choice_title_erwei = (ImageView) curView.findViewById(R.id.choice_title_erwei);
        rl_choice_search = (RelativeLayout) curView.findViewById(R.id.rl_choice_search);
        choice_myPullView = (PullToRefreshScrollView) curView.findViewById(R.id.choice_myPullView);

        rl_zonghe = (RelativeLayout) curView.findViewById(R.id.rl_zonghe);
        rl_xiaoliang = (RelativeLayout) curView.findViewById(R.id.rl_xiaoliang);
        rl_jiage = (RelativeLayout) curView.findViewById(R.id.rl_jiage);

        //销量的箭头
        iv_xiangliang_order = (ImageView) curView.findViewById(R.id.iv_xiangliang_order);
        //价格的箭头
        iv_price_order = (ImageView) curView.findViewById(R.id.iv_price_order);

        //综合排序，销量排序，价格排序的文本   tv_zonghe,tv_xiaoliang,tv_jiage
        tv_zonghe = (TextView) curView.findViewById(R.id.tv_zonghe);
        tv_xiaoliang = (TextView) curView.findViewById(R.id.tv_xiaoliang);
        tv_jiage = (TextView) curView.findViewById(R.id.tv_jiage);

        textViews = new ArrayList<>();
        Collections.addAll(textViews, tv_zonghe, tv_xiaoliang, tv_jiage);
    }

    @Override
    protected void achieveProgress() {
        //设置监听事件
        setAllListener();

        //绑定适配器
        gridAdapter = new ChoiceGridAdapter(getActivity(), datas);
        choice_gridView.setAdapter(gridAdapter);

        //模拟从网络获取数据
        setGridData();
        setPull();
    }

    private void setPull() {
        choice_myPullView.setMode(PullToRefreshBase.Mode.BOTH);
        RefreshUtils.setPullReContent(choice_myPullView, "开始刷新", "刷新成功：", "下拉刷新", "释放刷新");
        RefreshUtils.setPullMoreContent(choice_myPullView, "开始加载", "加载成功：", "上拉加载", "释放加载");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (choice_myPullView != null) {
                choice_myPullView.scrollBy(0, 0);
            }
        }

    }

    /**
     * 从网络获取数据
     */
    private void setGridData() {
        for (int i = 0; i < 10; i++) {
            datas.add("数据" + i);
        }
        gridAdapter.notifyDataSetChanged();
    }

    /**
     * 设置全部监听事件
     */
    private void setAllListener() {
        choice_title_erwei.setOnClickListener(listener);
        rl_choice_search.setOnClickListener(listener);
        choice_myPullView.setOnRefreshListener(listener);
        choice_gridView.setOnItemClickListener(listener);
        rl_zonghe.setOnClickListener(listener);
        rl_xiaoliang.setOnClickListener(listener);
        rl_jiage.setOnClickListener(listener);
    }

    @Override
    public void onResume() {
        super.onResume();
        choice_myPullView.setFocusable(true);
        choice_myPullView.setFocusableInTouchMode(true);
        choice_myPullView.requestFocus();
    }


    /**
     * 更改颜色
     */
    private void changeTextCorlor(int position) {
        TextView t;
        for (int i = 0; i < textViews.size(); i++) {
            t = textViews.get(i);
            if (t != null) {
                if (position == i) {
                    t.setTextColor(Color.RED);
                } else {
                    t.setTextColor(Color.BLACK);
                }
            }

        }
    }

    /**
     * 所有事件的监听事件
     */
    class AllListener implements View.OnClickListener, PullToRefreshBase.OnRefreshListener<ScrollView>, AdapterView.OnItemClickListener {

        /**
         * 点击事件
         */
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_choice_search: //搜索
                    ToastUtils.showShort(getActivity(), "搜索");
                    break;
                case R.id.choice_title_erwei: //二维码
                    ToastUtils.showShort(getActivity(), "二维码");
                    break;
                case R.id.rl_zonghe: //综合排序
                    setZongheOrder();
                    break;
                case R.id.rl_xiaoliang://销量排序
                    setXiaoLiangOrder();
                    break;
                case R.id.rl_jiage:  //价格排序
                    setPriceOrder();
                    break;
                default:
                    break;
            }
        }

        /**
         * Pull的刷新事件
         */
        @Override
        public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
            //这里来判断一下是上拉还是加载
            if (choice_myPullView.isShownHeader()) {  //刷新
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                choice_myPullView.onRefreshComplete();

            } else if (choice_myPullView.isShownFooter()) { //加载
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                choice_myPullView.onRefreshComplete();

            }
        }

        /**
         * GridView的点击事件
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ToastUtils.showShort(getActivity(), "当前点击位置：" + position);
            startActivity(new Intent(getActivity(), GoodDetailsActivity.class));
        }
    }



    /**
     * 综合排序，把销量和价格字体颜色变化，箭头双黑
     */
    private void setZongheOrder() {
        changeTextCorlor(0);  //字体改变
        clickJiageNum = 0;
        clickXiliangNum = 0;
        iv_price_order.setImageResource(R.mipmap.click_all_black);
        iv_xiangliang_order.setImageResource(R.mipmap.click_all_black);
        ToastUtils.showShort(getActivity(), "综合排序");
    }

    /**
     * 点击销量之后，变化箭头，并且操作不同，
     */
    private void setXiaoLiangOrder() {
        changeTextCorlor(1);  //把字体颜色改变一下
        clickJiageNum = 0; //价格的数量必须清0
        clickXiliangNum++;
        //并且设置成双箭头
        iv_price_order.setImageResource(R.mipmap.click_all_black);
        if(clickXiliangNum % 2 != 0){ // 1.上红，销量从高到低
            iv_xiangliang_order.setImageResource(R.mipmap.top_red_bottom_black);
            ToastUtils.showShort(getActivity(), "销量从高到低");
        }else{  // 2.下红 ，销量从低到高
            iv_xiangliang_order.setImageResource(R.mipmap.top_black_bottom_red);
            ToastUtils.showShort(getActivity(), "销量从低到高");
        }

    }

    /**
     * 点击价格之后，箭头变化，销量箭头双黑，字体改变
     */
    private void setPriceOrder() {
        changeTextCorlor(2);  //把字体颜色改变一下
        clickXiliangNum= 0; //销量的数量必须清0
        clickJiageNum++;
        //并且设置成双箭头
        iv_xiangliang_order.setImageResource(R.mipmap.click_all_black);
        if(clickJiageNum % 2 != 0){ // 1.上红，价格从高到低
            iv_price_order.setImageResource(R.mipmap.top_red_bottom_black);
            ToastUtils.showShort(getActivity(), "价格从高到低");
        }else{  // 2.下红 ，价格从低到高
            iv_price_order.setImageResource(R.mipmap.top_black_bottom_red);
            ToastUtils.showShort(getActivity(), "价格从低到高");
        }
    }


}


