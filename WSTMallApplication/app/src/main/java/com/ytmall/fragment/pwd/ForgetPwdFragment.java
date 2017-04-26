package com.ytmall.fragment.pwd;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ytmall.R;
import com.ytmall.activity.pwd.ChangePayPwdActivity;
import com.ytmall.api.pwd.BackpPwdEdit;
import com.ytmall.api.pwd.CheckCode;
import com.ytmall.application.Const;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.util.StrUtil;

/**
 * Created by lee on 2017/1/13.
 */
@FragmentView(id = R.layout.fragment_forget_pay_pwd)
public class ForgetPwdFragment extends BaseFragment implements View.OnClickListener{
    @InjectView(id = R.id.llBD)
    LinearLayout llBD;
    @InjectView(id = R.id.llUnBd)
    LinearLayout llUnBd;
    @InjectView(id = R.id.etPhone)
    EditText etPhone;
    @InjectView(id = R.id.etForgetCode)
    EditText etCode;
    @InjectView(id = R.id.btnSendCode)
    Button btnSendCode;
    @InjectView(id = R.id.btnForgetSure)
    Button btnForgetSure;
    @InjectView(id = R.id.txtPhone)
    TextView txtPhone;

    private MyCount countDown;
    private BackpPwdEdit codeParam;
    private CheckCode bdParam;

    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(codeParam.getA())){
            //验证码发送成功
            Toast.makeText(getActivity(),"验证码发送成功",Toast.LENGTH_SHORT).show();
            GetCode();

        }else if (url.contains(bdParam.getA())){
            if (Const.user.userPhone.length() > 0){

            }else {
                Const.user.userPhone = etPhone.getText().toString();
            }
            Intent i = new Intent(getActivity(), ChangePayPwdActivity.class);
            i.putExtra("from","ForgetPwdFragment");
            startActivity(i);
            leftClick();


//

        }
    }

    @Override
    protected void flagFailed(String url) {
        super.flagFailed(url);
    }

    @Override
    public void bindDataForUIElement() {
        //是否绑定手机号
        if (StrUtil.null2Str(Const.user.userPhone).length() > 0){
            llBD.setVisibility(View.VISIBLE);
            llUnBd.setVisibility(View.GONE);
            tWidget.setCenterViewText("修改支付密码");
            String phone = Const.user.userPhone;
            String prePhone = phone.substring(0,3);
            String lastPhone = phone.substring(7,phone.length());

            txtPhone.setText(prePhone+"xxxx"+lastPhone);


        }else {
            llUnBd.setVisibility(View.VISIBLE);
            llBD.setVisibility(View.GONE);
            tWidget.setCenterViewText("绑定手机");

        }
        countDown = new MyCount(60000,1000);
        btnSendCode.setOnClickListener(this);
        btnForgetSure.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        final String phone = etPhone.getText().toString();
        switch (v.getId()){

            case R.id.btnSendCode:
                if (Const.user.userPhone.length() > 0){
                    checkCode();


                }else {
                    if (phone.length() > 0){
                        checkCode();
                    }else {
                        Toast.makeText(getActivity(),"请输入手机号码",Toast.LENGTH_SHORT).show();
                    }
                }


                break;
            case R.id.btnForgetSure:
                String code = etCode.getText().toString();
                if (Const.user.userPhone.length() > 0){

                }else {
                    if (phone.length() > 0 ){


                    }else {
                        Toast.makeText(getActivity(),"请输入手机号码",Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

                if (code.length() > 0){

                }else {
                    Toast.makeText(getActivity(),"请输入验证码",Toast.LENGTH_SHORT).show();
                    return;
                }
                //绑定成功
                bdSuccess();
//                if (Const.user.userPhone.length() > 0){
//
//                }else {
//                    bdSuccess();
//                }

                break;
        }
    }
    //获取验证码
    public void GetCode(){
        countDown.start();


    }
    public class MyCount extends CountDownTimer {

        public MyCount(long millisInFuture,long countDownInterval){
            super(millisInFuture,countDownInterval);

        }
        @Override
        public void onTick(long millisUntilFinished) {
            btnSendCode.setClickable(false);
            btnSendCode.setText(millisUntilFinished / 1000 +"s后再次获取");

        }

        @Override
        public void onFinish() {
            btnSendCode.setClickable(true);
            btnSendCode.setText("重新获取");

        }
    }
    private void checkCode(){
        codeParam = new BackpPwdEdit();
        codeParam.tokenId = Const.cache.getTokenId();
        if (Const.user.userPhone.length() > 0){
            codeParam.a = "backpPwdEdit";
        }else {
            codeParam.userPhone = etPhone.getText().toString();
        }

        request(codeParam);

    }
    private void bdSuccess(){
        bdParam = new CheckCode();
        bdParam.tokenId = Const.cache.getTokenId();
        if(Const.user.userPhone.length() > 0){
//            bdParam.userPhone = Const.user.userPhone;
            bdParam.a = "verifyBackPaypwdCode";
        }else {
            bdParam.userPhone = etPhone.getText().toString();
        }

        bdParam.phoneCode = etCode.getText().toString();
        request(bdParam);

    }

    @Override
    protected void bindEvent() {

    }
}
