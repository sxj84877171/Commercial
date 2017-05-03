package com.ytmall.fragment.shop;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ytmall.R;
import com.ytmall.activity.imagecrop.CropActivity;
import com.ytmall.activity.shop.ShopManageActivity;
import com.ytmall.activity.user.MineActivity;
import com.ytmall.api.UploadPic;
import com.ytmall.application.Const;
import com.ytmall.bean.AbstractParam;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FileUtil;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.widget.BottomPopWindow;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

/**
 * Created by lee on 16/12/8.
 */
@FragmentView(id = R.layout.fragment_shop_manage)
public class ShopManageFragemnt extends BaseFragment implements View.OnClickListener, BottomPopWindow.OnMenuSelect{
    @InjectView(id = R.id.llIcon)
    LinearLayout llIcon;
    @InjectView(id = R.id.imgShop)
    ImageView imgShop;
    @InjectView(id = R.id.llNick)
    LinearLayout llNick;



    @Override
    protected void requestSuccess(String url, String data) {
        super.requestSuccess(url, data);
    }

    @Override
    protected void flagFailed(String url) {
        super.flagFailed(url);
    }

    @Override
    public void bindDataForUIElement() {
        tWidget.setCenterViewText("店铺管理");
        llIcon.setOnClickListener(this);
        llNick.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llIcon:
                showButtomPop(this, new String[] { "拍照", "我的相册" });
                break;
            case R.id.llNick:
                replaceFragment(new ChangeShopNameFragment(),true);
                break;
        }
    }
    public void refreshShopImage(){
        loadOnRoundImage(Const.BASE_URL + Const.user.userPhoto, imgShop);
    }

    @Override
    public void onCancelSelect() {
        // TODO Auto-generated method stub
        buttomPop.dismiss();
    }

    @Override
    public void onItemMenuSelect(int position) {
        // TODO Auto-generated method stub
        switch (position) {
            case 0:
//                ((MineActivity)getActivity()).startCamera();
                ((ShopManageActivity)getActivity()).startCamera();
                break;
            case 1:
//                ((MineActivity)getActivity()).startSelectPhoto();
                ((ShopManageActivity)getActivity()).startSelectPhoto();

                break;
        }
        buttomPop.dismiss();
    }



        @Override
    protected void bindEvent() {

    }
}
