package com.ytmall.api.pwd;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/1/11.
 */
@RequestType(type = HttpMethod.GET)
public class EditpayPwd extends AbstractParam {
    public String a = "editpayPwd";
    public int type;
    public String tokenId;
    public String orpayPwd;
    public String payPwd;
    public String copayPwd;

    @Override
    public String getA() {
        return a;
    }
}
