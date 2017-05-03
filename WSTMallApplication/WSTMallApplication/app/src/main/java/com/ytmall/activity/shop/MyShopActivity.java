package com.ytmall.activity.shop;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.shop.MyShopFragment;
import com.ytmall.fragment.user.MineFragment;

/**
 * Created by lee on 16/12/7.
 */
public class MyShopActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new MyShopFragment(),false);
    }
}
