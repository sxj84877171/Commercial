package com.ytmall.activity.share;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.share.RecommendListFragment;

/**
 * Created by lee on 2017/1/17.
 */

public class RecommendListActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String money = getIntent().getStringExtra("money");
        replaceFragment(new RecommendListFragment(money),false);
    }
}
