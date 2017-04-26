package com.ytmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.application.Const;
import com.ytmall.bean.ShopOrder;
import com.ytmall.bean.ShopOrderProduct;

import java.util.List;

/**
 * Created by lee on 2017/1/6.
 */

public class ShopOrderProductAdapter extends BaseAdapter {
    private Context ctxt;
    private List<ShopOrderProduct> list;
    public ShopOrderProductAdapter(Context ctxt, List<ShopOrderProduct> list){
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
            item = new HoldItem();
            view = LayoutInflater.from(ctxt).inflate(R.layout.item_list_shop_order_product,null);
            item.imgPro  = (ImageView) view.findViewById(R.id.imgPro);
            item.txtProName = (TextView) view.findViewById(R.id.txtProName);
            item.txtProPrice = (TextView) view.findViewById(R.id.txtProPrice);
            item.txtProNum = (TextView) view.findViewById(R.id.txtProNum);

            view.setTag(item);

        }else {
            item = (HoldItem) view.getTag();
        }
        ShopOrderProduct model = list.get(position);
        item.txtProName.setText(model.goodsName);
        item.txtProPrice.setText(model.shopPrice);
        item.txtProNum.setText(model.goodsNums);
        ((BaseActivity) ctxt).loadOnImage(Const.BASE_URL + model.goodsThums, item.imgPro);


        return view;
    }
    private class HoldItem{
        private ImageView imgPro;
        private TextView txtProName,txtProPrice,txtProNum;
    }
}
