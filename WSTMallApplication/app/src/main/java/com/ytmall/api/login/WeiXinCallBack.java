package com.ytmall.api.login;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/2/14.
 */
@RequestType(type = HttpMethod.POST)
public class WeiXinCallBack extends AbstractParam {
    public String a = "weiXinCallBack";
    public String unionid;
    public String openid;

    @Override
    public String getA() {
        return a;
    }
}
