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
# 创建日期：2015-6-12
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
*/
package com.ytmall.activity.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.ytmall.activity.BaseActivity;
import com.ytmall.activity.MainActivity;
import com.ytmall.activity.imagecrop.CropActivity;
import com.ytmall.api.UploadPic;
import com.ytmall.api.user.EditUserPhoto;
import com.ytmall.application.Const;
import com.ytmall.fragment.login.LoginFragment;
import com.ytmall.fragment.user.EditUserInfoFragment;
import com.ytmall.fragment.user.MineFragment;
import com.ytmall.util.FileUtil;

import rx.functions.Action0;
import rx.functions.Action1;


public class MineActivity  extends BaseActivity{
	public static boolean autoToMine=false;//用这个判断，是否自动跳转到Mine
	
	private final int CAMERA_PHOTO = 23;
	private final int CHOOSE_ALBUM = 24;
	private final int PHOTO_CROP = 25;
	
	private Uri selectedImageUri = null;
	private UploadPic uploadPic=new UploadPic();
	private EditUserPhoto editUserPhoto=new EditUserPhoto();
	public EditUserInfoFragment editUserInfoFragment;
	
	private String userPhoto;
	private RxPermissions rxPermissions;
	private Activity act;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		act = this;

//		RxPermissions  rxPermissions = new RxPermissions(this);
//		rxPermissions.setLogging(true);
		replaceFragment(new LoginFragment(LoginFragment.fromMainActivity), false);


//		rxPermissions.request(Manifest.permission.CAMERA).
//				subscribe(new Action1<Boolean>() {
//			@Override
//			public void call(Boolean aBoolean) {
//				if (aBoolean){
//					String a = "1";
//					Toast.makeText(act,""+aBoolean+a,Toast.LENGTH_SHORT).show();
//				}else {
//					String b= "2";
//					Toast.makeText(act,""+aBoolean+b,Toast.LENGTH_SHORT).show();
//				}
//
//			}


//		});




	}



	
	@Override
	protected void onResume() {
		super.onResume();
		if(autoToMine&&Const.isLogin){
			replaceFragment(new MineFragment(), false);
			autoToMine=false;
		}
	}
	
	@Override
	protected void requestSuccess(String url, String data) {
		if (url.contains(uploadPic.getA())) {
			try {
				JSONObject jsonobj = new JSONObject(data);
				jsonobj=jsonobj.getJSONObject("Filedata");
				userPhoto=jsonobj.getString("savepath")+jsonobj.getString("savename");
				editUserPhoto.tokenId=Const.cache.getTokenId();
				editUserPhoto.userPhoto=userPhoto;
				request(editUserPhoto);
			} catch (JSONException e) {
			}
		}else if (url.contains(editUserPhoto.getA())) {
			Const.user.userPhoto=userPhoto;
			editUserInfoFragment.refreshHeadImage();
		} 
	}

	public void startCamera() {// 用户点击拍照
		String FILE_NAME = UUID.randomUUID() + ".jpg";
		File photo = FileUtil.getNewFile(this, FILE_NAME);
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		if (photo != null) {
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
			selectedImageUri = Uri.fromFile(photo);
			startActivityForResult(intent, CAMERA_PHOTO);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { 
	      //do something here
	    	if(whatFragmentTopNow().equals("com.ytmall.fragment.user.MineFragment")||whatFragmentTopNow().equals("com.wstmall.fragment.login.LoginFragment")){
	    		MainActivity.mHost.setCurrentTab(0);
	    		return true;
	    	}
	    	return super.onKeyDown(keyCode, event);
	    }
	    return super.onKeyDown(keyCode, event);
	}

	public void startSelectPhoto() {// 用户点击 从相册获取
		Intent mIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(mIntent, CHOOSE_ALBUM);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			// 拍照前检查SDCard是否存在
			if (!isSDAvailable()) {
				return;
			}
			switch (requestCode) {
			case CAMERA_PHOTO:// 拍照后
				startPhotoCrop(selectedImageUri);
				break;
			case PHOTO_CROP:// 照片完成裁剪后
				readCropImage(data);
				break;
			case CHOOSE_ALBUM:// 选择相册
				if (data != null) {
					Uri originalUri = data.getData();
					startPhotoCrop(originalUri);
				}
				break;
			}
		}
	}

	/**
	 * 检查SD卡是否可用
	 */
	private boolean isSDAvailable() {
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * 读取裁剪好的图片
	 */
	private void readCropImage(Intent data) {

		if (data != null) {
			Uri uri = data.getParcelableExtra(CropActivity.CROP_IMAGE_URI);
			uploadPic.Filedata=new File(getFilePath(uri));
			request(uploadPic);
		}
	}
	
	/**
	 * 根据Uri返回文件路径
	 */
	private String getFilePath(Uri mUri) {
		try {
			if (mUri.getScheme().equals("file")) {
				return mUri.getPath();
			} else {
				return getFilePathByUri(mUri);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private String getFilePathByUri(Uri mUri) throws FileNotFoundException {
		String imgPath;
		Cursor cursor = getContentResolver().query(mUri, null, null, null, null);
		cursor.moveToFirst();
		imgPath = cursor.getString(1); // 图片文件路径
		return imgPath;
	}

	// 获取的照片，进行裁剪，无论拍照还是相册选择
	private void startPhotoCrop(Uri uri) {
		Intent intent = new Intent(this, CropActivity.class);
		intent.putExtra(CropActivity.IMAGE_URI, uri);
		startActivityForResult(intent, PHOTO_CROP);
	}

//	private void getPremission(){
//		RxView.clicks(rl_update)
//				// Ask for permissions when button is clicked
//				.compose(rxPermissions.ensureEach(Manifest.permission.WRITE_EXTERNAL_STORAGE))
//				.subscribe(new Action1<Permission>() {
//							   @Override
//							   public void call(Permission permission) {
////								   Log.i(TAG, "Permission result " + permission);
//								   if (permission.granted) {
//
//								   } else if (permission.shouldShowRequestPermissionRationale) {
//									   // Denied permission without ask never again
//									   Toast.makeText(act,
//											   "Denied permission without ask never again",
//											   Toast.LENGTH_SHORT).show();
//								   } else {
//									   // Denied permission with ask never again
//									   // Need to go to the settings
////									   Toast.makeText(this,
////											   "Permission denied, can't enable the camera",
////											   Toast.LENGTH_SHORT).show();
//									   Toast.makeText(act,"Permission denied, can't enable the camera",Toast.LENGTH_SHORT).show();
//								   }
//							   }
//						   },
//						new Action1<Throwable>() {
//							@Override
//							public void call(Throwable t) {
////								Log.e(TAG, "onError", t);
//							}
//						},
//						new Action0() {
//							@Override
//							public void call() {
////								Log.i(TAG, "OnComplete");
//							}
//						});
//	}
	
}
