package com.ytmall.activity.silver;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ytmall.R;
import com.ytmall.activity.money.TakeMoneyRecordActivity;
import com.ytmall.activity.recharge.RechargeOrderActivity;
import com.ytmall.api.recharge.RechargeOrder;
import com.ytmall.application.Const;
import com.ytmall.bean.RechargeOrderBean;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;

import org.json.JSONObject;

/**
 * fragment_bank_charge
 */
@FragmentView(id = R.layout.fragment_bank_charge)
public class SilverFragment extends BaseFragment implements View.OnClickListener {
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

    private int type = 0;

    public SilverFragment() {
        // Required empty public constructor
    }


    @Override
    public void bindDataForUIElement() {
        tWidget.setCenterViewText("充值");
        Button btnRight = tWidget.getRightButton();
        btnRight.setText("记录");
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TakeMoneyRecordActivity.class);
                i.putExtra("from", "recharge");
                startActivity(i);
            }
        });
    }

    @Override
    protected void bindEvent() {
        choose_pay.setOnClickListener(this);
        btn_recharge.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
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

        if (v.getId() == R.id.btn_recharge) {
            subbmit();
        }
    }

    private void subbmit() {
        RechargeOrder param = new RechargeOrder();
        param.tokenId = Const.cache.getTokenId();
        param.account = et_money.getText().toString();

        if (et_remark.getText().length() > 0) {
            param.remark = et_remark.getText().toString();
        } else {
            param.remark = "";
        }
        request(param);
    }

    @Override
    protected void requestSuccess(String url, String data) {
        super.requestSuccess(url, data);
        try {
            JSONObject jso = new JSONObject(data);
            RechargeOrderBean result = gson.fromJson(jso.get("data").toString(), RechargeOrderBean.class);
            Log.d("recharge:", data);
            Intent i = new Intent(getActivity(), RechargeOrderActivity.class);
            i.putExtra("money", result.recharge_money);
            i.putExtra("orderNo", result.recharge_sn);
            i.putExtra("rechargeId", result.recharge_id);
            i.putExtra("type",type);
            startActivity(i);
            getActivity().finish();

        } catch (Exception ex) {

        }

    }
}
