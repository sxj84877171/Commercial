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
package com.ytmall.activity.mainPage;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.application.WSTMallApplication;
import com.ytmall.fragment.mainPage.MainPageFragment;

import rx.functions.Action1;


public class MainPageActivity extends BaseActivity{
	private static MainPageFragment mainPageFragment;
	private Activity act;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mainPageFragment=new MainPageFragment();
		replaceFragment(mainPageFragment, false);
	}
	public static MainPageFragment getMainPageFragment(){
		return mainPageFragment;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { 
	      //do something here
	    	confirQuit();
	    	return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	private void confirQuit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("确定退出"+getResources().getString(R.string.app_name)+"吗？").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				((WSTMallApplication) getApplication()).exit();
				System.exit(0);
			}
		}).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			}
		});
		// Create the AlertDialog object and return it
		builder.create().show();
	}
	@Override
	protected void onPause() {
		super.onPause();
		((WSTMallApplication)getApplication()).saveCache();
	}
}
