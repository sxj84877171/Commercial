package com.ytmall.api.message;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by Administrator on 2016/4/3.
 */
@RequestType(type = HttpMethod.GET)
public class MessageList extends AbstractParam {
    private final String a = "getMessages";
    public String tokenId;
    public int p;

    @Override
    public String getA() {
        return a;
    }
}
