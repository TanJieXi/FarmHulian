package com.farmhulian.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.farmhulian.R;

import java.util.List;

/**
 * Created by 谭杰栖 on 2016/9/28.
 * 确定订单里面listview的适配器
 */

public class SureListViewAdapter extends BaseAdapter {
    private List<String> datas;
    private Context context;

    public SureListViewAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.sure_pay_order_listview_item,null);
            viewHolder.iv_sure_goodsImg = (ImageView) convertView.findViewById(R.id.iv_sure_goodsImg);
            viewHolder.tv_sure_goodsName = (TextView) convertView.findViewById(R.id.tv_sure_goodsName);
            viewHolder.tv_sure_goodsPrice = (TextView) convertView.findViewById(R.id.tv_sure_goodsPrice);
            viewHolder.tv_sure_goodsNum = (TextView) convertView.findViewById(R.id.tv_sure_goodsNum);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    class ViewHolder{
        private ImageView iv_sure_goodsImg;  //商品图片
        private TextView tv_sure_goodsName,tv_sure_goodsPrice,tv_sure_goodsNum;  //商品名字，价格，数量
    }


}
