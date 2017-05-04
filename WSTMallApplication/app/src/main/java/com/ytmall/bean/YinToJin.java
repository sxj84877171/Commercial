package com.ytmall.bean;

import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * WSTMallApplication
 * 作者： Elvis
 * 时间： 2017/5/4
 * 版本：1.0.0.0
 * 邮编：410000
 * 程序功能：
 * 该类为
 */
@RequestType(type = HttpMethod.GET)
public class YinToJin extends AbstractParam {

    public String a = "getYinMoneyToJinMoneyRate" ;
    public String tokenId;

    @Override
    public String getA() {
        return a;
    }
}
