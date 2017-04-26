package com.ytmall.api.useradress.community;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;
@RequestType(type = HttpMethod.GET)
public class GetCommunitys extends AbstractParam {
	public String a="getCommunitys";
	public String areaId3;//县区ID
	@Override
	public String getA() {
		return a;
	}
}
