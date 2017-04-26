package com.ytmall.api.recharge;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/1/23.
 */
@RequestType(type = HttpMethod.GET)
public class GetShopdrawsList extends AbstractParam {
    public String a = "getShopdrawsList";
    public int currPage;
    public String tokenId;

    @Override
    public String getA() {
        return a;
    }
}
