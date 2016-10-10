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
 *  生态粮油页面里面的GridView的适配器
 *   @author 谭杰栖
 */
public class OilGridViewAdapter extends BaseAdapter {
    private List<String> datas;
    private Context context;

    public OilGridViewAdapter(Context context, List<String> datas) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_oil_gridviewitem,null);
            viewHolder.iv_oil_goods_icon = (ImageView) convertView.findViewById(R.id.iv_oil_goods_icon);
            viewHolder.tv_oil_goods_name = (TextView) convertView.findViewById(R.id.tv_oil_goods_name);
            viewHolder.tv_oil_goods_mark = (TextView) convertView.findViewById(R.id.tv_oil_goods_mark);
            viewHolder.tv_oil_yunfei = (TextView) convertView.findViewById(R.id.tv_oil_yunfei);
            viewHolder.tv_oil_place = (TextView) convertView.findViewById(R.id.tv_oil_place);
            viewHolder.tv_oil_good_price = (TextView) convertView.findViewById(R.id.tv_oil_good_price);
            viewHolder.tv_oil_good_saleNum = (TextView) convertView.findViewById(R.id.tv_oil_good_saleNum);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        /*PicassoUtils.loadImgWithPl(context, "http://p3.so.qhmsg.com/t016ec2cd3a0c2312bd.jpg",R.mipmap.moren_nong,
                R.mipmap.moren_nong,viewHolder.iv_oil_goods_icon);*/
        return convertView;
    }

    class ViewHolder{
        private ImageView iv_oil_goods_icon; //图标
        //商品名，标志，运费，产地，价格，付款人数
        private TextView tv_oil_goods_name,tv_oil_goods_mark,tv_oil_yunfei,tv_oil_place,tv_oil_good_price,tv_oil_good_saleNum;
    }


}
