package com.ytmall.activity.recharge;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/1/23.
 */
@RequestType(type = HttpMethod.GET)
public class RechargeToPay extends AbstractParam {
    public String a = "rechargeToPay";
    public String tokenId;
    public String recharge_id;

    @Override
    public String getA() {
        return a;
    }
}
