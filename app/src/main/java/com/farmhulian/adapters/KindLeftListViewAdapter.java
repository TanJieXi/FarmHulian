package com.farmhulian.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.farmhulian.R;
import com.farmhulian.base.BbaseAdapter;

import java.util.List;

/**
 * 分类界面,左边ListView的适配器
 *  @author 谭杰栖
 */
public class KindLeftListViewAdapter extends BbaseAdapter<String>{

    private int selectedPosition = -1;// 选中的位置

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
    }

    public KindLeftListViewAdapter(Context context, List<String> datas) {
        super(context, datas);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.kind_left_listview_item,null);
            viewHolder.tv_kind_left_line = (TextView) convertView.findViewById(R.id.tv_kind_left_line);
            viewHolder.tv_kind_left_name = (TextView) convertView.findViewById(R.id.tv_kind_left_name);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(selectedPosition == position){ //背景换了
            viewHolder.tv_kind_left_name.setBackgroundColor(Color.rgb(239,238,237));
            viewHolder.tv_kind_left_line.setBackgroundColor(Color.rgb(242,148,0));
        }else{
            viewHolder.tv_kind_left_name.setBackgroundColor(Color.WHITE);
            viewHolder.tv_kind_left_line.setBackgroundColor(Color.WHITE);
        }



        viewHolder.tv_kind_left_name.setText(datas.get(position));
        return convertView;
    }



    class ViewHolder{
        private TextView tv_kind_left_line,tv_kind_left_name;

    }
}
