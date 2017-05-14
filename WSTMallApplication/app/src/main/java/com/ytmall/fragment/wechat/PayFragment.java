package com.ytmall.fragment.wechat;

import android.util.Log;
import android.widget.Toast;

import com.ytmall.R;
import com.ytmall.activity.recharge.RechargeToPay;
import com.ytmall.api.pay.WinXinPay;
import com.ytmall.api.recharge.RechargeOrder;
import com.ytmall.api.recharge.UngradetoPay;
import com.ytmall.application.Const;
import com.ytmall.bean.WeixinPayBean;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.ytmall.util.MD5;
import com.ytmall.util.StrUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */
@FragmentView(id = R.layout.fragment_wechat_pay)
public class PayFragment extends BaseFragment {
    private String orderId;
    private String from;
    //we chat pay
    private IWXAPI api;
    private String appId;
    WinXinPay winXinPay = new WinXinPay();
    private RechargeToPay rechargeParam = new RechargeToPay();
    private int yinTangJiFenWX = 1 ;
//    private UngradetoPay param = new UngradetoPay();

    public PayFragment(String orderId,String from,int isYinTangJiFenWX) {
        this.orderId = orderId;
        this.from = from;
        this.yinTangJiFenWX = isYinTangJiFenWX ;
    }

    @Override
    protected void requestSuccess(String url, String data) {
        super.requestSuccess(url, data);
        if (url.contains(winXinPay.getA()) || url.contains(rechargeParam.getA())) {
            //f0e7f4c7b4a108a2e525fee51721f0a1
            try {
                JSONObject dataJson = new JSONObject(data);
                WeixinPayBean jsonObject = gson.fromJson(dataJson.get("data").toString(),WeixinPayBean.class);
//
                appId = jsonObject.appid;
                Const.APP_ID = appId;
                api.registerApp(appId);
                PayReq req = new PayReq();
                req.appId = appId;
                req.partnerId = jsonObject.partnerid;
                req.prepayId = jsonObject.prepayid;
                req.nonceStr = jsonObject.noncestr;
                req.timeStamp = jsonObject.timestamp;
                req.packageValue = "Sign=WXPay";
                req.sign = jsonObject.sign;
                //req.extData			= "app data"; // optional
                Toast.makeText(getActivity(), "正在跳转微信支付...", Toast.LENGTH_SHORT).show();
                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                api.sendReq(req);
                Log.d("json checkargs:",req.checkArgs()+"");

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void flagFailed(String url) {
        super.flagFailed(url);
//        Toast.makeText(getActivity(),"wrong",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void bindDataForUIElement() {
        tWidget.setCenterViewText("微信支付");
        api = WXAPIFactory.createWXAPI(getActivity(), null);
        if (StrUtil.null2Str(from).equals("RechargeOrderFragment")){
            rechargeToPay();
        }
        else {
            orderToPay();
        }
    }
    private void orderToPay(){
        winXinPay.orderId = orderId;
        winXinPay.tokenId = Const.cache.getTokenId();
        request(winXinPay);


    }
    private void rechargeToPay(){
        rechargeParam = new RechargeToPay();
        if(yinTangJiFenWX == 5){
            rechargeParam.a = "rechargeToPayOfYinMoney";
        }
        rechargeParam.recharge_id = orderId;
        rechargeParam.tokenId = Const.cache.getTokenId();
        request(rechargeParam);
    }

    @Override
    protected void bindEvent() {

    }

}
