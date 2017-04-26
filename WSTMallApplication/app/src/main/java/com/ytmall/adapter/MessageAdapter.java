package com.ytmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ytmall.R;
import com.ytmall.bean.MessageBean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/3.
 */
public class MessageAdapter extends BaseAdapter {
    private Context context;
    private  List<MessageBean> messageBeanList;
    public MessageAdapter(List<MessageBean> messageBeanList,Context context){
        this.messageBeanList=messageBeanList;
        this.context=context;
    }
    @Override
    public int getCount() {
        return messageBeanList.size();
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
        convertView= LayoutInflater.from(context).inflate(R.layout.item_message,null);
        TextView tv_message= (TextView) convertView.findViewById(R.id.tv_message);
        TextView tv_time= (TextView) convertView.findViewById(R.id.tv_time);
        ImageView imageView= (ImageView) convertView.findViewById(R.id.iv_point_read);
        tv_message.setText(messageBeanList.get(position).msgContent);
        tv_time.setText(messageBeanList.get(position).createTime);
        if(messageBeanList.get(position).msgStatus.equals("1")){
            imageView.setVisibility(View.GONE);
        }
        return convertView;
    }

}
