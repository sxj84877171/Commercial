package com.ytmall.api.favorite;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by Administrator on 2016/8/1.
 */
@RequestType(type = HttpMethod.GET)
public class Favorite extends AbstractParam {
    private String a = "favorite";
    public String tokenId;//tokenId
    public String id;//关注记录ID
    public String type;//关注类型 0:商品 1:店铺
    @Override
    public String getA() {
        return a;
    }
}
