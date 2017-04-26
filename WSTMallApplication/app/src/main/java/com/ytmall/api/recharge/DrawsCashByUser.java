package com.ytmall.api.recharge;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/1/11.
 */
@RequestType(type = HttpMethod.POST)
public class DrawsCashByUser extends AbstractParam {
    public String a = "drawsCashByUser";
    public String drawMoney;
    public String tokenId;

    @Override
    public String getA() {
        return a;
    }
}
