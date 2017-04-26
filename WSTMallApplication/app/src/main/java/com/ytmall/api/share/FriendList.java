package com.ytmall.api.share;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/1/17.
 */
@RequestType(type = HttpMethod.GET)
public class FriendList extends AbstractParam {
    public String a = "queryRecommdByList";
    public String tokenId;
    public int currPage;

    @Override
    public String getA() {
        return a;
    }
}
