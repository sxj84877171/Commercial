package com.ytmall.internet;

/**
 * Created by lihui on 2015/8/26.
 */
public interface APIListener<T> {
    void success(T t);
    void error(String error);
    void stop();
}
