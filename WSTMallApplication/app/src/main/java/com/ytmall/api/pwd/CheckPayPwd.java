package com.ytmall.api.pwd;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/1/14.
 */
@RequestType(type = HttpMethod.POST)
public class CheckPayPwd extends AbstractParam {
    public String a = "verifypPwd";
    public String tokenId;
    public String payPwd;

    @Override
    public String getA() {
        return a;
    }
}
