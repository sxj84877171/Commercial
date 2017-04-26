package com.ytmall.sys;

import android.os.Environment;

public class AppConfig {
    /** 文件缓存目录 */
    public static final String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ytmall/";
    public static final String SDPATH_IMAGE = SDPATH + "image/";
    public static final String ROOT_PATH = "/yttmall";
//    public static final String URL = "http://192.168.0.113:8080/huaShengTV/rest/[service]";
    //外网
    public static final String URL = "http://112.74.20.129:8080/huaShengTV/rest/[service]";
    public static final String API_BASE_URL = "http://dev-wstfull.wstmall.com/index.php?m=App&c=Apis";

    public static final String TEAM_CACHE_IMAGE = SDPATH + "img_cache/";
    //120.24.253.63
//    public static final String TEAM_CACHE_IMAGE = ROOT_PATH + "/img_cache/";
    public static final String LOG_PATH = SDPATH + "log/";
//    public static final String LOG_PATH = ROOT_PATH + "/log/";
    public static final String WEIXIN_APPID = "wx3658b47fb33d227d";
    public static final String MCH_ID = "1233848001";
    public static final String WEIXIN_APP_SECRET = "5b4124fb594f69832bb8f8c5641a87d3";
    public static final String DESCRIPTOR = "com.umeng.share";
    public static final String SINA_REDIRECT_URL="http://sns.whalecloud.com/sina2/callback";
    public static final String QQ_ID = "1105566603";
    public static final String QQ_KEY = "fYuo92hg8Aj6ovco";
    public static final String activityId = "297ebe0e57db30180157dba5dcf3002d";
    //测试
    //app_key
    public static  String APP_KEY = "538b1ec4938b0f294957f9f02f3810bde1580e0fjia";
    //app_id
    public static  String APP_ID = "15";
    public static final String GT_MSG_ID = "A1429";




}
