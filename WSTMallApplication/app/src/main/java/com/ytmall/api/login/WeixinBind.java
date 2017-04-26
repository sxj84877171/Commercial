package com.ytmall.api.login;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/2/14.
 */
@RequestType(type = HttpMethod.POST)
public class WeixinBind extends AbstractParam {
    public String a = "weixinBind";
    public String loginName;
    public String loginPwd;
    public String openid;
    public String unionid;

    @Override
    public String getA() {
        return a;
    }
}
