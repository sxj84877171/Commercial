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
# 创建日期：2015-8-26
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
*/
package com.ytmall.activity.nearbybusiness;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.business.BusinessHomeFragment;


public class ToSelfSupermarketActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		replaceFragment(new BusinessHomeFragment(BusinessHomeFragment.FromSelfSupermarket), false);
	}
	
}
