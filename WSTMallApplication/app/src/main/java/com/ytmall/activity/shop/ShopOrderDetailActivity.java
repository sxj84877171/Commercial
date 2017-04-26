package com.ytmall.activity.shop;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.shop.ShopOrderDetailFragment;

/**
 * Created by lee on 2017/1/7.
 */

public class ShopOrderDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String  orderId = getIntent().getStringExtra("orderId");
        String  status = getIntent().getStringExtra("status");
        int position = getIntent().getIntExtra("position",-1);
        replaceFragment(new ShopOrderDetailFragment(orderId,status,position),false);
    }
}
