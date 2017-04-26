package com.ytmall.activity.order;
import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.brands.BrandsFragment;
import com.ytmall.fragment.business.NearbyBusinessFragment;
import com.ytmall.fragment.order.OrderManagement;


public class OrderActivity extends BaseActivity {
	public static boolean isNeedRefresh=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		replaceFragment(new OrderManagement(getIntent().getIntExtra("orderType", 0)), false);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		if(isNeedRefresh) {
			replaceFragment(new OrderManagement(), false);
			isNeedRefresh=false;
		}

	}
	
}
