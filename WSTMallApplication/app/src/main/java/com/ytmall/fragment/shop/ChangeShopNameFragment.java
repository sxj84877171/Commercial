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
# 创建日期：2015-7-21
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
*/
package com.ytmall.fragment.shop;

import android.widget.Toast;

import com.ytmall.R;
import com.ytmall.api.user.EditUserInfo;
import com.ytmall.application.Const;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.widget.ClearEditText;


@FragmentView(id = R.layout.fragment_change_username)
public class ChangeShopNameFragment extends BaseFragment{
	
	@InjectView(id = R.id.user_name)
	private ClearEditText user_name;
	
	private String tempName;
	private EditUserInfo editUserInfo=new EditUserInfo();

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		tWidget.setRightBtnText("确定");
//		editUserInfo.tokenId=Const.cache.getTokenId();
//		editUserInfo.userSex=Const.user.userSex;
	}
	
	@Override
	public void rightClick() {
		tempName=user_name.getText().toString().trim();
		if(tempName.equals("")){
			Toast.makeText(getActivity(), "店铺名称不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		editUserInfo.userName=tempName;
		//上传名字
//		request(editUserInfo);
	}
	
	@Override
	protected void requestSuccess(String url, String data) {
		Const.user.userName=tempName;
		leftClick();
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		
	}

}
