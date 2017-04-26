package com.ytmall.internet;


import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ytmall.api.advertisement.GetAds;
import com.ytmall.api.version.VersionCheckReturn;
import com.ytmall.bean.AbstractParam;
import com.ytmall.bean.YuEPayReturn;
import com.ytmall.model.BrandsReturn;

import org.json.JSONObject;

/**
 * Created by lihui on 2015/8/26.
 * 本类分装rest接口在本地的数据发送与解析逻辑，返回数据转化成对应的
 * 本地Model类，直接供UI层使用
 */
public class LocalAPI  {

    private final static String TAG = "LocalAPI";

    /**
     * 余额支付
     * @param param
     * @param callBack
     */
    public static void yuePay(AbstractParam param, final APICallBack<YuEPayReturn> callBack){
        APIResult.request(param);
        final APIResult result = new APIResult();
        result.setTaskCallBack(new APIResult.ApiTaskCallBack(){
            @Override
            public void result(String resultJson) {
                if (resultJson != null){
                    Log.d(TAG+"yuePay:",resultJson);
                    try {
                        JSONObject jsonStr = new JSONObject(resultJson);

                        YuEPayReturn result = new Gson().fromJson(jsonStr.toString(),
                                new TypeToken<YuEPayReturn>(){}.getType());
                        if (callBack != null){
                            callBack.success(result);
                        }

                    }catch (Exception e){
                        if (callBack != null){
                            callBack.error(e.getMessage());
                        }

                    }

                }
            }

            @Override
            public void error(String msg) {
                super.error(msg);
                if (callBack != null){
                    callBack.error(msg);
                }
            }
        });

    }
    /**
     * 充值记录
     * @param param
     * @param callBack
     */
    public static void queryRechargeList(AbstractParam param, final APICallBack<YuEPayReturn> callBack){
        APIResult.request(param);
        final APIResult result = new APIResult();
        result.setTaskCallBack(new APIResult.ApiTaskCallBack(){
            @Override
            public void result(String resultJson) {
                if (resultJson != null){
//                    Log.d(TAG+"queryRechargeList:",resultJson);
                    try {
                        JSONObject jsonStr = new JSONObject(resultJson);

                        YuEPayReturn result = new Gson().fromJson(jsonStr.toString(),
                                new TypeToken<YuEPayReturn>(){}.getType());
                        if (callBack != null){
                            callBack.success(result);
                        }

                    }catch (Exception e){
                        if (callBack != null){
                            callBack.error(e.getMessage());
                        }

                    }

                }
            }

            @Override
            public void error(String msg) {
                super.error(msg);
                if (callBack != null){
                    callBack.error(msg);
                }
            }
        });

    }
    /**
     * 获取用户人数
     * @param param
     * @param callBack
     */
    public static void queryRecommdCount(AbstractParam param, final APICallBack<YuEPayReturn> callBack){
        APIResult.request(param);
        final APIResult result = new APIResult();
        result.setTaskCallBack(new APIResult.ApiTaskCallBack(){
            @Override
            public void result(String resultJson) {
                if (resultJson != null){
                    Log.d(TAG,"json queryRecommdCount:"+resultJson);
                    try {
                        JSONObject jsonStr = new JSONObject(resultJson);

                        YuEPayReturn result = new Gson().fromJson(jsonStr.toString(),
                                new TypeToken<YuEPayReturn>(){}.getType());
                        if (callBack != null){
                            callBack.success(result);
                        }

                    }catch (Exception e){
                        if (callBack != null){
                            callBack.error(e.getMessage());
                        }

                    }

                }
            }

            @Override
            public void error(String msg) {
                super.error(msg);
                if (callBack != null){
                    callBack.error(msg);
                }
            }
        });

    }

    /**
     *  获取总佣金
     * @param param
     * @param callBack
     */
    public static void queryDistributMoneys(AbstractParam param, final APICallBack<YuEPayReturn> callBack){
        APIResult.request(param);
        final APIResult result = new APIResult();
        result.setTaskCallBack(new APIResult.ApiTaskCallBack(){
            @Override
            public void result(String resultJson) {
                if (resultJson != null){
                    Log.d("json ","queryDistributMoneys"+resultJson);
                    try {
                        JSONObject jsonStr = new JSONObject(resultJson);

                        YuEPayReturn result = new Gson().fromJson(jsonStr.toString(),
                                new TypeToken<YuEPayReturn>(){}.getType());
                        if (callBack != null){
                            callBack.success(result);
                        }

                    }catch (Exception e){
                        if (callBack != null){
                            callBack.error(e.getMessage());
                        }

                    }

                }
            }

            @Override
            public void error(String msg) {
                super.error(msg);
                if (callBack != null){
                    callBack.error(msg);
                }
            }
        });

    }

    /**
     *  校验支付密码
     * @param param
     * @param callBack
     */
    public static void checkPayPwd(AbstractParam param, final APICallBack<YuEPayReturn> callBack){
        APIResult.request(param);
        final APIResult result = new APIResult();
        result.setTaskCallBack(new APIResult.ApiTaskCallBack(){
            @Override
            public void result(String resultJson) {
                if (resultJson != null){
                    Log.d("json ","checkPayPwd"+resultJson);
                    try {
                        JSONObject jsonStr = new JSONObject(resultJson);

                        YuEPayReturn result = new Gson().fromJson(jsonStr.toString(),
                                new TypeToken<YuEPayReturn>(){}.getType());
                        if (callBack != null){
                            callBack.success(result);
                        }

                    }catch (Exception e){
                        if (callBack != null){
                            callBack.error(e.getMessage());
                        }

                    }

                }
            }

            @Override
            public void error(String msg) {
                super.error(msg);
                if (callBack != null){
                    callBack.error(msg);
                }
            }
        });

    }
    /**
     *  删除银行账户
     * @param param
     * @param callBack
     */
    public static void deleteAccount(AbstractParam param, final APICallBack<YuEPayReturn> callBack){
        APIResult.request(param);
        final APIResult result = new APIResult();
        result.setTaskCallBack(new APIResult.ApiTaskCallBack(){
            @Override
            public void result(String resultJson) {
                if (resultJson != null){
                    Log.d("json ","deleteAccount"+resultJson);
                    try {
                        JSONObject jsonStr = new JSONObject(resultJson);

                        YuEPayReturn result = new Gson().fromJson(jsonStr.toString(),
                                new TypeToken<YuEPayReturn>(){}.getType());
                        if (callBack != null){
                            callBack.success(result);
                        }

                    }catch (Exception e){
                        if (callBack != null){
                            callBack.error(e.getMessage());
                        }

                    }

                }
            }

            @Override
            public void error(String msg) {
                super.error(msg);
                if (callBack != null){
                    callBack.error(msg);
                }
            }
        });

    }
    /**
     *  删除银行账户
     * @param param
     * @param callBack
     */
    public static void version_check(AbstractParam param, final APICallBack<VersionCheckReturn> callBack){
        APIResult.request(param);
        final APIResult result = new APIResult();
        result.setTaskCallBack(new APIResult.ApiTaskCallBack(){
            @Override
            public void result(String resultJson) {
                if (resultJson != null){
                    Log.d("json ","version_check"+resultJson);
                    try {
                        JSONObject jsonStr = new JSONObject(resultJson);

                        VersionCheckReturn result = new Gson().fromJson(jsonStr.toString(),
                                new TypeToken<VersionCheckReturn>(){}.getType());
                        if (callBack != null){
                            callBack.success(result);
                        }

                    }catch (Exception e){
                        if (callBack != null){
                            callBack.error(e.getMessage());
                        }

                    }

                }
            }

            @Override
            public void error(String msg) {
                super.error(msg);
                if (callBack != null){
                    callBack.error(msg);
                }
            }
        });

    }
    /**
     *  获取服务器版本号
     * @param param
     * @param callBack
     */
    public static void getAppVersion(AbstractParam param, final APICallBack<VersionCheckReturn> callBack){
        APIResult.request(param);
        final APIResult result = new APIResult();
        result.setTaskCallBack(new APIResult.ApiTaskCallBack(){
            @Override
            public void result(String resultJson) {
                if (resultJson != null){
                    Log.d("json ","getAppVersion"+resultJson);
                    try {
                        JSONObject jsonStr = new JSONObject(resultJson);

                        VersionCheckReturn result = new Gson().fromJson(jsonStr.get("data").toString(),
                                new TypeToken<VersionCheckReturn>(){}.getType());
                        if (callBack != null){
                            callBack.success(result);
                        }

                    }catch (Exception e){
                        if (callBack != null){
                            callBack.error(e.getMessage());
                        }

                    }

                }
            }

            @Override
            public void error(String msg) {
                super.error(msg);
                if (callBack != null){
                    callBack.error(msg);
                }
            }
        });

    }
}

