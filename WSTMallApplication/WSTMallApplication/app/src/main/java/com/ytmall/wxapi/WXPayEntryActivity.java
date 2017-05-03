package com.ytmall.wxapi;


import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.ytmall.R;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.ytmall.application.Const;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{

    private IWXAPI api;
	private ImageView iv_back;
	private TextView tv_pay_result;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);
        iv_back= (ImageView) findViewById(R.id.iv_back);
		tv_pay_result= (TextView) findViewById(R.id.tv_pay_result);
     	api = WXAPIFactory.createWXAPI(this, Const.APP_ID);
        api.handleIntent(getIntent(), this);
		iv_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
//		Toast.makeText(this,"支付结果"+resp.errCode, Toast.LENGTH_SHORT).show();
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if(resp.errCode==0){
				tv_pay_result.setText("恭喜你,已支付成功");
			}else if(resp.errCode==-1){
				tv_pay_result.setText("抱歉，支付失败");
			}else if(resp.errCode==-2){
				tv_pay_result.setText("取消支付");
			}
		}
	}
}