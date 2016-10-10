package com.farmhulian.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 原理：首先让子控件的内容全部显示出来，禁用了它的滚动。如果超过了父控件的范围则显示父控件的scrollbar滚动显示内容。
 * 此GridView主要用于同ScrollView共存
 *  @author 谭杰栖
 */
public class NoScrollGridView extends GridView {
    public NoScrollGridView(Context context) {
        super(context);
    }

    public NoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 第一个参数决定布局的大小，第二个参数是布局模式。
         * MeasureSpec.AT_MOST的意思就是子控件需要多大的控件就扩展到多大的空间。
         */
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
