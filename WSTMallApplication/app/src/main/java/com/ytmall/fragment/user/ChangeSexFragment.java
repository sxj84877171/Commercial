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
package com.ytmall.fragment.user;

import android.view.View;
import android.widget.ImageView;

import com.ytmall.R;
import com.ytmall.api.user.EditUserInfo;
import com.ytmall.application.Const;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;


@FragmentView(id = R.layout.fragment_change_sex)
public class ChangeSexFragment extends BaseFragment implements View.OnClickListener {
	
	@InjectView(id = R.id.man_layout)
	private View man_layout;
	@InjectView(id = R.id.lady_layout)
	private View lady_layout;
	@InjectView(id = R.id.secret_layout)
	private View secret_layout;
	
	@InjectView(id = R.id.man_right)
	private ImageView man_right;
	@InjectView(id = R.id.lady_right)
	private ImageView lady_right;
	@InjectView(id = R.id.secret_right)
	private ImageView secret_right;
	
	private EditUserInfo editUserInfo=new EditUserInfo();
	
	private String tempSex;
	private String nowSex;

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		
		editUserInfo.tokenId=Const.cache.getTokenId();
		if(Const.user.userName!=null&&!Const.user.userName.equals("")){
		editUserInfo.userName=Const.user.userName;
		}
		
		nowSex=Const.user.getSex();
		if(nowSex.equals("男")){
			man_right.setVisibility(View.VISIBLE);
		}else if(nowSex.equals("女")){
			lady_right.setVisibility(View.VISIBLE);
		}else{
			secret_right.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		man_layout.setOnClickListener(this);
		lady_layout.setOnClickListener(this);
		secret_layout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		man_right.setVisibility(View.INVISIBLE);
		lady_right.setVisibility(View.INVISIBLE);
		secret_right.setVisibility(View.INVISIBLE);
		
		switch (v.getId()) {
		case R.id.man_layout:
			man_right.setVisibility(View.VISIBLE);
			tempSex=Const.user.getSexNum("男");
			break;
		case R.id.lady_layout:
			lady_right.setVisibility(View.VISIBLE);
			tempSex=Const.user.getSexNum("女");
			break;
		case R.id.secret_layout:
			secret_right.setVisibility(View.VISIBLE);
			tempSex=Const.user.getSexNum("保密");
			break;
		}
		if(!tempSex.equals(nowSex)){
			editUserInfo.userSex=tempSex;
			request(editUserInfo);
		}else{
			leftClick();
		}
	}
	
	@Override
	protected void requestSuccess(String url, String data) {
		Const.user.userSex=tempSex;
		leftClick();
	}

}
