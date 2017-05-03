package com.ytmall.api.recharge;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/1/20.
 */
@RequestType(type = HttpMethod.GET)
public class QueryCardsList extends AbstractParam {
    public String a = "queryCardsList";
    public String tokenId;
    public int currPage;

    @Override
    public String getA() {
        return a;
    }
}
