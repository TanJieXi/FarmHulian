package com.farmhulian.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farmhulian.R;
import com.farmhulian.base.BaseFragment;

/**
 * Created by 谭杰栖 on 2016/9/29.
 * 宝贝收藏的fragment
 */

public class CollectionLeftFragment extends BaseFragment{
    private View curView;
    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        curView = inflater.inflate(R.layout.collection_left_fragment_layout,null);
        return curView;
    }

    @Override
    protected void initFindView() {

    }

    @Override
    protected void achieveProgress() {

    }
}
