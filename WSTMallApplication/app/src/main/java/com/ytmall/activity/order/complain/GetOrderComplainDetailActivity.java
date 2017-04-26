package com.ytmall.activity.order.complain;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.order.GetOrderComplainDetailFragment;

/**
 * Created by Administrator on 2016/4/10.
 */
public class GetOrderComplainDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new GetOrderComplainDetailFragment(getIntent().getStringExtra("complainId")),false);
    }
}
