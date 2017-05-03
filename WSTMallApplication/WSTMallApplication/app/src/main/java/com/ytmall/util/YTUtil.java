package com.ytmall.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ytmall.R;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YTUtil {
    /**
     * 根据手机分辨率从dp转化成px
     * @param ctxt
     * @param dpValue
     * @return
     */
    public static int DP2PX(Context ctxt, float dpValue){
        final float scale = ctxt.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5);
    }

    /**
     * 根据名字获取图片
     * @param ctxt
     * @param keyword
     * @return
     */
    public static Drawable getDrawable(Context ctxt, String keyword){
        int id = ctxt.getResources().getIdentifier(keyword,"drawable","com.lingxiang.ytmall");
        return ctxt.getResources().getDrawable(id);

    }
    public final static int NETWORK_NULL = 0;
    public final static int NETWORK_WIFI = 99;
    public final static int NETWORK_2G = 2;
    public final static int NETWORK_3G = 3;
    public final static int NETWORK_UNKNOWN = -1;
    public final static int NETWORK_EXCEPTION = -2;
    /**************************
     * 网络类型
     * @param _ctxt :    上下文
     * */
    public static int NetworkType(Context _ctxt){
        try {
            int type = NETWORK_NULL;
            ConnectivityManager connectMgr = (ConnectivityManager) _ctxt.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectMgr.getActiveNetworkInfo();

            if (info == null) {
                return NETWORK_NULL;   // 无网络
            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return NETWORK_WIFI;   // WIFI
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                // 手机网络
                //Toast.makeText(_ctxt, , Toast.LENGTH_LONG).show();
                if(info.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS){
                    return NETWORK_2G;  // 2G移动
                }
                else if(info.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE){
                    return NETWORK_2G;  // 2G联通
                }
                else if(info.getSubtype() == TelephonyManager.NETWORK_TYPE_CDMA){
                    return NETWORK_2G;  // 2G电信
                } else {
                    return NETWORK_3G;   // 3G/4G
                }
            }
            return NETWORK_UNKNOWN;
        }
        catch (Exception ex){
//            Toast.makeText(_ctxt, ex.getMessage(), Toast.LENGTH_LONG).show();
            return NETWORK_EXCEPTION;
        }
    }
    /**************************
     * 自定义TOAST
     * @param _msg :        消息内容
     * @param  :   显示时长
     * */
    public static void _TOAST(Context _ctxt, String _msg){

        if(_ctxt == null) return;
        try{
            LayoutInflater inflater = (LayoutInflater) _ctxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View toastRoot = inflater.inflate(R.layout.toast, null);

            Toast toast = new Toast(_ctxt.getApplicationContext());
            toast.setGravity(Gravity.CENTER,0,0);
            toast.setView(toastRoot);
            TextView tv = (TextView)toastRoot.findViewById(R.id.txtToast);
            tv.setText(_msg);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /***********************
     * 正则匹配
     * @param _str :       待匹配字符串
     * @param _regexp :    表达式
     * */
    public static boolean MatchRegExp(String _str, String _regexp)
    {
        Pattern pattern = Pattern.compile(_regexp);
        Matcher matcher = pattern.matcher(_str);
        boolean matched = matcher.matches();
        return matched;
    }
    /**
     * 输入法隐藏,清除焦点
     * @param v
     * @param event
     * @return
     */
    public static boolean isShouldHideInput2(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                //不是输入框，清除焦点
                v.clearFocus();
                return true;
            }
        }
        return false;
    }
    /**************************
     * 隐藏输入法
     * @param _ctxt :       上下文
     * @param _views :      触发隐藏的控件
     * */
    public static void HideInputWhenTouchNoneEdit(final Context _ctxt, final ArrayList<View> _views){
        Iterator<View> it = _views.iterator();
        for(int i=0;i<_views.size();i++) {
            _views.get(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    InputMethodManager inputMethodManager = (InputMethodManager) _ctxt.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    view.requestFocus();
                    view.isFocusableInTouchMode();

                    return false;
                }
            });
        }
    }
    public static String BitmapToBase64ForAvatar(String _imgPath){
        InputStream is;
        String str = "";
        try {
            is = new FileInputStream(_imgPath);
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inTempStorage = new byte[100 * 1024];
            opts.inPreferredConfig = Bitmap.Config.RGB_565;
            opts.inPurgeable = true;

            //opts.inSampleSize = 4;
            opts.inInputShareable = true;
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, opts);

            // 如果图片宽度大于300px，缩小图片解析度
            if(bitmap.getWidth() > 300) {
                Matrix matrix = new Matrix();
                matrix.postScale(300f / (float) bitmap.getWidth(), 300f / (float) bitmap.getHeight()); //长和宽放大缩小的比例
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            }

            // 压缩图片质量
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bStream);

            byte[] bytes = bStream.toByteArray();
            str = Base64.encodeToString(bytes, Base64.DEFAULT);
            is.close();
            bitmap = null;

        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }

    public static String DateDesc(String date){
        Date now = YTUtil.Now();
        String dateStr = "";
        long timeLong = now.getTime() - Long.parseLong(date);
        if (timeLong<60*1000)
            dateStr = "刚刚";
        else if (timeLong<60*60*1000){
            timeLong = timeLong/1000 /60;
            dateStr = timeLong + "分钟前";
        }
        else if (timeLong<60*60*24*1000){
            timeLong = timeLong/60/60/1000;
            dateStr = timeLong+"小时前";
        }
        else if (timeLong<60*60*24*1000*7){
            timeLong = timeLong/1000/ 60 / 60 / 24;
            dateStr = timeLong + "天前";
        }
        else if (timeLong<60*60*24*1000*7*4){
            timeLong = timeLong/1000/ 60 / 60 / 24/7;
            dateStr = timeLong + "周前";
        }

        return dateStr;
    }
    public static Date Now(){
        return new Date();
    }

    public static Date Today(){
        Date now = new Date();
        String nowString = ConvertToDateTimeString("yyyy-MM-dd", now);
        now = ConvertToDateTime("yyyy-MM-dd", nowString);
        return now;
    }

    public static Boolean IsToday(Date date){
        Date now = new Date();
        String nowString = ConvertToDateTimeString("yyyy-MM-dd", now);
        String dateString = ConvertToDateTimeString("yyyy-MM-dd", date);
        return nowString.equals(dateString);
    }
    /***********************************
     * 日期对象转换成字符串
     * @param format :       日期时间格式：yyyy-MM-dd HH:mm:ss
     * @param dateTimeObject :   日期时间对象
     * */
    public static String ConvertToDateTimeString(String format, Date dateTimeObject){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date;
        try {
            date = sdf.format(dateTimeObject);
        }
        catch (Exception ex){
            date = "";
        }
        return date;
    }

    /***********************************
     * 字符串转换成日期对象
     * @param format :       日期时间格式：yyyy-MM-dd HH:mm:ss
     * @param dateTimeString :   日期时间字符串
     * */
    public static Date ConvertToDateTime(String format, String dateTimeString){
        if(dateTimeString.length() == 0){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date;
        try {
            date = sdf.parse(dateTimeString);
        }
        catch (Exception ex){
            date = null;
        }
        return date;
    }
    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidthPixels(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }
    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);//将当前窗口的信息放在DisplayMetrics类中
        return outMetrics.widthPixels;
    }

    /**
     * 判断输入法表情
     * @param codePoint
     * @return
     */
    public static boolean getIsEmoji(char codePoint) {
        return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }
    //生成随机数字和字母,
    public static String getStringRandom(int length) {

        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
    //初始化圆点指示器
//    public static void initIndicators(Context act,LinearLayout slidingDot,ImageView[] points){
//        ImageView dot;
//        if (slidingDot.getChildCount() > 0){
//            slidingDot.removeAllViews();
//        }
//        for (int i = 0;i < points.length;i ++){
//            dot = new ImageView(act);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,20);
//            params.setMargins(7, 7, 7, 7);
//            dot.setLayoutParams(params);
//            points[i] = dot;
//
//            if (i == 0){
//                points[i].setBackgroundResource(R.drawable.icon_hover);
//            }else {
//                points[i].setBackgroundResource(R.drawable.icon_normal);
//            }
//            slidingDot.addView(points[i]);
//        }
//    }
    //是否是手机号
    public static boolean isPhone (EditText view){
        return view.getText().length() > 0
                && YTUtil.MatchRegExp(view.getText().toString(), "^1[3|4|5|7|8][0-9]{9}$");
    }
    /**
     * 判断SDCard是否存在,并可写
     *
     * @return
     */
    public static boolean checkSDCard() {
        String  flag = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(flag);
    }

}
