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
# 创建日期：2015-7-1
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
 */
package com.ytmall.fragment.login;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.activity.user.MineActivity;
import com.ytmall.api.login.Login;
import com.ytmall.api.login.Register;
import com.ytmall.application.Const;
import com.ytmall.bean.User;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.fragment.user.MineFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.widget.ClearEditText;


@FragmentView(id = R.layout.fragment_register)
public class RegisterFragment extends BaseFragment implements View.OnClickListener {

	@InjectView(id = R.id.fragment_register_register)
	View registButton;
	@InjectView(id = R.id.fragment_register_name)
	ClearEditText nameEditText;
	@InjectView(id = R.id.fragment_register_psw)
	ClearEditText pswEditText;
	@InjectView(id = R.id.fragment_register_psw_again)
	ClearEditText pswAgainEditText;

	private Register register = new Register();

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		registButton.setOnClickListener(this);
	}

	@Override
	protected void requestSuccess(String url, String data) {
		if (url.contains(register.getA())) {
			try {
				Toast.makeText(getActivity(), "注册成功!", Toast.LENGTH_SHORT);
				JSONObject jsonobj = new JSONObject(data);
				Const.user = gson.fromJson(jsonobj.get("data").toString(), User.class);
				jsonobj=new JSONObject(jsonobj.get("data").toString());
				Const.cache.setTokenId(jsonobj.getString("tokenId"));
				Const.isLogin=true;
				MineActivity.autoToMine=true;
				leftClick();
			} catch (JSONException e) {

			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fragment_register_register:
			String nameStr = nameEditText.getText().toString();
			String pswStr = pswEditText.getText().toString();
			String pswaStr = pswAgainEditText.getText().toString();
			if (TextUtils.isEmpty(nameStr)) {
				Toast.makeText(getActivity(), "用户名不能为空", Toast.LENGTH_SHORT).show();
			} else if (TextUtils.isEmpty(pswStr)) {
				Toast.makeText(getActivity(), "密码不能为空", Toast.LENGTH_SHORT).show();
			}else if (pswaStr.length()<6) {
				Toast.makeText(getActivity(), "密码不能小于6位", Toast.LENGTH_SHORT).show();
			}
			else if (TextUtils.isEmpty(pswaStr)) {
				Toast.makeText(getActivity(), "请在输入一次密码", Toast.LENGTH_SHORT).show();
			} else if (!pswaStr.equals(pswStr)) {
				Toast.makeText(getActivity(), "两次输入密码必须一样", Toast.LENGTH_SHORT).show();
			} else {
				String registerKeyString = Base64.encodeToString(
						(Base64.encodeToString(nameStr.getBytes(), Base64.URL_SAFE) + "_" + Base64.encodeToString(
								pswStr.getBytes(), Base64.URL_SAFE)).getBytes(), Base64.NO_WRAP);
				register.registerKey = registerKeyString;
				request(register);
			}
			break;
		}
	}

}
