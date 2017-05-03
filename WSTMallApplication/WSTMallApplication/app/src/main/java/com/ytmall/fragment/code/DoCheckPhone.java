package com.ytmall.fragment.code;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by zhiqiang on 2017/4/29.
 */
@RequestType(type = HttpMethod.GET)
public class DoCheckPhone extends AbstractParam {

    public String a = "doCheckPhone" ;

    public String tokenId ;

    @Override
    public String getA() {
        return a;
    }

    public String phoneCode;

}
