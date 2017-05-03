package com.ytmall.api.recharge;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/1/22.
 */
@RequestType(type = HttpMethod.POST)
public class UngradetoPay extends AbstractParam {
    public String a = "ungradetoPay";
    public String tokenId;

    @Override
    public String getA() {
        return a;
    }
}
