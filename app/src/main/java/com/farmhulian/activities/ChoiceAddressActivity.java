package com.farmhulian.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.farmhulian.ContrantsF;
import com.farmhulian.R;
import com.farmhulian.adapters.ChoiceAddListViewAdapter;
import com.farmhulian.base.BaseActivity;
import com.farmhulian.utils.DensityUtils;
import com.farmhulian.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择收货地址的一个Activity
 */
public class ChoiceAddressActivity extends BaseActivity {

    private SwipeMenuListView lv_choice_listView;
    private ChoiceAddListViewAdapter listViewAdapter;
    private List<String> datas = new ArrayList<>();
    private AllListener listener = new AllListener();
    private TextView tv_add_address;  //添加地址
    private ImageView iv_choice_back;  //返回
    private SwipeMenuItem cancelItem,deleteItem,changeItem; //一个删除菜单，一个取消菜单
    private boolean isAddressManager = false;  //是否是碎片里面的地址管理
    private String whichAddressPart;
    private TextView tv_title; //标题
    private View deleteView;  //一个删除的Dialog的布局
    private Button btn_delete_no,btn_delete_ok;  //删除的Dialog上面按钮
    private AlertDialog deleteDialog;
    private TextView tv_address_delete;  //dialog上面的提示消息
    private int changePosition = 0;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_choice_address);
    }

    @Override
    public void initFindView() {
        lv_choice_listView = (SwipeMenuListView) findViewById(R.id.lv_choice_listView);
        tv_add_address = (TextView) findViewById(R.id.tv_add_address);
        iv_choice_back = (ImageView) findViewById(R.id.iv_choice_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        deleteView = LayoutInflater.from(this).inflate(R.layout.address_delete_layout,null);
        btn_delete_no = (Button) deleteView.findViewById(R.id.btn_delete_no);
        btn_delete_ok = (Button) deleteView.findViewById(R.id.btn_delete_ok);
        tv_address_delete = (TextView) deleteView.findViewById(R.id.tv_address_delete);
    }

    @Override
    public void achieveProgress() {
        //判断是碎片里面的地址管理还是购买信息里面的选择地址，因为布局一样
        judgeWhichAddressPart();
        setAllListener();
        listViewAdapter = new ChoiceAddListViewAdapter(this, datas);
        lv_choice_listView.setAdapter(listViewAdapter);
        //创建ListView侧滑菜单
        initListViewMenu();
        initDownLoadData();
        //初始化一个删除的dialog
        initDeleteDialog();


    }

    /**
     * 判断是MeFragment还是NowPayActivity跳过来的 ，一个是选择地址，一个是地址管理
     */
    private void judgeWhichAddressPart() {
        //取出来判断是购买信息里面的地址还是我的碎片里面的，因为一个要有返回值一个没有，而且还要区分标题等操作
        Intent intent = getIntent();
        whichAddressPart = intent.getStringExtra(ContrantsF.CHOICE_WHICH_NOW_PAY_ADDRESS_OR_ADDRESS_MANAGER);
        if(ContrantsF.CHOICE_ADDRESS_PART.equals(whichAddressPart)){//这个是选择地址
            isAddressManager = false;
        }else if(ContrantsF.ADDRESS_MANAGER_PART.equals(whichAddressPart)){  //这个是碎片里面的地址管理
            isAddressManager = true;
        }
        if(isAddressManager){
            tv_title.setText("地址管理");
        }else{
            tv_title.setText("选择收货地址");
        }

    }

    /**
     * 创建listview侧滑的菜单
     */
    private void initListViewMenu() {
        //创建侧滑菜单
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                //创建修改菜单
                changeItem = new SwipeMenuItem(
                        getApplicationContext());
                //设置背景
                changeItem.setBackground(new ColorDrawable(Color.rgb(246,115,0)));
                //设置宽度
                changeItem.setWidth(DensityUtils.dp2px(ChoiceAddressActivity.this, 90));
                // 设置名字
                changeItem.setTitle("修改");
                // 设置名字的字体大小
                changeItem.setTitleSize(18);
                //设置字体的颜色
                changeItem.setTitleColor(Color.WHITE);
                // 添加到菜单上去
                menu.addMenuItem(changeItem);


                //创建删除的菜单按钮
                deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                //设置背景
                deleteItem.setBackground(new ColorDrawable(Color.rgb(8,170,
                        59)));
                //设置宽度
                deleteItem.setWidth(DensityUtils.dp2px(ChoiceAddressActivity.this, 90));
                // 设置名字
                deleteItem.setTitle("删除");
                // 设置名字的字体大小
                deleteItem.setTitleSize(18);
                //设置字体的颜色
                deleteItem.setTitleColor(Color.WHITE);
                // 添加到菜单上去
                menu.addMenuItem(deleteItem);


                // 创建取消菜单
                cancelItem = new SwipeMenuItem(
                        getApplicationContext());

                cancelItem.setTitle("取消");

                // 设置名字的字体大小
                cancelItem.setTitleSize(18);
                //设置字体的颜色
                cancelItem.setTitleColor(Color.WHITE);
                // 设置背景
                cancelItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // 设置宽度

                cancelItem.setWidth(DensityUtils.dp2px(ChoiceAddressActivity.this, 90));
                //添加到菜单上去
                menu.addMenuItem(cancelItem);



            }
        };

        //创建菜单
        lv_choice_listView.setMenuCreator(creator);
    }


    /**
     * 完成所有的事件监听
     */
    private void setAllListener() {
        if(!isAddressManager) {   //只有购买里面的地址选择才有点击事件
            lv_choice_listView.setOnItemClickListener(listener);
        }
        tv_add_address.setOnClickListener(listener);
        iv_choice_back.setOnClickListener(listener);
        lv_choice_listView.setOnMenuItemClickListener(listener);
        btn_delete_ok.setOnClickListener(listener);
        btn_delete_no.setOnClickListener(listener);
    }


    float x ;
    float y;

    /**
     * 模拟下数据
     */
    private void initDownLoadData() {
        for (int i = 0; i < 9; i++) {
            datas.add("第" + i +"行数据");
        }
        listViewAdapter.notifyDataSetChanged();
    }


    /**
     * 删除
     */
    private void deleteAddress() {
        datas.remove(changePosition);
        listViewAdapter.notifyDataSetChanged();
        ToastUtils.showShort(ChoiceAddressActivity.this,"删除成功，被删除的数据是第" + changePosition +"行数据");
        deleteDialog.hide();
    }

    /**
     * 显示dialog
     * position: //这个是当前要删除数据的位置
     */
    private void showDeleteDialog(int position) {
        changePosition = position;
        tv_address_delete.setText("你确定要删除第" + position + "位置的数据吗?");
        deleteDialog.show();
    }

    /**
     * 初始化Dialog
     */
    private void initDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChoiceAddressActivity.this);
        deleteDialog = builder.setView(deleteView).create();
    }


    class AllListener implements  View.OnClickListener, AdapterView.OnItemClickListener, SwipeMenuListView.OnMenuItemClickListener {

        /**
         * 按钮的点击事件
         */
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_add_address:   //添加地址
                    startActivity(new Intent(ChoiceAddressActivity.this, AddAddressActivity.class));
                    break;
                case R.id.iv_choice_back:  //返回键
                    finish();
                    break;
                case R.id.btn_delete_ok: //dialog里面删除键
                    deleteAddress();
                    break;
                case R.id.btn_delete_no:  //dialog里面取消键
                    if(deleteDialog.isShowing()){
                        deleteDialog.hide();
                    }
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
            //直接把这个位置的数据返回回去
            //Log.i("info","===点了=" + position);
            Intent intent = new Intent();
            intent.putExtra(ContrantsF.CHOICE_WHICH_ADDRESS,datas.get(position));
            intent.putExtra(ContrantsF.CHOICE_WHICH_ADDRESS_NAME,position +"行名字");
            intent.putExtra(ContrantsF.CHOICE_WHICH_ADDRESS_PHONE,position+"行电话");
            setResult(RESULT_OK,intent);
            finish();
        }

        /**
         * 侧滑ListView的删除效果
         */
        @Override
        public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
            switch (index) {
                case 0:  //修改
                    ToastUtils.showShort(ChoiceAddressActivity.this,"你要修改第" + position +"行数据");
                    startActivity(new Intent(ChoiceAddressActivity.this,AddAddressActivity.class));
                    break;
                case 1: //删除
                    showDeleteDialog(position);
                    break;
                case 2:  //取消
                    break;
            }
            return false;
        }
    }




}
