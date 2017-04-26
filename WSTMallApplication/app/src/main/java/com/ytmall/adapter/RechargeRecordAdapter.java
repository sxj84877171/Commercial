package com.ytmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ytmall.R;
import com.ytmall.bean.RechargeRecordBean;
import com.ytmall.bean.TakeCashRecordBean;
import com.ytmall.util.StrUtil;

import java.util.List;

/**
 * Created by lee on 2017/1/11.
 */

public class RechargeRecordAdapter extends BaseAdapter {
    private Context ctxt;
    private List<RechargeRecordBean> list;
    public RechargeRecordAdapter(Context ctxt, List<RechargeRecordBean> list){
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

        item.txtMoney.setText("充值金额：¥"+list.get(position).recharge_money);
        item.txtDate.setText(StrUtil.null2Str(list.get(position).add_time));
        item.txtNo.setText("充值单号："+list.get(position).recharge_sn);
        if (StrUtil.null2Str(list.get(position).remark).length() > 0){
            item.txtRemark.setText(StrUtil.null2Str(list.get(position).remark));
        }else {
            item.txtRemark.setVisibility(View.GONE);
        }

        if (list.get(position).recharge_status.equals("0")){
            item.txtStatus.setText("未支付");

        }else if (list.get(position).recharge_status.equals("1")){
            item.txtStatus.setText("第一次已到账");

        }else if (list.get(position).recharge_status.equals("2")){
            item.txtStatus.setText("第二次已到账");

        }



        return view;
    }
    private class HoldItem{
        private TextView txtMoney,txtDate,txtNo,txtStatus,txtRemark;

    }
}
