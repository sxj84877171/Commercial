package com.ytmall.adapter;

import android.app.Activity;
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
import com.ytmall.bean.FavoriteGoods;

import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
public class FavoriteGoodsAdapter extends BaseAdapter {
    private Context context;
    private List<FavoriteGoods> favoriteGoodsList;
    public FavoriteGoodsAdapter(Context context, List<FavoriteGoods> favoriteGoodsList) {
        this.context = context;
        this.favoriteGoodsList = favoriteGoodsList;
    }

    @Override
    public int getCount() {
        return favoriteGoodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_favorite_goods, null);
            holder=new Holder();
            holder.im_goods= (ImageView) convertView.findViewById(R.id.im_goods);
            holder.tv_goods_name= (TextView) convertView.findViewById(R.id.tv_goods_name);
            holder.tv_is_sale= (TextView) convertView.findViewById(R.id.tv_is_sale);
            holder.tv_price= (TextView) convertView.findViewById(R.id.tv_price);
            holder.tv_sale_num= (TextView) convertView.findViewById(R.id.tv_sale_num);
            convertView.setTag(holder);
        }else{
            holder= (Holder) convertView.getTag();
        }
        ((BaseActivity) context).loadOnImage(Const.BASE_URL + favoriteGoodsList.get(position).goodsThums, holder.im_goods);
        holder.tv_goods_name.setText(favoriteGoodsList.get(position).goodsName);
        if(favoriteGoodsList.get(position).isSale.equals("0")){
            holder.tv_is_sale.setText("已下架");
        }else{
            holder.tv_is_sale.setText("销售中");
        }
        holder.tv_price.setText("￥"+favoriteGoodsList.get(position).shopPrice);
        holder.tv_sale_num.setText("已售："+favoriteGoodsList.get(position).saleCount+"件");
         return convertView;
    }

    static class Holder {
        ImageView im_goods;
        TextView tv_goods_name;
        TextView tv_is_sale;
        TextView tv_price;
        TextView tv_sale_num;

    }
}
