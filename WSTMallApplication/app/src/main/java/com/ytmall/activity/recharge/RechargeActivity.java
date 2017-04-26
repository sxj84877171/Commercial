package com.ytmall.activity.recharge;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.recharge.RechargeFragment;

/**
 * Created by lee on 16/12/7.
 */
public class RechargeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new RechargeFragment(),false);
    }
}
