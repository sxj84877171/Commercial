package com.ytmall.activity.order.complain;

import android.os.Bundle;

import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.order.GetOrderComplainListFragment;
import com.ytmall.util.FragmentView;

/**
 * Created by Administrator on 2016/4/10.
 */
public class GetOrderComplainListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new GetOrderComplainListFragment(),false);
    }
}
