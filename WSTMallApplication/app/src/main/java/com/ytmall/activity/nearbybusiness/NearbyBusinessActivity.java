package com.ytmall.activity.nearbybusiness;
import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.business.NearbyBusinessFragment;


public class NearbyBusinessActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getIntent().getStringExtra("searchContent")==null) {
			replaceFragment(new NearbyBusinessFragment(), false);
		}else{
			replaceFragment(new NearbyBusinessFragment(getIntent().getStringExtra("searchContent")), false);
		}
	}
}
