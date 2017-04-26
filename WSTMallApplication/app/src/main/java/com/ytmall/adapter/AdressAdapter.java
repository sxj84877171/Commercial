package com.ytmall.adapter;

import java.util.List;

import com.ytmall.R;
import com.ytmall.bean.AdressCommunitysbean;
import com.ytmall.bean.AdressDistrictsbean;
import com.ytmall.bean.AreaBean;
import com.ytmall.domain.ShippingAdressInfo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdressAdapter extends BaseAdapter {

    private List<AreaBean> areaBeanList;
    private Context context;

    public AdressAdapter(List<AreaBean> areaBeanList, Context context) {
        this.areaBeanList = areaBeanList;
        this.context = context;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return areaBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view = View.inflate(context, R.layout.shipping_adress_item, null);
        TextView tv_adress = (TextView) view.findViewById(R.id.tv_shippingadress);
        tv_adress.setText(areaBeanList.get(position).areaName);
        return view;
    }

}
