package com.ytmall.api.user;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/2/11.
 */
@RequestType(type = HttpMethod.POST)
public class UpdatePhone extends AbstractParam {
    public String a = "updatePhone";
    public String tokenId;
    public String userPhone;

    @Override
    public String getA() {
        return a;
    }
}
