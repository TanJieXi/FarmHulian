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
 * 生态旅游里面的GridView的适配器
 *  @author 谭杰栖
 */
public class TravelGridViewAdapter extends BaseAdapter {
    public TravelGridViewAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    private List<String> datas;
    private Context context;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_travel_gridview_item,null);
            viewHolder.iv_travel_icon = (ImageView) convertView.findViewById(R.id.iv_travel_icon);
            viewHolder.tv_travel_title = (TextView) convertView.findViewById(R.id.tv_travel_title);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    class ViewHolder{
        private TextView tv_travel_title;
        private ImageView iv_travel_icon;
    }
}
