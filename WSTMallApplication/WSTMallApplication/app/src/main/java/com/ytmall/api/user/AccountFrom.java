package com.ytmall.api.user;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/2/11.
 */
@RequestType(type = HttpMethod.GET)
public class AccountFrom extends AbstractParam {
    public String a = "getUserSource";
    public String tokenId;

    @Override
    public String getA() {
        return a;
    }
}
