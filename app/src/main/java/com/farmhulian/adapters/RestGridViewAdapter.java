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
 * 生态餐厅里面的GridView的适配器
 *  @author 谭杰栖
 */
public class RestGridViewAdapter extends BaseAdapter {
    public RestGridViewAdapter(Context context, List<String> datas) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_rest_gridview_item,null);
            viewHolder.iv_rest_icon = (ImageView) convertView.findViewById(R.id.iv_rest_icon);
            viewHolder.tv_rest_title = (TextView) convertView.findViewById(R.id.tv_rest_title);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    class ViewHolder{
        private TextView tv_rest_title;
        private ImageView iv_rest_icon;
    }
}
