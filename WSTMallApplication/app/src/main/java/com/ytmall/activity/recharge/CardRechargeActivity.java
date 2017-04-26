package com.ytmall.activity.recharge;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.recharge.CardRechargeFragment;

/**
 * Created by lee on 2017/1/18.
 */

public class CardRechargeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new CardRechargeFragment(),false);
    }
}
