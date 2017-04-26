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
# 创建日期：2015-6-12
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
*/
package com.ytmall.activity.shoppingCart;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.ytmall.activity.BaseActivity;
import com.ytmall.activity.MainActivity;
import com.ytmall.activity.user.LoginActivity;
import com.ytmall.application.Const;
import com.ytmall.fragment.mainPage.MainPageFragment;
import com.ytmall.fragment.shoppingCart.ShoppingCartFragment;


public class ShoppingCartActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			replaceFragment(new ShoppingCartFragment(), false);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { 
	      //do something here
	    	if(whatFragmentTopNow().equals("com.ytmall.fragment.shoppingCart.ShoppingCartFragment")){
	    		MainActivity.mHost.setCurrentTab(0);
	    		return true;
	    	}
	    	return super.onKeyDown(keyCode, event);
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
}
