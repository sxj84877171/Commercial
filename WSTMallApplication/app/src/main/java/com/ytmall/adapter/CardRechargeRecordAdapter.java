package com.ytmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ytmall.R;
import com.ytmall.bean.QueryCardsListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2017/1/20.
 */

public class CardRechargeRecordAdapter extends BaseAdapter {
    private Context ctxt;
    private List<QueryCardsListBean> list = new ArrayList<>();
    public CardRechargeRecordAdapter(Context ctxt,List<QueryCardsListBean> list){
        this.list = list;
        this.ctxt = ctxt;

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
        QueryCardsListBean model = list.get(position);
        if (view == null){
            item = new HoldItem();
            view = LayoutInflater.from(ctxt).inflate(R.layout.item_list_recharge_record,null);

            item.txtMoney = (TextView) view.findViewById(R.id.txtMoney);
            item.txtDate = (TextView) view.findViewById(R.id.txtDate);
            item.txtStatus = (TextView) view.findViewById(R.id.txtStatus);

            item.txtNo = (TextView) view.findViewById(R.id.txtNo);
            item.txtRemark = (TextView) view.findViewById(R.id.txtRemark);

            view.setTag(item);
        }else {
            item = (HoldItem) view.getTag();
        }
        if (model.card_type.equals("0")){
            item.txtMoney.setText("话费卡金额："+model.card_money);
        }else if (model.card_type.equals("1")){
            item.txtMoney.setText("油卡金额："+model.card_money);
        }else if (model.card_type.equals("2")){
            item.txtMoney.setText("水费金额："+model.card_money);
        }else if (model.card_type.equals("3")){
            item.txtMoney.setText("电费金额："+model.card_money);
        }

        item.txtNo.setText("充值单号："+model.card_sn);

        item.txtDate.setText(model.add_time);
        if (list.get(position).card_status.equals("0")){
            item.txtStatus.setText("未到账");

        }else if (list.get(position).card_status.equals("1")){
            item.txtStatus.setText("已到账");

        }
        item.txtRemark.setText("预计到账时间："+model.expire_time);
        return view;
    }
    private class HoldItem{
        private TextView txtMoney,txtDate,txtNo,txtStatus,txtRemark;

    }
}
