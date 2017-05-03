package com.ytmall.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 16/7/9.
 */
public class OrderPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private ArrayList<Fragment> fragments;
    private FragmentManager fm;
    public OrderPagerAdapter(FragmentManager fm, List<Fragment> list){
        super(fm);
        this.list = list;


    }
    public void setFragments(ArrayList<Fragment> fragments) {
        if(this.fragments != null){
            FragmentTransaction ft = fm.beginTransaction();
            for(Fragment f:this.fragments){
                ft.remove(f);
            }
            ft.commit();
            ft=null;
            fm.executePendingTransactions();
        }
        this.fragments = fragments;
        notifyDataSetChanged();
    }


    @Override
    public Fragment getItem(int position) {

        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
