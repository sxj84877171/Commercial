package com.ytmall.bean;

import java.io.Serializable;

/**
 * Created by lee on 2017/1/13.
 */

public class RechargeRecordBean implements Serializable {
    public String recharge_id;
    public String recharge_sn;
    public String user_id;
    public String recharge_money;
    public String receive_money;
    public String unreceive_money;
    public String add_time;
    public String pay_time;
    public String secend_receive_time;
    public String second_should_receive_time;
    public String recharge_status;
    public String remark;
    public String weipay_sn;
    public String recharge_from;
}
