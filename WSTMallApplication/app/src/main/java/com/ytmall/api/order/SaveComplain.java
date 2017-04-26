package com.ytmall.api.order;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by Administrator on 2016/4/6.
 * tokenId:tokenId
 *orderId:订单ID
 * complainType:投诉类型 （1:承诺的没有做到 2:未按约定时间发货 3:未按成交价格进行交易 4:恶意骚扰）
 *complainContent:投诉内容
 */
@RequestType(type = HttpMethod.GET)
public class SaveComplain extends AbstractParam {
    public String a="saveComplain";
    public String tokenId;
    public String orderId;
    public int complainType;
    public String complainContent;
    public String complainAnnex;
    @Override
    public String getA() {
        return a;
    }
}
