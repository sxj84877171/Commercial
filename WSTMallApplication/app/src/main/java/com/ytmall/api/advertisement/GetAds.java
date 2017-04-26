package com.ytmall.api.advertisement;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

@RequestType(type = HttpMethod.GET)
public class GetAds extends AbstractParam {
	private String a="getAds";
	public String areaId2;
	public int adPositionId;
	@Override
	public String getA() {
		return a;
	}
}
