package com.ytmall.api.shoppingcar;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by Administrator on 2016/7/4.
 */
@RequestType(type = HttpMethod.GET)
public class AddToCart extends AbstractParam {
    public String a="addToCart";
    public String tokenId;
    public String goodsId;//商品ID
    public String goodsAttrId;//商品属性
    public String goodsNum;//商品数量

    @Override
    public String getA() {
        return a;
    }
}
