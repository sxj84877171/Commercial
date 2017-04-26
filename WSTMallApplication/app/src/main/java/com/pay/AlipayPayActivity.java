package com.pay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.ytmall.sys.BaseAct;
import com.ytmall.util.StrUtil;
import com.ytmall.widget.GridviewForScroll;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class AlipayPayActivity extends BaseAct {
	public static Activity act;
	private String peasTotal;
	private String hsmTotal;
	private IWXAPI api;
	private Button btnWeixin;

	public static final String KEY_SUBJECT = "SUBJECT";
	public static final String KEY_DESC = "DESC";
	public static final String KEY_ORDER_NO = "ORDER_NO";
	public static final String KEY_PAY_PRICE = "PAY_PRICE";

	// 商户PID
	public static final String PARTNER = "2088421581118964";
	// 商户收款账号
	public static final String SELLER = "huashengtv@sina.com";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOll6kfI5WkFjGMl\n" +
			"E9lVyRwJLrxBDBqytR84iKgcVeqImHpCWc+iAWRuvK9wPfzadxBAQ2xa9epmQEjQ\n" +
			"J13x6zNDSuljjzShRs3Vp4BzNCe8BBDSHD8ayU4tQx4bswfN+m+TVfTwTBLzdVlF\n" +
			"k7un1ADxXCWKrKYXpGIQgjx+AAyFAgMBAAECgYASPqBAukiMnB1sRqK1k4okAefA\n" +
			"WYlbsOqNpTqxgX2J1uGsdPFWVMCtHCbtw+VklaNHBu7+UmH0z8doqbTaGCYddoSy\n" +
			"mestkhQPQJmoMFupWnCN5Hm6V9IzQ78A+ejIlX8ZzpfcDp+OHyUMEVJrZrvZVLlE\n" +
			"tuJzel8xN0Pww5DnIQJBAPm3fv8Fm531QUXqH++C4u8K+fd0+rddxfRXMNK7uHDX\n" +
			"+vLuGlPXXU1sLWahFUKadXxFM5fAXoDRtFRiTa+LfD0CQQDvRU4trkBzuJ9EfQZ3\n" +
			"aDnaHrNcZXb7NDXSxJ0i5n7Fo51eNgz1H5J787krbb3fvZBtA686+PQ/MNgWUGcp\n" +
			"Hm3pAkEAneJirSMCjRBvwKCSqIB8st6hxM91ZVIowqAOAm8l4lLsG4RnqPLVzwnC\n" +
			"es0GACJgfb3TLPM3zQLJnUC6FW9/kQJARDmPzCi2iginqUC5Gk0mydIdNIhd/8zo\n" +
			"V/vmDnCIz1QS6C0jTrW88AkT0JVPIcAMsXjuezNHeck0pFAyyh+6GQJAIQX2QSVl\n" +
			"jqoVGgf96mgCEuJlvHsyWCCfOePZkyrVupj2kYSCO8d7+bHXCB5ylBbSLpGekFg9\n" +
			"uv6lYtmIwKNksQ==";
	// 支付宝公钥
	public static final String   RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDXznbX6lWZ/BplCxilDJIA+FjXzpkj0b2xb195iCbA4Yiu+z0iRSnhTqjKEfUyewPAPMM2X3ADdqqvcaOxrDiz4+sjc1R4FEhWMSWQ/epBdgLla4D+BE09Ua/FZMe62UsTJNmJHNn/K5JBhcOobfcgeuGwWF60dnZiSy7QPLDIQwIDAQAB";
	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;

	String subject,desc,order_no;
	private static String pay_price ;
	private Button btnPay;
	private View lRecharge,lTakeMoney;
	private TextView txtRecharge,txtTakeMoney,txtMoney,txtAsMoney;
	private GridviewForScroll gridRecharge;
	private String [] money = {"60","200","500","2000","8000",""};
	private EditText etTakeMoney,etAccount;
	private ScrollView payScroll;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();
				payResult.getMemo();
//				MLogUtils.logD("pay result:"+resultInfo);

				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					setResult(Activity.RESULT_OK);
					finish();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(AlipayPayActivity.this, "支付结果确认中",
								Toast.LENGTH_SHORT).show();
						finish();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(AlipayPayActivity.this, "支付失败",
								Toast.LENGTH_SHORT).show();
						finish();

					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(AlipayPayActivity.this, "检查结果为：" + msg.obj,
						Toast.LENGTH_SHORT).show();
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
		act = this;
//		titleBar.showTxtTitle("我的账户");
		subject = getIntent().getStringExtra(KEY_SUBJECT);
		desc = getIntent().getStringExtra(KEY_DESC);
//		order_no = "U19988-160806-9934";
		order_no = getIntent().getStringExtra(KEY_ORDER_NO);
//		pay_price = "0.01";
		pay_price = getIntent().getStringExtra(KEY_PAY_PRICE);
		if(StrUtil.null2Str(order_no).equals("")
				||StrUtil.null2Str(pay_price).equals("") ){
			Toast.makeText(this,"参数错误",Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
//		check();
		pay();


	}

	public static void getMoney(String money){
		pay_price = money;


	}

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	private void pay() {
		if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
				|| TextUtils.isEmpty(SELLER)) {
			new AlertDialog.Builder(this)
					.setTitle("警告")
					.setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									//
									finish();
								}
							}).show();
			return;
		}
		// 订单
		String orderInfo = getOrderInfo(subject, desc, pay_price);

		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(AlipayPayActivity.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo,true);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}


	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String subject, String body, String price) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
//		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";
		orderInfo += "&out_trade_no=" + "\"" + order_no + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
//		if(AppConfig.WEB_DOMAIN .startsWith("http://v2.clofood.cn")){
//			//测试地址
//			orderInfo += "&notify_url=" + "\"" + "http://v2.clofood.cn/paynotify/alipay_notify_url.php"+ "\"";
//		}else{
			//正式地址
//			orderInfo += "&notify_url=" + "\"" + "http://cash.clofood.com/alipay_notify_url.php"+ "\"";
//		}
//		orderInfo += "&notify_url=" + "\""  + "http://120.24.253.63:8080/huaShengTV/rest/sys/aliPostCallBack";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
//		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
//		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	public String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

}
