package com.ytmall.activity.message;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.brands.BrandsFragment;
import com.ytmall.fragment.message.MessageFragment;

/**
 * Created by Administrator on 2016/4/2.
 */
public class MessageActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new MessageFragment(), false);
    }
}
