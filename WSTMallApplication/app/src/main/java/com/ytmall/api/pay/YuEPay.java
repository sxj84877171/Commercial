package com.ytmall.api.pay;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/1/9.
 */
@RequestType(type = HttpMethod.GET)
public class YuEPay extends AbstractParam {
    public String a = "yuePay";
    public String shopId;
    public String sum_money;
    public String tokenId;

    @Override
    public String getA() {
        return a;
    }
}
