/**
#************************************************
# 项目名称：WSTMall
# 版本号： V1.0  
#************************************************
# 文件说明：
#          
#************************************************
# 子模块说明：
#                     
#************************************************
# 创建人员： 谈泳豪
# 创建人员QQ：1511895018
# 创建日期：2015-8-6
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
*/
package com.ytmall.activity.goods;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.goods.GoodsFragment;


public class GoodsActivity extends BaseActivity{
	public static final String goodsID="GOODSID";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		replaceFragment(new GoodsFragment(getIntent().getStringExtra(goodsID)), false);
	}
	
}
