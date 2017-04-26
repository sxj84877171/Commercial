package com.ytmall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 投诉详情
 */

public class ComplainDetailbean implements Serializable {
    public String realTotalMoney;
    public String deliverMoney;
    public String orderNo;
    public String orderId;
    public String createTime;
    public String requireTime;

    public String shopName;
    public String shopId;
    public String complainId;
    public int complainType;
    public String complainTargetId;
    public String respondTargetId;
    public String needRespond;
    public String deliverRespondTime;
    public String complainContent;

    public String complainAnnex;
    public int complainStatus;
    public String complainTime;
    public String respondContent;
    public String respondAnnex;
    public String respondTime;
    public String finalResult;
    public String finalResultTime;
    public List<GoodsListBean> goodsList;

}
