package com.ytmall.api.user;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/2/11.
 */
@RequestType(type = HttpMethod.POST)
public class UpdateSource extends AbstractParam {
    public String a = "updateSource";
    public String tokenId;
    public String user_source;


    @Override
    public String getA() {
        return a;
    }
}
