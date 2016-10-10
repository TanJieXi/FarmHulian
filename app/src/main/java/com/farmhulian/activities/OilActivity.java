package com.farmhulian.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.farmhulian.ContrantsF;
import com.farmhulian.R;
import com.farmhulian.adapters.OilGridViewAdapter;
import com.farmhulian.adapters.OilVFragmentAdapter;
import com.farmhulian.base.BaseActivity;
import com.farmhulian.fragment.OilGoodsFragment;
import com.farmhulian.utils.ToastUtils;
import com.zbar.lib.CaptureActivity;
import com.zbar.lib.Contrants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 生态粮油
 *  @author 谭杰栖
 */
public class OilActivity extends BaseActivity{
    private TabLayout oil_tablayout; //标题的tablayout
    private String[] titles = {"综合","有机认证","绿色认证","无公害","其他认证","优产","优选"}; //标题题目
    private ViewPager oil_viewPager;
    private OilVFragmentAdapter vAdapter;
    private OilGoodsFragment oilGoodsFragment; //复用碎片
    private Bundle bundle;
    private List<Fragment> fragmentList;
    private ImageView oil_title_back,oil_iv_title_erwei;//返回键和二维码键
    private AllListener listener = new AllListener();
    private TextView oil_tv_title_search;
    private RelativeLayout rl_moreOrder_up,rl_moreOrder_down;  //标题右边那个弹出PopuWindow,一个上一个下
    private PopupWindow popupWindow;
    //销量最好，价格从低到高，从高到低，评价最好
    private TextView tv_order_saleMach,tv_order_price_to_up,tv_order_price_to_down,tv_order_opinion;
    //分别是弹出框各自右边的确定按钮
    private RelativeLayout rl_saleMach_choose,rl_price_to_up_choose,rl_price_to_down_choose,rl_opinion_choose;
    private View popView;
    private List<TextView> textViews;  //用一个集合保存，要变化颜色
    private List<RelativeLayout> relativeLayouts;  //用一个集合保存，变化颜色的同时让右边的钩钩显示出来
    private Intent intent;
    private String mark;  //用于判断七个按钮是哪个按钮
    private String getTabMarkFromKind = "综合";   //根据kind界面右边的gridView点击的是有机认证，绿色认证，无公害，其他认证，优产，优选中的哪一个

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_oil_layout);

        popView = LayoutInflater.from(OilActivity.this).inflate(R.layout.oil_popwindow_layout,null);
    }

    @Override
    public void initFindView() {

        oil_tablayout = (TabLayout) findViewById(R.id.oil_tablayout);
        oil_viewPager = (ViewPager) findViewById(R.id.oil_viewPager);
        oil_title_back = (ImageView) findViewById(R.id.oil_title_back);
        oil_tv_title_search = (TextView) findViewById(R.id.oil_tv_title_search);
        oil_iv_title_erwei = (ImageView) findViewById(R.id.oil_iv_title_erwei);
        rl_moreOrder_up = (RelativeLayout) findViewById(R.id.rl_moreOrder_up);
        rl_moreOrder_down = (RelativeLayout) findViewById(R.id.rl_moreOrder_down);


        textViews = new ArrayList<>();
        relativeLayouts = new ArrayList<>();
        //PopWindow上面的控件,销量排行
        tv_order_saleMach = (TextView) popView.findViewById(R.id.tv_order_saleMach);
        //价格从低往上
        tv_order_price_to_up = (TextView) popView.findViewById(R.id.tv_order_price_to_up);
        //价格从高往低
        tv_order_price_to_down = (TextView) popView.findViewById(R.id.tv_order_price_to_down);
        //评价最好
        tv_order_opinion = (TextView) popView.findViewById(R.id.tv_order_opinion);
        Collections.addAll(textViews,tv_order_saleMach,tv_order_price_to_up,tv_order_price_to_down,tv_order_opinion);

        //分别是右边的钩钩
        rl_saleMach_choose = (RelativeLayout) popView.findViewById(R.id.rl_saleMach_choose);
        rl_price_to_up_choose = (RelativeLayout) popView.findViewById(R.id.rl_price_to_up_choose);
        rl_price_to_down_choose = (RelativeLayout) popView.findViewById(R.id.rl_price_to_down_choose);
        rl_opinion_choose = (RelativeLayout) popView.findViewById(R.id.rl_opinion_choose);
        Collections.addAll(relativeLayouts,rl_saleMach_choose,rl_price_to_up_choose,rl_price_to_down_choose,rl_opinion_choose);


    }

    @Override
    public void achieveProgress() {

        //TODO 相同的
        //数据取出来判断一下是哪个的点击事件
        intent = getIntent();
        mark = intent.getStringExtra(ContrantsF.HOME_SEVEN_MARK);
        getTabMarkFromKind = intent.getStringExtra(ContrantsF.KIND_RIGHT_TEXT_PART);

        //Log.i("info","====mark=====" + mark);
        setAllListener();
        setTabLayout();
        initOilViewPager();
        //设置PopuWindow的信息
        initPopWindow();

        //Kind界面过来,循环遍历来设置上当前点击界面
        for(int i = 0 , len = titles.length ; i < len ;i++){
            if(titles[i].equals(getTabMarkFromKind)){
                oil_viewPager.setCurrentItem(i);
            }
        }
    }

    /**
     * 完成所有控件的监听事件
     */
    private void setAllListener() {
        oil_title_back.setOnClickListener(listener);
        oil_tv_title_search.setOnClickListener(listener);
        oil_iv_title_erwei.setOnClickListener(listener);
        rl_moreOrder_up.setOnClickListener(listener);
        rl_moreOrder_down.setOnClickListener(listener);
        oil_viewPager.addOnPageChangeListener(listener);


        //PopWindow上面四个点击事件
        for(int i = 0 , len = textViews.size() ; i < len ; i++){
            textViews.get(i).setOnClickListener(listener);
            relativeLayouts.get(i).setOnClickListener(listener);
        }

    }




    /**
     * 初始化ViewPager数据
     */
    private void initOilViewPager() {

        fragmentList = new ArrayList<>();
        for(int i = 0 , len = titles.length ; i < len ; i++){
            oilGoodsFragment = new OilGoodsFragment();
            bundle = new Bundle();
            //TODO 数据 这个数据，分别是传当前七个按钮点击的是哪个生态粮油等，还有一个是Tablayout的哪个，分别处理
            //这个数据，分别是传当前七个按钮点击的是哪个生态粮油等，还有一个是Tablayout的哪个，分别处理
            bundle.putString(ContrantsF.OVI_TABLAYOUT_WHICH,titles[i]);
            bundle.putString(ContrantsF.HOME_SEVEN_MARK,mark);

            oilGoodsFragment.setArguments(bundle);
            fragmentList.add(oilGoodsFragment);
        }
        vAdapter = new OilVFragmentAdapter(getSupportFragmentManager(),fragmentList,titles);
        oil_viewPager.setAdapter(vAdapter);
        oil_tablayout.setupWithViewPager(oil_viewPager);
        oil_viewPager.setOffscreenPageLimit(ContrantsF.VIEWPAGER_LIMIT_NUM);
    }

    /**
     * 设置一些TabLayout的属性
     */
    private void setTabLayout() {
        oil_tablayout.setSelectedTabIndicatorColor(Color.rgb(232,84,8));
        oil_tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        oil_tablayout.setTabTextColors(Color.WHITE,Color.WHITE);

    }

    /**
     * 显示PopWindow
     */
    private void initPopWindow(){

        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);

        popupWindow.getBackground().setAlpha(170);

        //popupWindow的点击事件
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //popupWindow.getBackground().setAlpha(0);
                rl_moreOrder_up.setVisibility(View.GONE);
                rl_moreOrder_down.setVisibility(View.VISIBLE);
            }
        });

    }

    /**
     * 显示PopWindow
     */
    private void showPopuWindow() {
        popupWindow.showAsDropDown(oil_tablayout);
        //popupWindow.getBackground().setAlpha(170);
        rl_moreOrder_up.setVisibility(View.VISIBLE);
        rl_moreOrder_down.setVisibility(View.GONE);
    }

    /**
     * 完成所有事件的监听
     */
    class AllListener extends ViewPager.SimpleOnPageChangeListener implements View.OnClickListener {

        /**
         * ViewPager的切换监听事件
         * @param position
         */
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            if(popupWindow != null){
                //TODO  切换Tab让PopuWindow默认选中第一个
                setPopLeftTextColor(0);
                popupWindow.dismiss();
            }
        }

        /**
         * 所有事件的点击事件
         * @param v
         */
        @Override
        public void onClick(View v) {
            /////////////////////////所有控件的点击事件//////////////////////////////////////////
             switch (v.getId()){
                 case R.id.oil_title_back:  //返回键
                     finish();
                     break;
                 case R.id.oil_tv_title_search: //搜索键
                     ToastUtils.showShort(OilActivity.this,"搜索");
                     break;
                 case R.id.oil_iv_title_erwei: //二维码
                     ToastUtils.showShort(OilActivity.this,"二维码");
                     startActivityForResult(new Intent(OilActivity.this, CaptureActivity.class), Contrants.captureActErWei);
                     break;
                 case R.id.rl_moreOrder_down: //弹出来东西
                     showPopuWindow();
                     break;
                 case R.id.rl_moreOrder_up: //收起Pop
                     if(popupWindow != null) {
                         popupWindow.dismiss();
                     }
                     rl_moreOrder_up.setVisibility(View.GONE);
                     rl_moreOrder_down.setVisibility(View.VISIBLE);
                     break;


                 //////////////下面四个是Popwindow里面的四个点击///////////////
                 case R.id.tv_order_saleMach:  //销量最高
                     setPopLeftTextColor(0);
                     break;
                 case R.id.tv_order_price_to_up: //销量从低到高
                     setPopLeftTextColor(1);
                     break;
                 case R.id.tv_order_price_to_down:  //销量从高到底
                     setPopLeftTextColor(2);
                     break;
                 case R.id.tv_order_opinion:  //评价最好
                     setPopLeftTextColor(3);
                     break;
                 /////////////////////////////////////////////////
                 ////////////////////////选好以后/////////////////////////////
                 case R.id.rl_saleMach_choose:  //销量优先
                     choosePopPosition(0);
                     break;
                 case R.id.rl_price_to_up_choose:  //价格从低到高
                     choosePopPosition(1);
                     break;
                 case R.id.rl_price_to_down_choose:  //价格从高到底
                     choosePopPosition(2);
                     break;
                 case R.id.rl_opinion_choose:  //评价最好
                     choosePopPosition(3);
                     break;

                 ////////////////以上是选好之后的//////////////////////////////////////


                 default:
                     break;

             }

            ////////////////////////////////////////////////////////////////////////////////////
        }


    }



    /**
     * 选中了Popu里面的数据之后
     */
    private void choosePopPosition(int position) {
        if(popupWindow != null){
            popupWindow.dismiss();
        }

        //TODO 这里很重要，思想是把碎片里面的data和适配器adapter回调出来，分别处理
        int curTab = oil_viewPager.getCurrentItem();
        OilGoodsFragment fragment = (OilGoodsFragment) fragmentList.get(curTab);
        List<String> datas = fragment.getFragmentDatas();
        OilGridViewAdapter adapters = fragment.getAdapters();


        //Log.i("info","====part:"  + mark + "----->Tab" + titles[curTab]);
        switch (position){
            case 0:
                ToastUtils.showShort(OilActivity.this,"选择了 销量最高");
                setDatas(datas,1);
                adapters.notifyDataSetChanged();
                break;
            case 1:
                ToastUtils.showShort(OilActivity.this,"选择了 销量从低到高");
                setDatas(datas,2);
                adapters.notifyDataSetChanged();
                break;
            case 2:
                ToastUtils.showShort(OilActivity.this,"选择了 销量从高到底");
                setDatas(datas,3);
                adapters.notifyDataSetChanged();
                break;
            case 3:
                ToastUtils.showShort(OilActivity.this,"选择了 评价最好");
                setDatas(datas,4);
                adapters.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    /**
     * 排序以后下载数据
     * @param datas
     */
    private void setDatas(List<String> datas, int count){
        if(datas.size() != 0){
            datas.clear();
        }
        for(int i = 0 ; i < count ;i++){
            datas.add("" + "");
        }

    }

    private void setPopLeftTextColor(int position){
        TextView textView = null;
        RelativeLayout relativeLayout = null;
        for(int i = 0 , len = textViews.size() ; i< len ; i++){
            if( position == i){
                textView = textViews.get(i);
                relativeLayout = relativeLayouts.get(i);
                if(textView != null && relativeLayout != null){
                    textView.setTextColor(Color.rgb(252,92,23));
                    relativeLayout.setVisibility(View.VISIBLE);

                }
            }else{
                textView = textViews.get(i);
                relativeLayout = relativeLayouts.get(i);
                if(textView != null && relativeLayout != null){
                    textView.setTextColor(Color.rgb(57,57,57));
                    relativeLayout.setVisibility(View.GONE);
                }
            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ////////////////二维码返回/////////////
        if(requestCode == Contrants.captureActErWei &&  RESULT_OK == resultCode){
            if(data != null){
                ToastUtils.showShort(OilActivity.this,data.getStringExtra("eResult"));
            }
        }
    }
}
