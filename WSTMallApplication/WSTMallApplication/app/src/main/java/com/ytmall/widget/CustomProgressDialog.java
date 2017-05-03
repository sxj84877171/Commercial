package com.ytmall.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.ytmall.R;


/**
 * Created by Administrator on 2015/1/21.
 */
public class CustomProgressDialog extends Dialog {
    private Context context = null;
    private static CustomProgressDialog customProgressDialog = null;

    public CustomProgressDialog(Context context){
        super(context);
        this.context = context;
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public static CustomProgressDialog createDialog(Context context){
        customProgressDialog = new CustomProgressDialog(context, R.style.CustomProgressDialog);
        customProgressDialog.setContentView(R.layout.layout_loading);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        customProgressDialog.setCancelable(true);
        return customProgressDialog;
    }

    public void onWindowFocusChanged(boolean hasFocus){

        if (customProgressDialog == null){
            return;
        }

        ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.imgLoading);

        Animation operatingAnim = AnimationUtils.loadAnimation(customProgressDialog.getContext(), R.anim.progress_round);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);

        if (operatingAnim != null) {
            imageView.startAnimation(operatingAnim);
        }
    }

    @Override
    public void dismiss(){
        ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.imgLoading);
        imageView.clearAnimation();
        try{
            super.dismiss();
        }catch (Exception e){
            e.printStackTrace();
        }catch (Error e){
            e.printStackTrace();
        }

    }

    @Override
    public void show() {
        try{
            super.show();
        }catch (WindowManager.BadTokenException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
