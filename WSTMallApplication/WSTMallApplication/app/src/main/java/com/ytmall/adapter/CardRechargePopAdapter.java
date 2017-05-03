package com.ytmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ytmall.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2017/1/18.
 */

public class CardRechargePopAdapter extends BaseAdapter {
    private Context ctxt;
    private List<String> list = new ArrayList<>();
    public CardRechargePopAdapter(Context ctxt,List<String> list){
        this.ctxt = ctxt;
        this.list = list;

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        HoldItem item;
        if (view == null){
            item = new HoldItem();
            view = LayoutInflater.from(ctxt).inflate(R.layout.item_list_string,null);
            item.text = (TextView) view.findViewById(R.id.text);
            view.setTag(item);
        }else {
            item = (HoldItem) view.getTag();
        }
        item.text.setText(list.get(position));
        return view;
    }
    private class HoldItem{
        private TextView text;
    }
}
