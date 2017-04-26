package com.ytmall.api.shoporder;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/1/7.
 */
@RequestType (type = HttpMethod.GET)
public class ShopOrderDetail extends AbstractParam {
    public String a = "getOrderDetails";
    public String tokenId;
    public String orderId;

    @Override
    public String getA() {
        return a;
    }
}
