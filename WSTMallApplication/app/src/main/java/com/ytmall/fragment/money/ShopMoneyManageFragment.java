package com.ytmall.fragment.money;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ytmall.R;
import com.ytmall.activity.money.BankAccountActivity;
import com.ytmall.activity.money.EditBankAccountActivity;
import com.ytmall.activity.money.SetPayPwdActivity;
import com.ytmall.activity.money.ShopCashRecordActivity;
import com.ytmall.activity.money.ShopTakeMoneyActivity;
import com.ytmall.api.pwd.CheckPayPwd;
import com.ytmall.api.recharge.GetMoneyBagInfo;
import com.ytmall.application.Const;
import com.ytmall.bean.GetMoneyBagInfoBean;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.util.StrUtil;
import com.ytmall.widget.CustomPop;
import com.ytmall.widget.PayPwdPopWindow;
import com.ytmall.widget.ToSetPwdPopWindow;

import org.json.JSONObject;

/**
 * Created by lee on 2017/1/23.
 */
@FragmentView(id = R.layout.fragment_shop_money_manage)
public class ShopMoneyManageFragment extends BaseFragment implements View.OnClickListener{
    @InjectView(id = R.id.txtMyMoney)
    TextView txtMyMoney;
    @InjectView(id = R.id.txtUseMoney)
    TextView txtUseMoney;
    @InjectView(id = R.id.txtLockMoney)
    TextView txtLockMoney;
    @InjectView(id = R.id.txtDistributMoney)
    TextView txtDistributMoney;
    @InjectView(id = R.id.btnTakeMoney)
    Button btnTakeMoney;
    @InjectView(id = R.id.scrollMoney)
    LinearLayout scrollMoney;
    @InjectView(id = R.id.llTakeCash)
    LinearLayout llTakeCash;
    @InjectView(id = R.id.txtAccountNumber)
    TextView txtAccountNumber;
    @InjectView(id = R.id.txtJinMoney)
    TextView txtJinMoney;
    @InjectView(id = R.id.txtLockJinMoney)
    TextView txtLockJinMoney;

    private CheckPayPwd pwdParam;
    private PayPwdPopWindow payPop;
    private GetMoneyBagInfo bagInfo;

    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(bagInfo.getA())){
            try {
                JSONObject jso = new JSONObject(data);
                GetMoneyBagInfoBean resut = gson.fromJson(jso.get("data").toString(),
                        GetMoneyBagInfoBean.class);
                txtAccountNumber.setText(resut.cashCnt);


            }catch (Exception e){
                e.printStackTrace();
            }

        }else if (url.contains(pwdParam.getA())){
            try {
                JSONObject jso = new JSONObject(data);
                payPop.dismiss();

                Intent i = new Intent(getActivity(), BankAccountActivity.class);
                i.putExtra("from","ShopMoneyManageFragment");
                startActivity(i);


            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void flagFailed(String url) {
        super.flagFailed(url);
        if (url.contains(pwdParam.getA())){
            payPop.dismiss();
        }
    }

    @Override
    public void bindDataForUIElement() {
        tWidget.setCenterViewText("商家资金管理");
        tWidget.setRightBtnText("记录");
        tWidget.right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ShopCashRecordActivity.class);
                startActivity(i);
            }
        });
        txtMyMoney.setText("商家金额");
        txtUseMoney.setText("商家可用金额：¥"+Const.user.shopMoney);
        txtLockMoney.setText("商家冻结金额：¥"+Const.user.shopLockMoney);
        txtJinMoney.setText("金堂宝: ￥" + trimString(Const.user.jinMoney));
        txtLockJinMoney.setText("金堂宝: ￥" +trimString(Const.user.lockJinMoney));
        txtDistributMoney.setVisibility(View.GONE);

        btnTakeMoney.setOnClickListener(this);
        llTakeCash.setOnClickListener(this);
    }

    private String trimString(String src){
        if(src != null){
            return src.trim();
        }
        return "";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llTakeCash:
                if (StrUtil.str2Int(txtAccountNumber.getText().toString()) > 0){
                    if (Const.user.payPwd.length() > 0){
                        //有密码
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
                        payPop.showAtLocation(scrollMoney,Gravity.CENTER,0,0);
                    }else {
                        //去设置支付密码
                        ToSetPwdPopWindow setPwdPopWindow = new ToSetPwdPopWindow(getActivity());
                        setPwdPopWindow.showAtLocation(scrollMoney,Gravity.CENTER,0,0);
                    }
                }else {
                    //去设置银行卡
                    Intent i = new Intent(getActivity(), EditBankAccountActivity.class);
//                    i.putExtra("id","ShopMoneyManageFragment");
                    startActivity(i);
                }


                break;
            case R.id.btnTakeMoney:
                Intent i = new Intent(getActivity(), ShopTakeMoneyActivity.class);
                startActivity(i);


                break;
        }
    }

    /**
     * 校验密码
     */
    private void checkPwd(){
        pwdParam = new CheckPayPwd();
        pwdParam.tokenId = Const.cache.getTokenId();
        pwdParam.payPwd = payPop.gridPwd.getPassWord();
        request(pwdParam);


    }
    private void getMoneyBagInfo(){
        bagInfo = new GetMoneyBagInfo();
        bagInfo.tokenId = Const.cache.getTokenId();
        request(bagInfo);
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void onResume() {
        super.onResume();
        txtUseMoney.setText("商家可用金额：¥"+Const.user.shopMoney);
        txtLockMoney.setText("商家冻结金额：¥"+Const.user.shopLockMoney);
        getMoneyBagInfo();
    }
}
