package com.farmhulian.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.farmhulian.base.BaseActivity;
import com.farmhulian.utils.ToastUtils;
import com.farmhulian.views.NavTopView;

/**
 * 立即购买
 *
 * @author 谭杰栖
 */
public class NowPayActivity extends BaseActivity {
    private NavTopView nowPay_topView;
    private ImageView iv_back;  //返回键
    private TextView tv_title;  //标题键
    private AllListener listener = new AllListener();
    private LinearLayout ll_buy_address;  //编辑地址
    private TextView tv_buyer_name, tv_buyer_phone, tv_buyer_address;//购买人姓名,电话，地址
    private LinearLayout ll_buyer_buyGoodsDetail;  //购买物品信息，点击展示具体信息
    private ImageView iv_buyer_goodsImg;  //购买物品图片
    private TextView tv_buyer_goodsName, tv_buyer_goodsPrice, tv_buyer_goodsNum;   //购买物品名字，价格，数量
    private ImageView iv_reduceGoodsNum, iv_addGoodsNum;  //增加立即购买商品数量
    private TextView tv_addOrRedGoodsNum;  //加减键中间的数量
    private int buyNum;  //购买数量
    private EditText et_buyer_liuyan;  //买家留言
    private TextView tv_end_buyNum, tv_end_totalPrice;  //最后购买的数量和价格
    private TextView tv_pay_price;  //确定键 上面的总价
    private Button btn_now_payOk;  //购买键
    private RelativeLayout rl_store_choice_peisong;  //选择配送
    private View popupView;  //PopupWindow的布局
    private PopupWindow popupWindow;  //一个PopuWindow
    private TextView tv_pop_show;
    private Button btn_sure_sonWays;  //确定配送方式
    private CheckBox cb_store_choice1, cb_store_choice2,cb_store_choice3;  //两个选择，一个农场直邮，一个用户自提，商家配送
    private String endWaysChoice = "商家配送";  //最终选择的
    private TextView tv_songWays;  //配送方式
    private String choiceAddress = "",choiceName = "",choicePhone = "";   //最终选择的地址,名字，电话

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_now_pay);
    }

    @Override
    public void initFindView() {
        tv_pop_show = (TextView) findViewById(R.id.tv_pop_show);
        nowPay_topView = (NavTopView) findViewById(R.id.nowPay_topView);
        iv_back = nowPay_topView.getLeftView();
        tv_title = nowPay_topView.getTitleView();
        ll_buy_address = (LinearLayout) findViewById(R.id.ll_buy_address);

        //购买人信息，姓名电话地址
        tv_buyer_name = (TextView) findViewById(R.id.tv_buyer_name);
        tv_buyer_phone = (TextView) findViewById(R.id.tv_buyer_phone);
        tv_buyer_address = (TextView) findViewById(R.id.tv_buyer_address);

        //购买物品信息,详细介绍，图片，名字，价格，数量
        ll_buyer_buyGoodsDetail = (LinearLayout) findViewById(R.id.ll_buyer_buyGoodsDetail);
        iv_buyer_goodsImg = (ImageView) findViewById(R.id.iv_buyer_goodsImg);
        tv_buyer_goodsName = (TextView) findViewById(R.id.tv_buyer_goodsName);
        tv_buyer_goodsPrice = (TextView) findViewById(R.id.tv_buyer_goodsPrice);
        tv_buyer_goodsNum = (TextView) findViewById(R.id.tv_buyer_goodsNum);

        //编辑购买物品信息  加减键,数量
        iv_reduceGoodsNum = (ImageView) findViewById(R.id.iv_reduceGoodsNum);
        iv_addGoodsNum = (ImageView) findViewById(R.id.iv_addGoodsNum);
        tv_addOrRedGoodsNum = (TextView) findViewById(R.id.tv_addOrRedGoodsNum);

        //买家留言
        et_buyer_liuyan = (EditText) findViewById(R.id.et_buyer_liuyan);

        //合计价格
        tv_end_buyNum = (TextView) findViewById(R.id.tv_end_buyNum);
        tv_end_totalPrice = (TextView) findViewById(R.id.tv_end_totalPrice);

        //确定键的总价
        tv_pay_price = (TextView) findViewById(R.id.tv_pay_price);

        //购买键
        btn_now_payOk = (Button) findViewById(R.id.btn_now_payOk);

        //配送选择
        rl_store_choice_peisong = (RelativeLayout) findViewById(R.id.rl_store_choice_peisong);


        //PopupWindow上面的控件
        popupView = LayoutInflater.from(this).inflate(R.layout.store_pei_popuwindow_layout, null);
        //配送确定
        btn_sure_sonWays = (Button) popupView.findViewById(R.id.btn_sure_sonWays);
        cb_store_choice1 = (CheckBox) popupView.findViewById(R.id.cb_store_choice1);
        cb_store_choice2 = (CheckBox) popupView.findViewById(R.id.cb_store_choice2);
        cb_store_choice3 = (CheckBox) popupView.findViewById(R.id.cb_store_choice3);
        tv_songWays = (TextView) findViewById(R.id.tv_songWays);


    }

    @Override
    public void achieveProgress() {
        tv_title.setText("确认订单");
        setAllListener();
        buyNum = Integer.valueOf(tv_addOrRedGoodsNum.getText().toString());  //获取输入框内容，默认1

        //初始化弹出框
        initPopuWindow();

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
        popupWindow.getBackground().setAlpha(170);
        //popupWindow的点击事件
        //popupWindow的点击事件
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //popupWindow.getBackground().setAlpha(0);
            }
        });
    }


    /**
     * 添加所有的监听事件
     */
    private void setAllListener() {
        iv_back.setOnClickListener(listener);
        ll_buy_address.setOnClickListener(listener);
        ll_buyer_buyGoodsDetail.setOnClickListener(listener);
        iv_reduceGoodsNum.setOnClickListener(listener);
        iv_addGoodsNum.setOnClickListener(listener);
        btn_now_payOk.setOnClickListener(listener);
        rl_store_choice_peisong.setOnClickListener(listener);
        btn_sure_sonWays.setOnClickListener(listener);
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

    class AllListener implements View.OnClickListener {
        Intent intent;
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.nav_top_left: //返回
                    finish();
                    break;
                case R.id.ll_buy_address:  //编辑地址
                    //TODO地址添加，需要返回选择的数据
                    intent = new Intent(NowPayActivity.this,ChoiceAddressActivity.class);
                    intent.putExtra(ContrantsF.CHOICE_WHICH_NOW_PAY_ADDRESS_OR_ADDRESS_MANAGER,ContrantsF.CHOICE_ADDRESS_PART);
                    startActivityForResult(intent, ContrantsF.CHOICE_ADDRESS);
                    break;
                case R.id.ll_buyer_buyGoodsDetail:  //购买的物品信息
                    startActivity(new Intent(NowPayActivity.this,GoodDetailsActivity.class));
                    break;
                case R.id.iv_reduceGoodsNum:  //减少键
                    resudeGoodNum();
                    break;
                case R.id.iv_addGoodsNum:  //增加键
                    addGoodNum();
                    break;
                case R.id.btn_now_payOk:  //购买键
                    ToastUtils.showShort(NowPayActivity.this, "购买");
                    break;
                case R.id.rl_store_choice_peisong:  //配送选择
                    showPopup();
                    break;
                case R.id.btn_sure_sonWays:  //配送确定
                    achiSureClick();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 显示弹出框
     */
    private void showPopup() {
        popupWindow.showAsDropDown(tv_pop_show);
        //popupWindow.getBackground().setAlpha(170);
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
     * 确定键
     */
    private void achiSureClick() {


        if (!cb_store_choice1.isChecked() && !cb_store_choice2.isChecked() && !cb_store_choice3.isChecked()) {
            ToastUtils.showShort(NowPayActivity.this, "你还没有选择哦，请选择一种配送方式");
            return;
        }
        tv_songWays.setText(endWaysChoice);
        popupWindow.dismiss();

    }

    /**
     * 增加键的点击事件
     */
    private void addGoodNum() {
        //buyNum = Integer.valueOf(tv_addOrRedGoodsNum.getText().toString());
        if (buyNum >= 99) {
            ToastUtils.showShort(NowPayActivity.this, "最多一次购买99件哦，建议分批购买");
            return;
        }
        buyNum++;
        tv_addOrRedGoodsNum.setText(buyNum + "");
    }

    /**
     * 减少键的点击事件
     */
    private void resudeGoodNum() {
        //buyNum = Integer.valueOf(tv_addOrRedGoodsNum.getText().toString());
        if (buyNum <= 1) {
            ToastUtils.showShort(NowPayActivity.this, "不能在减了哦，最少一件哦");
            return;
        }
        buyNum--;
        tv_addOrRedGoodsNum.setText(buyNum + "");
    }


    /**
     * 跳转界面的返回值接收
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 选择地址的
         */
        if(ContrantsF.CHOICE_ADDRESS == requestCode && RESULT_OK == resultCode){
            if(data != null){
                choiceAddress = data.getStringExtra(ContrantsF.CHOICE_WHICH_ADDRESS);  //地址返回回来
                choiceName = data.getStringExtra(ContrantsF.CHOICE_WHICH_ADDRESS_NAME); //名字
                choicePhone = data.getStringExtra(ContrantsF.CHOICE_WHICH_ADDRESS_PHONE);  //电话
                tv_buyer_address.setText(choiceAddress);
                tv_buyer_name.setText(choiceName);
                tv_buyer_phone.setText(choicePhone);

            }
        }
    }
}
