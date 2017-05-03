package com.ytmall.api.order;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by Administrator on 2016/7/26.
 */
@RequestType(type = HttpMethod.GET)
public class CheckUseScore extends AbstractParam {
    public String a="checkUseScore";
    public String tokenId;
    @Override
    public String getA() {
        return a;
    }
}
