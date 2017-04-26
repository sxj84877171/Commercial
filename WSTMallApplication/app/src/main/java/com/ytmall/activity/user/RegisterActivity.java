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
# 创建日期：2015-8-20
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
*/
package com.ytmall.activity.user;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.login.LoginFragment;
import com.ytmall.fragment.login.RegisterFragment;
import com.ytmall.fragment.login.YTRegisterFragment;


public class RegisterActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		replaceFragment(new YTRegisterFragment(), false);
//		replaceFragment(new RegisterFragment(), false);
	}
	
}
