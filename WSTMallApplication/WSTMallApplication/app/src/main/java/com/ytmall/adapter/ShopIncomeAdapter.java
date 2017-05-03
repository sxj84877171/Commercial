package com.ytmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.ytmall.R;
import com.ytmall.bean.ShopIncomeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 16/11/24.
 */
public class ShopIncomeAdapter extends BaseAdapter {
    private Context ctxt;
    private List<ShopIncomeModel> list = new ArrayList<>();
    public ShopIncomeAdapter(Context ctxt, List<ShopIncomeModel> list){
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
        ShopIncomeModel model = list.get(position);
        if (view == null){
            view = LayoutInflater.from(ctxt).inflate(R.layout.item_list_income_detail,null);
            item = new HoldItem();
            item.txtProName = (TextView) view.findViewById(R.id.txtProName);
            item.txtProDescrib = (TextView) view.findViewById(R.id.txtProDescrib);
            item.txtNumber = (TextView) view.findViewById(R.id.txtNumber);
            item.txtPrice = (TextView) view.findViewById(R.id.txtPrice);
            item.txtBanlance = (TextView) view.findViewById(R.id.txtBanlance);
            item.txtDate = (TextView) view.findViewById(R.id.txtDate);
            item.txtMoney = (TextView) view.findViewById(R.id.txtMoney);
            item.imgPro = (ImageView) view.findViewById(R.id.imgPro);

            view.setTag(item);
        }else {
            item = (HoldItem) view.getTag();
        }

        item.imgPro.setImageResource(model.getImg());
        item.txtProName.setText(model.getProName());
        item.txtProDescrib.setText(model.getProDescrib());
        item.txtPrice.setText("小计：￥"+model.getPrice());
        item.txtBanlance.setText("店铺余额："+model.getBalance());
        item.txtDate.setText(model.getDate());
        item.txtMoney.setText(model.getIncome());
        item.txtNumber.setText("数量 x"+String.valueOf(model.getNum()));
        return view;
    }
    private class HoldItem{
        private TextView txtProName,txtProDescrib,txtPrice,
                txtNumber,txtBanlance,txtDate,txtMoney;
        private ImageView imgPro;
    }
}
