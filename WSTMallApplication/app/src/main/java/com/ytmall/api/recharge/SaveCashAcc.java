package com.ytmall.api.recharge;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/1/23.
 */
@RequestType(type = HttpMethod.POST)
public class SaveCashAcc extends AbstractParam {
    public String a = "saveCashAcc";
    public String accTargetId;//所属银行id
    public String id;//提现账号id
    public String accUser;
    public String areaId2;
    public String accNo;
    public String  tokenId;
    public String  branch_bank;//支行

    @Override
    public String getA() {
        return a;
    }
}
