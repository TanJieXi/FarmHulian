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
 * 首页碎片 附近农场和海外农场 的 适配器
 *  @author 谭杰栖
 */
public class HomeNearSeaAdapter extends BaseAdapter {
    private Context context;
    private List<String> datas;

    public HomeNearSeaAdapter(Context context, List<String> datas) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_home_fujin_listitem,null);
            viewHolder.iv_home_fujin_icon = (ImageView) convertView.findViewById(R.id.iv_home_fujin_icon);
            viewHolder.tv_home_fujin_name = (TextView) convertView.findViewById(R.id.tv_home_fujin_name);
            viewHolder.tv_home_fujin_fen = (TextView) convertView.findViewById(R.id.tv_home_fujin_fen);
            viewHolder.tv_home_fujin_byNum = (TextView) convertView.findViewById(R.id.tv_home_fujin_byNum);
            viewHolder.tv_home_liuyan = (TextView) convertView.findViewById(R.id.tv_home_liuyan);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }


    class ViewHolder{
        private ImageView iv_home_fujin_icon;
        private TextView tv_home_fujin_name,tv_home_fujin_fen,tv_home_fujin_byNum,tv_home_liuyan;
    }
}
