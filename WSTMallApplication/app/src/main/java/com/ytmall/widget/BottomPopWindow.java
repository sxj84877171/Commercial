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
# 创建日期：2015-7-23
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
 */
package com.ytmall.widget;

import java.util.ArrayList;
import java.util.List;

import com.ytmall.R;

import android.content.Context;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;


public class BottomPopWindow extends LinearLayout {
	public interface OnMenuSelect {
		public void onItemMenuSelect(int position);

		public void onCancelSelect();
	}

	private OnMenuSelect menuSelectListener;
	List<String> nameList = new ArrayList<String>();
	private Context mContext;
	private LinearLayout contentView;
	private String title;
	private View pop_outside;

	public void setMenuSelectListener(OnMenuSelect menuSelectListener) {
		this.menuSelectListener = menuSelectListener;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getNameList() {
		return nameList;
	}

	public void setMenu(String[] menuName) {
		nameList.clear();
		for (String name : menuName) {
			nameList.add(name);
		}
		notifyDateSetChanged();
	}

	public BottomPopWindow(Context context, OnMenuSelect menuSelect) {
		super(context);
		this.mContext = context;
		this.menuSelectListener = menuSelect;
		LayoutInflater.from(context).inflate(R.layout.bottom_pop_layout, this);
		contentView = (LinearLayout) findViewById(R.id.contentView);
		pop_outside= (View) findViewById(R.id.pop_outside);
		
		LayoutParams p = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		setLayoutParams(p);
	}

	public void notifyDateSetChanged() {
		initButton();
	}

	private void initButton() {
		contentView.removeAllViews();
		if (title != null && title.trim().length() > 0) {

		}
		int textSize = getContext().getResources().getDimensionPixelSize(R.dimen.buttom_pop_text);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(32, 10, 32, 0);
		int i = 0;
		for (String name : nameList) {
			Button button = new Button(mContext);
			button.setText(name);
			button.setLayoutParams(params);
			button.setBackgroundResource(R.color.white);
			button.setId(i);
			i++;
			button.setOnClickListener(onClickListener);
			contentView.addView(button);
		}
		Button button = new Button(mContext);
		button.setText("取消");
		button.setVisibility(View.GONE);
	//	button.setTextSize(textSize);
		button.setTextColor(getResources().getColor(R.color.white));
		button.setLayoutParams(params);
		button.setBackgroundResource(R.drawable.btn_pop_cancel_bg);
		button.setGravity(Gravity.CENTER);
		LayoutParams bottomParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		bottomParams.setMargins(32, 10, 32, 150);
		button.setId(i);
		button.setLayoutParams(bottomParams);
		button.setOnClickListener(onClickListener);
		pop_outside.setOnClickListener(outSideListener);
		contentView.addView(button);
	}
	
	private OnClickListener outSideListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			menuSelectListener.onCancelSelect();
		}
	};

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (menuSelectListener != null) {
					menuSelectListener.onItemMenuSelect(v.getId());
			}
		}
	};
}
