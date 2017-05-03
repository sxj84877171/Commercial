package com.ytmall.fragment.user;

import android.view.View;
import android.widget.LinearLayout;

import com.ytmall.R;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.fragment.pwd.ChangePayPwdFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;

/**
 * Created by lee on 2017/1/14.
 */
@FragmentView(id = R.layout.fragment_change_pwds)
public class ChangePwdsFragment extends BaseFragment {
    @InjectView(id = R.id.llChangePayPwd)
    LinearLayout llChangePayPwd;
    @InjectView(id = R.id.llChangeLoginPwd)
    LinearLayout llChangeLoginPwd;
    @InjectView(id = R.id.llChangePhone)
    LinearLayout llChangePhone;
    @Override
    public void bindDataForUIElement() {
        llChangePayPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ChangePayPwdFragment("ChangePwdsFragment"),true);
            }
        });

        llChangeLoginPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AccountSecurityFragment(),true);
            }
        });

        llChangePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ChangePhoneFragment(),true);
            }
        });

    }

    @Override
    protected void bindEvent() {

    }
}
