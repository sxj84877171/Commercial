package com.ytmall.fragment.shop;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ytmall.R;
import com.ytmall.activity.shop.ShopOrderDetailActivity;
import com.ytmall.adapter.ShopOrderAdapter;
import com.ytmall.api.shoporder.QueryShopOrders;
import com.ytmall.application.Const;
import com.ytmall.bean.AbstractParam;
import com.ytmall.bean.GoodsListBean;
import com.ytmall.bean.OrderBean;
import com.ytmall.bean.ShopOrder;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by lee on 16/12/16.
 */
@FragmentView(id = R.layout.activity_shop_order)
public class ShopOrderManageFragment extends BaseFragment implements View.OnClickListener{
    @InjectView(id = R.id.shop_order)
    PullToRefreshListView shop_order;
    @InjectView(id = R.id.ll_order_undo)
    LinearLayout ll_order_undo;
    @InjectView(id = R.id.ll_order_send)
    LinearLayout ll_order_send;
    @InjectView(id = R.id.ll_order_cancel)
    LinearLayout ll_order_cancel;
    @InjectView(id = R.id.tv_order_undo)
    TextView tv_order_undo;
    @InjectView(id = R.id.iv_cursor_first)
    TextView iv_cursor_first;
    @InjectView(id = R.id.tv_order_send)
    TextView tv_order_send;
    @InjectView(id = R.id.iv_cursor_second)
    TextView iv_cursor_second;
    @InjectView(id = R.id.tv_order_cancel)
    TextView tv_order_cancel;
    @InjectView(id = R.id.iv_cursor_third)
    TextView iv_cursor_third;
    @InjectView(id = R.id.tb_order_status)
    TabLayout tb_order_status;
//    @InjectView(id = R.id.vpShopOrder)
//    ViewPagerFixed vpShopOrder;
    //已完成
    @InjectView(id = R.id.ll_order_way_done)
    LinearLayout ll_order_way_done;
    @InjectView(id = R.id.tv_order_way_done)
    TextView tv_order_way_done;
    @InjectView(id = R.id.iv_cursor_four)
    TextView iv_cursor_four;

    private QueryShopOrders shopOrders = new QueryShopOrders();
    private ListView listOrder;
    private int page = 1;

    private static int MENU_ITEM = 0;
    public static ShopOrderAdapter adapter ;
    private static List<ShopOrder> list = new ArrayList<>();

    public ShopOrderManageFragment() {
    }

    public ShopOrderManageFragment(int MENU_ITEM) {
        this.MENU_ITEM = MENU_ITEM;
    }

    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(shopOrders.getA())){
            Log.d("order:",data);
            try {
                JSONObject jsonObject = new JSONObject(data);
                JSONArray js = jsonObject.getJSONArray("data");
                Type listToken = new TypeToken<ArrayList<ShopOrder>>(){}.getType();
                List<ShopOrder> order = new Gson().fromJson(js.toString(),listToken);


                list.addAll(order);
                adapter.notifyDataSetChanged();
                shop_order.onRefreshComplete();

            }catch (Exception ex){


            }

        }else {

        }
    }

    @Override
    protected void flagFailed(String url) {
        super.flagFailed(url);
        shop_order.onRefreshComplete();

    }

    @Override
    public void bindDataForUIElement() {
        tWidget.setCenterViewText("店铺订单管理");
        //取消
        ll_order_cancel.setOnClickListener(this);
        //配送中
        ll_order_send.setOnClickListener(this);
        //未受理
        ll_order_undo.setOnClickListener(this);
        //已完成
        ll_order_way_done.setOnClickListener(this);
        listOrder = shop_order.getRefreshableView();

        listOrder.setDividerHeight(10);
        adapter = new ShopOrderAdapter(getActivity(),list);
        listOrder.setAdapter(adapter);
        shop_order.setMode(PullToRefreshBase.Mode.BOTH);
        shop_order.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                requesttype(MENU_ITEM);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                requesttype(MENU_ITEM);
            }
        });

        requesttype(MENU_ITEM);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_order_undo:
                MENU_ITEM = 0;
                page = 1;
                resetCursor();
                iv_cursor_first.setBackgroundResource(R.color.orange);
                tv_order_undo.setTextColor(Color.parseColor("#F6772A"));
                requesttype(0);
                break;
            case R.id.ll_order_send:
                MENU_ITEM = 3;
                resetCursor();
                page = 1;
                iv_cursor_second.setBackgroundResource(R.color.orange);
                tv_order_send.setTextColor(Color.parseColor("#F6772A"));
                requesttype(3);
                break;
            case R.id.ll_order_cancel:
                MENU_ITEM = 5;
                resetCursor();
                page = 1;
                iv_cursor_third.setBackgroundResource(R.color.orange);
                tv_order_cancel.setTextColor(Color.parseColor("#F6772A"));
                requesttype(5);
                break;
            case R.id.ll_order_way_done:
                MENU_ITEM = 4;
                resetCursor();
                page = 1;
                iv_cursor_four.setBackgroundResource(R.color.orange);
                tv_order_way_done.setTextColor(Color.parseColor("#F6772A"));
                requesttype(4);
                break;
        }
    }
    private void resetCursor() {
        iv_cursor_first.setBackgroundResource(R.color.white);
        iv_cursor_second.setBackgroundResource(R.color.white);
        iv_cursor_third.setBackgroundResource(R.color.white);
        iv_cursor_four.setBackgroundResource(R.color.white);

        tv_order_undo.setTextColor(Color.parseColor("#858585"));
        tv_order_send.setTextColor(Color.parseColor("#858585"));
        tv_order_cancel.setTextColor(Color.parseColor("#858585"));
        tv_order_way_done.setTextColor(Color.parseColor("#858585"));

    }

    @Override
    protected void bindEvent() {

    }

    private void requesttype(int status){
        shopOrders.a = "queryShopOrders";
        shopOrders.pcurr = page;
        shopOrders.statusMark = status;
        shopOrders.tokenId = Const.cache.getTokenId();
        if (page == 1){
            list.clear();
            adapter.notifyDataSetChanged();
        }
        request(shopOrders);

    }
    public static void delete(int position){
        list.remove(position);
        adapter.notifyDataSetChanged();
    }
}
