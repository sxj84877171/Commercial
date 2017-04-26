package com.ytmall.activity.money;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.money.ShopMoneyManageFragment;

/**
 * Created by lee on 2017/1/23.
 */

public class ShopMoneyManageActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new ShopMoneyManageFragment(),false);
    }
}
