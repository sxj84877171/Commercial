package com.ytmall.activity.money;

import android.os.Bundle;

import com.ytmall.activity.BaseActivity;
import com.ytmall.bean.GetCashConfBean;
import com.ytmall.fragment.money.EditBankAccountFragmnet;
import com.ytmall.util.StrUtil;

/**
 * Created by lee on 2017/1/23.
 */

public class EditBankAccountActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id = getIntent().getStringExtra("id");

        replaceFragment(new EditBankAccountFragmnet(id),false);

    }
}
