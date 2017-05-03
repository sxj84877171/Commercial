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
# 创建日期：2015-7-20
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
 */
package com.ytmall.fragment.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;
import java.util.jar.Manifest;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.activity.MainActivity;
import com.ytmall.activity.imagecrop.CropActivity;
import com.ytmall.activity.user.MineActivity;
import com.ytmall.api.UploadPic;
import com.ytmall.api.user.EditUserPhoto;
import com.ytmall.application.Const;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.fragment.money.BankAccountFragment;
import com.ytmall.util.FileUtil;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.widget.BottomPopWindow;


@FragmentView(id = R.layout.fragment_edit_user_info)
public class EditUserInfoFragment extends BaseFragment implements View.OnClickListener, BottomPopWindow.OnMenuSelect {

	@InjectView(id = R.id.head_image_layout)
	private View head_image_layout;
	@InjectView(id = R.id.user_name_layout)
	private View user_name_layout;
	@InjectView(id = R.id.sex_layout)
	private View sex_layout;
	@InjectView(id = R.id.account_from_layout)
	private View account_from_layout;
	@InjectView(id = R.id.back_score_layout)
	private View back_score_layout;


	@InjectView(id = R.id.head_image)
	private ImageView head_image;

	@InjectView(id = R.id.login_name)
	private TextView login_name;
	@InjectView(id = R.id.user_name)
	private TextView user_name;
	@InjectView(id = R.id.sex)
	private TextView sex;
	@InjectView(id = R.id.back_score)
	private TextView back_score;
	@InjectView(id = R.id.account_from)
	private TextView account_from;
	@InjectView(id = R.id.back_score_phone)
	private TextView back_score_phone;
	@InjectView(id = R.id.back_score_phone_1)
	private TextView back_score_phone_1;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.head_image_layout:
			showButtomPop(this, new String[] { "拍照", "我的相册" });
			break;
		case R.id.user_name_layout:
			replaceFragment(new ChangeUserNameFragment(), true);
			break;
		case R.id.sex_layout:
			replaceFragment(new ChangeSexFragment(), true);
			break;
		case R.id.account_from_layout:
			replaceFragment(new AccountFromFragment(),true);
			break;
		case R.id.back_score_layout:
			replaceFragment(new BackScroeFragment(),true);
			break;
		}
	}
	
	@Override
	public void leftClick() {
		super.leftClick();
		MainActivity.mHost.getTabWidget().setVisibility(View.VISIBLE);
	}

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		login_name.setText(Const.user.loginName);
		if (Const.user.userName != null && !Const.user.userName.equals("")) {
			user_name.setText(Const.user.userName);
		}
		sex.setText(Const.user.getSex());
		if (Const.user.userPhoto != null && !Const.user.userPhoto.equals("")) {
			loadOnRoundImage(Const.BASE_URL + Const.user.userPhoto, head_image);
		}
		if (Const.user.user_source > 0){
			if (Const.user.user_source==2){
				account_from.setText("大唐商会");
			}else if (Const.user.user_source==1){
				account_from.setText("云联商会");

			}

		}
		if (Const.user.userPhone != null && !Const.user.userPhone.equals("")){
			back_score_phone.setText(Const.user.userPhone);
		}
		if (Const.user.jifenPhone != null && !Const.user.userPhone.equals("")){
			back_score_phone_1.setText(Const.user.jifenPhone);
		}
		if (Const.user.user_link_account != null && !Const.user.user_link_account.equals("")){
			back_score.setText(Const.user.user_link_account);
		}
		((MineActivity)getActivity()).editUserInfoFragment=this;
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		head_image_layout.setOnClickListener(this);
		user_name_layout.setOnClickListener(this);
		sex_layout.setOnClickListener(this);
		account_from_layout.setOnClickListener(this);
		back_score_layout.setOnClickListener(this);
	}
	
	public void refreshHeadImage(){
		loadOnRoundImage(Const.BASE_URL + Const.user.userPhoto, head_image);
	}

	@Override
	public void onItemMenuSelect(int position) {
		// TODO Auto-generated method stub
		switch (position) {
		case 0:
			if (MainActivity.getCaramePermission()){
				((MineActivity)getActivity()).startCamera(MineActivity.CAMERA_PHOTO);
			}

			break;
		case 1:
			if (MainActivity.getWritePermission()){
				((MineActivity)getActivity()).startSelectPhoto(MineActivity.CHOOSE_ALBUM);
			}

			break;
		}
		buttomPop.dismiss();
	}

	@Override
	public void onCancelSelect() {
		// TODO Auto-generated method stub
		buttomPop.dismiss();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (Const.user.user_source > 0){
			if (Const.user.user_source==2){
				account_from.setText("大唐天下");
			}else if (Const.user.user_source==1){
				account_from.setText("云联商会");

			}

		}
		if (Const.user.user_link_account != null && !Const.user.user_link_account.equals("")){
			back_score.setText(Const.user.user_link_account);
		}
	}
}
