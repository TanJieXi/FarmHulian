package com.farmhulian.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.farmhulian.ContrantsF;
import com.farmhulian.R;
import com.farmhulian.activities.AboutOutActivity;
import com.farmhulian.activities.AdviceFeedBackActivity;
import com.farmhulian.activities.AllOrdersActivity;
import com.farmhulian.activities.ChoiceAddressActivity;
import com.farmhulian.activities.CollectionActivity;
import com.farmhulian.activities.JoinActivity;
import com.farmhulian.activities.LoginActivity;
import com.farmhulian.activities.SetActivity;
import com.farmhulian.base.BaseFragment;
import com.farmhulian.utils.CommonUtils;
import com.farmhulian.utils.ToastUtils;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * 我的碎片
 *  @author 谭杰栖
 */
public class MeFragment extends BaseFragment {
    private View curView;
    private LinearLayout me_ll_submit; //登录
    private AllListener listener = new AllListener();
    private ImageView iv_set;  //设置键
    private RoundedImageView me_userIcon;  //头像
    private TextView me_userName;  //名字
    private RelativeLayout me_allOrders; //全部订单
    //我的余额，我的积分，我的关注，新人特权，我的足迹，收获地址，客服电话，意见反馈，关于我们,加盟合作,我的红包
    private RelativeLayout rl_YuE, rl_jifen, rl_guanzhu, rl_xinren, rl_zuji, rl_dizhi, rl_kefu, rl_advice, rl_about, rl_jiameng,rl_hongbao;
    //客服打电话的一个dialog界面View
    private View serviceView;
    private AlertDialog dialog;
    private Button btn_call_no,btn_call_ok; //确认拨打和不拨打的按钮
    private TextView tv_kefu_phone;  //客服电话
    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        curView = inflater.inflate(R.layout.fragment_main_me, null);
        return curView;
    }

    @Override
    protected void initFindView() {
        me_ll_submit = (LinearLayout) curView.findViewById(R.id.me_ll_submit);
        iv_set = (ImageView) curView.findViewById(R.id.iv_set);
        me_userIcon = (RoundedImageView) curView.findViewById(R.id.me_userIcon);
        me_userName = (TextView) curView.findViewById(R.id.me_userName);
        me_allOrders = (RelativeLayout) curView.findViewById(R.id.me_allOrders);
        rl_YuE = (RelativeLayout) curView.findViewById(R.id.rl_YuE);
        rl_hongbao = (RelativeLayout) curView.findViewById(R.id.rl_hongbao);
        rl_jifen = (RelativeLayout) curView.findViewById(R.id.rl_jifen);
        rl_guanzhu = (RelativeLayout) curView.findViewById(R.id.rl_guanzhu);
        rl_xinren = (RelativeLayout) curView.findViewById(R.id.rl_xinren);
        rl_zuji = (RelativeLayout) curView.findViewById(R.id.rl_zuji);
        rl_dizhi = (RelativeLayout) curView.findViewById(R.id.rl_dizhi);
        rl_kefu = (RelativeLayout) curView.findViewById(R.id.rl_kefu);
        rl_advice = (RelativeLayout) curView.findViewById(R.id.rl_advice);
        rl_about = (RelativeLayout) curView.findViewById(R.id.rl_about);
        rl_jiameng = (RelativeLayout) curView.findViewById(R.id.rl_jiameng);
        serviceView = LayoutInflater.from(getActivity()).inflate(R.layout.call_kefu_phone_layout,null);
        btn_call_ok = (Button) serviceView.findViewById(R.id.btn_call_ok);
        btn_call_no = (Button) serviceView.findViewById(R.id.btn_call_no);
        tv_kefu_phone = (TextView) serviceView.findViewById(R.id.tv_kefu_phone);
    }

    @Override
    protected void achieveProgress() {
        setAllListener();
        initKefuDialog();
    }

    /**
     * 初始化客服的电话dialog
     */
    private void initKefuDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //设置属性
        dialog = builder.setView(serviceView).create();
        dialog.setCanceledOnTouchOutside(true);
    }

    /**
     * 设置所有的监听事件
     */
    private void setAllListener() {
        me_ll_submit.setOnClickListener(listener);
        iv_set.setOnClickListener(listener);
        me_allOrders.setOnClickListener(listener);

        rl_YuE.setOnClickListener(listener);
        rl_jifen.setOnClickListener(listener);
        rl_guanzhu.setOnClickListener(listener);
        rl_xinren.setOnClickListener(listener);
        rl_zuji.setOnClickListener(listener);
        rl_dizhi.setOnClickListener(listener);
        rl_kefu.setOnClickListener(listener);
        rl_advice.setOnClickListener(listener);
        rl_about.setOnClickListener(listener);
        rl_jiameng.setOnClickListener(listener);
        rl_hongbao.setOnClickListener(listener);
        btn_call_no.setOnClickListener(listener);
        btn_call_ok.setOnClickListener(listener);
    }


    /**
     * 打电话
     */
    private void callPhone() {
        CommonUtils.beginClickCallPhone(getActivity(),tv_kefu_phone.getText().toString());
    }


    class AllListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.me_ll_submit:  //登录
                    ToastUtils.showShort(getActivity(), "登录");
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    break;
                case R.id.iv_set:  //设置
                    startActivity(new Intent(getActivity(), SetActivity.class));
                    break;
                case R.id.me_allOrders: //全部订单
                    startActivity(new Intent(getActivity(), AllOrdersActivity.class));
                    break;
                case R.id.rl_YuE:  //我的余额
                    ToastUtils.showShort(getActivity(), "余额");
                    break;
                case R.id.rl_guanzhu:  //我的收藏
                    startActivity(new Intent(getActivity(), CollectionActivity.class));
                    break;
                case R.id.rl_hongbao:  //我的红包
                    ToastUtils.showShort(getActivity(), "我的红包");
                    break;
                case R.id.rl_jifen: //我的积分
                    ToastUtils.showShort(getActivity(), "我的积分");
                    break;
                case R.id.rl_xinren:  //新人特权
                    ToastUtils.showShort(getActivity(), "新人");
                    break;
                case R.id.rl_zuji:   //我的足迹
                    ToastUtils.showShort(getActivity(), "足迹");
                    break;
                case R.id.rl_dizhi:  //收货地址
                    Intent intent = new Intent(getActivity(), ChoiceAddressActivity.class);
                    intent.putExtra(ContrantsF.CHOICE_WHICH_NOW_PAY_ADDRESS_OR_ADDRESS_MANAGER,ContrantsF.ADDRESS_MANAGER_PART);
                    startActivity(intent);
                    break;
                case R.id.rl_kefu:  //客服电话
                    dialog.show();
                    break;
                case R.id.rl_advice://意见反馈
                    startActivity(new Intent(getActivity(), AdviceFeedBackActivity.class));
                    break;
                case R.id.rl_about://关于我们
                    startActivity(new Intent(getActivity(), AboutOutActivity.class));
                    break;
                case R.id.rl_jiameng: //加盟合作
                    startActivity(new Intent(getActivity(), JoinActivity.class));
                    break;
                case R.id.btn_call_no:  //不打电话
                    dialog.hide();
                    break;
                case R.id.btn_call_ok:  //确认打电话
                    callPhone();
                    dialog.hide();
                    break;
                default:
                    break;

            }
        }
    }




}
