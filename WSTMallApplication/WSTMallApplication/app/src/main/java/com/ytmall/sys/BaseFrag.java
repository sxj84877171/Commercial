package com.ytmall.sys;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ytmall.widget.CustomProgressDialog;


public class BaseFrag extends Fragment {

    //传递过来的参数Bundle，供子类使用
    protected  Bundle args;
    public static CustomProgressDialog progress;

    /**
     * 创建fragment的静态方法，方便传递参数
     * @param args 传递的参数
     * @return
     */
    public static <T extends Fragment>T newInstance(Class clazz, Bundle args) {
        T mFragment=null;
        try {
            mFragment= (T) clazz.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mFragment.setArguments(args);
        return mFragment;
    }

    /**
     * 初始创建Fragment对象时调用
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments();
    }


    /**
     * 默认进度显示
     */
    public void showProgress(){
        if(progress !=null && progress.isShowing()){
            return;
        }else{
            progress = CustomProgressDialog.createDialog(getActivity());
            progress.show();
        }
    }

    /**
     * 关闭默认进度显示
     */
    public void closeProgress(){
        if(progress != null && progress.isShowing()){
            progress.dismiss();
        }
    }



}
