package com.ytmall.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/26.
 */
public class UserSoreBean implements Serializable {
    public String userScore; //用户帐号可用积分数
    public String useScore; //本次交易可用积分数
    public double scoreMoney;//积分可抵扣金额
}
