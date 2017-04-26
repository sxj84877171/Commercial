package com.ytmall.api.shoppingcar;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by Administrator on 2016/7/5.
 */
@RequestType(type = HttpMethod.GET)
public class DelCartGoods extends AbstractParam {
    public String a="delCartGoods";
    public String tokenId;
    public String goodsIds;
    @Override
    public String getA() {
        return a;
    }
}
