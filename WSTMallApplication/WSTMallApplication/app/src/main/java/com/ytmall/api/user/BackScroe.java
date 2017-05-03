package com.ytmall.api.user;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/2/11.
 */
@RequestType(type = HttpMethod.POST)
public class BackScroe extends AbstractParam {
    public String a = "updateAccount";
    public String tokenId;
    public String user_link_account;

    @Override
    public String getA() {
        return a;
    }
}
