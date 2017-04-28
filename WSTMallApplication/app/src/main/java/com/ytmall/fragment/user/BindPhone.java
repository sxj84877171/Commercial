package com.ytmall.fragment.user;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by admin on 2017/4/28.
 */

@RequestType(type = HttpMethod.GET)
public class BindPhone extends AbstractParam {
    public String a;
    public String tokenId;
    public String userPhone ;
    public String phoneCode ;

    public BindPhone(String a, String tokenId, String userPhone, String phoneCode) {
        this.a = a;
        this.tokenId = tokenId;
        this.userPhone = userPhone;
        this.phoneCode = phoneCode;
    }

    @Override
    public String getA() {
        return a;
    }
}