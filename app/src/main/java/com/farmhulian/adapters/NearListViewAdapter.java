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
 * 附近商家的Banner条下面的ListView适配器
 *  @author 谭杰栖
 */
public class NearListViewAdapter extends BbaseAdapter<String>{

    public NearListViewAdapter(Context context, List<String> datas) {
        super(context, datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.fragment_near_listitem,null);
            viewHolder.iv_buyImg = (ImageView) convertView.findViewById(R.id.iv_buyImg);
            viewHolder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            viewHolder.tv_buyNum = (TextView) convertView.findViewById(R.id.tv_buyNum);
            viewHolder.tv_distance = (TextView) convertView.findViewById(R.id.tv_distance);
            viewHolder.tv_score = (TextView) convertView.findViewById(R.id.tv_score);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        /*PicassoUtils.loadImgWithPl(context, "http://p3.so.qhmsg.com/t016ec2cd3a0c2312bd.jpg",R.mipmap.moren_nong,
                R.mipmap.moren_nong,viewHolder.iv_buyImg);*/
        return convertView;
    }

    class ViewHolder{
        private ImageView iv_buyImg;
        private TextView tv_score,tv_address,tv_buyNum,tv_distance;
    }

}
