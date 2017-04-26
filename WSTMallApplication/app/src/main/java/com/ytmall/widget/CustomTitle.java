package com.ytmall.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ytmall.R;


/**
 * Created by lee on 16/10/26.
 */
public class CustomTitle extends RelativeLayout {
    private Context ctxt;
    public TextView txtTitle,txtRight;
    public ImageView left;
    private View containerView;
    private LinearLayout mainLayout;
    public CustomTitle(Context context, FrameLayout view) {
        super(context);
        ctxt = context;
        containerView = LayoutInflater.from(ctxt).inflate(R.layout.common_title_bar,null);
        txtTitle = (TextView) containerView.findViewById(R.id.txtTitle);
        txtRight = (TextView) containerView.findViewById(R.id.txtRight);
        left = (ImageButton) containerView.findViewById(R.id.imgBack);
        mainLayout = (LinearLayout) containerView.findViewById(R.id.mainLayout);

//        mainLayout.setBackgroundColor(getResources().getColor(R.color.title_bg));
//        txtTitle.setBackgroundColor(getResources().getColor(R.color.white));

        view.addView(containerView);


    }
    public TextView setTxtTitle(String title){
        if (txtTitle != null){
            txtTitle.setText(title);
        }
        return txtTitle;
    }
    public ImageView setLeftButton(){
        return left;
    }
    public TextView setRight(String rightText){
        if (txtRight != null){
            txtRight.setVisibility(VISIBLE);
            txtRight.setText(rightText);
        }
        return txtRight;

    }

}
