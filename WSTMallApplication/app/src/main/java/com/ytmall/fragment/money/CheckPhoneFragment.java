package com.ytmall.fragment.money;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ytmall.R;
import com.ytmall.activity.CountDownTimerButton;
import com.ytmall.activity.money.ShopTakeMoneyActivity;
import com.ytmall.application.Const;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.fragment.user.GetCode;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.util.StrUtil;
import com.ytmall.widget.EditTextBar;

import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * Created by admin on 2017/4/29.
 */
@FragmentView(id = R.layout.fragment_check_phone)
public class CheckPhoneFragment extends BaseFragment implements View.OnClickListener {
    @InjectView(id = R.id.et_phone_info)
    TextView et_phone_info;
    @InjectView(id = R.id.et_phone_code)
    EditText et_phone_code;
    @InjectView(id = R.id.get_code)
    CountDownTimerButton get_code;
    @InjectView(id = R.id.btnComfirm)
    Button btnComfirm;

    private String phoneNum ;
    @Override
    public void bindDataForUIElement() {
        phoneNum = Const.user.userPhone ;
        et_phone_info.setText("你绑定的手机号码为:" + changeToStar(Const.user.userPhone));
    }

    private String changeToStar(String phoneN){
        phoneN = phoneN.replace(" ","").trim();
        if(phoneN.length() >= 11){
            phoneN = phoneN.substring(0,phoneN.length() - 8) + "****" + phoneN.substring(phoneN.length() -4);
        }else{
            phoneN = "***********" ;
        }
        return phoneN;
    }

    @Override
    protected void bindEvent() {
        get_code.setOnClickListener(this);
        btnComfirm.setOnClickListener(this);
    }

    @Override
    protected void requestSuccess(String url, String data) {
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.get_code){
            GetCode getCode = new GetCode();
            if (TextUtils.isEmpty(Const.user.userPhone)) {
                getCode.a = "sendBindCode" ;
            }else{
                getCode.a = "sendUnbindCode" ;
            }
            getCode.userPhone =phoneNum ;
            if (TextUtils.isEmpty(getCode.userPhone)){
                Toast.makeText(getActivity(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                get_code.stop();
                return;
            }

            requestNoDialog(getCode);
        }
        if(v.getId() == R.id.btnComfirm){
            getActivity().startActivity(new Intent(getActivity(), ShopTakeMoneyActivity.class));
        }
    }
}
