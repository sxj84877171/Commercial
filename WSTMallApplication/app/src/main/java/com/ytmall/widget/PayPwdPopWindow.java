package com.ytmall.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jungly.gridpasswordview.GridPasswordView;
import com.ytmall.R;
import com.ytmall.activity.pwd.ChangePayPwdActivity;

/**
 * Created by lee on 2017/1/13.
 */

public class PayPwdPopWindow extends PopupWindow {
    private Context ctxt;
    public GridPasswordView gridPwd;
    public Button btnSure;
    public PayPwdPopWindow(final Context ctxt){
        this.ctxt = ctxt;
        final View view = LayoutInflater.from(ctxt).inflate(R.layout.pop_pay_pwd,null);
        this.setContentView(view);

        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);

        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);

        ImageView imgPayExit = (ImageView) view.findViewById(R.id.imgPayExit);
        imgPayExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        gridPwd = (GridPasswordView) view.findViewById(R.id.txtPayPwd);
        btnSure = (Button) view.findViewById(R.id.btnPopConfirm);


        view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.llPopBg).getTop();
                int bottom = view.findViewById(R.id.llPopBg).getBottom();
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
