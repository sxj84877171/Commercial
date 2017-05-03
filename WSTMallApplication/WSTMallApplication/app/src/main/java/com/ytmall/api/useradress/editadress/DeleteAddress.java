package com.ytmall.api.useradress.editadress;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

@RequestType(type = HttpMethod.GET)
public class DeleteAddress extends AbstractParam {
	public String a = "delAddress";
	public String tokenId;// 个人ID
	public String id;
	@Override
	public String getA() {
		return a;
	}
}
