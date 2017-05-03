package com.ytmall.fragment.money;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ytmall.R;
import com.ytmall.activity.money.BankAccountActivity;
import com.ytmall.api.pwd.CheckPayPwd;
import com.ytmall.api.recharge.DrawsCashByShop;
import com.ytmall.application.Const;
import com.ytmall.bean.GetCashConfBean;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.util.StrUtil;
import com.ytmall.widget.PayPwdPopWindow;
import com.ytmall.widget.ToSetPwdPopWindow;

import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * Created by lee on 2017/1/23.
 */
@FragmentView(id = R.layout.fragment_shop_take_money)
public class ShopTakeMoneyFragment extends BaseFragment implements View.OnClickListener{
    @InjectView(id = R.id.llBank)
    LinearLayout llBank;
    @InjectView(id = R.id.llShopTakeMoney)
    LinearLayout llShopTakeMoney;
    @InjectView(id = R.id.txtBankAccount)
    TextView txtBankAccount;
    @InjectView(id = R.id.etMoney)
    EditText etMoney;
    @InjectView(id = R.id.btnSure)
    Button btnSure;

    @InjectView(id = R.id.pay_type_name)
    TextView pay_type_name;
    @InjectView(id = R.id.choose_pay)
    View choose_pay;

    private DrawsCashByShop shopParam = new DrawsCashByShop();
    private PayPwdPopWindow payPop;
    private CheckPayPwd pwdParam;

    private int type;
    @Override
    protected void bindEvent() {
        choose_pay.setOnClickListener(this);
    }

    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(pwdParam.getA())){
            try {
                JSONObject jso = new JSONObject(data);
                drawsCashByShop();
                payPop.dismiss();

            }catch (Exception e){
                e.printStackTrace();

            }
        }else if (url.contains(shopParam.getA())){

            try {
                JSONObject jso = new JSONObject(data);

                DecimalFormat df = new DecimalFormat("###.00");

                double oringal = StrUtil.str2double(Const.user.shopMoney);
                double lockMoney = StrUtil.str2float(Const.user.shopLockMoney);

                double takeCash = StrUtil.str2double(etMoney.getText().toString());

                double lastResult = oringal - takeCash;
                double lastLock = lockMoney + takeCash;

                Const.user.shopMoney = df.format(lastResult);
                Const.user.shopLockMoney = df.format(lastLock);


                Toast.makeText(getActivity(),"提现成功",Toast.LENGTH_SHORT).show();
                leftClick();



            }catch (Exception e){
                e.printStackTrace();

            }
        }
    }

    @Override
    protected void flagFailed(String url) {
        super.flagFailed(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSure:
                String bank = txtBankAccount.getText().toString();
                String money = etMoney.getText().toString();
                if (bank.length() > 0){

                }else {
                    Toast.makeText(getActivity(),"请选择银行卡",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (money.length() > 0){

                }else {
                    Toast.makeText(getActivity(),"请输入金额",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StrUtil.null2Str(Const.user.payPwd).length() > 0){
                    //转出
                    payPop = new PayPwdPopWindow(getActivity());
                    payPop.btnSure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (payPop.gridPwd.getPassWord().length() < 6){
                                Toast.makeText(getActivity(),"请输入完整的密码",Toast.LENGTH_SHORT).show();
                                return;
                            }else {
                                checkPwd();
                            }
                        }
                    });
                    payPop.showAtLocation(llShopTakeMoney, Gravity.CENTER,0,0);
                }else {
                    //去设置支付密码
                    ToSetPwdPopWindow setPwdPopWindow = new ToSetPwdPopWindow(getActivity());
                    setPwdPopWindow.showAtLocation(llShopTakeMoney,Gravity.CENTER,0,0);

                }
                break;
            case R.id.llBank:
                Intent i = new Intent(getActivity(), BankAccountActivity.class);
                startActivity(i);
                break;
            case R.id.choose_pay:
            {
                new AlertDialog.Builder(getActivity()).setSingleChoiceItems(new String[]{"商家账户", "金堂宝"}, type, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        type = which;
                        if (which == 0) {
                            pay_type_name.setText("商家账户");
                        } else {
                            pay_type_name.setText("金堂宝");
                        }
                        dialog.dismiss();
                    }
                }).show();
                break;
            }
        }
    }

    private void drawsCashByShop(){

        shopParam.tokenId = Const.cache.getTokenId();
        shopParam.drawMoney = etMoney.getText().toString();
        if (Const.cache.getBankAcc() != null && !Const.cache.getBankAcc().id.equals("")){
            shopParam.configId = Const.cache.getBankAcc().id;
        }else {
            Toast.makeText(getActivity(),"请选择提现账户",Toast.LENGTH_SHORT).show();
        }

        if(type == 0){
            shopParam.a = "drawsCashByShop";

        }else{
            shopParam.a = "jinMoneyToBank";
            shopParam.type = "1" ;
        }

        request(shopParam);

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
    public void bindDataForUIElement() {
        btnSure.setOnClickListener(this);
        llBank.setOnClickListener(this);

        etMoney.setHint("当前可提现余额"+Const.user.shopMoney);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (Const.cache.getBankAcc() != null){
            GetCashConfBean bank = Const.cache.getBankAcc();
            if (!Const.cache.getBankAcc().id.equals("")){
                String lastNum = bank.accNo.substring(bank.accNo.length()-4,bank.accNo.length());
                txtBankAccount.setText(bank.bankName+"("+lastNum+")");
            }

        }
    }
}
