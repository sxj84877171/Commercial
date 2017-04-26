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

import android.os.Environment;

import com.ytmall.bean.CacheBean;
import com.ytmall.bean.City;
import com.ytmall.bean.Point;
import com.ytmall.bean.User;


public class Const {

	public static User user;
	public static Boolean isLogin=false;//判断是否已登录
	public static CacheBean cache;//数据缓存
	public static boolean isNeedSaveCache=false;//是否需要保存缓存


	
	public static final String FILE_NAME="WSTMall";//缓存以及Loading图等存放目录的名称

	public static final String BASE_URL = "http://www.yuntangnet.cn/";
//	public static final String BASE_URL = "http://ytsc.zglxkj.com/";
	public static final String API_BASE_URL = BASE_URL+"index.php?m=App&c=Apis";
//	public static final String API_BASE_URL = "http://ytsc.zglxkj.com/index.php?m=App&c=Apis";
	public static final Point defaultPoint = new Point(40.657366, 109.874611);
	//判断当前是那种搜索
	/**1:商品搜索 2：商家搜索*/
	public static int searchType=1;
	public static final String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WSTMall/";
	public static final String LOG_PATH = SDPATH + "log/";

	public static String APP_ID="";
}
