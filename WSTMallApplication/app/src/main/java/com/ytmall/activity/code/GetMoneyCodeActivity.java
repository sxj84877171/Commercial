package com.ytmall.activity.code;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.code.GetMoneyCodeFragment;

/**
 * Created by lee on 2017/1/7.
 */

public class GetMoneyCodeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new GetMoneyCodeFragment(),false);
    }
}
