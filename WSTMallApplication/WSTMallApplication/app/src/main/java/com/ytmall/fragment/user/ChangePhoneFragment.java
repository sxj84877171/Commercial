package com.ytmall.fragment.user;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.umeng.socialize.log.crash.ExceptionHandler;
import com.ytmall.R;
import com.ytmall.activity.CountDownTimerButton;
import com.ytmall.api.user.UpdatePhone;
import com.ytmall.application.Const;
import com.ytmall.bean.AbstractParam;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

import org.json.JSONObject;

/**
 * Created by lee on 2017/2/11.
 */
@FragmentView(id = R.layout.fragment_change_phone)
public class ChangePhoneFragment extends BaseFragment {

    private GetCode getCode = new GetCode();




    private UpdatePhone param = new UpdatePhone();

    @InjectView(id = R.id.etPhone)
    EditText etPhone;

    @InjectView(id = R.id.code)
    EditText code;

    @InjectView(id = R.id.get_code)
    CountDownTimerButton get_code;

    @InjectView(id = R.id.submit_button)
    Button submit_button;

    @Override
    protected void bindEvent() {
        get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetCode getCode = new GetCode();
                if (TextUtils.isEmpty(Const.user.userPhone)) {
                    getCode.a = "sendBindCode" ;
                }else{
                    getCode.a = "sendUnbindCode" ;
                }
                getCode.userPhone = etPhone.getText().toString();
                getCode.tokenId = Const.cache.getTokenId();
                if (TextUtils.isEmpty(getCode.userPhone)){
                    Toast.makeText(getActivity(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                    get_code.stop();
                    return;
                }

                requestNoDialog(getCode);
            }
        });
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etPhone.getText().toString();
                String c = code.getText().toString();
                if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(c)) {
                    updatePhone();
                } else {
                    Toast.makeText(getActivity(), "请输入完整", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void bindDataForUIElement() {
//        tWidget.setRightBtnText("确定");
        if (TextUtils.isEmpty(Const.user.userPhone)) {
            etPhone.setText("");
            etPhone.setEnabled(true);
            etPhone.setClickable(true);
            tWidget.getRightButton().setText("绑定");
            submit_button.setText("绑  定");
        } else {
            etPhone.setText(Const.user.userPhone);
            etPhone.setEnabled(false);
            etPhone.setClickable(false);
            tWidget.getRightButton().setText("解绑");
            submit_button.setText("解  绑");
        }
        tWidget.getRightButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etPhone.getText().toString();
                String c = code.getText().toString();
                if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(c)) {
                    updatePhone();
                } else {
                    Toast.makeText(getActivity(), "请输入完整", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void requestSuccess(String url, String data) {
        if(url.contains("indCode")){
            Toast.makeText(getActivity(), "短信发送成功", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
            if (TextUtils.isEmpty(Const.user.userPhone)) {
                try{
                    JSONObject jsonobj = new JSONObject(data);
                    Const.user.userPhone = etPhone.getText().toString();
                }catch (Exception ex){}
            }else{
                Const.user.userPhone = "" ;
            }
            leftClick();
        }

    }

    private void updatePhone() {
        String userPhone = etPhone.getText().toString();
        String phoneCode = code.getText().toString();
        BindPhone bindPhone = null;
        if (TextUtils.isEmpty(Const.user.userPhone)) {
            bindPhone = new BindPhone("bindPhone",Const.cache.getTokenId(),userPhone,phoneCode);
        }else{
            bindPhone = new BindPhone("unBindPhone",Const.cache.getTokenId(),userPhone,phoneCode);
        }
        request(bindPhone);
    }

    @Override
    protected void flagFailed(String url) {
        super.flagFailed(url);
        get_code.stop();
    }
}
