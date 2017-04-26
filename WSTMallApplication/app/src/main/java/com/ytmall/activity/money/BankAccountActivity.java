package com.ytmall.activity.money;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.money.BankAccountFragment;

/**
 * Created by lee on 2017/1/23.
 */

public class BankAccountActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String from = getIntent().getStringExtra("from");
        replaceFragment(new BankAccountFragment(from),false);
    }
}
