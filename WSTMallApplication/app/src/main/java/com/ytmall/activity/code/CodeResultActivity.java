package com.ytmall.activity.code;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.api.pay.YuEPay;
import com.ytmall.application.Const;
import com.ytmall.bean.YuEPayReturn;
import com.ytmall.internet.APICallBack;
import com.ytmall.internet.LocalAPI;
import com.ytmall.model.BrandsReturn;
import com.ytmall.sys.BaseAct;
import com.ytmall.util.StrUtil;

import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * Created by lee on 16/11/1.
 */
public class CodeResultActivity extends BaseAct {
    private String url = "";
    private TextView txtBanlance;
    private EditText etMoney;
    private Button btnPay;
    private String shopId = "";
    private Activity act;
    private YuEPay param;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_result);
        act = this;
        url = getIntent().getStringExtra("url");

        shopId = url.substring(url.indexOf("=")+1,url.length());

        initUI();


    }


    private void initUI(){
        titleBar.setTxtTitle("余额支付");
        etMoney = (EditText) findViewById(R.id.etMoney);
        txtBanlance = (TextView) findViewById(R.id.txtBanlance);
        btnPay = (Button) findViewById(R.id.btnPay);

        etMoney.addTextChangedListener(new MoneyTextWacher());
        txtBanlance.setText(Const.user.userMoney);


        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                param = new YuEPay();
//                param.tokenId = Const.cache.getTokenId();
//                param.shopId = shopId;
//                param.sum_money = etMoney.getText().toString();
//                request(param);

                YuEPay param = new YuEPay();
                param.shopId = shopId;
                String money = etMoney.getText().toString();
                if (StrUtil.str2double(money) > 0){
                    param.sum_money = money;
                    param.tokenId = Const.cache.getTokenId();
                    LocalAPI.yuePay(param, new APICallBack<YuEPayReturn>() {
                        @Override
                        public void success(YuEPayReturn result) {
                            Toast.makeText(act,"支付成功",Toast.LENGTH_LONG).show();
                            finish();

                        }

                        @Override
                        public void error(String msg) {
                            Toast.makeText(act,msg,Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(act,"请输入大于0的金额",Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }
    class MoneyTextWacher implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0){
                if (hasMoney() == false){
                    btnPay.setEnabled(false);
                    btnPay.setBackgroundColor(Color.parseColor("#E2E2E2"));
                }else {
                    btnPay.setEnabled(true);
                    btnPay.setBackgroundColor(Color.parseColor("#F6772A"));
                }
            }else {
                btnPay.setEnabled(false);
                btnPay.setBackgroundColor(Color.parseColor("#E2E2E2"));
            }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
    private boolean hasMoney(){
        String money = etMoney.getText().toString();
        double dMoney = StrUtil.str2double(money);

        String balance = txtBanlance.getText().toString();
        double dBalance = StrUtil.str2double(balance);
        if ((dBalance - dMoney) < 0){
            Log.d("money;false",String.valueOf(dMoney-dBalance));
            return false;
        }else {
            Log.d("money;true",String.valueOf(dMoney-dBalance));
            return true;
        }

    }
}
