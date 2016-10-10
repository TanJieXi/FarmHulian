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
 * 门店详情的ListView适配器.
 *  @author 谭杰栖
 */
public class StoreDetaisListViewAdapter extends BaseAdapter{
    private List<String> datas;
    private Context context;

    public StoreDetaisListViewAdapter(Context context, List<String> datas) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.buy_store_listview_item,null);
            viewHolder.iv_store_icon = (ImageView) convertView.findViewById(R.id.iv_store_icon);
            viewHolder.tv_store_indroduce = (TextView) convertView.findViewById(R.id.tv_store_indroduce);
            viewHolder.tv_store_title = (TextView) convertView.findViewById(R.id.tv_store_title);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
            viewHolder.tv_store_title.setText(datas.get(position));
        return convertView;
    }

    class ViewHolder{
        private TextView tv_store_title,tv_store_indroduce;
        private ImageView iv_store_icon;

    }

}
