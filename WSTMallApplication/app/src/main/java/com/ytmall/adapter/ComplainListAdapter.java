package com.ytmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ytmall.R;
import com.ytmall.bean.OrderComplainList;

import java.util.List;

/**
 * Created by Administrator on 2016/4/10.
 */
public class ComplainListAdapter extends BaseAdapter {
    private Context context;
    private List<OrderComplainList> orderComplainLists;
    public ComplainListAdapter(List<OrderComplainList> orderComplainLists,Context context){
        this.orderComplainLists=orderComplainLists;
        this.context=context;
    }
    @Override
    public int getCount() {
        return orderComplainLists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if(convertView==null){
            holder=new Holder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_complain_list,null);
            holder.tv_order_id= (TextView) convertView.findViewById(R.id.tv_order_id);
            holder.tv_shop_name= (TextView) convertView.findViewById(R.id.tv_shop_name);
            holder.tv_complain_state= (TextView) convertView.findViewById(R.id.tv_complain_state);
            holder.tv_complain_content= (TextView) convertView.findViewById(R.id.tv_complain_content);
            holder.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        }else{
            holder= (Holder) convertView.getTag();
        }
        holder.tv_order_id.setText(orderComplainLists.get(position).orderNo);
        holder.tv_shop_name.setText(orderComplainLists.get(position).shopName);
        holder.tv_complain_state.setText(getComplainState(orderComplainLists.get(position).complainStatus));
        holder.tv_complain_content.setText(orderComplainLists.get(position).complainContent);
        holder.tv_time.setText(orderComplainLists.get(position).complainTime);
        return convertView;
    }
    static class Holder{
        TextView tv_order_id;
        TextView tv_shop_name;
        TextView tv_complain_state;
        TextView tv_complain_content;
        TextView tv_time;
    }
    /**
     * 0:待处理 1:转给应诉人 2:应诉人回应 3:等待仲裁 4:已仲裁
     */
    private String getComplainState(int satus){
        switch (satus){
            case 0:
                return "待处理";
            case 1:
                return "已转给应诉人";
            case 2:
                return "应诉人回应";
            case 3:
                return "等待仲裁";
            case 4:
                return "已仲裁";
        }
        return "";
    }
}
