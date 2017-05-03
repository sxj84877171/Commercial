package com.ytmall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lee on 2017/1/7.
 */

public class ShopOrderDetailBean implements Serializable {
    public List<ShopOrderProduct> goodsList;
    public ShopOrderBean order;
    public List<OrderDetailLog> logs;
}
