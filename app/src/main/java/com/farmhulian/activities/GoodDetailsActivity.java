package com.farmhulian.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.farmhulian.R;
import com.farmhulian.base.BaseActivity;
import com.farmhulian.utils.ToastUtils;
import com.farmhulian.views.Viewpager_RadioButton_View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 商品信息的Activity
 */
public class GoodDetailsActivity extends BaseActivity {
    private LinearLayout ll_partOne,ll_partTwo,ll_partThree; //被切换的界面
    private RadioGroup details_top_radioGroup;  //商品详情最顶部的几个按钮，切换界面
    private AllListener listener = new AllListener();
    private RadioButton rb;
    private List<LinearLayout> parts;  //用一个集合保存三个部分
    private ImageView iv_details_back;  //返回键
    private Viewpager_RadioButton_View vr_details_banner;  //banner条
    private ImageView iv_detais_share;  //分享键
    private TextView tv_goods_name,tv_goods_price,tv_goods_youfei,tv_goods_saleNum,tv_goods_place;  //商品名字,价格,邮费,月销量，产地
    //最下面的一排，客服，购买什么的
    private Button btn_PayNow;  //立即购买
    private TextView tv_pass;  //溯源通过或者溯源中
    private TextView tv_kefu,tv_coll,tv_farm,tv_add_toCar;  //客服，收藏，农场，加入购物车
    private boolean isPayOk = true;  //判断是否可以购买，溯源中，溯源通过可以

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_good_details);
    }

    @Override
    public void initFindView() {
        details_top_radioGroup = (RadioGroup) findViewById(R.id.details_top_radioGroup);
        ll_partOne = (LinearLayout) findViewById(R.id.ll_partOne);
        ll_partTwo = (LinearLayout) findViewById(R.id.ll_partTwo);
        ll_partThree = (LinearLayout) findViewById(R.id.ll_partThree);
        iv_details_back = (ImageView) findViewById(R.id.iv_details_back);
        iv_detais_share = (ImageView) findViewById(R.id.iv_detais_share);
        btn_PayNow = (Button) findViewById(R.id.btn_PayNow);
        tv_kefu = (TextView) findViewById(R.id.tv_kefu);
        tv_coll = (TextView) findViewById(R.id.tv_coll);
        tv_farm = (TextView) findViewById(R.id.tv_farm);
        tv_add_toCar = (TextView) findViewById(R.id.tv_add_toCar);
        tv_pass = (TextView) findViewById(R.id.tv_pass);

        vr_details_banner = (Viewpager_RadioButton_View) findViewById(R.id.vr_details_banner);

        //商品信息，名，价格，邮费，月销，产地
        tv_goods_name = (TextView) findViewById(R.id.tv_goods_name);
        tv_goods_price = (TextView) findViewById(R.id.tv_goods_price);
        tv_goods_youfei = (TextView) findViewById(R.id.tv_goods_youfei);
        tv_goods_saleNum = (TextView) findViewById(R.id.tv_goods_saleNum);
        tv_goods_place = (TextView) findViewById(R.id.tv_goods_place);


        parts = new ArrayList<>();
        Collections.addAll(parts,ll_partOne,ll_partTwo,ll_partThree);

        //一个初始值
        ((RadioButton) details_top_radioGroup.getChildAt(0)).setChecked(true);

    }

    @Override
    public void achieveProgress() {
        setAllListener();

        vr_details_banner.setViewPagerImagesResource(this,new int[]{R.mipmap.banner1,R.mipmap.banner2,R.mipmap.banner3});

    }

    /**
     * 完成所有控件的监听事件
     */
    private void setAllListener() {
        details_top_radioGroup.setOnCheckedChangeListener(listener);
        iv_details_back.setOnClickListener(listener);
        btn_PayNow.setOnClickListener(listener);
        iv_detais_share.setOnClickListener(listener);

        tv_kefu.setOnClickListener(listener);
        tv_coll.setOnClickListener(listener);
        tv_farm.setOnClickListener(listener);
        tv_add_toCar.setOnClickListener(listener);

        tv_pass.setOnClickListener(listener);
    }

    /**
     * 切换界面
     */
    private void changePart(int curPositon){
        LinearLayout ll;
        for(int i = 0 ; i < parts.size() ; i++){
            ll = parts.get(i);
            if(curPositon == i){
                ll.setVisibility(View.VISIBLE);
            }else{
                ll.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 不可以购买，立即购买按钮变颜色，不可点击，弹出提醒框
     */
    private void noPay() {
        String text_pass = tv_pass.getText().toString();
        isPayOk = "溯源通过".equals(text_pass) ? true : false;
        if(isPayOk){
            tv_pass.setText("溯源中");
            btn_PayNow.setBackgroundColor(Color.rgb(194,188,188));
            isPayOk = false;
        }else{
            tv_pass.setText("溯源通过");
            btn_PayNow.setBackgroundColor(Color.rgb(255,192,1));
            isPayOk = true;
        }
    }


    class AllListener implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

        //RadioGroup的点击事件
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            for(int i = 0 , len = group.getChildCount() ; i < len ; i++){
                rb = (RadioButton) group.getChildAt(i);
                if(rb.isChecked()){
                    changePart(i);
                }
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_details_back:
                    finish();
                    break;
                case R.id.btn_PayNow:  //立即购买
                    if(isPayOk) {
                        startActivity(new Intent(GoodDetailsActivity.this, NowPayActivity.class));
                    }else{
                        ToastUtils.showShort(GoodDetailsActivity.this,"商品溯源中，无法购买，请先关注");
                    }
                    break;
                case R.id.iv_detais_share:  //分享键
                    showShare();
                    break;
                //下面四个键
                case R.id.tv_kefu:  //客服
                    ToastUtils.showShort(GoodDetailsActivity.this,"客服");
                    break;
                case R.id.tv_coll:  //收藏
                    ToastUtils.showShort(GoodDetailsActivity.this,"收藏");
                    break;
                case R.id.tv_farm:  //农场
                    ToastUtils.showShort(GoodDetailsActivity.this,"农场");
                    break;
                case R.id.tv_add_toCar:  //加入购物车
                    ToastUtils.showShort(GoodDetailsActivity.this,"加入购物车");
                    break;
                case R.id.tv_pass:  //点击模拟网络，变成溯源中并且立即购买按钮变颜色，不可点击
                    noPay();
                    break;
                default:
                    break;
            }
        }
    }




}
