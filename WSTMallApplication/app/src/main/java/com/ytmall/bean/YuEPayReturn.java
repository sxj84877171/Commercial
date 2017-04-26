package com.ytmall.bean;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by lee on 2017/1/9.
 */

public class YuEPayReturn extends BaseParam {
    private String msg;
    private int status;
    private String data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
