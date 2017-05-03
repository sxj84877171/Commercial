package com.ytmall.activity.user;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.user.UpdateUserFragment;

/**
 * Created by lee on 2017/1/22.
 */

public class UpdateUserActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new UpdateUserFragment(),false);
    }
}
