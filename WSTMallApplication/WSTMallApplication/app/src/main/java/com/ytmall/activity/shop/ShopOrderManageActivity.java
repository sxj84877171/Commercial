package com.ytmall.activity.shop;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.shop.ShopOrderManageFragment;

/**
 * Created by lee on 2016/12/21.
 */

public class ShopOrderManageActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new ShopOrderManageFragment(),false);
    }
}
