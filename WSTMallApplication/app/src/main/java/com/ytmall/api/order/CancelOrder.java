package com.ytmall.api.order;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;
@RequestType(type = HttpMethod.GET)
public class CancelOrder extends AbstractParam {
	public String a="cancelOrder";
	public String tokenId;
	public String orderId;
	public String rejectionRemarks;
	@Override
	public String getA() {
		return a;
	}
}
