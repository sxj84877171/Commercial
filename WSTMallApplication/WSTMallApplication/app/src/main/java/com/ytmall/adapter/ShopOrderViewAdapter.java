package com.ytmall.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.ytmall.fragment.shop.ShopOrderPayFragment;
import com.ytmall.fragment.shop.ShopOrderReceiveFragment;
import com.ytmall.fragment.shop.ShopOrderSendFragment;

/**
 * Created by lee on 16/12/17.
 */
public class ShopOrderViewAdapter extends PagerAdapter {
    ShopOrderReceiveFragment receiveFragment;
    ShopOrderPayFragment payFragment;
    ShopOrderSendFragment sendFragment;
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
