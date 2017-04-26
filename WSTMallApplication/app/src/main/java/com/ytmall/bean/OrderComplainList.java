package com.ytmall.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/10.
 * "complainId":投诉记录ID,
 "orderId":订单ID,
 "orderNo":订单号,
 "shopId":店铺ID,
 "shopName":店铺名称,
 "complainContent":投诉内容,
 "complainStatus":投诉状态(0:待处理 1:转给应诉人 2:应诉人回应 3:等待仲裁 4:已仲裁)
 "complainTime":投诉时间
 */
public class OrderComplainList implements Serializable {
    public String complainId;
    public String orderId;
    public String orderNo;
    public String shopId;
    public String shopName;
    public String complainContent;
    public int complainStatus;
    public String complainTime;
}
