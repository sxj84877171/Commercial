package com.ytmall.api.order;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;
@RequestType(type = HttpMethod.GET)
public class GetOrderDetail extends AbstractParam {
	public String a="getOrderDetail";
	public String tokenId;
	public String orderId;
	@Override
	public String getA() {
		return a;
	}
}
