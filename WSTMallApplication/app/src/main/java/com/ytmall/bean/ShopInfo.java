package com.ytmall.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/5.
 */
public class ShopInfo implements Serializable {
    public double totalPrice;//同一店铺商品总价钱
    public boolean cbgroup;
    public String 	shopId;//店铺ID,
    public String 	shopName;//店铺名称,
    public String 	deliveryType;///配送方式 0-商城配送 1-门店配送,
    public String 	shopAtive;//店铺状态 1-营业中 0-休息中,
    public String 	deliveryTime;//配送服务时间,
    public String 	isInvoice;//是否可开发票,
    public String 	deliveryStartMoney;//店铺起送价格(要满足这个价格才允许下单),
    public double deliveryFreeMoney;//包邮起步价，
    public String  deliveryMoney;//配送费,
    public String 	serviceStartTime;//店铺服务开始时间,
    public String 	serviceEndTime;//店铺服务结束时间
}
