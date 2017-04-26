package com.ytmall.api.recharge;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/2/7.
 */
@RequestType(type = HttpMethod.POST)
public class DelCashAcc extends AbstractParam {
    public String a = "delCashAcc";
    public String id;
    public String tokenId;

    @Override
    public String getA() {
        return a;
    }
}
