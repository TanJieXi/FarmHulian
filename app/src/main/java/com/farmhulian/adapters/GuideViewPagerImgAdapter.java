package com.farmhulian.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;


/**
 * 几张轮播图的适配器
 *  @author 谭杰栖
 */
public class GuideViewPagerImgAdapter extends PagerAdapter {
    private Context context;
    private int[] imgIds;

    public GuideViewPagerImgAdapter(Context context, int[] imgIds) {
        this.context = context;
        this.imgIds = imgIds;
    }

    @Override
    public int getCount() {
        return imgIds.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView iv = new ImageView(context);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(imgIds[position]).override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL )
                .dontAnimate().into(iv);
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
