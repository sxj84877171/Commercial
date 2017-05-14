package com.ytmall.fragment.recharge;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.ytmall.R;
import com.ytmall.activity.recharge.RechargeToPay;
import com.ytmall.activity.wechat.WeChatPayActivity;
import com.ytmall.api.recharge.RechargeOrder;
import com.ytmall.application.Const;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;

import org.json.JSONObject;

/**
 * Created by lee on 2017/1/10.
 */
@FragmentView(id = R.layout.fragment_recharge_order)
public class RechargeOrderFragment extends BaseFragment implements View.OnClickListener {
    @InjectView(id = R.id.txtOrderNo)
    TextView txtOrderNo;
    @InjectView(id = R.id.txtOrderMoney)
    TextView txtOrderMoney;
    @InjectView(id = R.id.btnWeixinPay)
    Button btnWeixinPay;
    private RechargeOrder param;

    @InjectView(id = R.id.webview)
    WebView webView;

    private String money;
    private String orderNo;
    private String rechargeId;
    private String payment_type;
    private int type = -1;

    private RechargeToPay payParam;

    public RechargeOrderFragment(){}

    public RechargeOrderFragment(String money, String orderNo, String rechargeId,String payment_type, int type) {
        this.money = money;
        this.orderNo = orderNo;
        this.rechargeId = rechargeId;
        this.payment_type = payment_type;
        this.type = type;
    }

    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(payParam.getA())) {
            Log.d("json pay ", data);
            if (url.contains("rechargeToPay")) {
                try {
                    JSONObject jso = new JSONObject(data);

                    startActivity(new Intent(getActivity(), WeChatPayActivity.class).putExtra
                            ("orderId", orderNo).putExtra("from", "RechargeOrderFragment"));
                    leftClick();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Log.i("TAG", data);
            }

        }
    }

    @Override
    protected void flagFailed(String url) {
        super.flagFailed(url);
    }

    @Override
    public void bindDataForUIElement() {
        txtOrderNo.setText(orderNo);
        txtOrderMoney.setText(money);

        btnWeixinPay.setOnClickListener(this);
        if ("2".equals(payment_type)) {
            btnWeixinPay.setText("支付宝支付");
            tWidget.setCenterViewText("支付宝支付");
        } else {
            btnWeixinPay.setText("微信支付");
            tWidget.setCenterViewText("微信支付");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnWeixinPay:
                if ("1".equals(payment_type)) {
                    Intent intent = new Intent(getActivity(), WeChatPayActivity.class).putExtra
                            ("orderId", rechargeId).putExtra("from", "RechargeOrderFragment");
                    if(type != 1){
                        intent.putExtra("isAdd",5);
                    }
                    startActivity(intent);
                } else {

//                    RechargeOrder rechargeOrder = new RechargeOrder();
//                    rechargeOrder.a = "rechrageToAliPay" ;
//                    param.tokenId = Const.cache.getTokenId();
//                    param.recharge_id = rechargeId ;
//                    request(rechargeOrder);

                    rechargeToPay();
                }

                break;
        }
    }

    private void rechargeToPay() {
        payParam = new RechargeToPay();
        if(type == 1) {
            if ("1".equals(payment_type)) {
                payParam.a = "rechargeToPay";
            } else {
                payParam.a = "rechrageToAliPay";
            }
        }else{
            if ("1".equals(payment_type)) {
                payParam.a = "rechargeToPayOfYinMoney";
            } else {
                payParam.a = "rechargeToYinMoneyOfAliPay";
            }
        }
        payParam.recharge_id = rechargeId;
        payParam.tokenId = Const.cache.getTokenId();
        Log.d("json recharge id:", rechargeId);
//        request(payParam);
 //       webView.setVisibility(View.VISIBLE);
//        WebSettings webSettings = webView.getSettings();
////不保存密码
//        webSettings.setSavePassword(false);
////不保存表单数据
//        webSettings.setSaveFormData(false);
//        webSettings.setJavaScriptEnabled(true);
////不支持页面放大功能
//        webSettings.setSupportZoom(false);

//        webView.loadData(content,"text/html","utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        String url = Const.API_BASE_URL + "&a="+payParam.getA() + "&" + payParam.getString();;
        webView.loadUrl(url);
        webView.setVisibility(View.VISIBLE);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
//        Uri uri = Uri.parse(url);
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);
    }

    @Override
    protected void bindEvent() {

    }


    @Override
    public boolean isLoadWebView(String content) {
        webView.setVisibility(View.VISIBLE);
        WebSettings webSettings = webView.getSettings();
//不保存密码
        webSettings.setSavePassword(false);
//不保存表单数据
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
//不支持页面放大功能
        webSettings.setSupportZoom(false);

//        webView.loadData(content,"text/html","utf-8");
        webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
        return true;
    }
}
