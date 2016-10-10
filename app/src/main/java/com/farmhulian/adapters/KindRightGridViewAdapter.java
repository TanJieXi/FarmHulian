package com.farmhulian.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.farmhulian.R;
import com.farmhulian.beans.KindRightBean;

import java.util.List;

/**
 * 分类界面,右边GridView的适配器
 *  @author 谭杰栖
 */
public class KindRightGridViewAdapter extends BaseAdapter{
    private List<KindRightBean> kindRightBeens;
    private KindRightBean kindRightBean;
    private Context context;

    public KindRightGridViewAdapter(Context context, List<KindRightBean> kindRightBeens) {
        this.context = context;
        this.kindRightBeens = kindRightBeens;
    }

    @Override
    public int getCount() {
        return kindRightBeens.size();
    }

    @Override
    public Object getItem(int position) {
        return kindRightBeens.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.kind_right_gridview_item,null);
            viewHolder.tv_right_text1 = (TextView) convertView.findViewById(R.id.tv_right_text1);
            viewHolder.iv_right_img = (ImageView) convertView.findViewById(R.id.iv_right_img);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        kindRightBean = kindRightBeens.get(position);

        if(kindRightBean != null) {
            viewHolder.tv_right_text1.setText(kindRightBean.getImgName());
            viewHolder.iv_right_img.setImageResource(kindRightBean.getImgId());
        }
        return convertView;
    }



    class ViewHolder{
        private TextView tv_right_text1;
        private ImageView iv_right_img;

    }
}
