package com.ytmall.api.message;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by pzl on 2016/4/11.
 */
@RequestType(type = HttpMethod.GET)
public class DelMessages extends AbstractParam {
    private final String a = "delMessages";
    public String tokenId;
    public String ids;

    @Override
    public String getA() {
        return a;
    }
}
