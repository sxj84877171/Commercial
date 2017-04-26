package com.ytmall.api.recharge;

import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

/**
 * Created by lee on 2017/1/18.
 */
@RequestType(type = HttpMethod.GET)
public class AddCardByUser extends AbstractParam {
    public String a = "addCardByUser";
    public int card_type;//0:话费卡 1 油卡 2水费 3:电费

    public String tokenId;
    public String money;
    public String remark;

    @Override
    public String getA() {
        return a;
    }
}
