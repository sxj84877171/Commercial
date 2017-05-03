package com.ytmall.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * Created by lee on 16/10/27.
 */
public class CustomPop extends PopupWindow {
    private Context ctxt;
    public View root;

    public CustomPop(Context ctxt, int layoutId, final View background, int style){
        this.ctxt = ctxt;
        this.setFocusable(true);
        root = LayoutInflater.from(ctxt).inflate(layoutId,null);
        this.setContentView(root);
        if (style == 0){
            setMatchParent();
        }else if (style == 1){
            setMatchWidth();
        }else {
            setMatchHeight();
        }
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);


        root.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                int height = background.getTop();
                int bottom = background.getBottom();
                int y=(int) event.getY();
                if(event.getAction()== MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();

                    }
                }
                if(event.getAction()== MotionEvent.ACTION_UP){
                    if(y>bottom){
                        dismiss();

                    }
                }
                return true;
            }
        });


    }
    public void setMatchParent(){
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);

    }
    public void setMatchWidth(){
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
    }
    public void setMatchHeight(){
        this.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
    }
}

