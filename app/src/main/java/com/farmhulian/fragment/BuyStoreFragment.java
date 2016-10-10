package com.farmhulian.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.farmhulian.R;
import com.farmhulian.activities.SurePayOrderActivity;
import com.farmhulian.adapters.BuyListViewAdapter;
import com.farmhulian.base.BaseFragment;
import com.farmhulian.utils.RefreshUtils;
import com.farmhulian.utils.ToastUtils;
import com.farmhulian.views.NavTopView;
import com.farmhulian.views.NoScrollListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车碎片
 *  @author 谭杰栖
 */
public class BuyStoreFragment extends BaseFragment {
    private View curView; //标题布局
    private TextView tv_title; //标题
    private NoScrollListView buy_listView;
    private BuyListViewAdapter listAdapter;  //listView的适配器
    private List<String> datas = new ArrayList<>();
    private PullToRefreshScrollView buyPullToScroll;  //刷新
    private AllListener listener = new AllListener();
    private CheckBox cb_buy_allChoice; //全选框
    private TextView tv_buyGoodsTotalPrice; //购买总价
    private TextView tv_pay; //购买键

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        curView = inflater.inflate(R.layout.fragment_main_buy,null);
        return curView;
    }

    @Override
    protected void initFindView() {
        NavTopView buy_titleView = (NavTopView) curView.findViewById(R.id.buy_titleView);
        buy_titleView.getLeftView().setVisibility(View.GONE);
        tv_title = buy_titleView.getTitleView();
        buy_listView = (NoScrollListView) curView.findViewById(R.id.buy_listView);
        buyPullToScroll = (PullToRefreshScrollView) curView.findViewById(R.id.buyPullToScroll);
        cb_buy_allChoice = (CheckBox) curView.findViewById(R.id.cb_buy_allChoice);
        tv_buyGoodsTotalPrice = (TextView) curView.findViewById(R.id.tv_buyGoodsTotalPrice);
        tv_pay = (TextView) curView.findViewById(R.id.tv_pay);
    }

    @Override
    protected void achieveProgress() {
        tv_title.setText("购物车");


        listAdapter = new BuyListViewAdapter(getActivity(),datas);
        buy_listView.setAdapter(listAdapter);


        setAllListener();
        setPull();



        setListViewData();

    }

    /**
     * 设置所有事件的点击事件
     */
    private void setAllListener() {
        buyPullToScroll.setOnRefreshListener(listener);
        cb_buy_allChoice.setOnCheckedChangeListener(listener);
        tv_pay.setOnClickListener(listener);
        listAdapter.setOnItemCheckBoxChangeListener(listener);
    }

    /**
     *  设置一些Pull的数据，字体以及支持加载
     */
    private void setPull() {
        buyPullToScroll.setMode(PullToRefreshBase.Mode.BOTH);
        RefreshUtils.setPullReContent(buyPullToScroll, "开始刷新", "刷新成功：", "下拉刷新", "释放刷新");
        RefreshUtils.setPullMoreContent(buyPullToScroll, "开始加载", "加载成功：", "上拉加载", "释放加载");
    }

    /**
     * 模拟网络下数据
     */
    private void setListViewData() {
        for(int i = 0 ; i < 5 ; i++){
            datas.add("数据"  + i );
        }
            listAdapter.notifyDataSetChanged();
    }

    /**
     * 所有事件的监听事件类
     */
    class AllListener implements PullToRefreshBase.OnRefreshListener<ScrollView>, CompoundButton.OnCheckedChangeListener, View.OnClickListener, BuyListViewAdapter.OnItemCheckBoxChangeListener {
        /**
         * PullToRefresh的刷新事件监听
         */
        @Override
        public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
            //这里来判断一下是上拉还是加载
            if (buyPullToScroll.isShownHeader()) {  //刷新
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                buyPullToScroll.onRefreshComplete();

            } else if (buyPullToScroll.isShownFooter()) { //加载
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                buyPullToScroll.onRefreshComplete();

            }


        }

        /**
         * CheckBox的选中监听事件
         */
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                listAdapter.setAllCheck(false);
                listAdapter.setAllCheck(true);
            }else{
                listAdapter.setAllCheck(false);
            }
        }


        /**
         * 所有的点击事件
         */
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_pay:
                    ToastUtils.showShort(getActivity(),"付款");
                    startActivity(new Intent(getActivity(), SurePayOrderActivity.class));
                    break;
            }
        }

        /**
         * item里面的check选中事件
         * @param position  位置
         * @param buyNum   购买数量
         */
        @Override
        public void onChecked(int position, int buyNum) {
            ToastUtils.showShort(getActivity(),"选中的是第" + position + "位置的东西，数量是" + buyNum);
        }

        @Override
        public void onUnChecked(int position) {
            //ToastUtils.showShort(getActivity(),"取消的的是第" + position + "位置的东西");
        }
    }

}
