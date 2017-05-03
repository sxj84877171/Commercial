package com.ytmall.bean;

import android.location.Address;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lee on 2017/2/7.
 */

public class GetCashAccInfoBean implements Serializable {
    public String id;
    public String accTargetId;
    public String accNo;
    public String accUser;
    public String areaId2;
    public String bank_name;
    public String branch_bank;
    public String city_name;
    public String provincia_name;
    public List<AreaBean> areas;
}
