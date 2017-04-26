package com.ytmall.activity.pwd;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.pwd.ChangePayPwdFragment;

/**
 * Created by lee on 2017/1/11.
 */

public class ChangePayPwdActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String from = getIntent().getStringExtra("from");
        replaceFragment(new ChangePayPwdFragment(from),false);
    }
}
