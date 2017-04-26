package com.ytmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ytmall.R;
import com.ytmall.bean.TakeCashRecordBean;

import java.util.List;

/**
 * Created by lee on 2017/1/11.
 */

public class TakeCashRecordAdapter extends BaseAdapter {
    private Context ctxt;
    private List<TakeCashRecordBean> list;
    public TakeCashRecordAdapter(Context ctxt,List<TakeCashRecordBean> list){
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
            view = LayoutInflater.from(ctxt).inflate(R.layout.item_list_take_cash,null);

            item.txtMoney = (TextView) view.findViewById(R.id.txtMoney);
            item.txtDate = (TextView) view.findViewById(R.id.txtDate);
            item.txtStatus = (TextView) view.findViewById(R.id.txtStatus);

            view.setTag(item);
        }else {
            item = (HoldItem) view.getTag();
        }


        item.txtMoney.setText("提现金额：¥"+list.get(position).money);
        item.txtDate.setText(list.get(position).createTime);
        if (list.get(position).cashSatus.equals("0")){
            item.txtStatus.setText("待处理");

        }else if (list.get(position).cashSatus.equals("1")){
            item.txtStatus.setText("已同意");
        }else {
            //2
            item.txtStatus.setText("已拒绝");
        }

        return view;
    }
    private class HoldItem{
        private TextView txtMoney,txtDate,txtStatus;
    }
}
