package com.ytmall.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.activity.MainActivity;
import com.ytmall.activity.goodlist.GoodListActivity;
import com.ytmall.activity.nearbybusiness.NearbyBusinessActivity;
import com.ytmall.api.brands.FetchAds;
import com.ytmall.application.Const;
import com.ytmall.bean.FetchAdsBean;
import com.ytmall.fragment.goods.GoodsListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 16/12/1.
 */
public class GridClassifyAdapter extends BaseAdapter {
    private Context ctxt;
    private List<FetchAdsBean> list;
    public GridClassifyAdapter(Context ctxt, List<FetchAdsBean> list){
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
        final FetchAdsBean model = list.get(position);
        if (view == null){
            item = new HoldItem();
            view = LayoutInflater.from(ctxt).inflate(R.layout.item_classify_grid,null);
            item.img = (ImageView) view.findViewById(R.id.imgPro);
            item.text = (TextView) view.findViewById(R.id.txtProName);

            view.setTag(item);
        }else {
            item = (HoldItem) view.getTag();
        }
        ((BaseActivity) ctxt).loadOnImage(Const.BASE_URL + model.adFile, item.img);
//        Log.d("json menu url:",Const.BASE_URL+model.adFile);
        item.text.setText(model.adName);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent ;
//                Toast.makeText(ctxt,model.adMark,Toast.LENGTH_SHORT).show();
                if (model.adMark.equals("shops")){
                    //联盟商家
                    intent = new Intent(ctxt, NearbyBusinessActivity.class);
                    ctxt.startActivity(intent);
                }else if (model.adMark.equals("more")){
                    MainActivity.mHost.setCurrentTab(1);
                }else {
                    intent = new Intent(ctxt, GoodListActivity.class);
                    intent.putExtra(GoodsListFragment.Mode_GoodsCatIdOne,
                            Integer.parseInt(model.adMark));
                    ctxt.startActivity(intent);
                }
            }
        });
        return view;
    }
    private class HoldItem {
        private ImageView img;
        private TextView text;
    }
}
