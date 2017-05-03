package com.ytmall.api.recharge;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/1/23.
 */
@RequestType(type = HttpMethod.POST)
public class DrawsCashByShop extends AbstractParam {
    public String a = "drawsCashByShop";
    public String tokenId;
    public String configId;
    public String drawMoney;

    @Override
    public String getA() {
        return a;
    }
}
