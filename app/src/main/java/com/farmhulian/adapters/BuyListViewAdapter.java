package com.farmhulian.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.farmhulian.R;
import com.farmhulian.activities.GoodDetailsActivity;
import com.farmhulian.activities.StoreDetailsActivity;
import com.farmhulian.base.BbaseAdapter;
import com.farmhulian.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车中间ListView的
 *  @author 谭杰栖
 */
public class BuyListViewAdapter extends BbaseAdapter<String> {
    private int buyNum = 1;
    private List<CheckBox> checkBoxes = new ArrayList<>();
    private OnItemCheckBoxChangeListener onItemCheckBoxChangeListener;

    public BuyListViewAdapter(Context context, List<String> datas) {
        super(context, datas);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.fragment_buy_listviewitem, null);
            viewHolder.tv_title_good_store = (TextView) convertView.findViewById(R.id.tv_title_good_store);
            viewHolder.tv_goodsName = (TextView) convertView.findViewById(R.id.tv_goodsName);
            viewHolder.tv_goodsPrice = (TextView) convertView.findViewById(R.id.tv_goodsPrice);
            viewHolder.tv_goodsAddress = (TextView) convertView.findViewById(R.id.tv_goodsAddress);
            viewHolder.tv_title_edit = (TextView) convertView.findViewById(R.id.tv_title_edit);
            viewHolder.tv_title_finish = (TextView) convertView.findViewById(R.id.tv_title_finish);
            viewHolder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            viewHolder.tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
            viewHolder.ll_goodsDetailsPart = (LinearLayout) convertView.findViewById(R.id.ll_goodsDetailsPart);
            viewHolder.rl_addOrReducePart = (RelativeLayout) convertView.findViewById(R.id.rl_addOrReducePart);
            viewHolder.cb_goods_choice_n = (CheckBox) convertView.findViewById(R.id.cb_goods_choice_n);
            viewHolder.iv_goods_img = (ImageView) convertView.findViewById(R.id.iv_goods_img);
            viewHolder.iv_reduce = (ImageView) convertView.findViewById(R.id.iv_reduce);
            viewHolder.iv_add = (ImageView) convertView.findViewById(R.id.iv_add);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        /*PicassoUtils.loadImgWithPl(context, "http://p3.so.qhmsg.com/t016ec2cd3a0c2312bd.jpg",R.mipmap.moren_nong,
                R.mipmap.moren_nong,viewHolder.iv_goods_img);*/

        //购买数量
        buyNum = Integer.valueOf(viewHolder.tv_num.getText().toString());


        final ViewHolder finalViewHolder = viewHolder;


        checkBoxes.add(viewHolder.cb_goods_choice_n);


        //编辑按钮的点击事件,要让checked取消选中
        viewHolder.tv_title_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalViewHolder.tv_title_edit.setVisibility(View.GONE);
                finalViewHolder.tv_title_finish.setVisibility(View.VISIBLE);
                finalViewHolder.rl_addOrReducePart.setVisibility(View.VISIBLE);
                finalViewHolder.ll_goodsDetailsPart.setVisibility(View.GONE);
                finalViewHolder.cb_goods_choice_n.setChecked(false);
                finalViewHolder.cb_goods_choice_n.setClickable(false);
            }
        });

        //完成按钮的点击事件
        viewHolder.tv_title_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalViewHolder.tv_title_edit.setVisibility(View.VISIBLE);
                finalViewHolder.tv_title_finish.setVisibility(View.GONE);
                finalViewHolder.rl_addOrReducePart.setVisibility(View.GONE);
                finalViewHolder.ll_goodsDetailsPart.setVisibility(View.VISIBLE);
                finalViewHolder.cb_goods_choice_n.setClickable(true);
            }
        });

        //删除按钮的点击事件
        viewHolder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(context, "点击了删除");
            }
        });

        //圈圈那个CheckBox的点击事件
        viewHolder.cb_goods_choice_n.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    buyNum = Integer.valueOf(finalViewHolder.tv_num.getText().toString());
                    onItemCheckBoxChangeListener.onChecked(position,buyNum);
                }else{
                    onItemCheckBoxChangeListener.onUnChecked(position);
                }
            }
        });


        //加减键的点击事件
        viewHolder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buyNum >= 99) {
                    ToastUtils.showShort(context, "达到上限了哦");
                    return;
                }

                buyNum++;
                finalViewHolder.tv_num.setText(buyNum + "");
            }
        });

        viewHolder.iv_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buyNum <= 1) {
                    ToastUtils.showShort(context, "不能在减少了");
                    return;
                }
                buyNum--;
                finalViewHolder.tv_num.setText(buyNum + "");
            }
        });

        //图片的，跳转物品详情
        viewHolder.iv_goods_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(context,"当前选中位置是：" + position);
                context.startActivity(new Intent(context, GoodDetailsActivity.class));
            }
        });

        //TODO : 这里跳转店铺详情，需要传值过去
        /**
         *
         * 点击跳转店铺的
         */
        viewHolder.tv_title_good_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, StoreDetailsActivity.class));
            }
        });


        return convertView;
    }

    /**
     * 让所有的CheckBox选中
     */
    public void setAllCheck(boolean isAllCheck) {
        for (CheckBox c : checkBoxes) {
            if (isAllCheck) {
                if(c != null) {
                    c.setChecked(true);
                }
            } else{
                if(c != null) {
                    c.setChecked(false);
                }
            }
        }
    }


    class ViewHolder {
        private TextView tv_title_good_store;  //题目店铺
        private LinearLayout ll_goodsDetailsPart; //编辑前的部分
        private RelativeLayout rl_addOrReducePart;//点击编辑之后完成的部分
        private ImageView iv_goods_img; //左边圈圈选中前状态,商品图片
        private CheckBox cb_goods_choice_n;
        private TextView tv_goodsName, tv_goodsPrice, tv_goodsAddress;  //商品名字，商品价格，商品地址
        private TextView tv_title_edit, tv_title_finish;  //编辑，完成
        private ImageView iv_reduce, iv_add; //减少增加
        private TextView tv_num;  //商品数量
        private TextView tv_delete;  //删除商品
    }

    /**
     * 将被选中的Checked的position调回去
     */
    public interface OnItemCheckBoxChangeListener{
        void onChecked(int position,int buyNum);
        void onUnChecked(int position);
    }

    public void setOnItemCheckBoxChangeListener(OnItemCheckBoxChangeListener onItemCheckBoxChangeListener){
        this.onItemCheckBoxChangeListener = onItemCheckBoxChangeListener;
    }


}
