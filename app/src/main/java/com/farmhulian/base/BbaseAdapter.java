package com.farmhulian.base;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.List;

/**
 * 一个自定义的BaseAdapter父类
 *  @author 谭杰栖
 */
public abstract class BbaseAdapter<T> extends BaseAdapter{
    protected List<T> datas;
    protected Context context;
    protected LayoutInflater inflater;
    private int preLength;   //添加之前的集合长度
    private int afterLength;  //添加了数据之后的集合长度，用来判断是否添加成功

    public BbaseAdapter(Context context, List<T> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    /**
     *添加全部数据进适配器
     * @return  是否添加成功  1.成功    0；失败
     */
    public int addAll(List<T> newDatas){
        datas.clear();
        datas.addAll(newDatas);
        notifyDataSetChanged();
        return datas.size() == 0 ? 0 : 1;
    }
    /**
     *添加某条数据进适配器
     * @return  是否添加成功  1.成功    0；失败
     */
    public int add(T newData){
        preLength = datas.size();
        datas.add(newData);
        afterLength = datas.size();
        notifyDataSetChanged();
        return afterLength > preLength ? 1 : 0;
    }
    /**
     *删除全部数据
     * @return  是否删除成功  1.成功    0；失败
     */
    public int deleteAll(){
        if(datas.size() == 0){
            Toast.makeText(context, "数据本来就是空的,不需要删除", Toast.LENGTH_SHORT).show();
            Log.i("info","========数据本来就是空的,不需要删除===============");
            return 1;
        }
        datas.clear();
        notifyDataSetChanged();
        return datas.size() == 0 ? 1 : 0;
    }

    /**
     *删除某条数据
     * @return  是否删除成功  1.成功    0；失败
     */
    public int delete(int position){
        if(datas.get(position) == null){
            Toast.makeText(context, "待删除的这条数据是空的，不需要删除", Toast.LENGTH_SHORT).show();
            Log.i("info","========待删除的这条数据是空的，不需要删除===============");
            return 1;
        }
        preLength = datas.size();
        datas.remove(position);
        afterLength = datas.size();
        notifyDataSetChanged();
        return afterLength < preLength ? 1 : 0;
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
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
