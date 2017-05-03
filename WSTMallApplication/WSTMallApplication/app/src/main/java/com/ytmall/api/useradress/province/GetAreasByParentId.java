package com.ytmall.api.useradress.province;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

@RequestType(type = HttpMethod.GET)
public class GetAreasByParentId extends AbstractParam {
	public String a="getAreasByParentId";

	public String parentId;
	public String tokendId;
	@Override
	public String getA() {
		return a;
	}
}
