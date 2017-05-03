package com.ytmall.activity.nearbybusiness;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.business.BusinessHomeFragment;

/**
 * Created by Administrator on 2016/8/3.
 */
public class ShopHomePageActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        replaceFragment(
                new BusinessHomeFragment(
                        getIntent().getStringExtra("shopId"),
                        getIntent().getStringExtra("shopName")), false);
    }
}
