package com.ytmall.fragment.user;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.ytmall.R;
import com.ytmall.activity.wechat.WeChatPayActivity;
import com.ytmall.api.recharge.UngradetoPay;
import com.ytmall.application.Const;
import com.ytmall.bean.UngradetoPayBean;
import com.ytmall.bean.WeixinPayBean;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lee on 2017/1/22.
 */
@FragmentView(id = R.layout.fragment_recharge_order)
public class UpdateUserFragment extends BaseFragment {
    @InjectView(id = R.id.llRemark)
    LinearLayout llRemark;
    @InjectView(id = R.id.txtOrderNo)
    TextView txtOrderNo;
    @InjectView(id = R.id.txtOrderMoney)
    TextView txtOrderMoney;
    @InjectView(id = R.id.txtRemark)
    TextView txtRemark;
    @InjectView(id = R.id.btnWeixinPay)
    Button btnWeixinPay;
    private UngradetoPay param;
    private IWXAPI api;
    private String appId;
    private WeixinPayBean weixinPay;

    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(param.getA())){
            try {
                JSONObject jso = new JSONObject(data);
                UngradetoPayBean up_info = gson.fromJson(jso.get("up_info").toString(), UngradetoPayBean.class);
                txtOrderNo.setText(up_info.upgrade_sn);
                txtOrderMoney.setText(up_info.total_money);

                weixinPay = gson.fromJson(jso.get("data").toString(),WeixinPayBean.class);



            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void flagFailed(String url) {
        super.flagFailed(url);
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void bindDataForUIElement() {
        llRemark.setVisibility(View.VISIBLE);
        txtRemark.setText("升级高级会员");
        api = WXAPIFactory.createWXAPI(getActivity(), null);
        btnWeixinPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appId = weixinPay.appid;
                Const.APP_ID = appId;
                api.registerApp(appId);
                PayReq req = new PayReq();
                req.appId = appId;
                req.partnerId = weixinPay.partnerid;
                req.prepayId = weixinPay.prepayid;
                req.nonceStr = weixinPay.noncestr;
                req.timeStamp = weixinPay.timestamp;
                req.packageValue = "Sign=WXPay";
                req.sign = weixinPay.sign;
                //req.extData			= "app data"; // optional
                Toast.makeText(getActivity(), "正在跳转微信支付...", Toast.LENGTH_SHORT).show();
                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                api.sendReq(req);
                leftClick();
                Log.d("json checkargs:",req.checkArgs()+"");

            }
        });
        getInfo();


    }

    private void getInfo(){
        param = new UngradetoPay();
        param.tokenId = Const.cache.getTokenId();
        request(param);

    }


}
