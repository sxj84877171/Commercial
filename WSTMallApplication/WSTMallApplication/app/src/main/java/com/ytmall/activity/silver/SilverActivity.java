package com.ytmall.activity.silver;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.recharge.RechargeFragment;

/**
 * WSTMallApplication
 * 作者： Elvis
 * 时间： 2017/4/27
 * 版本：1.0.0.0
 * 银堂宝充值转换Acitivty
 * 保持和其他一致，中间跳转
 */

public class SilverActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new SilverFragment(),false);
    }
}
