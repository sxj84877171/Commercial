package com.ytmall.activity.order.goods;
import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.brands.BrandsFragment;
import com.ytmall.fragment.business.NearbyBusinessFragment;
import com.ytmall.fragment.order_goods.OrderForGoodsFragment;


public class OrderForGoodsActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		double totalprice=getIntent().getDoubleExtra("totalprice",0);
		replaceFragment(new OrderForGoodsFragment(), false);
	}
}
