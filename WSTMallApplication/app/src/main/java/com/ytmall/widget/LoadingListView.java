package com.ytmall.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ytmall.R;

/**
 * LoadingListView
 * Created by pzl
 * 2016/5/18 10:26
 * 下拉刷新，到底自动加载更多
 */

public class LoadingListView extends LinearLayout {
    private LinearLayout mainView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView lv_message;
    private KateOnRefreshListener kateOnRefreshListener;
    private LinearLayout footerView;
    private boolean isAddFooterView;
    private boolean isLoadingData;
    private boolean isLastPager;
    public LoadingListView(Context context) {
        super(context);
        init(context);
    }

    public LoadingListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        initView();
        setListener();
        initFooter(context);
        initStatus();
        setOnRefreshing();
    }
    private void initView() {
        isAddFooterView=false;
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainView = (LinearLayout) inflater.inflate(R.layout.sw_listview, this);
        lv_message = (ListView) mainView.findViewById(R.id.lv_message);
        swipeRefreshLayout = (SwipeRefreshLayout) mainView
                .findViewById(R.id.swipe_container);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light, android.R.color.holo_red_light,
                android.R.color.holo_red_light, android.R.color.holo_red_light);
    }
    private void initStatus(){
        isLoadingData=false;
        isAddFooterView=false;
        isLastPager=false;
    }
    public ListView getListView() {
        return lv_message;
    }

    private void setListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                kateOnRefreshListener.onReFresh();
            }
        });
    }

    public void setOnRefreshing(){
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    public void setAdapter(ListAdapter baseAdapter) {
        lv_message.addFooterView(footerView, null, false);
        lv_message.setAdapter(baseAdapter);
        lv_message.removeFooterView(footerView);
    }
    public void setOnRefreshFinish() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        removeFooterView();
    }

    public void setOnRefresh(KateOnRefreshListener kateOnRefreshListener) {
        this.kateOnRefreshListener = kateOnRefreshListener;
    }

    public interface KateOnRefreshListener {
        void onReFresh();
        void onLastItemVisible();
    }

    private void initFooter(Context context) {
        footerView = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.listview_footer, null);
        ImageView footerAmi = (ImageView) footerView.findViewById(R.id.loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) footerAmi
                .getBackground();
        animationDrawable.start();
        lv_message.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.e("firstVisibleItem", firstVisibleItem + "");
                Log.e("visibleItemCount", visibleItemCount + "");
                Log.e("totalItemCount", totalItemCount + "");
                if ((firstVisibleItem+visibleItemCount) == totalItemCount&&visibleItemCount!=0&&visibleItemCount!=totalItemCount&&!isLoadingData&&!isLastPager) {
                            kateOnRefreshListener.onLastItemVisible();
                            isLoadingData=true;
                            addFooterView();
                            Log.e("LoadingListView", "到底");
                 }

                if(isLastPager){
                    removeFooterView();
                }
            }
        });
    }
    private void addFooterView(){
        if(!isAddFooterView) {
           // footerView.setVisibility(View.VISIBLE);
            lv_message.addFooterView(footerView,null,false);
            isAddFooterView=true;
        }
    }
    private void removeFooterView(){
        if(isAddFooterView) {
           // footerView.setVisibility(View.GONE);
            lv_message.removeFooterView(footerView);
            isAddFooterView=false;
        }
        isLoadingData=false;
    }
    public void setLastPagerStatus(boolean isLastPager){
        this.isLastPager=isLastPager;
    }
}
