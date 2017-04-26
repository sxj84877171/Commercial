package com.ytmall.api.order;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by Administrator on 2016/4/10.
 */
@RequestType(type = HttpMethod.GET)
public class GetComplainDetail extends AbstractParam {
    public String a="getComplainDetail";
    public String tokenId;
    public String id;
    @Override
    public String getA() {
        return a;
    }
}
