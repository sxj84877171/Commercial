package com.ytmall.internet;


/**
 * Created by lihui on 2015/8/26.
 */
public abstract class APICallBack<T> implements APIListener<T> {

    private final static String TAG = "APICallBack";

    @Override
    public abstract void success(T t) ;

    @Override
    public void stop() {
    }

    @Override
    public void error(String msg) {
    }



}
