package com.ytmall.api.pwd;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/1/14.
 */
@RequestType(type = HttpMethod.POST)
public class BackpPwdEdit extends AbstractParam{
    public String a = "sendBindCode";
    public String tokenId;
    public String userPhone;

    @Override
    public String getA() {
        return a;
    }
}
