package com.ytmall.api.recharge;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/2/13.
 */
@RequestType(type = HttpMethod.POST)
public class ToCardRechargeMark extends AbstractParam {
    public String a = "toCardRechargeMark";
    public String tokenId;
    public String card_type;

    @Override
    public String getA() {
        return a;
    }
}
