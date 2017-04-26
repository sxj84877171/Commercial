package com.ytmall.api.order;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;
@RequestType(type = HttpMethod.GET)
public class GetOrdersStatus extends AbstractParam {
	public String a="getOrdersStatus";
	public String tokenId;
	@Override
	public String getA() {
		return a;
	}
}
