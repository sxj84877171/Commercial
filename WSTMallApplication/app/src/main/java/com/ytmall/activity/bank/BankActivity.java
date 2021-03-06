package com.ytmall.activity.bank;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.order.OrderManagement;

/**
 * WSTMallApplication
 * 作者： Elvis
 * 时间： 2017/4/26
 * 公司：长沙硕铠电子科技有限公司
 * Email：sunxiangjin@soarsky-e.com
 * 公司网址：http://www.soarsky-e.com
 * 公司地址（Add）  ：湖南省长沙市岳麓区麓谷信息港A座8楼
 * 版本：1.0.0.0
 * 邮编：410000
 * 程序功能：
 * 该类为
 */

public class BankActivity extends BaseActivity {
    public static boolean isNeedRefresh=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new BankFragment(), false);
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        if(isNeedRefresh) {
            replaceFragment(new BankFragment(), false);
            isNeedRefresh=false;
        }

    }
}
