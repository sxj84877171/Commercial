package com.ytmall.activity.wechat;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.bean.WeixinPayBean;
import com.ytmall.fragment.wechat.PayFragment;

/**
 * Created by Administrator on 2016/12/1.
 */
public class WeChatPayActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        WeixinPayBean info = (WeixinPayBean) getIntent().getSerializableExtra("info");
        int isAdd = getIntent().getIntExtra("isAdd",-1);
        replaceFragment(new PayFragment(getIntent().getStringExtra("orderId")
                ,getIntent().getStringExtra("from"),isAdd), false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
