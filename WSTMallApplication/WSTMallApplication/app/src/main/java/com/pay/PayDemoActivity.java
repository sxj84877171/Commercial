package com.pay;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;


import com.alipay.sdk.app.PayTask;
import com.ytmall.R;

import java.util.Map;

public class PayDemoActivity extends FragmentActivity {

	public static final String APPID = "2016080401703201";
	// 商户收款账号
	public static final String SELLER = "huashengtv@sina.com";
	// 商户私钥，pkcs8格式
//	public static final String RSA_PRIVATE = "";
//	public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAL/0cYc1FxH1OedHPNDavNxtEJDOkXMyPC5vv9KKVlY7BlccpUFDB++BWsGy+bgrXjm5f0KFRdCpOcXWqWNpaDHnVyOumxmr134aWy3CVxTLNQNtmk3AjVLVqISSInADK1DBARKoFEXaJ1vU/vn+m2oJuqN1AjlYrLDxPRAooohdAgMBAAECgYA3x2yGAaNmkH/2+PhvKGDhHMBHk/8oHlzmkYqIP7K+8//vqF9NC5Fuw4P/7WYrYk7LL2APkHKtI7+qAarPPH4OICpl8u7EhclWu+sHU7E/d61NQJWkZZaXieoLe5IpiJtTLSnrDr0dTGwBW5Sf2RGDEKAzKxFfAjJozhUJ7uegAQJBAP+ksyHsLJvFWU/fVUz4D9VZGlPXZ/6PTu3jUiea93gTv9XDUGBqoxFWR8NYwoL1fbsRyvDGYc9XUFl7UNi7Kl0CQQDAOP+BtF04Udq7gw+XE85fBfJFHW7vml0oODFRqkyTaja9XEwjT8kMVabeANPFMADRmZPqY9Guanf56pzK2vYBAkEAporepF/fI8/qgk1lndIRRpVYwHwZZM5pxjKMx/597702AYXPbDCscVP7Rnkhpv1VXrSVuEwVVmV0lAbxQXCVhQJBAIpzg3WE1/AM5SNSTxReK7K9sbaecJ0b7iVd9AgCRY2YXz0dEaoNeZ3sw3f72Iya+rv9hlC4/PpHrYrQtYZXGAECQQDSC3cQMrpLYpsZvmqYicA50nVcXFvMMc+F98BQWnYGxW1UgxgqpTj0TbUjQ7iq8uHu0VBGy5M3XyioL7qT3JZQ";
	public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKDoYOsiJp4S60Uln1JfEtccTAGqFgDIICXKG/puUhVRCm3XmYekORf1uakpBLwlNjqTV0p5QZb+9O1VdOIF19jzHMtv2yEAKzOEBAujZdQuVPKHNmT6pfqT/HmcnF3guMrcMtd32Ot9/xmSiahphYGV0Ymcjtff1Hu4uOtcHfSdAgMBAAECgYBV59qejmwJptYe45gs1P9tAvl9Rf5bSn0HT+EiEbYXFV45CIs6RK4KobEOpAyYGlHacgF/jD89Sz8eWqp9ZVs36s8aryAvGqFLDrvJzonIFeXzCX4g/qQMv/o8FEnZJz+4FevM3cy5yGnswFadz26p7cUBT8gH2rPVDSKpfjCEIQJBAM3O7zFvD+WWIRZPijVIiUgMODgLlGxH3Q0gH90eU8oZMid6nlu0UEM0Sg/tSzH8OqqJ31gaGWMywpC85zqOSskCQQDIJjNpdZHCkXW6of98NYzAS877kRUT33+8tB+iyFfqB58Lc4WblhUUPGgcPASr2DLJuCCz2Tm1GJrdoqlRmDE1AkB1OcYygREr80xbWejeGrffKt6mxam/11sIi+C6YPjW520S4ZvrnhQNg24gDkhDa2i7FJb7proJL7N1H7QRPVVJAkEAqUt2rTtfIV58YHLqcZuyBG2bFDrxwy+HEoyhPcWMZ04u/zalyqa2CbnWrhsuMFTdELcIIlgZcc7wH4yY/4VmCQJBAJAyYLv+axUd1Ph8RtpLPAwwCW+U0vUgesqVzO80wRzMun7PHLwCq8lPTfMelX9tn6b0Q/L+n2IsZW5pg1qMRkY=";

	// 支付宝公钥
	public static final String RSA_PUBLIC = "";
	private static final int SDK_PAY_FLAG = 1;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				/**
				 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
				 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
				 * docType=1) 建议商户依赖异步通知
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息

				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(PayDemoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
				} else {
					// 判断resultStatus 为非"9000"则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(PayDemoActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(PayDemoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

					}
				}
				break;
			}
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
	}
	
	public void pay(View v) {
		if (TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
			new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE| SELLER")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
							//
							finish();
						}
					}).show();
			return;
		}
		
		// 请配置好如下3个参数
		String seller_id = SELLER;
		String app_id = APPID;
		String rsaKey = RSA_PRIVATE;
		
		Map<String, String> params = PayOrderInfoUtil2_0.buildOrderParamMap(seller_id, app_id);
		String orderParam = PayOrderInfoUtil2_0.buildOrderParam(params);
		String sign = PayOrderInfoUtil2_0.getSign(params, rsaKey);
		
		final String orderInfo = orderParam + "&" + sign;
		
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				PayTask alipay = new PayTask(PayDemoActivity.this);
				String result = alipay.pay(orderInfo, true);
				
				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
	 * 
	 * @param v
	 */
//	public void h5Pay(View v) {
//		Intent intent = new Intent(this, H5PayDemoActivity.class);
//		Bundle extras = new Bundle();
//		/**
//		 * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
//		 * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
//		 * 商户可以根据自己的需求来实现
//		 */
//		String url = "http://m.taobao.com";
//		// url可以是一号店或者淘宝等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
//		extras.putString("url", url);
//		intent.putExtras(extras);
//		startActivity(intent);
//	}

}
