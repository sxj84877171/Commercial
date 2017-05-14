package com.ytmall.api.recharge;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/1/10.
 */
@RequestType(type = HttpMethod.POST)
public class RechargeOrder extends AbstractParam {
    public String a = "recharge";
    public String account;
    public String remark;
    public String tokenId;
    public String recharge_id;
    public String payment_type;

    @Override
    public String getA() {
        return a;
    }
}
