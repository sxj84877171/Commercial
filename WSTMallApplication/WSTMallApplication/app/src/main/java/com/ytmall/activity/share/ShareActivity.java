package com.ytmall.activity.share;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jakewharton.rxbinding.view.RxView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;
import com.ytmall.R;
import com.ytmall.api.share.QueryDistributMoneys;
import com.ytmall.api.share.QueryRecommdCount;
import com.ytmall.application.Const;
import com.ytmall.application.WSTMallApplication;
import com.ytmall.bean.YuEPayReturn;
import com.ytmall.internet.APICallBack;
import com.ytmall.internet.LocalAPI;
import com.ytmall.sys.BaseAct;
import com.ytmall.util.StrUtil;
import com.ytmall.widget.ShareCodePop;

import java.util.Hashtable;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by lee on 2017/1/13.
 */

public class ShareActivity extends BaseAct implements View.OnClickListener{
    private ImageView imgUser;
    private TextView txtName,txtFriend,txtCodeShare,txtShare,
            txtAllUser,txtAllMoney;
    private LinearLayout llInfo,llFriend,llShare;
    private ImageLoader imageLoader;
    private int QR_WIDTH = 450;
    private int QR_HEIGHT = 450;
    private ShareCodePop codePop;
    private String url = Const.BASE_URL+"index.php?m=WeChat&c=Index&a=index&shareUserId=";
    private UMImage image;
    private Activity act;
    private String distributMoney = "0";
    private String TAG = "ShareActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        act = this;
        String userId = String.valueOf(Base64.encode(Const.user.userId.getBytes(),Base64.DEFAULT));
        url = url+userId;
        imageLoader = ImageLoader.getInstance();
        initUI();

    }
    private void loadOnRoundImage(String uri, ImageView imageView) {
        imageLoader.displayImage(uri, imageView, ((WSTMallApplication) getApplication()).imageRoundOptions);
    }
    private void initUI() {
        image = new UMImage(this,R.drawable.ytlogo);
        titleBar.setTxtTitle("分享赚钱");
        imgUser = (ImageView) findViewById(R.id.imgUser);
        txtName = (TextView) findViewById(R.id.txtName);
        txtFriend = (TextView) findViewById(R.id.txtFriend);
        txtCodeShare = (TextView) findViewById(R.id.txtCodeShare);
        txtShare = (TextView) findViewById(R.id.txtShare);
        txtAllUser = (TextView) findViewById(R.id.txtAllUser);
        txtAllMoney = (TextView) findViewById(R.id.txtAllMoney);
        llInfo = (LinearLayout) findViewById(R.id.llInfo);
        llFriend = (LinearLayout) findViewById(R.id.llFriend);
        llShare = (LinearLayout) findViewById(R.id.llShare);

        txtCodeShare.setOnClickListener(this);
        txtShare.setOnClickListener(this);

        loadOnRoundImage(Const.user.userPhoto,imgUser);
        txtName.setText(Const.user.userName);
        if (StrUtil.null2Str(Const.user.recommend_user).length() > 0){
            txtFriend.setText("我的好友："+Const.user.recommend_user);
        }else {
            txtFriend.setVisibility(View.GONE);
        }


        llFriend.setOnClickListener(this);
        llInfo.setOnClickListener(this);

        getUserNumber();




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtCodeShare:
                //二维码分享
                codePop = new ShareCodePop(this);
                codePop.showAtLocation(llShare, Gravity.CENTER,0,0);
                createQRImage(url);
                break;
            case R.id.txtShare:
                new ShareAction(act).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener)
                        .withTitle("云堂商城")
//                        .withText(url).withTargetUrl(url)
                        .withText(url).withMedia(image).withTargetUrl(url)
                        .share();
                break;
            case R.id.llFriend:
                Intent iFriend = new Intent(act,FriendListActivity.class);
                startActivity(iFriend);
                break;
            case R.id.llInfo:
                Intent iInfo = new Intent(act,RecommendListActivity.class);
                iInfo.putExtra("money",distributMoney);
                startActivity(iInfo);
                break;
        }
    }
    private void getUserNumber(){
        QueryRecommdCount userNumber = new QueryRecommdCount();
        userNumber.tokenId = Const.cache.getTokenId();
        LocalAPI.queryRecommdCount(userNumber, new APICallBack<YuEPayReturn>() {
            @Override
            public void success(YuEPayReturn result) {
                if (result.getStatus() == 1){
                    txtAllUser.setText(result.getData());
                    getMoney();
                }

            }

            @Override
            public void error(String msg) {
                super.error(msg);
                Toast.makeText(act,msg,Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void getMoney(){
        QueryDistributMoneys moneys = new QueryDistributMoneys();
        moneys.tokenId = Const.cache.getTokenId();
        LocalAPI.queryDistributMoneys(moneys, new APICallBack<YuEPayReturn>() {
            @Override
            public void success(YuEPayReturn result) {
                if (result.getStatus() == 1){
                    txtAllMoney.setText(result.getData());
                    distributMoney = result.getData();
                }

            }
        });

    }
    //要转换的地址或字符串,可以是中文
    private void createQRImage(String url)
    {
        try
        {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++)
            {
                for (int x = 0; x < QR_WIDTH; x++)
                {
                    if (bitMatrix.get(x, y))
                    {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    }
                    else
                    {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            //显示到一个ImageView上面
            if (codePop != null){
                codePop.imgCode.setImageBitmap(bitmap);
            }

        }
        catch (WriterException e)
        {
            e.printStackTrace();
        }
    }
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(act, " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(act, " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(act, " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Log.d("result","onActivityResult");
    }
}
