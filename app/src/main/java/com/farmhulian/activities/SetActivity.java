package com.farmhulian.activities;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.farmhulian.R;
import com.farmhulian.base.BaseActivity;
import com.farmhulian.utils.DataCleanManager;
import com.farmhulian.utils.ToastUtils;

/**
 * 设置界面
 */
public class SetActivity extends BaseActivity {
    private RelativeLayout rl_clean_cache,rl_app_share;  //清除缓存,应用分享
    private TextView tv_cache_size;  //当前缓存大小
    private AllListener listener = new AllListener();
    private AlertDialog dialog;
    private Button btn_clear_no,btn_clear_ok;//取消键，和确定键
    private View cusTomView;
    private ImageView iv_set_back;  //返回键
    private Button btn_exit;  //确认退出

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_set);
    }

    @Override
    public void initFindView() {
        tv_cache_size = (TextView) findViewById(R.id.tv_cache_size);
        rl_clean_cache = (RelativeLayout) findViewById(R.id.rl_clean_cache);
        cusTomView = LayoutInflater.from(this).inflate(R.layout.set_clear_cache_layout,null);
        btn_clear_no = (Button) cusTomView.findViewById(R.id.btn_clear_no);
        btn_clear_ok = (Button) cusTomView.findViewById(R.id.btn_clear_ok);
        rl_app_share = (RelativeLayout) findViewById(R.id.rl_app_share);
        iv_set_back = (ImageView) findViewById(R.id.iv_set_back);
        btn_exit = (Button) findViewById(R.id.btn_exit);
    }

    @Override
    public void achieveProgress() {
        setTotalCacheSize();
        setAllListener();
        initDialog();
    }

    private void setAllListener() {
        rl_clean_cache.setOnClickListener(listener);
        btn_clear_ok.setOnClickListener(listener);
        btn_clear_no.setOnClickListener(listener);
        rl_app_share.setOnClickListener(listener);
        iv_set_back.setOnClickListener(listener);
        btn_exit.setOnClickListener(listener);
    }

    class AllListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rl_clean_cache:  //清除缓存
                    dialog.show();
                    break;
                case R.id.btn_clear_ok:  //清除缓存
                    clearCaches();
                    break;
                case R.id.btn_clear_no:  //不清除
                    dialog.hide();
                    break;
                case R.id.rl_app_share: //分享
                    showShare();
                    break;
                case R.id.iv_set_back:  //返回键
                    finish();
                    break;
                case R.id.btn_exit:  // 退出键
                    ToastUtils.showShort(SetActivity.this,"退出登录");
                    break;
                default:
                    break;

            }
        }
    }

    /**
     * 清除缓存
     */
    private void clearCaches() {
        DataCleanManager.clearAllCache(getApplicationContext());
        ToastUtils.showShort(SetActivity.this,"清除成功");
        tv_cache_size.setText("0KB");
        dialog.hide();
    }

    /**
     * 初始化一个Dialog
     */
    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置属性
        dialog = builder.setView(cusTomView).create();
        dialog.setCanceledOnTouchOutside(true);
    }

    /**
     * 设置缓存大小
     */
    private void setTotalCacheSize() {
        try {
            tv_cache_size.setText(DataCleanManager.getTotalCacheSize(getApplicationContext()));

        } catch (Exception e) {
            Log.i("info","=====SetActivity===" + e.toString());
            e.printStackTrace();
        }
    }
}
