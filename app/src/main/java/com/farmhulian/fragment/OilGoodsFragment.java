package com.farmhulian.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.farmhulian.ContrantsF;
import com.farmhulian.R;
import com.farmhulian.activities.GoodDetailsActivity;
import com.farmhulian.adapters.OilGridViewAdapter;
import com.farmhulian.base.BaseFragment;
import com.farmhulian.utils.RefreshUtils;
import com.farmhulian.utils.ToastUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import java.util.ArrayList;
import java.util.List;


/**
 * 有机粮油里面的碎片，一个GrildView,根据传过来的数字不同来判断加载什么数据
 *
 * @author 谭杰栖
 */
public class OilGoodsFragment extends BaseFragment {
    private View curView;  //当前布局
    private Bundle bundle;  //需要传递的值
    private PullToRefreshGridView oil_pullGridView; //一个刷新
    private String[] titles = {"综合", "有机认证", "绿色认证", "无公害", "其他认证", "优产", "优选"};
    private String curTabMark; //判断当前传的tablayout的那个
    private String curPartMark;  //判断是哪个按钮，生态粮油等七个
    private OilGridViewAdapter adapter;  //适配器
    private List<String> datas = new ArrayList<>();
    private List<String> needDatas;
    private AllListener listener = new AllListener();

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        curView = inflater.inflate(R.layout.oil_goods_fragment_layout, null);
        return curView;
    }

    @Override
    protected void initFindView() {
        oil_pullGridView = (PullToRefreshGridView) curView.findViewById(R.id.oil_pullGridView);


    }


    public List<String> getFragmentDatas(){
        return datas;
    }

    public OilGridViewAdapter getAdapters(){
        return adapter;
    }

    @Override
    protected void achieveProgress() {
        adapter = new OilGridViewAdapter(getActivity(), datas);
        oil_pullGridView.setAdapter(adapter);

        setAllListener();
        setPull();
        judgeWhoData();

    }

    /**
     * 所有事件的监听事件
     */
    private void setAllListener() {
        oil_pullGridView.setOnRefreshListener(listener);
        oil_pullGridView.setOnItemClickListener(listener);
    }

    /**
     * 设置一些Pull的属性
     */
    private void setPull() {
        oil_pullGridView.setMode(PullToRefreshBase.Mode.BOTH);
        RefreshUtils.setPullReContent(oil_pullGridView, "开始刷新", "刷新成功：", "下拉刷新", "释放刷新");
        RefreshUtils.setPullMoreContent(oil_pullGridView, "开始加载", "加载成功：", "上拉加载", "释放加载");
    }

    /**
     * 过来先判断一下应该加载什么数据
     */
    private void judgeWhoData() {
        //首先来接受是哪个碎片
        bundle = getArguments();
        curTabMark = bundle.getString(ContrantsF.OVI_TABLAYOUT_WHICH);
        curPartMark = bundle.getString(ContrantsF.HOME_SEVEN_MARK);

        // Log.i("info", "=OilGoodsFragment==curMark==" + curTabMark);
        //Log.i("info", "=OilGoodsFragment==curPartMark==" + curPartMark);
        //循环遍历一下
        for (int i = 0, len = titles.length; i < len; i++) {
            if (titles[i].equals(curTabMark)) {
                //这里来模拟数据
                downLoadData(i);
            }
        }

    }

    /**
     * 下载数据
     *
     * @param mark 判断当前是哪个位置
     */
    private void downLoadData(int mark) {
        needDatas = new ArrayList<>();
        for (int m = 0; m < mark + 1; m++) {
            needDatas.add("数据" + m);
        }
        if (datas.size() != 0) {
            datas.clear();
        }
        datas.addAll(needDatas);

        adapter.notifyDataSetChanged();
    }


    class AllListener implements PullToRefreshBase.OnRefreshListener<GridView>, AdapterView.OnItemClickListener {

        @Override
        public void onRefresh(PullToRefreshBase<GridView> refreshView) {
            //这里来判断一下是上拉还是加载
            if (oil_pullGridView.isShownHeader()) {  //刷新
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    oil_pullGridView.onRefreshComplete();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


            } else if (oil_pullGridView.isShownFooter()) { //加载
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    oil_pullGridView.onRefreshComplete();
                                }
                            });

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


            }
        }

        ////////////////////////////////////GridView的点击事件///////////////////////////////////////
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ToastUtils.showShort(getActivity(), "当前是：" + curPartMark + "界面里面" + curTabMark + "的第" + position + "条数据");
            startActivity(new Intent(getActivity(), GoodDetailsActivity.class));

        }
    }


}
