package com.ytmall.bean;

import com.google.gson.Gson;
import com.ytmall.bean.BaseParam;

/**
 * Created by lee on 2017/1/18.
 */

public class QueryRecommdCountBean extends BaseParam {
    private String msg;
    private int status;
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
