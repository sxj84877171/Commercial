package com.ytmall.api.message;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by Administrator on 2016/4/4.
 */
@RequestType(type = HttpMethod.GET)
public class GetMessageContent extends AbstractParam {
    private final String a = "getMessageContent";
    public String tokenId;
    public String id;

    @Override
    public String getA() {
        return a;
    }
}
