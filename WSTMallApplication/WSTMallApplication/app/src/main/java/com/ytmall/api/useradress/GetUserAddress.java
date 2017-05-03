package com.ytmall.api.useradress;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;
@RequestType(type = HttpMethod.GET)
public class GetUserAddress extends AbstractParam {
	public String a="getUserAddress";
	public String tokenId;//个人ID
	@Override
	public String getA() {
		return a;
	}
}
