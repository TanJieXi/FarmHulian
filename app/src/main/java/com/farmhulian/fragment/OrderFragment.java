package com.farmhulian.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.farmhulian.ContrantsF;
import com.farmhulian.R;
import com.farmhulian.base.BaseFragment;

/**
 * Created by 谭杰栖 on 2016/9/29.
 * 全部订单里面的五个部分，包括全部，已发货，待发货，待收货，待评价
 */

public class OrderFragment extends BaseFragment{
    private View curView;
    private TextView tv_content;
    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        curView = inflater.inflate(R.layout.order_fragment_layout,null);
        return curView;
    }

    @Override
    protected void initFindView() {
        tv_content = (TextView) curView.findViewById(R.id.tv_content);
    }

    @Override
    protected void achieveProgress() {
        Bundle bundle = getArguments();
        String which = bundle.getString(ContrantsF.ORDERS_WHICH_TAB_S);
        tv_content.setText(which);
    }
}
