package com.ytmall.fragment.code;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.umeng.socialize.net.utils.BaseNCodec;
import com.ytmall.R;
import com.ytmall.application.Const;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.util.StrUtil;

import java.util.Hashtable;

/**
 * Created by lee on 2017/1/7.
 */
@FragmentView(id = R.layout.fragment_get_money_code)
public class GetMoneyCodeFragment extends BaseFragment {
    @InjectView(id = R.id.imgCode)
    ImageView imgCode;
    private int QR_WIDTH = 180;
    private int QR_HEIGHT = 180;
    private String url = Const.BASE_URL+"index.php?m=WeChat&c=Qrcode&a=topay"+"&shopId=";

    @Override
    public void bindDataForUIElement() {

        tWidget.setCenterViewText("收款二维码");
        String shopId = new String(Base64.encode(
                String.valueOf(Const.user.shopId).getBytes(), Base64.DEFAULT));
        url = url+shopId;
        createQRImage(url);

        Log.d("json code url:",url);


    }

    @Override
    protected void bindEvent() {

    }

    //要转换的地址或字符串,可以是中文
    public void createQRImage(String url)
    {
        try
        {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1)
            {
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
            imgCode.setImageBitmap(bitmap);
        }
        catch (WriterException e)
        {
            e.printStackTrace();
        }
    }


}
