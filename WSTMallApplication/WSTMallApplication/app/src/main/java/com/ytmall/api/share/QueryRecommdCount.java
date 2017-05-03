package com.ytmall.api.share;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/1/18.
 */
@RequestType(type = HttpMethod.GET)
public class QueryRecommdCount extends AbstractParam {
    public String a = "queryRecommdCount";
    public String tokenId;

    @Override
    public String getA() {
        return a;
    }
}
