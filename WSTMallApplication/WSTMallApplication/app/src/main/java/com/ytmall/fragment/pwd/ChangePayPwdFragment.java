package com.ytmall.fragment.pwd;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ytmall.R;
import com.ytmall.activity.pwd.FrogetPwdActivity;
import com.ytmall.api.pwd.EditpayPwd;
import com.ytmall.application.Const;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.util.StrUtil;

import org.json.JSONObject;

/**
 * Created by lee on 2017/1/11.
 */
@FragmentView(id = R.layout.fragment_change_pay_pwd)
public class ChangePayPwdFragment extends BaseFragment implements View.OnClickListener{
    @InjectView(id = R.id.etOringalPwd)
    EditText etOringalPwd;
    @InjectView(id = R.id.etNewPwd)
    EditText etNewPwd;
    @InjectView(id = R.id.etConfrimPwd)
    EditText etConfrimPwd;
    @InjectView(id = R.id.btnChangePwdSure)
    Button btnChangePwdSure;
    @InjectView(id = R.id.txtForgetPwd)
    TextView txtForgetPwd;
    private String from;
    private EditpayPwd param;
    public ChangePayPwdFragment(String from){
        this.from = from;

    }

    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(param.getA())){
            try {
                JSONObject jso = new JSONObject(data);
                Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_SHORT).show();
                Const.user.payPwd = "1";
                leftClick();
            }catch (Exception e){

            }
        }else {

        }
    }

    @Override
    public void bindDataForUIElement() {
        String payPwd = Const.user.payPwd;
        if (from.equals("TakeMoneyFragment") || from.equals("ForgetPwdFragment")){
            etOringalPwd.setVisibility(View.GONE);

        }else {
            if (StrUtil.null2Str(payPwd).length() > 0){
                etOringalPwd.setVisibility(View.VISIBLE);

            }else {
                etOringalPwd.setVisibility(View.GONE);
            }
        }
        if (from.equals("ForgetPwdFragment")){
            txtForgetPwd.setVisibility(View.GONE);
        }

        tWidget.setCenterViewText("修改支付密码");

        btnChangePwdSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPwd();
            }
        });
        txtForgetPwd.setOnClickListener(this);


    }
    private void setPwd(){
        param = new EditpayPwd();
        String orpayPwd = StrUtil.null2Str(etOringalPwd.getText().toString());
        String payPwd = etNewPwd.getText().toString();
        String copayPwd = etConfrimPwd.getText().toString();
        if (payPwd.length() > 0){
            param.payPwd = payPwd;
        }else {
            Toast.makeText(getActivity(),"请输入支付密码",Toast.LENGTH_SHORT).show();
            return;
        }
        if (copayPwd.length() > 0){
            param.copayPwd = copayPwd;
        }else {
            Toast.makeText(getActivity(),"请确认支付密码",Toast.LENGTH_SHORT).show();
            return;
        }
        if (payPwd.equals(copayPwd)){

            param.tokenId = Const.cache.getTokenId();
            if (StrUtil.null2Str(Const.user.payPwd).length() > 0){
                if (orpayPwd.length() > 0){
                    param.orpayPwd = orpayPwd;
                    param.type = 1;
                }else {
                    Toast.makeText(getActivity(),"请输入原支付密码",Toast.LENGTH_SHORT).show();
                    return;
                }
            }else {
                if (from.equals("ForgetPwdFragment")){
                    param.type = 2;
                }else {
                    param.type = 0;
                }

            }

//            if (orpayPwd.length() > 0){
//                param.orpayPwd = orpayPwd;
//                param.type = 1;//修改支付密码
//            }else {
//                if (from.equals("TakeMoneyFragment")){
//                    param.type = 0;//第一次设置支付密码
//                }else if (from.equals("ForgetPwdFragment")){
//                    param.type = 2;//忘记支付密码
//                }
//            }
            request(param);
        }else {
            Toast.makeText(getActivity(),"两次密码不一致",Toast.LENGTH_SHORT).show();
            return;
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtForgetPwd:
                Intent i = new Intent(getActivity(), FrogetPwdActivity.class);
                getActivity().startActivity(i);
                break;
        }
    }

    @Override
    protected void bindEvent() {

    }
}
