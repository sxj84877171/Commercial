package com.ytmall.api.addorder;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by Administrator on 2016/7/5.
 */
@RequestType(type = HttpMethod.GET)
public class GroupGoodsForOrder extends AbstractParam {
    public String a="groupGoodsForOrder";
    public String tokenId;
    @Override
    public String getA() {
        return a;
    }
}
