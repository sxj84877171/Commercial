package com.ytmall.activity.share;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.share.FriendListFragment;

/**
 * Created by lee on 2017/1/17.
 */

public class FriendListActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new FriendListFragment(),false);
    }
}
