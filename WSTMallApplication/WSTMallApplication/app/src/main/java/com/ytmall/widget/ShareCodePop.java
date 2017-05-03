package com.ytmall.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.ytmall.R;

/**
 * Created by lee on 2017/1/16.
 */

public class ShareCodePop extends PopupWindow {
    private Context ctxt;
    public ImageView imgCode;
    public ShareCodePop(Context ctxt){
        this.ctxt = ctxt;
        final View view = LayoutInflater.from(ctxt).inflate(R.layout.pop_share_code,null);
        this.setContentView(view);

        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);

        this.setFocusable(true);
        ColorDrawable cd = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(cd);

        final LinearLayout llSharePop = (LinearLayout) view.findViewById(R.id.llSharePop);
        ImageView imgDelete = (ImageView) view.findViewById(R.id.imgDelete);
        imgCode = (ImageView) view.findViewById(R.id.imgCode);

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                int height = llSharePop.getTop();
                int bottom = llSharePop.getBottom();
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

}
