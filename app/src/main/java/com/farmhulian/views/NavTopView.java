package com.farmhulian.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.farmhulian.R;


/**
 * @author 谭杰栖
 */
public class NavTopView extends RelativeLayout {
    private ImageView imageViewleft;
    private ImageView imageViewRight;

    private TextView mtext;
    private TextView tvRight;
    private ImageView imageViewRight2;
    private TextView leftTv;

    public NavTopView(Context context) {
        super(context);
    }

    public NavTopView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.public_nav_top, this, true);
        imageViewleft = (ImageView) view.findViewById(R.id.nav_top_left);
        mtext = (TextView) view.findViewById(R.id.nav_top_title);
        imageViewRight = (ImageView) view.findViewById(R.id.nav_top_right);
        tvRight = (TextView) view.findViewById(R.id.nav_top_right_tv);
        imageViewRight2 = (ImageView) view.findViewById(R.id.nav_top_right2);
        leftTv = (TextView) view.findViewById(R.id.nav_top_left_tv);
    }

    /**
     * 获取左边的tv加显示
     */
    public TextView getLeftTv() {
        leftTv.setVisibility(VISIBLE);
        return leftTv;
    }

    /**
     * 设置标题
     * @param tv
     */
    public void setLeftTv(String tv){
        leftTv.setVisibility(VISIBLE);
        mtext.setText(tv);
    }



    /**
     * 获取返回按钮
     * @return
     */
    public ImageView getLeftView() {
        return imageViewleft;
    }

    /**
     * 获取右边的图标按钮
     * @return
     */
    public ImageView getRightView() {
        return imageViewRight;
    }

    /**
     * 获取右边的图标按钮
     * @return
     */
    public ImageView getRightView2() {
        return imageViewRight2;
    }


    /**
     * 获取右边的文字按钮
     * @return
     */
    public TextView getRightTextView(){
        return tvRight;
    }

    /**
     * 获取标题控件
     * @return
     */
    public TextView getTitleView() {
        return mtext;
    }

    /**
     * 设置标题
     * @param title
     */
    public void setTitle(String title){
        mtext.setText(title);
    }


    /**
     * 设置右边的图标
     */
    public void setImageViewRight(Integer img){
        imageViewRight.setImageResource(img);
    }

    /**
     * 设置右边倒数第二个控件的图标
     */
    public void setImageViewRight2(Integer img){
        imageViewRight2.setImageResource(img);
    }

    /**
     * 设置右边的图标变成文字
     */
    public void setTextRight(String tv){
        imageViewRight.setVisibility(GONE);
        tvRight.setVisibility(VISIBLE);
        tvRight.setText(tv);
    }

    /**
     * 设置右边倒数第二个ImageView的显示
     */
    public void setimageViewRight2Visiable(){
        imageViewRight2.setVisibility(VISIBLE);
    }

    public void  setLeftInVisiable(){
        imageViewleft.setVisibility(VISIBLE);
    }

    public void  setRightInVisiable(){
        imageViewRight.setVisibility(VISIBLE);
    }

    public void  setBothInVisiable(){
        imageViewleft.setVisibility(VISIBLE);
        imageViewRight.setVisibility(VISIBLE);
    }

}
