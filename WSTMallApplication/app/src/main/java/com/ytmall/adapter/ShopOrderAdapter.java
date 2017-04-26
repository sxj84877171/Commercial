package com.ytmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ytmall.R;
import com.ytmall.activity.shop.ShopOrderDetailActivity;
import com.ytmall.bean.ShopOrder;
import com.ytmall.widget.ListViewForScroll;

import java.util.List;

/**
 * Created by lee on 2017/1/6.
 */

public class ShopOrderAdapter extends BaseAdapter {
    private Context ctxt;
    private List<ShopOrder> list;
    public ShopOrderAdapter(Context ctx, List<ShopOrder> list){
        this.ctxt = ctx;
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
    public View getView(final int position, View view, ViewGroup parent) {
        final HoldItem item;
        if (view == null){
            view = LayoutInflater.from(ctxt).inflate(R.layout.item_list_shop_order,null);
            item = new HoldItem();
            item.txtShopOrder = (TextView) view.findViewById(R.id.txtShopOrder);
            item.txtShopStatus = (TextView) view.findViewById(R.id.txtShopStatus);
            item.listShopPro = (ListViewForScroll) view.findViewById(R.id.listShopPro);

            view.setTag(item);

        }else {
            item = (HoldItem) view.getTag();
        }
        final ShopOrder model = list.get(position);
        ShopOrderProductAdapter adapter = new ShopOrderProductAdapter(ctxt,model.goodsList);
        item.listShopPro.setAdapter(adapter);

        item.txtShopOrder.setText("订单编号："+model.orderNo);
        if (model.orderStatus == 0){
            item.txtShopStatus.setText("未受理");
        }else if (model.orderStatus == 3){
            item.txtShopStatus.setText("配送中");
        }else if (model.orderStatus == -7){
            item.txtShopStatus.setText("用户取消");
        }else if (model.orderStatus == 4){
            item.txtShopStatus.setText("已完成");
        }else if (model.orderStatus == -5){
            item.txtShopStatus.setText("门店不同意拒收");

        }else if (model.orderStatus == -6){
            item.txtShopStatus.setText("用户取消");

        }else if (model.orderStatus == -4){
            item.txtShopStatus.setText("门店同意拒收");
        }else if (model.orderStatus == -3){
            item.txtShopStatus.setText("用户拒收");
        }else if (model.orderStatus == -2){
            item.txtShopStatus.setText("未付款的订单");
        }else if (model.orderStatus == -1){
            item.txtShopStatus.setText("用户取消");
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctxt, ShopOrderDetailActivity.class);
                i.putExtra("orderId",model.orderId);
                i.putExtra("position",position);
                i.putExtra("status",item.txtShopStatus.getText().toString());
                ctxt.startActivity(i);
            }
        });

        return view;
    }
    private class HoldItem{
        private TextView txtShopOrder,txtShopStatus;
        private ListViewForScroll listShopPro;
    }
}
