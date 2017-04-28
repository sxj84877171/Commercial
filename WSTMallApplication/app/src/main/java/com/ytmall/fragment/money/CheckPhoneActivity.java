package com.ytmall.fragment.money;

import android.app.Activity;
import android.os.Bundle;

import com.ytmall.activity.BaseActivity;

/**
 * Created by admin on 2017/4/29.
 */

public class CheckPhoneActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new CheckPhoneFragment(),false);
    }
}
