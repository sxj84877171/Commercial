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
# 创建日期：2015-8-10
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
*/
package com.ytmall.adapter;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.widget.BaseAdapter;

/**
 * @ClassName BaseAdapter
 * @Create Date 2015-8-10 下午11:04:56 
 * 
 * @author Tans      QQ     1511895018
 * WSTMall开源商城-合作团队     官网地址:http://www.wstmall.com   联系QQ:707563272
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
	protected List<T> modelList;
	protected Context mContext;
	DecimalFormat df0 = new DecimalFormat("0.0");
	DecimalFormat df00 = new DecimalFormat("0.00");

	public MyBaseAdapter(Context context, List<T> list) {
		this.mContext = context;
		this.modelList = list;
	}

	@Override
	public int getCount() {
		return modelList.size();
	}

	public List<T> getModelList() {
		return modelList;
	}

	@Override
	public T getItem(int position) {
		return modelList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
