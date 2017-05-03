package com.ytmall.api.shoporder;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/1/7.
 */
@RequestType (type = HttpMethod.GET)
public class ShopOrderAccept extends AbstractParam {
    public String a = "shopOrderAccept";
    public String tokenId;
    public String orderId;

    @Override
    public String getA() {
        return a;
    }
}
