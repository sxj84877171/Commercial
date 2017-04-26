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
# 创建日期：2015-6-11
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
 */
package com.ytmall.application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.socialize.PlatformConfig;
import com.ytmall.R;
import com.ytmall.bean.CacheBean;
import com.ytmall.util.CrashHandler;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;


public class WSTMallApplication extends MultiDexApplication {

	private List<Activity> activityList = new ArrayList<Activity>();
	public static WSTMallApplication instance;
	public static DisplayImageOptions imageRectangleOptions;
	public static DisplayImageOptions imageLongRectangleOptions;
	public static DisplayImageOptions imageEllipseOptions;
	public static DisplayImageOptions imageRoundOptions;
	private MyPref basePref;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		basePref = MyPref.getInstance(this);
		initImageLoader(getApplicationContext());
		initImageRectangleOptions();
		initImageLongRectangleOptions();
		initImageEllipseOptions();
		initImageRoundOptions();
		initInfo();
		//全局异常捕获
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());

//		MultiDex.install(this);
		initSharePlatform();



	}
	private void initSharePlatform(){
		//微信 appid appsecret
		PlatformConfig.setWeixin("wxd86856881c0ba3f1","ac5b1da36424379209000a8f47036b44");



	}

	private void initInfo() {
		if (Const.cache == null) {
			Const.cache = parseCache(basePref.getCache());
		}
	}

	public void saveCache() {
		if (Const.isNeedSaveCache) {
			basePref.setCache(new Gson().toJson(Const.cache).toString());
		}
	}

	public void cleanCache() {
		basePref.setCache(null);
		Const.user = null;
		Const.cache = null;
	}

	private CacheBean parseCache(String json) {
		if (json == null) {
			return new CacheBean();
		}
		try {
			CacheBean cache = new Gson().fromJson(json, CacheBean.class);
			return cache;
		} catch (Exception e) {
			return new CacheBean();
		}
	}

	// 正方形图片加载适配（普通加载器）
	public static void initImageRectangleOptions() {
		// 使用DisplayImageOptions.Builder()创建DisplayImageOptions
		imageRectangleOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ytlogo) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ytlogo) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ytlogo) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				.build(); // 构建完成
	}
	// 长方形图片加载适配（普通加载器）
	public static void initImageLongRectangleOptions() {
		// 使用DisplayImageOptions.Builder()创建DisplayImageOptions
		imageLongRectangleOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_rectagle_img) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.default_rectagle_img) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.default_rectagle_img) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				.build(); // 构建完成
	}

	// 椭圆图片加载适配
	public static void initImageEllipseOptions() {
		// 使用DisplayImageOptions.Builder()创建DisplayImageOptions
		imageEllipseOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ytlogo) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ytlogo) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ytlogo) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				.displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build(); // 构建完成
	}
	
	public static void initImageRoundOptions(){
		// 使用DisplayImageOptions.Builder()创建DisplayImageOptions
				imageRoundOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.person_img) // 设置图片下载期间显示的图片
						.showImageForEmptyUri(R.drawable.person_img) // 设置图片Uri为空或是错误的时候显示的图片
						.showImageOnFail(R.drawable.person_img) // 设置图片加载或解码过程中发生错误显示的图片
						.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
						.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
						.displayer(new RoundedBitmapDisplayer(60)) // 设置成圆型图片
						.build(); // 构建完成
	}

	// 图片加载器设定
	public static void initImageLoader(Context context) {
		// 缓存文件的目录
		File cacheDir = StorageUtils.getOwnCacheDirectory(context, "WSTMall/Cache");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.memoryCacheExtraOptions(480, 800)
				// max width, max height，即保存的每个缓存文件的最大长宽
				.threadPoolSize(3)
				// 线程池内线程的数量
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator()) // 将保存的时候的URI名称用MD5
																		// 加密
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)).memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值
				.diskCacheSize(50 * 1024 * 1024) // SD卡缓存的最大值
				.tasksProcessingOrder(QueueProcessingType.LIFO).diskCache(new UnlimitedDiskCache(cacheDir))// 自定义缓存路径
				.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout
				// (5 s),readTimeout (30 s)超时时间
				.writeDebugLogs() // Remove for release app
				.build();
		// 全局初始化此配置
		ImageLoader.getInstance().init(config);
	}

	public void addActivityToList(Activity activity) {
		activityList.add(activity);
	}

	public List<Activity> getActivityList() {
		return activityList;
	}

	public void exit() {
		for (Activity a : activityList) {
			a.finish();
		}
	}

	static RxPermissions sSingleton;
	public static WSTMallApplication getInstance() {

		return instance;
	}
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

}
