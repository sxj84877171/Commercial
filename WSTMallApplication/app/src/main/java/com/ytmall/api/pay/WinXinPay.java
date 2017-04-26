package com.ytmall.api.pay;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by Administrator on 2016/11/7.
 */
@RequestType(type = HttpMethod.GET)
public class WinXinPay extends AbstractParam {
    private String a = "winXinPay";
    public String tokenId;
    public String orderId;

    @Override
    public String getA() {
        return a;
    }

}
