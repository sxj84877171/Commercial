package com.ytmall.activity.brands;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.brands.BrandsFragment;
import com.ytmall.fragment.business.NearbyBusinessFragment;


public class BrandsActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		replaceFragment(new BrandsFragment(), false);
	}

}
