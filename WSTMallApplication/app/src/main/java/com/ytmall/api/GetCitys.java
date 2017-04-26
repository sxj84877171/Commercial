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
package com.ytmall.api;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;


@RequestType(type = HttpMethod.GET)
public class GetCitys extends AbstractParam{
	
	private String a = "getCitys";
	
	public String key;//城市名称
	public String key2;//县区名称，可以不填

	@Override
	public String getA() {
		return a;
	}
	
}
