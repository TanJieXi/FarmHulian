package com.farmhulian.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.farmhulian.R;

import java.util.List;

/**
 * Created by 谭杰栖 on 2016/9/27.
 * 地址选择的ListView的适配器
 */

public class ChoiceAddListViewAdapter extends BaseAdapter{
    private Context context;
    private List<String> datas;

    public ChoiceAddListViewAdapter(Context context, List<String> datas) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.choice_address_listview_item_layout,null);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            viewHolder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }


    class ViewHolder{
        private TextView tv_name,tv_phone,tv_address;   //地址选择里面的名字，电话，详细地址
    }


}
