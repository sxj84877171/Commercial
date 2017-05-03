package com.ytmall.activity.shop;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.shop.ShopIncomFragment;

/**
 * Created by lee on 16/12/8.
 */
public class ShopIncomeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new ShopIncomFragment(),false);
    }
}
