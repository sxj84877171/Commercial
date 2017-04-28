package com.ytmall.fragment.recharge;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pay.AlipayPayActivity;
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
public class RechargeOrderFragment extends BaseFragment implements View.OnClickListener{
    @InjectView(id = R.id.txtOrderNo)
    TextView txtOrderNo;
    @InjectView(id = R.id.txtOrderMoney)
    TextView txtOrderMoney;
    @InjectView(id = R.id.btnWeixinPay)
    Button btnWeixinPay;
    private RechargeOrder param;
    private String money;
    private String orderNo;
    private String rechargeId;
    private int type = -1;

    private RechargeToPay payParam ;

    public RechargeOrderFragment(String money,String orderNo,String rechargeId,int type){
        this.money = money;
        this.orderNo = orderNo;
        this.rechargeId = rechargeId;
        this.type = type ;
    }

    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(payParam.getA())){
            Log.d("json pay ",data);
            try {
                JSONObject jso = new JSONObject(data);

                startActivity(new Intent(getActivity(), WeChatPayActivity.class).putExtra
                        ("orderId",rechargeId).putExtra("from","RechargeOrderFragment"));
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
    public void bindDataForUIElement() {
        txtOrderNo.setText(orderNo);
        txtOrderMoney.setText(money);

        btnWeixinPay.setOnClickListener(this);
        if(type == 1){
            btnWeixinPay.setText("支付宝支付");
        }else{
            btnWeixinPay.setText("微信支付");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnWeixinPay:
                if(type == 0) {
                    startActivity(new Intent(getActivity(), WeChatPayActivity.class).putExtra
                            ("orderId", rechargeId).putExtra("from", "RechargeOrderFragment"));
                }else{
                    //支付宝支付
                    Intent intent = new Intent(getActivity(), AlipayPayActivity.class);
                    intent.putExtra("SUBJECT","哪位的账户");
                    intent.putExtra("DESC","商品描述");
                    intent.putExtra("ORDER_NO",rechargeId);
                    intent.putExtra("PAY_PRICE",money);
                    startActivity(intent);
                }

//                rechargeToPay();
                break;
        }
    }

    private void rechargeToPay(){
        payParam = new RechargeToPay();
        payParam.recharge_id = rechargeId;
        payParam.tokenId = Const.cache.getTokenId();
        Log.d("json recharge id:",rechargeId);
        request(payParam);

    }
    @Override
    protected void bindEvent() {

    }
}
