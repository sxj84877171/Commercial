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
# 创建日期：2015-6-11
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
*/
package com.ytmall.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class MyPref {

	private static MyPref instance;
	private SharedPreferences mSharedPref = null;
	private final String APP_NAME = "com.ytmall";
	private final String TOKENID = "TOKENID";
	private final String CACHE_JSON = "CACHE_JSON";
	
	private MyPref(Context context) {
		mSharedPref = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
	}
	
	public static MyPref getInstance(Context context) {
		instance = new MyPref(context);
		return instance;
	}
	
	public void setCache(String cacheJson){
		Editor editor = mSharedPref.edit();
		editor.putString(CACHE_JSON, cacheJson);
		editor.commit();
	}
	
	public String getCache(){
		String cacheJson = mSharedPref.getString(CACHE_JSON, null);
		return cacheJson;
	}
	
	public void setTokenid(String tokenid) {
		Editor editor = mSharedPref.edit();
		editor.putString(TOKENID, tokenid);
		editor.commit();
	}

	public String getTokenid() {
		String tokenid = mSharedPref.getString(TOKENID, null);
		return tokenid;
	}
	
	
}
