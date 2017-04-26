package com.ytmall.api.message;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by Administrator on 2016/4/2.
 */
@RequestType(type = HttpMethod.GET)
public class Message extends AbstractParam {
    public String a = "getMessagesNum";
    public String tokenId;
    @Override
    public String getA() {
        return a;
    }
}
