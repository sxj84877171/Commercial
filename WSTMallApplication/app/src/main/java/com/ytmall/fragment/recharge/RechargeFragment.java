package com.ytmall.fragment.recharge;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ytmall.R;
import com.ytmall.activity.money.TakeMoneyRecordActivity;
import com.ytmall.activity.recharge.RechargeOrderActivity;
import com.ytmall.api.recharge.RechargeOrder;
import com.ytmall.application.Const;
import com.ytmall.bean.AbstractParam;
import com.ytmall.bean.RechargeOrderBean;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.util.StrUtil;

import org.json.JSONObject;

/**
 * Created by lee on 16/12/7.
 */
@FragmentView(id = R.layout.fragment_recharge)
public class RechargeFragment extends BaseFragment implements View.OnClickListener{
    private Activity mContext;
    @InjectView(id = R.id.et_money)
    private EditText et_money;
    @InjectView(id = R.id.et_remark)
    private EditText et_remark;
    @InjectView(id = R.id.btn_recharge)
    private Button btn_recharge;
    @InjectView(id = R.id.pay_type_name)
    private TextView pay_type_name;
    @InjectView(id = R.id.choose_pay)
    private View choose_pay;
    private RechargeOrder param;

    private int type = 0;
    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(param.getA())){
            try {
                JSONObject jso = new JSONObject(data);
                RechargeOrderBean result = gson.fromJson(jso.get("data").toString(), RechargeOrderBean.class);
                Log.d("recharge:",data);
                Intent i = new Intent(getActivity(), RechargeOrderActivity.class);
                i.putExtra("money",result.recharge_money);
                i.putExtra("orderNo",result.recharge_sn);
                i.putExtra("rechargeId",result.recharge_id);
                i.putExtra("payment_type",result.payment_type);
                i.putExtra("type","1");
                startActivity(i);
                getActivity().finish();

            }catch (Exception ex){

            }

        }else {

        }
    }

    @Override
    protected void flagFailed(String url) {
        super.flagFailed(url);
    }

    @Override
    public void bindDataForUIElement() {
        tWidget.setCenterViewText("充值");
        mContext = getActivity();
        Button btnRight = tWidget.getRightButton();
        btnRight.setText("记录");
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TakeMoneyRecordActivity.class);
                i.putExtra("from","recharge");
                startActivity(i);
            }
        });
        choose_pay.setOnClickListener(this);
    }

    @Override
    protected void bindEvent() {
        btn_recharge.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_recharge:
                String money = et_money.getText().toString();
                getRechargeOrder();


                break;
        }
        if (v.getId() == R.id.choose_pay) {
            new AlertDialog.Builder(getActivity()).setSingleChoiceItems(new String[]{"微信支付", "支付宝支付"}, type, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    type = which;
                    if (which == 0) {
                        pay_type_name.setText("微信支付");
                    } else {
                        pay_type_name.setText("支付宝支付");
                    }
                    dialog.dismiss();
                }
            }).show();
        }
    }
    private void getRechargeOrder(){
        param = new RechargeOrder();
        param.tokenId = Const.cache.getTokenId();
        param.account = et_money.getText().toString();
        if (type == 0) {
            param.payment_type = "1" ;
        } else {
            param.payment_type = "2" ;
        }
        if (et_remark.getText().length() > 0){
            param.remark = et_remark.getText().toString();
        }else {
            param.remark = "";
        }
        request(param);


    }


}
