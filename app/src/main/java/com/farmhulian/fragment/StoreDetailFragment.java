package com.farmhulian.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.farmhulian.ContrantsF;
import com.farmhulian.R;
import com.farmhulian.adapters.StoreDetaisListViewAdapter;
import com.farmhulian.base.BaseFragment;
import com.farmhulian.views.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 复用碎片，门店详情里面的，热销，粮油，生鲜，蔬果
 *  @author 谭杰栖
 */
public class StoreDetailFragment extends BaseFragment {
    private View curView;
    private NoScrollListView store_listView;
    private String curTab;  //用于判断当前是哪个tabs
    private Bundle bundle;
    private StoreDetaisListViewAdapter adapter;
    private List<String> datas = new ArrayList<>();
    private List<String> needDatas;

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        curView = inflater.inflate(R.layout.fragment_store_layout, null);
        return curView;
    }

    @Override
    protected void initFindView() {
        store_listView = (NoScrollListView) curView.findViewById(R.id.store_listView);
    }

    @Override
    protected void achieveProgress() {

        adapter = new StoreDetaisListViewAdapter(getActivity(), datas);
        store_listView.setAdapter(adapter);
        //设置上
        store_listView.setLayoutParams(getListViewParams());
        judgeWhichTabs();


    }

    /**
     * 判断当前点击的是哪个tabs
     */
    private void judgeWhichTabs() {
        bundle = getArguments();
        curTab = bundle.getString(ContrantsF.STORE_DETALS_WHICH_TABS);
        if ("热销".equals(curTab)) {
            setFragmentDatas(2);
        } else if ("粮油".equals(curTab)) {
            setFragmentDatas(3);
        } else if ("生鲜".equals(curTab)) {
            setFragmentDatas(4);
        } else if ("蔬果".equals(curTab)) {
            setFragmentDatas(5);
        }
    }

    private void setFragmentDatas(int count) {
        if (datas.size() != 0) {
            datas.clear();
        }
        needDatas = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            needDatas.add("你好");
        }
        datas.addAll(needDatas);
        adapter.notifyDataSetChanged();
    }

    /**
     * 动态的算出ListView实际的LayoutParams
     * 最关键的是算出LayoutParams.height
     */
    public ViewGroup.LayoutParams getListViewParams() {
        //通过ListView获取其中的适配器adapter
        ListAdapter listAdapter = store_listView.getAdapter();

        //声明默认高度为0
        int totalHeight = 0;
        //便利ListView所有的item，累加所有item的高度就是ListView的实际高度
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, store_listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        //将累加获取的totalHeight赋值给LayoutParams的height属性
        ViewGroup.LayoutParams params = store_listView.getLayoutParams();
        params.height = totalHeight + (store_listView.getDividerHeight() * (listAdapter.getCount() - 1));
        return params;
    }


}
