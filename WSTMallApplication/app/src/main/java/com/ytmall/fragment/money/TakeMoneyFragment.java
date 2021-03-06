package com.ytmall.fragment.money;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jungly.gridpasswordview.GridPasswordView;
import com.ytmall.R;
import com.ytmall.activity.pwd.ChangePayPwdActivity;
import com.ytmall.api.pwd.CheckPayPwd;
import com.ytmall.api.recharge.DrawsCashByUser;
import com.ytmall.application.Const;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.util.StrUtil;
import com.ytmall.widget.CustomPop;
import com.ytmall.widget.PayPwdPopWindow;
import com.ytmall.widget.ToSetPwdPopWindow;

import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * Created by lee on 2017/1/11.
 */
@FragmentView(id = R.layout.fragment_take_money)
public class TakeMoneyFragment extends BaseFragment implements View.OnClickListener{
    @InjectView(id = R.id.etMoney)
    EditText etMoney;
    @InjectView(id = R.id.btnConfirm)
    Button btnConfirm;
    @InjectView(id = R.id.llTakeMoneyBg)
    LinearLayout llTakeMoneyBg;

    private DrawsCashByUser param;
    private PayPwdPopWindow payPop;
    private CheckPayPwd pwdParam;

    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(pwdParam.getA())){
            try {
                Log.d("json pwdparam:",data);
                toTakeCash();

            }catch (Exception e){

            }
        }else {
            payPop.dismiss();
            DecimalFormat df=new DecimalFormat("###.00");

            double oringal = StrUtil.str2double(Const.user.distributMoney);
            double lockMoney = StrUtil.str2float(Const.user.lockMoney);

            double takeCash = StrUtil.str2double(etMoney.getText().toString());

            double lastResult = oringal - takeCash;
            double lastLock = lockMoney + takeCash;

            Const.user.distributMoney = df.format(lastResult);
            Const.user.lockMoney = df.format(lastLock);

            Toast.makeText(getActivity(),"提现成功",Toast.LENGTH_SHORT).show();
            leftClick();
        }
    }

    @Override
    protected void flagFailed(String url) {
        if (url.contains(pwdParam.getA())){
            payPop.dismiss();
//            Toast.makeText(getActivity(),"提现失败",Toast.LENGTH_SHORT).show();
        }else if (url.contains(param.getA())){
//            payPop.dismiss();
//            Toast.makeText(getActivity(),"密码错误",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void bindDataForUIElement() {
        String hint = "当前可提现金额"+Const.user.distributMoney;
        etMoney.setHint(hint);
        String money = etMoney.getText().toString();
        final double dMoney = StrUtil.str2double(money);

        final double balance = StrUtil.str2double(Const.user.distributMoney);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StrUtil.null2Str(Const.user.payPwd).length() > 0){
                    if ((dMoney - balance) > 0){
                        Toast.makeText(getActivity(),"可提现余额不足！",Toast.LENGTH_SHORT).show();

                    }else {
                        //转出
                        payPop = new PayPwdPopWindow(getActivity());
                        payPop.btnSure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (payPop.gridPwd.getPassWord().length() < 6){
                                    Toast.makeText(getActivity(),"请输入完整的密码",Toast.LENGTH_SHORT).show();
                                    return;
                                }else {
//                                    toTakeCash();
                                    checkPwd();
                                }
                            }
                        });
                        payPop.showAtLocation(llTakeMoneyBg,Gravity.CENTER,0,0);

                    }
                }else {
                    //去设置支付密码
                    ToSetPwdPopWindow setPwdPopWindow = new ToSetPwdPopWindow(getActivity());
                    setPwdPopWindow.showAtLocation(llTakeMoneyBg,Gravity.CENTER,0,0);

                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgPayExit:
                payPop.dismiss();
                break;

        }
    }

    /**
     * 提现
     */
    private void toTakeCash(){
        param = new DrawsCashByUser();
        param.drawMoney = etMoney.getText().toString();
        param.tokenId = Const.cache.getTokenId();
        request(param);

    }

    /**
     * 校验密码
     */
    private void checkPwd(){
        pwdParam = new CheckPayPwd();
        pwdParam.tokenId = Const.cache.getTokenId();
        pwdParam.payPwd = payPop.gridPwd.getPassWord();
        Log.d("json pwd:",payPop.gridPwd.getPassWord());
        request(pwdParam);


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void bindEvent() {

    }
}
