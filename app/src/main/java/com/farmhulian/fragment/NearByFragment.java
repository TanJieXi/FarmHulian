package com.farmhulian.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.farmhulian.R;
import com.farmhulian.activities.StoreDetailsActivity;
import com.farmhulian.adapters.NearListViewAdapter;
import com.farmhulian.base.BaseFragment;
import com.farmhulian.utils.RefreshUtils;
import com.farmhulian.utils.ToastUtils;
import com.farmhulian.views.NavTopView;
import com.farmhulian.views.NoScrollListView;
import com.farmhulian.views.Viewpager_RadioButton_View;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * 附近商家碎片
 *  @author 谭杰栖
 */
public class NearByFragment extends BaseFragment {
    private View curView;
    private Viewpager_RadioButton_View near_banner;  //Banner条
    private NoScrollListView near_listView;  //列表
    private NavTopView vt_titleView;  //标题
    private ImageView iv_back; //返回键
    private AllListener listener = new AllListener(); //监听
    private TextView tv_title;
    private NearListViewAdapter adapter;
    private List<String> datas = new ArrayList<>();
    private PullToRefreshScrollView near_myPullView;  //刷新的控件

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        curView = inflater.inflate(R.layout.fragment_main_nearby, null);
        return curView;
    }



    @Override
    protected void initFindView() {
        near_banner = (Viewpager_RadioButton_View) curView.findViewById(R.id.near_banner);
        near_listView = (NoScrollListView) curView.findViewById(R.id.near_listView);
        vt_titleView = (NavTopView) curView.findViewById(R.id.vt_titleView);
        near_myPullView = (PullToRefreshScrollView) curView.findViewById(R.id.near_myPullView);


        iv_back = vt_titleView.getLeftView();
        tv_title = vt_titleView.getTitleView();
        iv_back.setVisibility(View.GONE);
    }

    @Override
    protected void achieveProgress() {
        tv_title.setText("附近商家");
        //设置监听
        setAllListener();
        setBanner();
        setPull();

        //ListView绑定
        adapter = new NearListViewAdapter(getActivity(), datas);
        near_listView.setAdapter(adapter);

        //模拟网络下载数据
        setListViewData();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(near_myPullView != null) {
            near_myPullView.scrollTo(0, 0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        vt_titleView.setFocusable(true);
        vt_titleView.setFocusableInTouchMode(true);
        vt_titleView.requestFocus();
    }

    /**
     * 设置加载字样  和  支持加载
     */
    private void setPull() {
        //支持加载
        near_myPullView.setMode(PullToRefreshBase.Mode.BOTH);

        //设置字体
        RefreshUtils.setPullReContent(near_myPullView,"开始刷新", "刷新成功：", "下拉刷新", "释放刷新");
        RefreshUtils.setPullMoreContent(near_myPullView,"开始加载", "加载成功：", "上拉加载", "释放加载");
    }

    private void setListViewData() {
        for (int i = 0; i < 10; i++) {
            datas.add("Data=  + i");
        }
        adapter.notifyDataSetChanged();
        near_myPullView.scrollTo(0,0);

    }

    //设置广告
    private void setBanner() {
        String[] imgsUrl = {"http://p3.so.qhmsg.com/t016ec2cd3a0c2312bd.jpg",
                "http://p3.so.qhmsg.com/t01723278fa3d63c048.jpg",
                "http://p2.so.qhmsg.com/t01ba3a9f0dd885778c.jpg"};
        int[] imgs = {R.mipmap.banner1,R.mipmap.banner2,R.mipmap.banner3};
        near_banner.setViewPagerImagesResource(getActivity(),imgs, imgsUrl);
    }

    /**
     * 完成所有事件的监听事件
     */
    private void setAllListener() {
        near_banner.setOnItemClickListener(listener);
        near_myPullView.setOnRefreshListener(listener);
        near_listView.setOnItemClickListener(listener);
    }


    /**
     * 所有事件的监听
     */
    class AllListener implements Viewpager_RadioButton_View.OnItemClickListener, View.OnClickListener, PullToRefreshBase.OnRefreshListener<ScrollView>, AdapterView.OnItemClickListener {

        /**
         * Banner条的点击事件
         * @param position 位置
         * @param webUrl  网址
         */
        @Override
        public void onItemClick(int position, String webUrl) {
            ToastUtils.showShort(getActivity(), "=地址=" + webUrl + ",位置=" + position);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {


            }
        }

        /**
         * 刷新的点击事件
         */
        @Override
        public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
            //这里来判断一下是上拉还是加载
            if (near_myPullView.isShownHeader()) {  //刷新
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

                near_myPullView.onRefreshComplete();

            } else if (near_myPullView.isShownFooter()) { //加载
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

                near_myPullView.onRefreshComplete();

            }
        }

        /**
         * ListView的点击事件
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ToastUtils.showShort(getActivity(),"当前点击位置：" + position);
            startActivity(new Intent(getActivity(),StoreDetailsActivity.class));
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        //near_banner.removeHandler();
    }
}
