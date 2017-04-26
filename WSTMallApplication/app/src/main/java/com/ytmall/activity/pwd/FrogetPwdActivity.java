package com.ytmall.activity.pwd;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.pwd.ForgetPwdFragment;

/**
 * Created by lee on 2017/1/13.
 */

public class FrogetPwdActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new ForgetPwdFragment(),false);
    }
}
