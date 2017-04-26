package com.ytmall.fragment.order;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.ytmall.R;
import com.ytmall.R.color;
import com.ytmall.activity.order.OrderDetailActivity;
import com.ytmall.adapter.OrderExpandAdapter;
import com.ytmall.api.order.GetAllOrderList;
import com.ytmall.api.order.GetOrderList;
import com.ytmall.application.Const;
import com.ytmall.bean.GoodsListBean;
import com.ytmall.bean.OrderBean;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.widget.ListViewIndecator;
/**
 * 顶部导航 隐藏了5.0 android.support.design.widget.TabLayout，用的是自定义导航。要换的话在xml显示出来TabLayout。隐藏自定义导航。
 *
 * */
@FragmentView(id = R.layout.fragment_order)
public class OrderManagement extends BaseFragment implements OnClickListener {
	@InjectView(id = R.id.ll_title)
	private LinearLayout ll_title;
	@InjectView(id = R.id.ll_order_all)
	private LinearLayout ll_order_all;
	@InjectView(id = R.id.ll_order_way_pay)
	private LinearLayout ll_order_way_pay;
	@InjectView(id = R.id.ll_order_way_accept)
	private LinearLayout ll_order_way_accept;
	@InjectView(id = R.id.ll_order_way_recevice)
	private LinearLayout ll_order_way_recevice;
	@InjectView(id = R.id.ll_order_way_evalution)
	private LinearLayout ll_order_way_evalution;
	@InjectView(id = R.id.ll_order_payed)
	private LinearLayout ll_order_payed;

	@InjectView(id = R.id.tv_order_all)
	private TextView tv_order_all;
	@InjectView(id = R.id.tv_order_way_pay)
	private TextView tv_order_way_pay;
	@InjectView(id = R.id.tv_order_way_accept)
	private TextView tv_order_way_accept;
	@InjectView(id = R.id.tv_order_way_recevice)
	private TextView tv_order_way_recevice;
	@InjectView(id = R.id.tv_order_way_evalution)
	private TextView tv_order_way_evalution;
	@InjectView(id = R.id.tv_order_payed)
	private TextView tv_order_payed;

	// Cursor
	@InjectView(id = R.id.iv_cursor_first)
	private TextView iv_cursor_first;
	@InjectView(id = R.id.iv_cursor_second)
	private TextView iv_cursor_second;
	@InjectView(id = R.id.iv_cursor_third)
	private TextView iv_cursor_third;
	@InjectView(id = R.id.iv_cursor_fouth)
	private TextView iv_cursor_fouth;
	@InjectView(id = R.id.iv_cursor_fifth)
	private TextView iv_cursor_fifth;
	@InjectView(id = R.id.iv_cursor_sixth)
	private TextView iv_cursor_sixth;
	//tb_order_status
	@InjectView(id = R.id.tb_order_status)
	private TabLayout tb_order_status;

	@InjectView(id = R.id.el_order)
	private PullToRefreshExpandableListView el_order;

	@InjectView(id = R.id.tv_mycontainer)
	private ListViewIndecator tv_mycontainer;
	private OrderExpandAdapter orderadapter;
	private GetOrderList orderlist;
	/**每一个订单的内容*/
	public static List<OrderBean> orderbeanlist;
	public static int orderDetailPosition;
	//
	private static int MENU_ITEM = 0;

	public OrderManagement() {
	}

	public OrderManagement(int MENU_ITEM) {
		this.MENU_ITEM = MENU_ITEM;
	}

	protected void requestSuccess(String url, String data) {
		if (url.contains(orderlist.getA())) {


			try {
				JSONObject jsonreobj = new JSONObject(data);// 获取分类对象集合

				if (jsonreobj.has("data")) {
					JSONArray jsonreArray = jsonreobj.getJSONArray("data");// 包含集合-集合

					for (int n = 0; n < jsonreArray.length(); n++) {

						OrderBean odrder = new OrderBean();

						odrder.isAppraises = jsonreArray.getJSONObject(n)
								.getInt("isAppraises");
						odrder.complainId=jsonreArray.getJSONObject(n)
								.getInt("complainId");
						odrder.payType = jsonreArray.getJSONObject(n)
								.getString("payType");

						odrder.orderId = jsonreArray.getJSONObject(n)
								.getString("orderId");

						odrder.orderNo = jsonreArray.getJSONObject(n)
								.getString("orderNo");
						odrder.orderStatus = jsonreArray.getJSONObject(n)
								.getInt("orderStatus");

						odrder.needPay = jsonreArray.getJSONObject(n)
								.getDouble("needPay");

						odrder.createTime = jsonreArray.getJSONObject(n)
								.getString("createTime");

						odrder.realTotalMoney = jsonreArray.getJSONObject(n)
								.getDouble("realTotalMoney");

						odrder.shopName = jsonreArray.getJSONObject(n)
								.getString("shopName");
						odrder.shopId = jsonreArray.getJSONObject(n).getString(
								"shopId");

						JSONArray jsonarraydata = jsonreArray.getJSONObject(n)
								.getJSONArray("data");// 最后一层data

						for (int i = 0; i < jsonarraydata.length(); i++) {
							GoodsListBean goodslistbean = new Gson().fromJson(
									jsonarraydata.getJSONObject(i).toString(),
									GoodsListBean.class);
							odrder.goodlist.add(goodslistbean);
							odrder.shopgoodcount = odrder.shopgoodcount
									+ goodslistbean.goodsNum;
						}
						orderbeanlist.add(odrder);
					}

				}

			} catch (JSONException e) {
				e.printStackTrace();
			} finally {
				orderadapter.notifyDataSetChanged();
				setIndecator();
				el_order.getRefreshableView().setGroupIndicator(null);
				for (int c = 0; c < orderadapter.getGroupCount(); c++) {
					el_order.getRefreshableView().expandGroup(c);
				}
				el_order.onRefreshComplete();
			}
		}
	}

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		tWidget.setCenterViewText("我的订单");
		orderbeanlist = new ArrayList<OrderBean>();

		orderadapter = new OrderExpandAdapter(getActivity(), orderbeanlist);
		el_order.getRefreshableView().setAdapter(orderadapter);

		el_order.getRefreshableView().setGroupIndicator(null);
		for (int c = 0; c < orderadapter.getGroupCount(); c++) {
			el_order.getRefreshableView().expandGroup(c);
		}
		// all order
		orderlist = new GetOrderList();
		refreshActivity(MENU_ITEM);
		initTabs();
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		ll_order_all.setOnClickListener(this);
		ll_order_way_pay.setOnClickListener(this);
		ll_order_way_accept.setOnClickListener(this);
		ll_order_way_recevice.setOnClickListener(this);
		ll_order_way_evalution.setOnClickListener(this);
		ll_order_payed.setOnClickListener(this);

		el_order.setMode(Mode.PULL_FROM_START);
		el_order.setOnRefreshListener(new OnRefreshListener<ExpandableListView>() {

			@Override
			public void onRefresh(
					PullToRefreshBase<ExpandableListView> refreshView) {
				// TODO Auto-generated method stub
				orderbeanlist.clear();
				// orderlist.tokenId = Const.cache.getTokenId();
				orderlist.p = 1;
				request(orderlist);
			}
		});
		el_order.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				// TODO Auto-generated method stub
				orderlist.p = orderlist.p + 1;
				requestNoDialog(orderlist);
			}
		});
		el_order.getRefreshableView().setOnGroupClickListener(
				new OnGroupClickListener() {

					@Override
					public boolean onGroupClick(ExpandableListView parent,
												View v, int groupPosition, long id) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(getActivity(),
								OrderDetailActivity.class);
						startActivity(intent);
						orderDetailPosition = groupPosition;
						return true;
					}
				});
		el_order.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
								 int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				setIndecator();
			}
		});
	}

	private int countGroupPosition(int position) {
		int a=0;
		if (orderbeanlist.size() != 0) {
			for(int i=0;i<orderbeanlist.size();i++){
				a++;
				if(a>=position){
					return i+1;
				}
				for(int j=0;j<orderbeanlist.get(i).goodlist.size();j++){
					a++;
					if(a>=position){
						return i+1;
					}
				}
			}
		}
		return 1;
	}
	private void initTabs(){
		tb_order_status.addTab(tb_order_status.newTab().setText("全部"));
		tb_order_status.addTab(tb_order_status.newTab().setText("待付款"));
		tb_order_status.addTab(tb_order_status.newTab().setText("待受理"));
		tb_order_status.addTab(tb_order_status.newTab().setText("待收货"));
		tb_order_status.addTab(tb_order_status.newTab().setText("待评价"));
		tb_order_status.addTab(tb_order_status.newTab().setText("已消费"));
		tb_order_status.addTab(tb_order_status.newTab().setText("拒收/取消"));
		tb_order_status.setTabMode(TabLayout.MODE_SCROLLABLE);
		tb_order_status.setScrollPosition(MENU_ITEM, 0, false);
		if(MENU_ITEM>5) {
			tb_order_status.post(new Runnable() {
				@Override
				public void run() {
					tb_order_status.scrollTo(400, 0);
				}
			});
		}
		tb_order_status.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
			switch (tab.getPosition()){
				case 0:
					MENU_ITEM = 0;
					orderbeanlist.clear();
					requesttype(-999);
					break;
				case 1:
					MENU_ITEM = 1;
					orderbeanlist.clear();
					requesttype(-2);
					break;
				case 2:
					MENU_ITEM = 2;
					orderbeanlist.clear();
					requesttype(0);
					break;
				case 3:
					MENU_ITEM = 3;
					orderbeanlist.clear();
					requesttype(3);
					break;
				case 4:
					MENU_ITEM = 4;
					orderbeanlist.clear();
					requesttype(4);
					break;
				case 5:
					MENU_ITEM = 5;
					orderbeanlist.clear();
					requesttype(5);
					break;
				case 6:
					MENU_ITEM = 6;
					orderbeanlist.clear();
					requesttype(6);
					break;
			}

			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_order_all:
			MENU_ITEM = 0;
			resetCursor();
			iv_cursor_first.setBackgroundResource(R.color.orange);
			tv_order_all.setTextColor(Color.parseColor("#F6772A"));
			orderbeanlist.clear();
			requesttype(-999);
			break;
		case R.id.ll_order_way_pay:
			MENU_ITEM = 1;
			resetCursor();
			iv_cursor_second.setBackgroundResource(R.color.orange);
			tv_order_way_pay.setTextColor(Color.parseColor("#F6772A"));
			requesttype(-2);
			break;
		case R.id.ll_order_way_accept:
			MENU_ITEM = 2;
			resetCursor();
			iv_cursor_third.setBackgroundResource(R.color.orange);
			tv_order_way_accept.setTextColor(Color.parseColor("#F6772A"));
			requesttype(0);
			break;
		case R.id.ll_order_way_recevice:
			MENU_ITEM = 3;
			resetCursor();
			iv_cursor_fouth.setBackgroundResource(R.color.orange);
			tv_order_way_recevice.setTextColor(Color.parseColor("#F6772A"));
			requesttype(3);
			break;
		case R.id.ll_order_way_evalution:
			MENU_ITEM = 4;
			resetCursor();
			iv_cursor_fifth.setBackgroundResource(R.color.orange);
			tv_order_way_evalution.setTextColor(Color.parseColor("#F6772A"));
			requesttype(4);
			break;
		case R.id.ll_order_payed:
			MENU_ITEM = 5;
			resetCursor();
			iv_cursor_sixth.setBackgroundResource(R.color.orange);
			tv_order_payed.setTextColor(Color.parseColor("#F6772A"));
			requesttype(5);
			break;
		}
	}

	private void setIndecator() {

		tv_mycontainer.setFz(countGroupPosition(el_order.getRefreshableView().getLastVisiblePosition()) + "");
		tv_mycontainer.setFm(orderbeanlist.size() + "");
	}

	private void resetCursor() {
		iv_cursor_first.setBackgroundResource(R.color.white);
		iv_cursor_second.setBackgroundResource(R.color.white);
		iv_cursor_third.setBackgroundResource(R.color.white);
		iv_cursor_fouth.setBackgroundResource(R.color.white);
		iv_cursor_fifth.setBackgroundResource(R.color.white);
		iv_cursor_sixth.setBackgroundResource(R.color.white);

		tv_order_all.setTextColor(Color.parseColor("#858585"));
		tv_order_way_pay.setTextColor(Color.parseColor("#858585"));
		tv_order_way_accept.setTextColor(Color.parseColor("#858585"));
		tv_order_way_recevice.setTextColor(Color.parseColor("#858585"));
		tv_order_way_evalution.setTextColor(Color.parseColor("#858585"));
		tv_order_payed.setTextColor(Color.parseColor("#858585"));
	}

	public void requesttype(int type) {
		orderbeanlist.clear();
		orderadapter.notifyDataSetChanged();
		orderlist.tokenId = Const.cache.getTokenId();
		orderlist.p = 1;
		orderlist.status = type;
		request(orderlist);
	}

	public void refreshActivity(int position) {
		// TODO Auto-generated method stub
		switch (position) {
		case 0:
			resetCursor();
			iv_cursor_first.setBackgroundResource(R.color.orange);
			tv_order_all.setTextColor(Color.parseColor("#F6772A"));
			orderbeanlist.clear();
			requesttype(-999);
			break;
		case 1:
			resetCursor();
			iv_cursor_second.setBackgroundResource(R.color.orange);
			tv_order_way_pay.setTextColor(Color.parseColor("#F6772A"));
			requesttype(-2);
			break;
		case 2:
			resetCursor();
			iv_cursor_third.setBackgroundResource(R.color.orange);
			tv_order_way_accept.setTextColor(Color.parseColor("#F6772A"));
			requesttype(0);
			break;
		case 3:
			resetCursor();
			iv_cursor_fouth.setBackgroundResource(R.color.orange);
			tv_order_way_recevice.setTextColor(Color.parseColor("#F6772A"));
			requesttype(3);
			break;
		case 4:
			resetCursor();
			iv_cursor_fifth.setBackgroundResource(R.color.orange);
			tv_order_way_evalution.setTextColor(Color.parseColor("#F6772A"));
			requesttype(4);
			break;
		case 5:
			resetCursor();
			iv_cursor_sixth.setBackgroundResource(R.color.orange);
			tv_order_payed.setTextColor(Color.parseColor("#F6772A"));
			requesttype(5);
			break;
			case 6:
				ll_title.setVisibility(View.GONE);
				tWidget.setCenterViewText("拒收/取消");
				requesttype(6);
				break;
		}
	}
}
