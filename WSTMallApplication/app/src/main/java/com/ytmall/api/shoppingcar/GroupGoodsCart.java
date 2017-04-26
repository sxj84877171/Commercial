package com.ytmall.api.shoppingcar;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by Administrator on 2016/7/4.
 */
@RequestType(type = HttpMethod.GET)
public class GroupGoodsCart extends AbstractParam {
    public String a="groupGoodsCart";
    public String tokenId;
    @Override
    public String getA() {
        return a;
    }
}
