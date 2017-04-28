package com.ytmall.activity;

import android.app.Notification;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

/**
 * WSTMallApplication
 * 作者： Elvis
 * 时间： 2017/4/28
 * 版本：1.0.0.0
 * 邮编：410000
 * 程序功能：
 * 该类为
 */

public class CountDownTimerButton extends Button implements Runnable{

    private OnClickListener onClickListener;
    private int duration = 60;//倒计时时长 设置默认10秒
    private String text ;

    public CountDownTimerButton(Context context) {
        this(context,null);
    }

    public CountDownTimerButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CountDownTimerButton(Context context,AttributeSet attrs,int style){
        super(context,attrs,style);

    }
    private Handler mHandler = new Handler();

    @Override
    public void setOnClickListener(final OnClickListener onClickListener) {
        this.onClickListener = onClickListener;

        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                text = getText().toString();
                mHandler.post(CountDownTimerButton.this);
                if(onClickListener != null){
                    onClickListener.onClick(v);
                }
            }
        });
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        if(duration > 0){
            setText(duration + "秒");
            duration--;
            mHandler.postDelayed(this,1000);
            setEnabled(false);
            setClickable(false);
        }else{
            duration = 60;
            setText(text);
            setEnabled(true);
            setClickable(true);
//            setBackgroundColor(Color.GRAY);
        }
    }

    public void stop(){
        mHandler.removeCallbacks(this);
        duration = 60;
        setText(text);
        setEnabled(true);
        setClickable(true);
    }
}
