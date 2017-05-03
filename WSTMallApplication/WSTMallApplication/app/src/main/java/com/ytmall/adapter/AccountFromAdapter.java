package com.ytmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ytmall.R;
import com.ytmall.bean.AccountFromBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2017/2/11.
 */

public class AccountFromAdapter extends BaseAdapter {
    private Context ctxt;
    private List<AccountFromBean> list = new ArrayList<>();
    public AccountFromAdapter(Context ctxt,List<AccountFromBean> list){
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
        HoldItem item;
        if (view == null){
            view = LayoutInflater.from(ctxt).inflate(R.layout.item_list_account,null);
            item = new HoldItem();
            item.txtFrom = (TextView) view.findViewById(R.id.txtAccountFrom);
            item.checkBox = (ImageView) view.findViewById(R.id.cbSelect);

            view.setTag(item);
        }else {
            item = (HoldItem) view.getTag();
        }
        AccountFromBean model = list.get(position);
        if (model.isSelected){
            item.checkBox.setVisibility(View.VISIBLE);
        }else {
            item.checkBox.setVisibility(View.GONE);
        }
        item.txtFrom.setText(model.name);


        return view;
    }

    private class HoldItem{
        private TextView txtFrom;
        private ImageView checkBox;
    }
}
