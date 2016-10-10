package com.farmhulian.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.farmhulian.ContrantsF;
import com.farmhulian.R;
import com.farmhulian.base.BaseActivity;
import com.farmhulian.utils.ToastUtils;
import com.farmhulian.views.NavTopView;

import static com.farmhulian.ContrantsF.EVA_TYPE_FUWU;

/**
 * 评价的Activity
 */
public class EvaluateActivity extends BaseActivity {
    private TextView tv_gongying_name;  //供应商名字
    private AllListener listener = new AllListener();
    private LinearLayout ll_fuwu_taidu,ll_daohuo_sudu,ll_manyidu;  //服务态度的星星,到货速度星星,商品满意度星星
    private ImageView firstStarView;
    private ImageView iv_back;
    private int serviceStartNum = -1;  //服务态度的星星记录
    private int daohuoStartNum = -1;  //到货速度的星星记录
    private int manyiStartNum = -1;   //满意度的星星记录
    private RadioGroup rg_eva;  //商品包装，三种，完好，基本完整，破损严重
    private int baozhuangNum = -1;  //包装的记录
    private EditText et_advice; //意见
    private Button btn_evaluate_upload;  //提交按钮
    private String[] manyiType = {"完好","基本完整","破损严重"};
    private TextView tv_farm_name,tv_goodName;  //农场考评,商品评价名字
    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_evaluate);
    }

    @Override
    public void initFindView() {
        tv_gongying_name = (TextView) findViewById(R.id.tv_gongying_name);
        ll_fuwu_taidu = (LinearLayout) findViewById(R.id.ll_fuwu_taidu);
        ll_manyidu = (LinearLayout) findViewById(R.id.ll_manyidu);
        ll_daohuo_sudu = (LinearLayout) findViewById(R.id.ll_daohuo_sudu);
        NavTopView evaluate_topView = (NavTopView) findViewById(R.id.evaluate_topView);
        tv_goodName = (TextView) findViewById(R.id.tv_goodName);
        tv_farm_name = (TextView) findViewById(R.id.tv_farm_name);
        btn_evaluate_upload = (Button) findViewById(R.id.btn_evaluate_upload);
        et_advice = (EditText) findViewById(R.id.et_advice);
        rg_eva = (RadioGroup) findViewById(R.id.rg_eva);
        iv_back = evaluate_topView.getLeftView();
        evaluate_topView.getTitleView().setText("评价");

    }

    @Override
    public void achieveProgress() {
        setAllListener();

    }
    /**
     * 添加所有的事件监听
     */
    private void setAllListener() {
        iv_back.setOnClickListener(listener);
        rg_eva.setOnCheckedChangeListener(listener);
        btn_evaluate_upload.setOnClickListener(listener);
        achieveStarsClick(ll_fuwu_taidu, EVA_TYPE_FUWU);//服务态度星星
        achieveStarsClick(ll_daohuo_sudu,ContrantsF.EVA_TYPE_DAOHUO);//到货速度星星
        achieveStarsClick(ll_manyidu,ContrantsF.EVA_TYPE_MANYI); //商品满意度的星星
    }
    /**
     * 完成星星的评价，并且要记录下星星个数
     * @param ll    星星布局
     * @param startNumMark   星星个数
     */
    private void achieveStarsClick(final LinearLayout ll, final int startNumMark) {
        for(int i = 0,len = ll.getChildCount(); i < len ; i++){
            firstStarView = (ImageView) ll.getChildAt(i);
            firstStarView.setClickable(true);
            final int curI = i;
            firstStarView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //记录星星颗数
                    if(ContrantsF.EVA_TYPE_FUWU == startNumMark){ //服务态度
                        serviceStartNum = curI;
                    }else if(ContrantsF.EVA_TYPE_DAOHUO== startNumMark){ //到货速度
                        daohuoStartNum = curI;
                    }else if(ContrantsF.EVA_TYPE_MANYI == startNumMark){ //商品满意度
                        manyiStartNum = curI;
                    }
                    //前面的星星全部选中，后面的不选中
                    for(int j = 0 , startCount = ll.getChildCount(); j < startCount; j++){
                        firstStarView = (ImageView) ll.getChildAt(j);
                        if( j <= curI){
                            firstStarView.setImageResource(R.mipmap.evaluate_star_s);
                        }else{
                            firstStarView.setImageResource(R.mipmap.evaluate_star_n);
                        }
                    }
                }
            });
        }
    }

    /**
     * 提交按钮
     */
    private void upload() {
        if( -1 == serviceStartNum){
            ToastUtils.showShort(this,"请评价服务态度");
        }else if( -1 == daohuoStartNum){
            ToastUtils.showShort(this,"请评价到货速度");
        }else if(-1 == baozhuangNum){
            ToastUtils.showShort(this,"请评价商品包装");
        }else if( -1 == manyiStartNum){
            ToastUtils.showShort(this,"请评价商品满意度");
        }else{
            ToastUtils.showLong(this,"供应商：" + tv_gongying_name.getText().toString()
            +",服务态度:" + (serviceStartNum + 1) + ",到货速度:" + (daohuoStartNum + 1) + "\n农场：" + tv_farm_name.getText().toString()
            + "，商品包装：" + manyiType[baozhuangNum] + "\n商品：" + tv_goodName.getText().toString() + "商品满意度：" + (manyiStartNum + 1)
             + "\n意见" + et_advice.getText().toString());
        }
    }



    class AllListener implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.nav_top_left: //返回键
                    finish();
                    break;
                case R.id.btn_evaluate_upload:  //提价按钮
                    upload();
                default:
                    break;
            }
        }

        /**
         * 商品包装的RadioGroup的监听事件
         */
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton rb = null;
            for(int i = 0, len = group.getChildCount(); i < len ;i++){
                rb = (RadioButton) group.getChildAt(i);
                if(rb.isChecked()){
                    baozhuangNum = i;
                }
            }
        }
    }



}
