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
        String payment_type = getIntent().getStringExtra("payment_type");
        String strType = getIntent().getStringExtra("type");
        int type = 1 ;
        try{
            type = Integer.parseInt(strType);
        }catch (Exception ex){}

        replaceFragment(new RechargeOrderFragment(money,orderNo,rechargeId,payment_type,type),false);
    }
}
