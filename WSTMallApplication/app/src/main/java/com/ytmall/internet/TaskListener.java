package com.ytmall.internet;

/**
 * Created by lihui on 2015/9/14.
 */
public interface TaskListener {
    void result(String resultJson);
    void stopTask();
    void error(String msg);
}
