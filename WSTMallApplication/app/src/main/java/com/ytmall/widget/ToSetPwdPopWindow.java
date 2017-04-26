package com.ytmall.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ytmall.R;
import com.ytmall.activity.pwd.ChangePayPwdActivity;

/**
 * Created by lee on 2017/1/13.
 */

public class ToSetPwdPopWindow extends PopupWindow {
    private Context ctxt;
    public ToSetPwdPopWindow(final Context ctxt){
        this.ctxt = ctxt;
        final View view = LayoutInflater.from(ctxt).inflate(R.layout.pop_to_set_paypwd,null);
        this.setContentView(view);

        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);

        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);

        ImageView imgExit = (ImageView) view.findViewById(R.id.imgSetPwdExit);
        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        TextView txtToSet = (TextView) view.findViewById(R.id.btnToSet);
        txtToSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctxt, ChangePayPwdActivity.class);
                i.putExtra("from","TakeMoneyFragment");
                ctxt.startActivity(i);
                dismiss();
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.popSetPwd).getTop();
                int bottom = view.findViewById(R.id.popSetPwd).getBottom();
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
