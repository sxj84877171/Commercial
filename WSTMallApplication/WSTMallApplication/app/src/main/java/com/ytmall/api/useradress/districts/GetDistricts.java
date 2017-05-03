package com.ytmall.api.useradress.districts;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;
@RequestType(type = HttpMethod.GET)
public class GetDistricts extends AbstractParam {
	public String a="getDistricts";

	public String areaId2;//城市ID
	@Override
	public String getA() {
		return a;
	}
}
