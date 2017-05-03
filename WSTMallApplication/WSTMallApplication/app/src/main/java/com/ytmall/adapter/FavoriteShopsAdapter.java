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
import com.ytmall.bean.FavoriteShop;

import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
public class FavoriteShopsAdapter extends BaseAdapter {
    private Context context;
    private List<FavoriteShop> favoriteShopList;
    public FavoriteShopsAdapter(Context context,List<FavoriteShop> favoriteShopList){
        this.context=context;
        this.favoriteShopList=favoriteShopList;
    }

    @Override
    public int getCount() {
        return favoriteShopList.size();
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
            holder=new Holder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_favorite_shop,null);
            holder.im_shop= (ImageView) convertView.findViewById(R.id.im_shop);
            holder.tv_shop_name= (TextView) convertView.findViewById(R.id.tv_shop_name);
            convertView.setTag(holder);
        }else{
            holder= (Holder) convertView.getTag();
        }
        holder.tv_shop_name.setText(favoriteShopList.get(position).shopName);
        ((BaseActivity) context).loadOnImage(Const.BASE_URL + favoriteShopList.get(position).shopImg, holder.im_shop);
        return convertView;
    }
    static class Holder{
        ImageView im_shop;
        TextView tv_shop_name;
    }
}
