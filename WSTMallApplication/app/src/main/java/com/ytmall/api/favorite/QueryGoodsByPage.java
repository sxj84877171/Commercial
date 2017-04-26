package com.ytmall.api.favorite;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by Administrator on 2016/8/1.
 */
@RequestType(type = HttpMethod.GET)
public class QueryGoodsByPage extends AbstractParam {
    private String a = "queryGoodsByPage";
    public String tokenId;
    public int p;

    @Override
    public String getA() {
        return a;
    }
}
