package com.ytmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ytmall.R;
import com.ytmall.bean.GetShopDrawlistBean;
import com.ytmall.bean.RechargeRecordBean;
import com.ytmall.util.StrUtil;

import java.util.List;

/**
 * Created by lee on 2017/1/11.
 */

public class ShopCashRecordAdapter extends BaseAdapter {
    private Context ctxt;
    private List<GetShopDrawlistBean> list;
    public ShopCashRecordAdapter(Context ctxt, List<GetShopDrawlistBean> list){
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

        item.txtRemark.setVisibility(View.GONE);
        GetShopDrawlistBean model = list.get(position);
        item.txtMoney.setText("提现金额：¥"+model.money);
        item.txtDate.setText(model.createTime);
        String bankNo = model.accNo.substring(model.accNo.length() - 4,model.accNo.length());
        item.txtNo.setText("["+model.bankName+"]"+"***"+bankNo);

        //0未处理1已同意2已拒绝
        if (model.cashSatus == 0){
            item.txtStatus.setText("未处理");

        }else if (model.cashSatus == 1){
            item.txtStatus.setText("提现成功");

        }else {
            item.txtStatus.setText("已拒绝");

        }





        return view;
    }
    private class HoldItem{
        private TextView txtMoney,txtDate,txtNo,txtStatus,txtRemark;

    }
}
