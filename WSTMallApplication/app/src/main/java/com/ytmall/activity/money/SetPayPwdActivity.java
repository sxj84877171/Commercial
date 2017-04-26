package com.ytmall.activity.money;

import android.os.Bundle;

import com.ytmall.R;
import com.ytmall.sys.BaseAct;

/**
 * Created by lee on 2016/12/28.
 */

public class SetPayPwdActivity extends BaseAct {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pay_pwd);
        initUI();
    }
    private void initUI(){
        titleBar.setTxtTitle("设置支付密码");

    }
}
