package com.ytmall.model;

import com.google.gson.Gson;
import com.ytmall.bean.BaseParam;
import com.ytmall.bean.Brandsbean;

import java.util.List;

/**
 * Created by lee on 2016/12/29.
 */

public class BrandsReturn extends BaseParam {
    private List<Brandsbean> data;

    public List<Brandsbean> getData() {
        return data;
    }

    public void setData(List<Brandsbean> data) {
        this.data = data;
    }
    //    private String brandId;
//    private String brandName;
//    private String brandIco;
//
//    public String getBrandId() {
//        return brandId;
//    }
//
//    public void setBrandId(String brandId) {
//        this.brandId = brandId;
//    }
//
//    public String getBrandName() {
//        return brandName;
//    }
//
//    public void setBrandName(String brandName) {
//        this.brandName = brandName;
//    }
//
//    public String getBrandIco() {
//        return brandIco;
//    }
//
//    public void setBrandIco(String brandIco) {
//        this.brandIco = brandIco;
//    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
