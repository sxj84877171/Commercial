package com.ytmall.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by lee on 16/12/12.
 */
public class MainPageReturn extends BaseParam {
    private String status;
    private String msg;
    private List<Advertisement> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Advertisement> getData() {
        return data;
    }

    public void setData(List<Advertisement> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
