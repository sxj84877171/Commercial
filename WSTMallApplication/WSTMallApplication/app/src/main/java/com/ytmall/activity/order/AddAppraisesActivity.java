package com.ytmall.activity.order;
import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.brands.BrandsFragment;
import com.ytmall.fragment.business.NearbyBusinessFragment;
import com.ytmall.fragment.order.EvaluationFragment;
import com.ytmall.fragment.order.OrderManagement;


public class AddAppraisesActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		replaceFragment(new EvaluationFragment(getIntent().getIntExtra("orderId", 0)), false);
	}

}
