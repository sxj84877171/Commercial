package com.ytmall.activity.money;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.money.ShopTakeMoneyFragment;

/**
 * Created by lee on 2017/1/23.
 */

public class ShopTakeMoneyActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new ShopTakeMoneyFragment(),false);
    }
}
