package com.ytmall.activity.bank;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;

/**
 * WSTMallApplication
 * 作者： Elvis
 * 时间： 2017/4/26
 * 版本：1.0.0.0
 * 邮编：410000
 * 程序功能：
 * 该类为
 */

public class BankActivity extends BaseActivity {
    public static boolean isNeedRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BankFragment bankFragment = new BankFragment();
        replaceFragment(bankFragment, false);
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        if (isNeedRefresh) {
            BankFragment bankFragment = new BankFragment();
            replaceFragment(bankFragment, false);
            isNeedRefresh = false;
        }

    }

}
