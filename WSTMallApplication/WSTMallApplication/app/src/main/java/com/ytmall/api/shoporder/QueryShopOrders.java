package com.ytmall.api.shoporder;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/1/6.
 */
@RequestType(type = HttpMethod.GET)
public class QueryShopOrders extends AbstractParam {
    public String a = "queryShopOrders";
    public int pcurr;
    // -7:用户取消(受理后-店铺已读) -6:用户取消(已受理后-店铺未读) -5:门店不同意拒收
    // -4:门店同意拒收 -3:用户拒收 -2:未付款的订单 -1：用户取消(未受理前)
    // 0:未受理 1:已受理 2:打包中 3:配送中 4:用户确认收货
    public int statusMark;
    public String tokenId;

    @Override
    public String getA() {
        return a;
    }
}
