package com.ytmall.bean;

import java.io.Serializable;

import com.ytmall.util.EAJson;

/**
 * Created by Administrator on 13-7-17.
 */
public class City implements Serializable{

    @EAJson private String cityid;
    @EAJson private String cityname;

    public City() { }

    public City(String id, String name) {
        cityid = id;
        cityname = name;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
}
