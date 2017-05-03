package com.ytmall.activity.recharge;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.recharge.CardRechargeRecordFragment;

/**
 * Created by lee on 2017/1/20.
 */

public class QueryCardListActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new CardRechargeRecordFragment(),false);
    }
}
