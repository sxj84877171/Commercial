package com.ytmall.internet;

import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.application.Const;
import com.ytmall.bean.AbstractParam;
import com.ytmall.util.http.HttpMethod;
import com.ytmall.util.http.RequestType;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by lee on 2016/12/27.
 */

public class APIResult {
    private static AbstractParam param;
    private static ApiTaskCallBack taskCallBack;

    public void setTaskCallBack(ApiTaskCallBack taskCallBack){
        this.taskCallBack = taskCallBack;
    }
    public  APIResult (){
        taskCallBack = new ApiTaskCallBack();
    }

    public static void request(final AbstractParam ap) {
        Class clazz = ap.getClass();
        param = ap;
        RequestType type = (RequestType) clazz.getAnnotation(RequestType.class);
        if (type != null) {
            final String url = Const.API_BASE_URL + "&a="+param.getA();
            RequestParams p = ap.getChildFatherRequestParam();
            AsyncHttpClient c = new AsyncHttpClient();
            if (type.type().equals(HttpMethod.GET)) {
                c.get(url + ap.getString(), textHttpResponseHandler);

                Log.i("get信息", url + ap.getString());
            } else {
                c.post(url, p, textHttpResponseHandler);
                Log.i("post信息", url + p);
            }
        }
    }
    static TextHttpResponseHandler textHttpResponseHandler = new TextHttpResponseHandler() {

        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseBody) {
            // TODO Auto-generated method stub
            System.out.println("responseBody : " + responseBody);
            try {
                if (responseBody.startsWith("\ufeff")) {
                    responseBody = responseBody.substring(1);
                }
                if (responseBody.indexOf("{") > -1) {
                    responseBody = responseBody.substring(responseBody.indexOf("{"));
                    JSONObject response = new JSONObject(responseBody);
                    if (response.getInt("status") == -1000) {
                        if (taskCallBack != null){
                            taskCallBack.error("用户令牌已过期，请重新登录");
                        }
                        reLogin();
                        return;
                    } else if (response.getInt("status") == 1) {
                        response.toString();
                        if(taskCallBack != null){
                            if(response.toString()!=null){
                                taskCallBack.result(response.toString());
                            }else{
                                taskCallBack.result(null);
                            }
                        }
                    } else {
                        if (taskCallBack != null){
                            taskCallBack.error(response.getString("msg"));
                        }
                    }
                } else {
                    if (taskCallBack != null){
                        taskCallBack.error("请求出错，请重试！");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (taskCallBack != null){
                    taskCallBack.error(e.getMessage());
                }
            }
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
            // TODO Auto-generated method stub
            requestFailed();
        }
    };

    public static void reLogin() {
        Const.isLogin = false;
        Const.cache.setTokenId(null);
        Const.user = null;

    }
    protected static void requestFailed() {
        if (taskCallBack != null){
            taskCallBack.error("网络错误!");
        }

    }
    public static class ApiTaskCallBack extends AbsTaskCallBack{

    }
}
