package com.ytmall.activity.money;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.money.ShopCashRecordFragment;

/**
 * Created by lee on 2017/2/7.
 */

public class ShopCashRecordActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new ShopCashRecordFragment(),false);
    }
}
