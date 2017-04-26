package com.ytmall.api.brands;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/1/20.
 */
@RequestType(type = HttpMethod.GET)
public class FetchAds extends AbstractParam {
//    public String a = "getAds";
    public String a = "fetchAds";
//    public String tokenId;
    public int adPositionId;

    @Override
    public String getA() {
        return a;
    }
}
