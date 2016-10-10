package com.farmhulian.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 一个自定义的ViewPager禁止滑动，设置setScanScroll
 *  @author 谭杰栖
 */
public class ScrollViewPager extends ViewPager {
    private boolean isScrollable = true;

    public ScrollViewPager(Context context) {
        super(context);
    }

    public ScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setScanScroll(boolean isScrollable){
        this.isScrollable = isScrollable;
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isScrollable) {
            return false;
        } else {
            return super.onTouchEvent(ev);
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isScrollable) {
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    /**
     * 取消页面切换时的动画
     */
    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item,false);
    }
}
