package com.ytmall.api.order;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by Administrator on 2016/4/10.
 * tokenId:tokenId
 *orderNo:订单号
 * p:页码
 */
@RequestType(type = HttpMethod.GET)
public class GetComplainsForUser extends AbstractParam {
    public String a="getComplainsForUser";
    public String tokenId;
    public String orderNo;
    public int p;
    @Override
    public String getA() {
        return a;
    }
}
