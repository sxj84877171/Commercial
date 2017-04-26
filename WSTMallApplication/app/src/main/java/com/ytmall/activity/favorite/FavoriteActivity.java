package com.ytmall.activity.favorite;

import android.app.Activity;
import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.favorite.FavoriteFragment;

/**
 * Created by Administrator on 2016/7/26.
 */
public class FavoriteActivity extends BaseActivity {
    int i;
    Integer fi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new FavoriteFragment(),false);

    }
}
