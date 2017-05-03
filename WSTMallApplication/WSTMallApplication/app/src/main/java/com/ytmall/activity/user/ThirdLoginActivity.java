package com.ytmall.activity.user;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.login.ThirdLoginFragment;

/**
 * Created by lee on 2017/2/14.
 */

public class ThirdLoginActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String openid = getIntent().getStringExtra("openid");
        String unionid = getIntent().getStringExtra("unionid");
        replaceFragment(new ThirdLoginFragment(openid,unionid,ThirdLoginFragment.fromMainActivity),false);
    }
}
