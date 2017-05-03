package com.ytmall.api.recharge;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/1/10.
 */
@RequestType(type = HttpMethod.GET)
public class RechargeList extends AbstractParam {
    public String a = "queryRechargeList";
    public int currPage;
    public String tokenId;

    @Override
    public String getA() {
        return a;
    }
}