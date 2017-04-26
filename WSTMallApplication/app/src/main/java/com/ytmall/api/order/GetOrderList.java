package com.ytmall.api.order;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;
@RequestType(type = HttpMethod.GET)
public class GetOrderList extends AbstractParam {
	public String a="getOrderList";
	public String tokenId;
	public int status;
	public int p;
	@Override
	public String getA() {
		return a;
	}
}
