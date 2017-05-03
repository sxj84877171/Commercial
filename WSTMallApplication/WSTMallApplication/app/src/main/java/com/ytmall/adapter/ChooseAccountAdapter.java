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
 * Created by lee on 16/10/27.
 */
public class ChooseAccountAdapter extends BaseAdapter {
    private Context ctxt;
    private List<String> list = new ArrayList<>();
    public ChooseAccountAdapter(Context ctxt, List<String> list){
        this.ctxt = ctxt;
        this.list = list;

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        HoldItem item ;
        if (view == null){
            item = new HoldItem();
            view = LayoutInflater.from(ctxt).inflate(R.layout.layout_string,null);
            item.text = (TextView) view.findViewById(R.id.text);

            view.setTag(item);

        }else {
            item = (HoldItem) view.getTag();
        }
        item.text.setText(list.get(position));
        return view;
    }
    class HoldItem {
        private TextView text;
    }
}
