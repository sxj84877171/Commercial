package com.ytmall.util;

import java.text.DecimalFormat;

/**
 * Created by lihui on 2015/8/27.
 * 工具类：字符串处理工具
 */
public class StrUtil {

    /**
     * null 转 ""
     * @param str
     * @return
     */
    public static String null2Str(String str){
        if(str == null){
            return "";
        }else{
            return str;
        }
    }

    /**
     * String 转 int
     * @param str
     * @return
     */
    public static int str2Int(String str){
        try{
            return Integer.parseInt(str);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }

    /**
     * String 转 int
     * @param str
     * @return
     */
    public static long str2long(String str){
        try{
            return Long.parseLong(str);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }

    /**
     * String 转 float
     * @param str
     * @return
     */
    public static float str2float(String str){
        try{
            return Float.parseFloat(str);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * String 转 double
     * @param str
     * @return
     */
    public static double str2double(String str){
        try{

            return Double.valueOf(str);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
