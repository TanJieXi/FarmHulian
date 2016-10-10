package com.farmhulian.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.farmhulian.ContrantsF;
import com.farmhulian.R;
import com.farmhulian.adapters.SureListViewAdapter;
import com.farmhulian.base.BaseActivity;
import com.farmhulian.utils.ToastUtils;
import com.farmhulian.views.NavTopView;
import com.farmhulian.views.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 确认订单
 */
public class SurePayOrderActivity extends BaseActivity {
    private NoScrollListView sure_listview;
    private SureListViewAdapter listViewAdapter;
    private AllListener listener = new AllListener();
    private List<String> datas = new ArrayList<>();
    private NavTopView surePay_topView;
    private ImageView sure_back;
    private LinearLayout ll_sure_buy_address;  //选择地址
    private String choiceAddress,choiceAddressName,choiceAddressPhone;  //地址,名字，电话
    private TextView tv_sure_address,tv_sure_name,tv_sure_phone;  //确定地址,姓名，电话
    private RelativeLayout rl_sure_choice_peisong;  //选择配送方式
    private TextView tv_pop_show1;//一个pop弹出位置
    private View popupView;  //PopupWindow的布局
    private PopupWindow popupWindow;  //一个PopuWindow
    private CheckBox cb_store_choice1, cb_store_choice2,cb_store_choice3;  //两个选择，一个农场直邮，一个用户自提，商家配送
    private String endWaysChoice = "商家配送";  //最终选择的
    private TextView tv_sure_songWays;  //配送方式
    private Button btn_sure_sonWays;  //确定配送方式
    private Button btn_now_payOk;  //确认购买
    private EditText et_buyer_liuyan;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sure_pay_order);
    }

    @Override
    public void initFindView() {
        surePay_topView = (NavTopView) findViewById(R.id.surePay_topView);
        surePay_topView.getTitleView().setText("确定订单");
        sure_listview = (NoScrollListView) findViewById(R.id.sure_listview);
        sure_back = surePay_topView.getLeftView();
        ll_sure_buy_address = (LinearLayout) findViewById(R.id.ll_sure_buy_address);
        tv_sure_address = (TextView) findViewById(R.id.tv_sure_address);
        tv_sure_name = (TextView) findViewById(R.id.tv_sure_name);
        tv_sure_phone = (TextView) findViewById(R.id.tv_sure_phone);
        rl_sure_choice_peisong = (RelativeLayout) findViewById(R.id.rl_sure_choice_peisong);
        tv_pop_show1  = (TextView) findViewById(R.id.tv_pop_show1);

        //留言
        et_buyer_liuyan = (EditText) findViewById(R.id.et_buyer_liuyan);

        //PopupWindow上面的控件
        popupView = LayoutInflater.from(this).inflate(R.layout.store_pei_popuwindow_layout, null);
        //配送确定
        btn_sure_sonWays = (Button) popupView.findViewById(R.id.btn_sure_sonWays);
        cb_store_choice1 = (CheckBox) popupView.findViewById(R.id.cb_store_choice1);
        cb_store_choice2 = (CheckBox) popupView.findViewById(R.id.cb_store_choice2);
        cb_store_choice3 = (CheckBox) popupView.findViewById(R.id.cb_store_choice3);
        tv_sure_songWays = (TextView) findViewById(R.id.tv_sure_songWays);

        //购买
        btn_now_payOk = (Button) findViewById(R.id.btn_now_payOk);

    }


    @Override
    public void achieveProgress() {
        setAllListener();
        initPopuWindow();
        listViewAdapter = new SureListViewAdapter(this,datas);
        sure_listview.setAdapter(listViewAdapter);

        downLoadData();

    }

    /**
     * 模拟网络下载数据
     */
    private void downLoadData() {
        for(int i = 0 ; i < 3 ; i++){
            datas.add(""+i);
        }
            listViewAdapter.notifyDataSetChanged();
    }

    /**
     * 完成所有监听事件
     */
    private void setAllListener() {
        sure_back.setOnClickListener(listener);
        ll_sure_buy_address.setOnClickListener(listener);
        sure_listview.setOnItemClickListener(listener);
        rl_sure_choice_peisong.setOnClickListener(listener);
        btn_sure_sonWays.setOnClickListener(listener);
        btn_now_payOk.setOnClickListener(listener);

        cb_store_choice1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    endWaysChoice = "农场直邮";
                    cb_store_choice2.setChecked(false);
                    cb_store_choice3.setChecked(false);
                }
            }
        });
        cb_store_choice2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    endWaysChoice = "用户自提";
                    cb_store_choice1.setChecked(false);
                    cb_store_choice3.setChecked(false);
                }
            }
        });
        cb_store_choice3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    endWaysChoice = "商家配送";
                    cb_store_choice1.setChecked(false);
                    cb_store_choice2.setChecked(false);
                }
            }
        });
    }

    class AllListener implements View.OnClickListener, AdapterView.OnItemClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.nav_top_left: //返回键
                    finish();
                    break;
                case R.id.ll_sure_buy_address:  //选择地址
                    Intent intent = new Intent(SurePayOrderActivity.this,ChoiceAddressActivity.class);
                    startActivityForResult(intent,ContrantsF.CHOICE_SURE_ADDRESS);
                    break;
                case R.id.rl_sure_choice_peisong:  //选择配送方式
                    showPopup();
                    break;
                case R.id.btn_sure_sonWays:  //pop上面的配送确定
                    achiSureClick();
                    break;
                case R.id.btn_now_payOk:  //确定购买
                    ToastUtils.showShort(SurePayOrderActivity.this,"支付");
                    break;
                default:
                    break;

            }
        }

        /**
         * ListView的点击事件
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.i("info","====点了===");
            startActivity(new Intent(SurePayOrderActivity.this,GoodDetailsActivity.class));
        }
    }

    /**
     * 配送确定
     */
    private void achiSureClick() {
        if (!cb_store_choice1.isChecked() && !cb_store_choice2.isChecked() && !cb_store_choice3.isChecked()) {
            ToastUtils.showShort(SurePayOrderActivity.this, "你还没有选择哦，请选择一种配送方式");
            return;
        }
        tv_sure_songWays.setText(endWaysChoice);
        popupWindow.dismiss();
    }

    private void showPopup() {
        popupWindow.showAsDropDown(tv_pop_show1);
        popupWindow.getBackground().setAlpha(170);
        if ("农场直邮".equals(endWaysChoice)) {
            cb_store_choice1.setChecked(true);
            cb_store_choice2.setChecked(false);
            cb_store_choice3.setChecked(false);
        }else if("用户自提".equals(endWaysChoice)){
            cb_store_choice1.setChecked(false);
            cb_store_choice2.setChecked(true);
            cb_store_choice3.setChecked(false);
        }else if("商家配送".equals(endWaysChoice)){
            cb_store_choice1.setChecked(false);
            cb_store_choice2.setChecked(false);
            cb_store_choice3.setChecked(true);
        }
    }



    /**
     * 初始化配送选择的弹出框
     */
    private void initPopuWindow() {

        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.getBackground().setAlpha(150);
        //popupWindow的点击事件
        //popupWindow的点击事件
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.getBackground().setAlpha(0);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 选择地址的
         */
        if(ContrantsF.CHOICE_SURE_ADDRESS == requestCode && RESULT_OK == resultCode){
            if(data != null){
                choiceAddress = data.getStringExtra(ContrantsF.CHOICE_WHICH_ADDRESS);  //地址返回回来
                choiceAddressName = data.getStringExtra(ContrantsF.CHOICE_WHICH_ADDRESS_NAME);  //名字返回回来
                choiceAddressPhone = data.getStringExtra(ContrantsF.CHOICE_WHICH_ADDRESS_PHONE);  //电话返回回来
                tv_sure_address.setText(choiceAddress);
                tv_sure_name.setText(choiceAddressName);
                tv_sure_phone.setText(choiceAddressPhone);

            }
        }

    }
}
