package com.ytmall.api.recommendgoods;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;
@RequestType(type = HttpMethod.GET)
public class GetGoodsCatAndGoods extends AbstractParam {
	public String a="getGoodsCatAndGoods";
	public String areaId2;
	@Override
	public String getA() {
		return a;
	}
}
