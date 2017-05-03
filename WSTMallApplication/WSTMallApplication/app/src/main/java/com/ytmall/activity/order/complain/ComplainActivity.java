package com.ytmall.activity.order.complain;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.ytmall.activity.BaseActivity;
import com.ytmall.fragment.order.ComplainFragment;
import com.ytmall.util.slectphotos.Bimp;
import com.ytmall.util.slectphotos.FileUtils;
import com.ytmall.util.slectphotos.ImageItem;
import com.ytmall.util.slectphotos.PublicWay;

/**
 * Created by Administrator on 2016/4/7.
 */
public class ComplainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new ComplainFragment(getIntent().getIntExtra("orderId", 0)), false);
        Log.e("ComplainActivity", "=============onCreate");
        clearComplain();
    }

    /**清楚缓存投诉图片*/
    private void clearComplain(){
        Bimp.tempSelectBitmap.clear();
        Bimp.max=0;
    }
}
