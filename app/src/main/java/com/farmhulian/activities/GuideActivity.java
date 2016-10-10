package com.farmhulian.activities;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.farmhulian.ContrantsF;
import com.farmhulian.MainActivity;
import com.farmhulian.R;
import com.farmhulian.adapters.GuideViewPagerImgAdapter;
import com.farmhulian.base.BaseActivity;
import com.farmhulian.utils.SharePreUtils;

/**
 * 引导页
 *  @author 谭杰栖
 */
public class GuideActivity extends BaseActivity {
    private ViewPager guide_viewPager;
    private RadioGroup guide_radioGroup;
    private GuideViewPagerImgAdapter adapter;
    private int[] imgIds = {R.mipmap.guide1,R.mipmap.guide2,R.mipmap.guide3};
    private ImageView iv_jump;
    private AllListener listener = new AllListener();
    private RadioButton rb;
    private Animator animator;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_guide);
    }

    @Override
    public void initFindView() {
        guide_viewPager = (ViewPager) findViewById(R.id.guide_viewPager);
        guide_radioGroup = (RadioGroup) findViewById(R.id.guide_radioGroup);
        iv_jump = (ImageView) findViewById(R.id.iv_jump);
    }

    @Override
    public void achieveProgress() {



        adapter = new GuideViewPagerImgAdapter(this,imgIds);
        guide_viewPager.setAdapter(adapter);

        addAllListener();

        animator = AnimatorInflater.loadAnimator(this, R.animator.guide_btn_anim);
        animator.setTarget(iv_jump);
        ((RadioButton)(guide_radioGroup.getChildAt(0))).setChecked(true);
    }

    /**
     * 添加所有的监听事件
     */
    private void addAllListener() {
        guide_viewPager.addOnPageChangeListener(listener);
        iv_jump.setOnClickListener(listener);
    }

    class AllListener extends ViewPager.SimpleOnPageChangeListener implements View.OnClickListener {


        @Override
        public void onPageSelected(int position) {

            super.onPageSelected(position);
            rb = (RadioButton) guide_radioGroup.getChildAt(position);
            if(rb != null){
                rb.setChecked(true);
            }

            if(2 == position){
                animator.start();
            }else{
                iv_jump.setAlpha(0f);
            }
        }

        @Override
        public void onClick(View v) {
            SharePreUtils.put(GuideActivity.this, ContrantsF.isFirst,true);
            startActivity(new Intent(GuideActivity.this, MainActivity.class));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

}
