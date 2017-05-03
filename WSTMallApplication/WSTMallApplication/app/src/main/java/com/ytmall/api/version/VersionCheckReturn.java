package com.ytmall.api.version;

import com.google.gson.Gson;
import com.ytmall.bean.BaseParam;

/**
 * Created by lee on 2016/12/22.
 */

public class VersionCheckReturn extends BaseParam{
    public int app_version;
    public String download_url;
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
