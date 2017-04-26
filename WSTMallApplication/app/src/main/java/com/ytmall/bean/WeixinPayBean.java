package com.ytmall.bean;

import java.io.Serializable;

/**
 * Created by lee on 2017/2/9.
 */

public class WeixinPayBean implements Serializable {
    public String appid;
    public String partnerid;
    public String prepayid;
    public String sign;
    public String noncestr;
    public String timestamp;


}
