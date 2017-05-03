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
# 创建日期：2015-6-30
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
*/
package com.ytmall.activity.widget;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.widget.HtmlViewFragment;

import android.os.Bundle;


public class HtmlViewActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String title = getIntent().getStringExtra("title");
		String Url  = getIntent().getStringExtra("Url");
		HtmlViewFragment htmlViewFragment = new HtmlViewFragment(Url,title);
		replaceFragment(htmlViewFragment, false);
	}	
}