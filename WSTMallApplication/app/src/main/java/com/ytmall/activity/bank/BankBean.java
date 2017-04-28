package com.ytmall.activity.bank;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * WSTMallApplication
 * 作者： Elvis
 * 时间： 2017/4/27
 * 版本：1.0.0.0
 * 邮编：410000
 * 程序功能：
 * 该类为 银行转账
 */
@RequestType(type = HttpMethod.GET)
public class BankBean extends AbstractParam {

    public String a = "transfer";
    public String tokenId;

    //银行流水单号
    public String transfer_sn;

    //银行卡号
    public String card_no ;

    //转账金额
    public int money;

    //开户名
    public String user_name ;

    //转账银行
    public String bank_name ;

    //充值账户  0:商城充值 1:银堂宝充值
    public String type;

    //图片凭证
    public String image;

    @Override
    public String getA() {
        return a;
    }
}
