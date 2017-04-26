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
# 创建日期：2015-7-30
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
*/
package com.ytmall.api.goods;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;


@RequestType(type = HttpMethod.GET)
public class GetGoodsDesc extends AbstractParam{
	
	private String a = "getGoodsDesc";
	
	public String id;

	@Override
	public String getA() {
		return a;
	}
	
}
