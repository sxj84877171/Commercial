package com.ytmall.activity.recharge;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.recharge.RechargeOrderFragment;

/**
 * Created by lee on 2017/1/10.
 */

public class RechargeOrderActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String money = getIntent().getStringExtra("money");
        String orderNo = getIntent().getStringExtra("orderNo");
        String rechargeId = getIntent().getStringExtra("rechargeId");
        replaceFragment(new RechargeOrderFragment(money,orderNo,rechargeId),false);
    }
}
