package com.ytmall.activity.shop;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.activity.imagecrop.CropActivity;
import com.ytmall.api.UploadPic;
import com.ytmall.api.user.EditUserPhoto;
import com.ytmall.application.Const;
import com.ytmall.fragment.shop.ShopManageFragemnt;
import com.ytmall.fragment.user.EditUserInfoFragment;
import com.ytmall.util.FileUtil;
import com.ytmall.util.InjectView;
import com.ytmall.widget.BottomPopWindow;
import com.ytmall.widget.TitleWidget;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

/**
 * Created by lee on 16/12/8.
 */
public class ShopManageActivity extends BaseActivity implements View.OnClickListener,
        BottomPopWindow.OnMenuSelect,TitleWidget.IOnClick{
    TitleWidget titleBar;
//    private LinearLayout titleBar;
    LinearLayout llIcon;
    ImageView imgShop;
    LinearLayout llNick;
    private Uri selectedImageUri = null;
    private final int CAMERA_PHOTO = 23;
    private final int CHOOSE_ALBUM = 24;
    private final int PHOTO_CROP = 25;

    private EditUserPhoto editUserPhoto=new EditUserPhoto();
    public EditUserInfoFragment editUserInfoFragment;

    private String userPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        replaceFragment(new ShopManageFragemnt(),false);
        setContentView(R.layout.fragment_shop_manage);
        initUI();
    }
    private void initUI(){
        titleBar = (TitleWidget) findViewById(R.id.titleBar);
        titleBar.setCenterViewText("店铺管理");
        if (titleBar != null) {
            titleBar.setTitleListener(this);
        }
//        titleBar = (LinearLayout) findViewById(R.id.titleBar);
//        RelativeLayout left = (RelativeLayout) findViewById(R.id.left);
//        left.setOnClickListener(this);
//        TextView center_view = (TextView) findViewById(R.id.center_view);
//        center_view.setText("店铺管理");
        LinearLayout llIcon = (LinearLayout) findViewById(R.id.llIcon);
        llIcon.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.left:
//                finish();
//                break;
            case R.id.llIcon:
                showButtomPop(this, new String[] { "拍照", "我的相册" });
                break;
        }
    }

    @Override
    public void leftClick() {
        finish();

    }

    @Override
    public void rightClick() {

    }

    @Override
    public void centerClick() {

    }

    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(uploadPic.getA())) {
            try {
                JSONObject jsonobj = new JSONObject(data);
                jsonobj=jsonobj.getJSONObject("Filedata");
                userPhoto=jsonobj.getString("savepath")+jsonobj.getString("savename");
                editUserPhoto.tokenId= Const.cache.getTokenId();
                editUserPhoto.userPhoto=userPhoto;
                request(editUserPhoto);
            } catch (JSONException e) {
            }
        }else if (url.contains(editUserPhoto.getA())) {
            Const.user.userPhoto=userPhoto;
            editUserInfoFragment.refreshHeadImage();
        }
    }
    public void startCamera() {// 用户点击拍照
        String FILE_NAME = UUID.randomUUID() + ".jpg";
        File photo = FileUtil.getNewFile(this, FILE_NAME);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (photo != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
            selectedImageUri = Uri.fromFile(photo);
            startActivityForResult(intent, CAMERA_PHOTO);
        }
    }
    public void startSelectPhoto() {// 用户点击 从相册获取
        Intent mIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(mIntent, CHOOSE_ALBUM);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // 拍照前检查SDCard是否存在
            if (!isSDAvailable()) {
                return;
            }
            switch (requestCode) {
                case CAMERA_PHOTO:// 拍照后
                    startPhotoCrop(selectedImageUri);
                    break;
                case PHOTO_CROP:// 照片完成裁剪后
                    readCropImage(data);
                    break;
                case CHOOSE_ALBUM:// 选择相册
                    if (data != null) {
                        Uri originalUri = data.getData();
                        startPhotoCrop(originalUri);
                    }
                    break;
            }
        }
    }

    /**
     * 检查SD卡是否可用
     */
    private boolean isSDAvailable() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    private UploadPic uploadPic = new UploadPic();
    /**
     * 读取裁剪好的图片
     */
    private void readCropImage(Intent data) {

        if (data != null) {
            Uri uri = data.getParcelableExtra(CropActivity.CROP_IMAGE_URI);
//            uploadPic.Filedata=new File(getFilePath(uri));
//            request(uploadPic);
            Toast.makeText(this,uri.toString(),Toast.LENGTH_SHORT).show();
            Log.d("pic:",uri.toString());
        }
    }
    /**
     * 根据Uri返回文件路径
     */
    private String getFilePath(Uri mUri) {
        try {
            if (mUri.getScheme().equals("file")) {
                return mUri.getPath();
            } else {
                return getFilePathByUri(mUri);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    // 获取的照片，进行裁剪，无论拍照还是相册选择
    private void startPhotoCrop(Uri uri) {
        Intent intent = new Intent(this, CropActivity.class);
        intent.putExtra(CropActivity.IMAGE_URI, uri);
        startActivity(intent);
    }


    private String getFilePathByUri(Uri mUri) throws FileNotFoundException {
        String imgPath;
        Cursor cursor = getContentResolver().query(mUri, null, null, null, null);
        cursor.moveToFirst();
        imgPath = cursor.getString(1); // 图片文件路径
        return imgPath;
    }
    private void showButtomPop(BottomPopWindow.OnMenuSelect onMenuSelect, String[] menus) {
        BottomPopWindow popLayout = new BottomPopWindow(this, onMenuSelect);
        popLayout.setMenu(menus);
        popLayout.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        buttomPop = new PopupWindow(popLayout, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        buttomPop.setFocusable(true);
        buttomPop.setBackgroundDrawable(new BitmapDrawable());
        buttomPop.setOutsideTouchable(true);
        buttomPop.update();
        buttomPop.showAsDropDown(titleBar);
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
                startCamera();
                break;
            case 1:
                startSelectPhoto();

                break;
        }
        buttomPop.dismiss();
    }
}
