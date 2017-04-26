package com.ytmall.api.business.home;


import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;
/*isSelfShop:是否自营店0-不是 1-是
shopId:店铺ID 如果是获取自营店则不用传
areaId2:城市ID*/
@RequestType(type = HttpMethod.GET)
public class GetShopInfo extends AbstractParam {
	private String a = "getShopInfo";
	public String tokenId;
	public int isSelfShop;
	public String shopId;
	public String areaId2;
	@Override
	public String getA() {
		return a;
	}
}
