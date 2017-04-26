package com.ytmall.sys;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ytmall.util.YTUtil;
import com.ytmall.widget.CustomProgressDialog;
import com.ytmall.widget.CustomTitle;
import com.ytmall.R;


/**
 * Created by lee on 16/10/26.
 */
public class BaseAct extends Activity {
    private LinearLayout layContainer;
    private FrameLayout frame;
    public CustomTitle titleBar;
    public CustomProgressDialog progress;
    private Context ctxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.layout_base_act);
        init();
    }
    private void init(){
        ctxt = this;
//        layContainer = (LinearLayout) findViewById(R.id.layContainer);
//        frame = (FrameLayout) layContainer.findViewById(R.id.title_frame);
        layContainer = (LinearLayout) findViewById(R.id.layoutContainer);
        frame = (FrameLayout) findViewById(R.id.title_frame);
        creatTitle();

    }
    private void creatTitle(){
        if (frame != null){
            titleBar = new CustomTitle(this,frame);
            titleBar.setLeftButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }


    @Override
    public void setContentView(int layoutResID) {
        layContainer.addView(LayoutInflater.from(getBaseContext()).inflate(layoutResID, layContainer, false));
    }

    @Override
    public void setContentView(View view) {
        layContainer.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        layContainer.addView(view,params);
    }
    /**
     * 默认进度显示
     */
    public void showProgress(){
        if(progress !=null && progress.isShowing()){
            return;
        }else{
            progress = CustomProgressDialog.createDialog(this);
            progress.show();
        }
    }

    /**
     * 关闭默认进度显示
     */
    public void closeProgress(){
        if(progress != null && progress.isShowing()){
            progress.dismiss();
        }
    }
    /**
     * 隐藏输入法
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (YTUtil.isShouldHideInput2(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
}
