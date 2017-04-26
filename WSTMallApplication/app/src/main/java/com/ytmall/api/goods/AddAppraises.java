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
# 创建日期：2015-8-3
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
*/
package com.ytmall.api.goods;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;


@RequestType(type = HttpMethod.GET)
public class AddAppraises extends AbstractParam{
	
	private String a = "addAppraises";
	
	public String tokenId;
	public String orderId;//订单ID
	public String goodsId;//商品ID
	public String goodsAttrId;
	public int goodsScore=0;//商品评分（1~5）
	public int timeScore=0;//时效评分（1~5）
	public int serviceScore=0;//服务评分（1~5）
	public String content;//评价详情(至少10个字)

	@Override
	public String getA() {
		return a;
	}
	
}
