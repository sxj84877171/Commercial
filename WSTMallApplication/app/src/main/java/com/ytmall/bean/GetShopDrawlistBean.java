package com.ytmall.bean;

import java.io.Serializable;

/**
 * Created by lee on 2017/2/8.
 */

public class GetShopDrawlistBean implements Serializable {
    public String cashId;
    public String targetType;
    public String targetId;
    public String money;
    public String accType;
    public String accTargetId;
    public String accNo;
    public String accUser;
    public int cashSatus;//0未处理1已同意2已拒绝
    public String createTime;
    public String cashRemarks;
    public String cashConfigId;
    public String branch_bank;
    public String bankName;
}
