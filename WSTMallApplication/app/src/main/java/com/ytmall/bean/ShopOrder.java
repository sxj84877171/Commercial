package com.ytmall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lee on 2017/1/6.
 */

public class ShopOrder implements Serializable{
    public String userAddress;
    public String createTime;
    public String orderNo;
    public String userId;
    public String userName;
    public int orderStatus;
    public String rejectionRemarks;
    public String totalMoney;
    public String orderId;
    public String realTotalMoney;
    public List<ShopOrderProduct> goodsList;

}
