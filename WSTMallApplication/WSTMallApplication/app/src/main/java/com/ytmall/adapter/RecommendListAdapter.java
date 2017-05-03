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
import com.ytmall.bean.RecommendMoneyBean;
import com.ytmall.bean.RecommendUserBean;
import com.ytmall.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2017/1/17.
 */

public class RecommendListAdapter extends BaseAdapter {
    private Context ctxt;
    private List<RecommendMoneyBean> list = new ArrayList<>();
    public RecommendListAdapter(Context ctxt, List<RecommendMoneyBean> list){
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
        RecommendMoneyBean model = list.get(position);
        if (view == null){
            view = LayoutInflater.from(ctxt).inflate(R.layout.item_list_recommend,null);
            item = new HoldItem();
            item.txtRecommendNo = (TextView) view.findViewById(R.id.txtRecommendNo);
            item.txtRecommendMoney = (TextView) view.findViewById(R.id.txtRecommendMoney);
            item.txtRecommendTime = (TextView) view.findViewById(R.id.txtRecommendTime);
            item.txtPlusMoney = (TextView) view.findViewById(R.id.txtPlusMoney);

            view.setTag(item);
        }else {
            item = (HoldItem) view.getTag();
        }
        item.txtRecommendNo.setText(model.moneyRemark);
        item.txtRecommendMoney.setText(model.money);
        item.txtPlusMoney.setText("+  ¥"+model.distributMoney);
        item.txtRecommendTime.setText(model.createTime);
        return view;
    }
    private class HoldItem{
        private TextView txtRecommendNo,txtRecommendMoney,txtPlusMoney,txtRecommendTime;
    }
}
