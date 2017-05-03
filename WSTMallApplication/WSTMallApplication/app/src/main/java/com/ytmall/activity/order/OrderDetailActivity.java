package com.ytmall.activity.order;
import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.order.OrderDetailFragment;


public class OrderDetailActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		replaceFragment(new OrderDetailFragment(), false);
	}
}
