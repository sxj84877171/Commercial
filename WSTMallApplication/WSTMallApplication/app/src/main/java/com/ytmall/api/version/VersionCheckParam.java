package com.ytmall.api.version;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2016/12/22.
 */
@RequestType(type = HttpMethod.GET)
public class VersionCheckParam extends AbstractParam {
    public String a = "getAppVersion";


    @Override
    public String getA() {
        return a;
    }

}
