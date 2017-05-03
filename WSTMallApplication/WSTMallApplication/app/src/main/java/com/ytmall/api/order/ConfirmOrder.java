package com.ytmall.api.order;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

@RequestType(type = HttpMethod.GET)
public class ConfirmOrder extends AbstractParam {
	public String a = "confirmOrder";
	public String tokenId;
	public String orderId;
	public String type;
	public String rejectionRemarks;

	@Override
	public String getA() {
		return a;
	}
}
