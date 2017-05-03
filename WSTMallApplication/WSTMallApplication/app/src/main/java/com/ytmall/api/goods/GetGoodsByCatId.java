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
# 创建日期：2015-6-26
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
*/
package com.ytmall.api.goods;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;


@RequestType(type = HttpMethod.GET)
public class GetGoodsByCatId extends AbstractParam{
	
	private String a = "getGoodsByCatId";
	
	public String key;//查询的商品关键字-查询的时候会用到
	public int goodsCatId;//第三层的分类ID
	public int goodsCatId1;//第一层商品分类ID，查询条件
	public int goodsCatId2;//第二层商品分类ID，查询条件
	public String areaId2;
	public int desc;//排序类型 0-价格 1-销售量 2:-好评度
	public int descType;//排序方式 0-升序 1-倒序
	public double latitude;//纬度
	public double longitude;//经度
	public String brandId;//品牌ID ，查询条件
	public int p;//页码

	@Override
	public String getA() {
		return a;
	}
	
}
