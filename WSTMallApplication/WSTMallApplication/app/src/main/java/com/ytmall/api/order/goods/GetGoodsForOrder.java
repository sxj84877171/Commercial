package com.ytmall.api.order.goods;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;
@RequestType(type = HttpMethod.GET)
public class GetGoodsForOrder extends AbstractParam {
	public String a="groupGoodsForOrder";
	public String tokenId;
	public String goodsIds;
	@Override
	public String getA() {
		return a;
	}
}
