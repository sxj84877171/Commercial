package com.ytmall.util;

import android.Manifest;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.BuildConfig;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.ytmall.R;
import com.ytmall.api.version.VersionCheckParam;
import com.ytmall.api.version.VersionCheckReturn;
import com.ytmall.application.Const;
import com.ytmall.internet.APICallBack;
import com.ytmall.internet.LocalAPI;
import com.ytmall.service.UpdataService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 *@author coolszy
 *@date 2012-4-26
 *@blog http://blog.92coding.com
 */

public class UpdateManager
{
	/* 下载中 */
	private static final int DOWNLOAD = 1;
	/* 下载结束 */
	private static final int DOWNLOAD_FINISH = 2;
	/* 保存解析的XML信息 */
	HashMap<String, String> mHashMap;
	/* 下载保存路径 */
	private String mSavePath;
	/* 记录进度条数量 */
	private int progress;
	/* 是否取消更新 */
	private boolean cancelUpdate = false;

	private Context mContext;
	/* 更新进度条 */
	private ProgressBar mProgress;
	private Dialog mDownloadDialog;

	private Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			// 正在下载
			case DOWNLOAD:
				// 设置进度条位置
				mProgress.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				// 安装文件
				installApk();

				break;
			default:
				break;
			}
		};
	};

	public UpdateManager(Context context)
	{
		this.mContext = context;
	}

	/**
	 * 检测软件更新
	 */
	public void checkUpdate(String url) {
		int versionNumber = getVersionCode(mContext);
		downloadUrl = url;
		showNoticeDialog();
	}

	/**
	 * 检查软件是否有更新版本
	 * 
	 * @return
	 */
	private boolean isUpdate() {


//		if (code == 1){
//			return true;
//
//		}
//		// 获取当前软件版本
//		int versionCode = getVersionCode(mContext);
//		// 把version.xml放到网络上，然后获取文件信息
//		InputStream inStream = ParseXmlService.class.getClassLoader().getResourceAsStream("version.xml");
//		// 解析XML文件。 由于XML文件比较小，因此使用DOM方式进行解析
//		ParseXmlService service = new ParseXmlService();
//		try
//		{
//			mHashMap = service.parseXml(inStream);
//		} catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//		if (null != mHashMap)
//		{
//			int serviceCode = Integer.valueOf(mHashMap.get("version"));
//			// 版本判断
//			if (serviceCode > versionCode)
//			{
//				return true;
//			}
//		}
		return false;
	}
	private int code = 0;
	private String downloadUrl = "";





	/**
	 * 获取软件版本号
	 * 
	 * @param context
	 * @return
	 */
	private int getVersionCode(Context context)
	{
		int versionCode = 0;
		try
		{
			// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
			versionCode = context.getPackageManager().getPackageInfo("com.ytmall", 0).versionCode;
		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * 显示软件更新对话框
	 */
	private void showNoticeDialog()
	{
		// 构造对话框
		Builder builder = new Builder(mContext);
		builder.setTitle(R.string.soft_update_title);
		builder.setMessage(R.string.soft_update_info);
		// 更新
		builder.setPositiveButton(R.string.soft_update_updatebtn, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				// 显示下载对话框
				showDownloadDialog();
			}
		});
		// 稍后更新
		builder.setNegativeButton(R.string.soft_update_later, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
		Dialog noticeDialog = builder.create();
		noticeDialog.show();
	}

	/**
	 * 显示软件下载对话框
	 */
	private void showDownloadDialog()
	{
		// 构造软件下载对话框
		Builder builder = new Builder(mContext);
		builder.setTitle(R.string.soft_updating);
		// 给下载对话框增加进度条
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.softupdate_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		builder.setView(v);
		// 取消更新
		builder.setNegativeButton(R.string.soft_update_cancel, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				// 设置取消状态
				cancelUpdate = true;
			}
		});
		mDownloadDialog = builder.create();
		mDownloadDialog.show();
		// 现在文件
		downloadApk();
	}

	/**
	 * 下载apk文件
	 */
	private void downloadApk()
	{
		// 启动新线程下载软件
		new downloadApkThread().start();
	}

	/**
	 * 下载文件线程
	 * 
	 * @author coolszy
	 *@date 2012-4-26
	 *@blog http://blog.92coding.com
	 */
	private class downloadApkThread extends Thread
	{
		@Override
		public void run()
		{
			try
			{
				// 判断SD卡是否存在，并且是否具有读写权限
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
				{
					// 获得存储卡的路径
//					String sdpath = Environment.getExternalStorageDirectory()+"Android/data/com.ytmall/";
					String sdpath = Environment.getExternalStorageDirectory() + "/";
//					mSavePath = mContext.getFilesDir()+"/images";
					mSavePath = sdpath + "download";
					Log.d("apk path",mSavePath);

//					File imagePath = new File(mContext.getFilesDir(), "images");
//					File newFile = new File(imagePath, "yuntang.apk");
//					Uri contentUri = getUriForFile(getContext(), "com.mydomain.fileprovider", newFile);




					URL url = new URL(downloadUrl);
					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.connect();
					// 获取文件大小
					int length = conn.getContentLength();
					// 创建输入流
					InputStream is = conn.getInputStream();

					File file = new File(mSavePath);
//					File imagePath = new File(mContext.getFilesDir(), "images");
					// 判断文件目录是否存在
					if (!file.exists())
					{
						file.mkdir();
					}
					File apkFile = new File(mSavePath, "yuntang.apk");
//					File apkFile = new File(mSavePath, mHashMap.get("name"));
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// 缓存
					byte buf[] = new byte[1024];
					// 写入到文件中
					do
					{
						int numread = is.read(buf);
						count += numread;
						// 计算进度条位置
						progress = (int) (((float) count / length) * 100);
						// 更新进度
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0)
						{
							// 下载完成
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// 写入文件
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// 点击取消就停止下载.
					fos.close();
					is.close();
				}
			} catch (MalformedURLException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			// 取消下载对话框显示
			mDownloadDialog.dismiss();
		}
	};

	/**
	 * 安装APK文件
	 */
	private void installApk()
	{
		File apkfile = new File(mSavePath, "yuntang.apk");

		Log.i("apk1",apkfile.toString()+","+apkfile.length());
		Toast.makeText(mContext,apkfile.toString(),Toast.LENGTH_SHORT).show();

		if (!apkfile.exists())
		{
			return;
		}
//		 //通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");

		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Log.d("apk",apkfile.toString());
		mContext.startActivity(i);

//		Intent intent = new Intent(Intent.ACTION_VIEW);
////      //判断是否是AndroidN以及更高的版本
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//			intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
////			Uri photoURI = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().
////					getPackageName() + ".provider", createImageFile());
//			Uri contentUri = FileProvider.getUriForFile(mContext, "com.ytmall.fileprovider", apkfile);
////			Uri contentUri = FileProvider.getUriForFile(getContext(), "com.mydomain.fileprovider", newFile);
//
//			Log.i("apk",String.valueOf(contentUri));
//			intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
//		} else {
//			intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
//			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			Log.i("apk",apkfile.toString());
//		}
//		mContext.startActivity(intent);
	}
}
