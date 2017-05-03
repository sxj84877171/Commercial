package com.ytmall.activity.money;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.money.TakeMoneyFragment;

/**
 * Created by lee on 2017/1/11.
 */

public class TakeMoneyActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new TakeMoneyFragment(),false);
    }
}
