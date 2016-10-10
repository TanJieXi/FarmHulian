package com.farmhulian.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.farmhulian.R;
import com.farmhulian.base.BbaseAdapter;

import java.util.List;

/**
 * 精选里面的GridView的适配器
 *  @author 谭杰栖
 */
public class ChoiceGridAdapter extends BbaseAdapter<String>{

    public ChoiceGridAdapter(Context context, List<String> datas) {
        super(context, datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.fragment_choice_griditem,null);

            viewHolder.goods_icon = (ImageView) convertView.findViewById(R.id.goods_icon);
            viewHolder.goods_name = (TextView) convertView.findViewById(R.id.goods_name);
            viewHolder.goods_price = (TextView) convertView.findViewById(R.id.goods_price);
            viewHolder.goods_mark = (TextView) convertView.findViewById(R.id.goods_mark);
            viewHolder.tv_yunfei = (TextView) convertView.findViewById(R.id.tv_yunfei);
            viewHolder.tv_place = (TextView) convertView.findViewById(R.id.tv_place);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

       /* PicassoUtils.loadImgWithPl(context, "http://p3.so.qhmsg.com/t016ec2cd3a0c2312bd.jpg",R.mipmap.moren_nong,
                R.mipmap.moren_nong,viewHolder.goods_icon);*/

        return convertView;
    }

    class ViewHolder{
        private ImageView goods_icon;
        private TextView goods_name,goods_price,goods_mark,tv_yunfei,tv_place;

    }

}
