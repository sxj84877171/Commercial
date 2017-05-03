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
package com.ytmall.api.goods;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;


@RequestType(type = HttpMethod.GET)
public class GetAppraises extends AbstractParam{
	
	private String a = "getAppraises";
	
	public String id;//商品ID
	public int type;//评论类型 0:全部 1:差评 2:中评 3:好评 
	public int p;

	@Override
	public String getA() {
		return a;
	}
	
}
