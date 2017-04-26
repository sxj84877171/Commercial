package com.ytmall.activity.message;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.message.MessageDetailFragment;

/**
 * Created by Administrator on 2016/4/10.
 */
public class MessageDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new MessageDetailFragment(getIntent().getStringExtra("id")), false);
    }
}
