/**
#************************************************
# 项目名称：WSTMall
# 版本号： V1.0  
#************************************************
# 文件说明：
#          
#************************************************
# 子模块说明：
#                     
#************************************************
# 创建人员： 谈泳豪
# 创建人员QQ：1511895018
# 创建日期：2015-6-23
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
 */
package com.ytmall.fragment.login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;
import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.activity.MainActivity;
import com.ytmall.activity.user.MineActivity;
import com.ytmall.activity.user.RegisterActivity;
import com.ytmall.activity.user.ThirdLoginActivity;
import com.ytmall.api.login.Login;
import com.ytmall.api.login.WeiXinCallBack;
import com.ytmall.api.shoppingcar.GroupGoodsCart;
import com.ytmall.application.Const;
import com.ytmall.bean.User;
import com.ytmall.domain.ShoppingCart;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.fragment.user.MineFragment;
import com.ytmall.model.BrandsReturn;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.util.SharedPreferencesUtils;
import com.ytmall.util.StrUtil;
import com.ytmall.widget.ClearEditText;
import com.zbar.lib.decode.FinishListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@FragmentView(id = R.layout.fragment_login)
public class LoginFragment extends BaseFragment implements View.OnClickListener {

	public final static String fromMainActivity = "FromMainActivity";

	@InjectView(id = R.id.fragment_login_regist)
	View registButton;
	@InjectView(id = R.id.fragment_login_name)
	ClearEditText nameEditText;
	@InjectView(id = R.id.fragment_login_psw)
	ClearEditText pswEditText;
	@InjectView(id = R.id.fragment_login_login)
	Button loginButton;
	@InjectView(id = R.id.fragment_login_forget)
	View forgetButton;
	@InjectView(id = R.id.tv_phone_register)
	private TextView tv_phone_register;

	@InjectView(id = R.id.btnWeixinLogin)
	Button btnWeixinLogin;
	
	private boolean isFromMainActivity = false;

	private Login login = new Login();
	private UMShareAPI mShareAPI ;
	private String openId = "";
	private String unionid = "";
	private WeiXinCallBack weixinParam = new WeiXinCallBack();
	//API
	private GroupGoodsCart groupGoodsCart=new GroupGoodsCart();
	public LoginFragment(String mode) {
		if (mode.equals(fromMainActivity)) {
			isFromMainActivity = true;
		}
	}

	public LoginFragment() {

	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(!isFromMainActivity&&Const.isLogin){
			leftClick();
		}
	}

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		if (isFromMainActivity) {
			tWidget.getLeftView().setVisibility(View.INVISIBLE);
		}
		mShareAPI = UMShareAPI.get(getActivity());
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		registButton.setOnClickListener(this);
		loginButton.setOnClickListener(this);
		forgetButton.setOnClickListener(this);
		//tv_phone_register.setOnClickListener(this);
		tv_phone_register.setVisibility(View.GONE);

		btnWeixinLogin.setOnClickListener(this);
	}

	@Override
	protected void requestSuccess(String url, String data) {
		if (url.contains(login.getA())|| url.contains(weixinParam.getA())) {
			try {
				Toast.makeText(getActivity(), "登录成功!", Toast.LENGTH_SHORT);
				JSONObject jsonobj = new JSONObject(data);
				Const.user = gson.fromJson(jsonobj.get("data").toString(), User.class);
				jsonobj = new JSONObject(jsonobj.get("data").toString());
				Const.cache.setTokenId(jsonobj.getString("tokenId"));
				Const.isLogin = true;
				MineActivity.autoToMine=true;
				getShoppingCarInfo();
			} catch (JSONException e) {

			}
		}
		else if(url.contains(groupGoodsCart.getA())){
			try {
				JSONObject jsonObject = new JSONObject(data);
				JSONArray jsonArrayData = jsonObject.getJSONArray("data");
				Type listType = new TypeToken<ArrayList<ShoppingCart>>() {
				}.getType();
				List<ShoppingCart> shoppingListTemp = new Gson().fromJson(jsonArrayData.toString(), listType);
				MainActivity.mainActivity.refreshBuyNum(getShoppingCartNum(shoppingListTemp));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			finally {
				if(isFromMainActivity){
					((BaseActivity) getActivity()).replaceFragment(new MineFragment(), false);
				}else{
					leftClick();
				}
			}

		}
	}

	@Override
	protected void flagFailed(String url) {
		super.flagFailed(url);
		if(url.contains(groupGoodsCart.getA())){
			if(isFromMainActivity){
				((BaseActivity) getActivity()).replaceFragment(new MineFragment(), false);
			}else{
				leftClick();
			}
		}else if (url.contains(weixinParam.getA())){
			Intent i = new Intent(getActivity(), ThirdLoginActivity.class);
			i.putExtra("openid",openId);
			i.putExtra("unionid",unionid);
			startActivity(i);
		}
	}

	private int getShoppingCartNum(List<ShoppingCart> shoppinglist){
		int goodsTotalNum=0;
		for(int i=0;i<shoppinglist.size();i++){
			for(int j=0;j<shoppinglist.get(i).goods.size();j++){
				//goodsTotalNum=goodsTotalNum+shoppinglist.get(i).goods.get(j).goodsNum;
				goodsTotalNum++;
			}
		}
		SharedPreferencesUtils.saveValue(getActivity(), "cartNum", String.valueOf(goodsTotalNum));
		return goodsTotalNum;
	}
	private void getShoppingCarInfo(){
		groupGoodsCart.tokenId=Const.cache.getTokenId();
		requestNoDialog(groupGoodsCart);
	}
	private void weixinlogin(String openId,String unionid){
		weixinParam.openid = openId;
		weixinParam.unionid = unionid;
		request(weixinParam);

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		SHARE_MEDIA platform = null;
		switch (v.getId()) {
		case R.id.fragment_login_regist:
			Intent intent = new Intent(getActivity(), RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.fragment_login_login:
			String nameStr = nameEditText.getText().toString();
			String pswStr = pswEditText.getText().toString();
			if (TextUtils.isEmpty(nameStr)) {
				Toast.makeText(getActivity(), "用户名不能为空", Toast.LENGTH_SHORT).show();
			} else if (TextUtils.isEmpty(pswStr)) {
				Toast.makeText(getActivity(), "密码不能为空", Toast.LENGTH_SHORT).show();
			} else {
				login.login_name = nameStr;
				login.login_pwd = pswStr;
//				login.loginKey = Base64.encodeToString((Base64.encodeToString(nameStr.getBytes(), Base64.URL_SAFE)
//						+ "_" + Base64.encodeToString(pswStr.getBytes(), Base64.URL_SAFE)).getBytes(), Base64.NO_WRAP);
				request(login);
			}
			break;
		case R.id.fragment_login_forget:
			/*
			 * Intent intent = new Intent(getActivity(), MainActivity.class);
			 * getActivity().startActivity(intent);
			 */
			break;
		case R.id.btnWeixinLogin:
			platform = SHARE_MEDIA.WEIXIN;
			mShareAPI.doOauthVerify(getActivity(), platform, umAuthListener);
			mShareAPI.getPlatformInfo(getActivity(),platform,umAuthListener);
			break;
	/*	case R.id.tv_phone_register:
			replaceFragment(new PhoneRegisterFragment(),true);
		break;*/
		}
	}
	/** auth callback interface**/
	private UMAuthListener umAuthListener = new UMAuthListener() {
		@Override
		public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
			Log.d("json action",action+"");
			if (action == 0 && data!=null){
				openId = data.get("openid");
				unionid = data.get("unionid");
				weixinlogin(openId,unionid);
				Log.i("json openid&unionid,"+openId+","+unionid);

			}
		}

		@Override
		public void onError(SHARE_MEDIA platform, int action, Throwable t) {
			Log.d("json action fail",action+"");
			Toast.makeText( getActivity(), "Authorize fail", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel(SHARE_MEDIA platform, int action) {
			Log.d("json action cancel",action+"");
			Toast.makeText( getActivity(), "Authorize cancel", Toast.LENGTH_SHORT).show();
		}
	};


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mShareAPI.HandleQQError(getActivity(),requestCode,umAuthListener);
		mShareAPI.onActivityResult(requestCode, resultCode, data);

	}
}
