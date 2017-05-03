package com.ytmall.fragment.user;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by admin on 2017/4/28.
 */
@RequestType(type = HttpMethod.GET)
public class GetCode extends AbstractParam {
    public String a = "sendBindCode";
    public String tokenId ;
    public String userPhone;

    @Override
    public String getA() {
        return a;
    }
}
