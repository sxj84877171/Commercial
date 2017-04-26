package com.ytmall.api.pwd;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/1/16.
 */
@RequestType(type = HttpMethod.POST)
public class CheckCode extends AbstractParam {
    public String  a = "bindPhone";
    public String tokenId;
    public String userPhone;
    public String phoneCode;

    @Override
    public String getA() {
        return a;
    }
}
