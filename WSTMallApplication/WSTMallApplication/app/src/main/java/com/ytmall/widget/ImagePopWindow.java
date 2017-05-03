package com.ytmall.widget;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.ytmall.R;
import com.ytmall.application.Const;
import com.ytmall.bean.GoodsListBean;
import com.ytmall.fragment.order.GetOrderComplainDetailFragment;

/**
 * Created by Administrator on 2016/7/17.
 */
public class ImagePopWindow extends PopupWindow {

    public ImagePopWindow(Context context,String imageUrl,GetOrderComplainDetailFragment fragment){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myView = inflater.inflate(R.layout.popwindow_image, null);
        ImageView iv_good_image= (ImageView) myView.findViewById(R.id.iv_good_image);
        iv_good_image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        fragment.loadOnImage(
                Const.BASE_URL + imageUrl,
                iv_good_image);
        this.setContentView(myView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setAnimationStyle(R.style.image_popwindow_anim_style);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
        iv_good_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
