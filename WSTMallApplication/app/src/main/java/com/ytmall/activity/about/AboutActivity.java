package com.ytmall.activity.about;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.ytmall.R;
import com.ytmall.sys.BaseAct;


/**
 * Created by lee on 2017/1/18.
 */

public class AboutActivity extends BaseAct {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_yt);
        titleBar.setTxtTitle("关于云堂");
        TextView txtVersionName = (TextView) findViewById(R.id.txtVersion);
        txtVersionName.setText("云堂V"+getVersionCode(this));
    }
    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    private String getVersionCode(Context context)
    {
        String versionName = "" ;
        try
        {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionName = context.getPackageManager().getPackageInfo("com.ytmall", 0).versionName;
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return versionName;
    }
}
