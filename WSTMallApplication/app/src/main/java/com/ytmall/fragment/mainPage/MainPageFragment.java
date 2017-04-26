package com.ytmall.fragment.mainPage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.activity.MainActivity;
import com.ytmall.activity.brands.BrandsActivity;
import com.ytmall.activity.goodlist.GoodListActivity;
import com.ytmall.activity.nearbybusiness.NearbyBusinessActivity;
import com.ytmall.activity.nearbybusiness.ToSelfSupermarketActivity;
import com.ytmall.activity.order.OrderActivity;
import com.ytmall.activity.user.LoginActivity;
import com.ytmall.adapter.AdAdapter;
import com.ytmall.adapter.GridClassifyAdapter;
import com.ytmall.api.advertisement.GetAds;
import com.ytmall.api.brands.FetchAds;
import com.ytmall.api.recommendgoods.GetGoodsCatAndGoods;
import com.ytmall.api.shoppingcar.GroupGoodsCart;
import com.ytmall.application.Const;
import com.ytmall.application.SortFiled;
import com.ytmall.application.WSTMallApplication;
import com.ytmall.bean.Advertisement;
import com.ytmall.bean.City;
import com.ytmall.bean.FetchAdsBean;
import com.ytmall.bean.GoodsListBean;
import com.ytmall.bean.MainPageReturn;
import com.ytmall.bean.RecommendGoodsBean;
import com.ytmall.domain.ShoppingCart;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.fragment.goods.GoodsListFragment;
import com.ytmall.fragment.shoppingCart.ShoppingCartFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.util.LocationUtils;
import com.ytmall.util.SharedPreferencesUtils;
import com.ytmall.util.StrUtil;
import com.ytmall.widget.GridviewForScroll;
import com.ytmall.widget.HorizontalScrollViewAdapter;
import com.ytmall.widget.MyHorizontalScrollView;
import com.ytmall.widget.ObservableScrollView;
import com.ytmall.widget.ObservableScrollView.Callbacks;


@FragmentView(id = R.layout.fragment_mainpage)
public class MainPageFragment extends BaseFragment implements
		View.OnClickListener {
	private static final String TAG = "MainPageFragment";
	public static MainPageFragment mainPageFragment;
	private ImageView[] tips, mImageViews;
	private GetAds getads;
	private GetGoodsCatAndGoods getgoodcatandgoods = new GetGoodsCatAndGoods();

	private AdAdapter adadapter;
	private boolean isEnterOrderLoading = false;

	// 广告
	private View adView;
	private ViewPager adViewpager;
	private ViewGroup tipGroup;

	// 导航菜单
	private View menuView;
	private ImageButton nearby_businesses;
	private ImageButton brand_hall;
	private ImageButton self_supermarket;
	private ImageButton my_order;

	@InjectView(id = R.id.lv_main_pager)
	private LinearLayout lv_main_pager;

	@InjectView(id = R.id.pl_scrollVeiw)
	private ObservableScrollView pl_scrollVeiw;
	@InjectView(id = R.id.swipeRefreshLayout)
	private SwipeRefreshLayout swipeRefreshLayout;
	private boolean isSwrfRefresh;
//	private GridView gridClassify;
	private GridviewForScroll gridClassify;
	private List<FetchAdsBean> menuList = new ArrayList<>();
	private GridClassifyAdapter adapter;

	//定位
	private AMapLocationClient locationClient = null;
	private AMapLocationClientOption locationOption = new AMapLocationClientOption();

	private String location = "";
	private FetchAds menuParam = new FetchAds(); ;

	@Override
	protected void requestSuccess(String url, String data) {
		if (url.contains(getads.getA())) {
			Log.d("json getads:",data);
			JSONObject jsonobj;
			try {

				jsonobj = new JSONObject(data);
				if (jsonobj.has("data")) {
					JSONArray jsonArray = jsonobj.getJSONArray("data");
					Log.d("json getads :",jsonArray.toString());
					Const.cache.removeAdvList();
					for (int i = 0; i < jsonArray.length(); i++) {
						Advertisement adean = new Gson().fromJson(jsonArray
								.getJSONObject(i).toString(),
								Advertisement.class);
						Const.cache.addAdvList(adean);
					}
				}
			} catch (JSONException e) {

			} finally {
				lv_main_pager.removeAllViews();
				initAdvertisement();

				menuParam.adPositionId = -9;
				requestNoDialog(menuParam);
				getgoodcatandgoods.areaId2 = Const.cache.city.getCityid();
				Log.i(TAG, "城市ID" + Const.cache.city.getCityid());
			}
		}
		else if (url.contains(getgoodcatandgoods.getA())) {
			Log.d("json goodcatandgoods:",data);
			try {
				JSONObject jsonreobj = new JSONObject(data);// 获取分类对象集合

				if (jsonreobj.has("data")) {
					JSONArray jsonreArray = jsonreobj.getJSONArray("data");// 包含集合-集合
					Const.cache.removeRgList();
					for (int n = 0; n < jsonreArray.length(); n++) {

						RecommendGoodsBean recommendgoodbean = new RecommendGoodsBean();
						recommendgoodbean.catId = jsonreArray.getJSONObject(n)
								.getString("catId");
						recommendgoodbean.catName = jsonreArray
								.getJSONObject(n).getString("catName");

						if (jsonreArray.getJSONObject(n).has("data")) {
							JSONArray jsonarraydata = jsonreArray
									.getJSONObject(n).getJSONArray("data");
							List<GoodsListBean> reconmmendlist = new ArrayList<GoodsListBean>();
							for (int i = 0; i < jsonarraydata.length(); i++) {
								GoodsListBean goodslistbean = gson.fromJson(
										jsonarraydata.getJSONObject(i)
												.toString(),
										GoodsListBean.class);
								reconmmendlist.add(goodslistbean);
							}
							recommendgoodbean.goodlistbean = reconmmendlist;
						}
						Const.cache.addRgList(recommendgoodbean);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
//				((WSTMallApplication) getActivity().getApplication()).saveCache();
				initReGood();
				((WSTMallApplication) getActivity().getApplication()).saveCache();
				swipeRefreshLayout.post(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						swipeRefreshLayout.setRefreshing(false);
						isSwrfRefresh = false;
					}
				});
				tWidget.setChageAlpha();
				getShoppingCarInfo();
			}
		}
		else if(url.contains(groupGoodsCart.getA())){
			Log.d("json groupgoodcart",data);
			try {
				JSONObject jsonObject = new JSONObject(data);
				JSONArray jsonArrayData = jsonObject.getJSONArray("data");
				Type listType = new TypeToken<ArrayList<ShoppingCart>>() {
				}.getType();
				List<ShoppingCart> shoppingListTemp = new Gson().fromJson(jsonArrayData.toString(), listType);
				ShoppingCartFragment.shoppinglist.clear();
				ShoppingCartFragment.shoppinglist.addAll(shoppingListTemp);
				MainActivity.mainActivity.refreshBuyNum(getShoppingCartNum(shoppingListTemp));

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		else if (url.contains(menuParam.getA())){
			Log.d("json menu:",data);
			try {

				JSONObject jso = new JSONObject(data);
				if (jso.has("data")){
					JSONArray ja = jso.getJSONArray("data");
					Const.cache.removeMenuList();
					for (int i = 0;i < ja.length();i++){
						FetchAdsBean bean = new Gson().fromJson(ja.getJSONObject(i).toString()
								,FetchAdsBean.class);
						Const.cache.addMenuList(bean);
					}
				}


			}catch (JSONException e){
				e.printStackTrace();


			}
			finally {
				initMeun();
				requestNoDialog(getgoodcatandgoods);

			}

		}

	}



	private int getShoppingCartNum(List<ShoppingCart> shoppinglist){
		int goodsTotalNum=0;
		for(int i=0;i<shoppinglist.size();i++){
			for(int j=0;j<shoppinglist.get(i).goods.size();j++){
				//goodsTotalNum=goodsTotalNum+shoppinglist.get(i).goods.get(j).goodsNum;
				goodsTotalNum++;
			}
		}
		SharedPreferencesUtils.saveValue(getActivity(), "cartNum", String.valueOf(goodsTotalNum));
		return goodsTotalNum;
	}
	public void GetAds() {
		getads = new GetAds();
		getads.adPositionId = -1;
//		getads.areaId2 = Const.cache.city.getCityid();
		requestNoDialog(getads);
	}

	public void GetAdsNoDialog() {
		getads = new GetAds();
//		getads.areaId2 = Const.cache.city.getCityid();
		requestNoDialog(getads);
	}

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		mainPageFragment = this;
		tWidget.changeMode(tWidget.Location_Search_Zbar_Mode);

		initAdvertisement();
		initMeun();
		initReGood();

		GetAds();

		swipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				swipeRefreshLayout.setRefreshing(true);
				setCancelRefresh();
				isSwrfRefresh = true;
			}
		});
		listener.onRefresh();
		initHight();
		initLocation();
//		startLocation();
	}

	OnRefreshListener listener = new OnRefreshListener() {
		public void onRefresh() {
			// TODO
		}
	};

	/**
	 * 10秒如果没数据取消刷新状态
	 */
	private void setCancelRefresh() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (isSwrfRefresh) {
					swipeRefreshLayout.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							swipeRefreshLayout.setRefreshing(false);
							isSwrfRefresh = false;
						}
					});
				}
			}
		}, 7000);
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
//		nearby_businesses.setOnClickListener(this);
//		brand_hall.setOnClickListener(this);
//		self_supermarket.setOnClickListener(this);
//		my_order.setOnClickListener(this);
		swipeRefreshLayout.setOnRefreshListener(listener);
		tWidget.setChageAlpha();
		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				refreshOperation();
			}
		});
		pl_scrollVeiw.setCallbacks(new Callbacks() {
			
			@Override
			public void onUpOrCancelMotionEvent() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScrollChanged(int scrollY) {
				// TODO Auto-generated method stub
				Log.e("gaodu", scrollY+"");
				int adHeight=adView.getHeight();
				float f=(float)scrollY/(float)adHeight;
				if(f<=1){
				tWidget.setTitleAlpha((int)(225*f));
				}else{
					tWidget.setTitleAlpha(225);
				}
			}
			
			@Override
			public void onDownMotionEvent() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void doOnBottom() {
				// TODO Auto-generated method stub
				
			}
		});

	}

	public void refreshOperation() {
		Const.cache.removeAdvList();
		Const.cache.removeRgList();
		Const.cache.removeMenuList();
//		lv_main_pager.removeAllViews();
		GetAds();
		bindEvent();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId()) {
		case R.id.nearby_businesses:
			intent = new Intent(getActivity(), NearbyBusinessActivity.class);
			startActivity(intent);
			break;
		case R.id.brand_hall:
			intent = new Intent(getActivity(), BrandsActivity.class);
			startActivity(intent);
			break;
		case R.id.self_supermarket:
			intent = new Intent(getActivity(), ToSelfSupermarketActivity.class);
			startActivity(intent);
			break;
		case R.id.my_order:
			if (Const.isLogin) {
				intent = new Intent(getActivity(), OrderActivity.class);
				startActivity(intent);
			} else {
				isEnterOrderLoading = true;
				intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
			}
			break;
		}
	}
 
	@Override
	public void onResume() {
		super.onResume();
		refreshCity();
		if (isEnterOrderLoading) {
			if (Const.isLogin) {
				Intent intent = new Intent(getActivity(), OrderActivity.class);
				startActivity(intent);
			}
			isEnterOrderLoading = false;
		}
	}

	public void refreshCity() {
		tWidget.refreshCity();
	}
//	public void refreshCity(String text) {
//		tWidget.refreshCity(text);
//	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				int currentitem = adViewpager.getCurrentItem();
				currentitem++;
				adViewpager.setCurrentItem(currentitem);
				this.sendEmptyMessageDelayed(1, 3000);
				break;
			}
		}
	};

	/**
	 * INIT AD
	 */
	private void initAdvertisement() {
		swipeRefreshLayout.setColorSchemeResources(
				android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		if(Const.cache.getAdvertisementList()!=null&&Const.cache.getAdvertisementList().size()!=0){
		adView = View.inflate(getActivity(), R.layout.fragment_mainpager_ad,
				null);
		adViewpager = (ViewPager) adView.findViewById(R.id.adviewPager);
		tipGroup = (ViewGroup) adView.findViewById(R.id.viewGroup);
		settips(); 
		setimage();
		adadapter = new AdAdapter(mImageViews, this, Const.cache.getAdvertisementList());
		adViewpager.setAdapter(adadapter);
		if (Const.cache.getAdvertisementList().size() != 1) {
			handler.sendEmptyMessageAtTime(1, 3000);
			adViewpager.setCurrentItem((mImageViews.length) * 100);
		} else {
			adViewpager.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					return true;
				}
			});
		}
		adViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageScrollStateChanged(int arg0) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onPageSelected(int arg0) {

						if (Const.cache.getAdvertisementList().size() != 1) {
							handler.removeMessages(1);
							handler.sendEmptyMessageDelayed(1, 3000);
						}
						int selectItems = arg0 % mImageViews.length;
						for (int i = 0; i < tips.length; i++) {
							if (i == selectItems) {
								tips[i].setBackgroundResource(R.drawable.onfocuse);
							} else {
								tips[i].setBackgroundResource(R.drawable.focuse);
							}
						}
					}

				});
		lv_main_pager.addView(adView);

		}
	}

	/**
	 * INIT MENU
	 */
	private void initMeun() {

		if (Const.cache.getMenuList() != null && Const.cache.getMenuList().size() != 0){
			menuView=LayoutInflater.from(getActivity()).inflate(R.layout.layout_home_grid,null);

			gridClassify = (GridviewForScroll) menuView.findViewById(R.id.gridClassify);
			adapter = new GridClassifyAdapter(getActivity(),Const.cache.getMenuList());
			gridClassify.setAdapter(adapter);
//
			lv_main_pager.addView(menuView);
		}



//		menuView = View.inflate(getActivity(),
//				R.layout.fragment_mainpager_menu, null);
//		nearby_businesses = (ImageButton) menuView
//				.findViewById(R.id.nearby_businesses);
//		brand_hall = (ImageButton) menuView.findViewById(R.id.brand_hall);
//		self_supermarket = (ImageButton) menuView
//				.findViewById(R.id.self_supermarket);
//		my_order = (ImageButton) menuView.findViewById(R.id.my_order);

	}
	private void getMenu(){
		menuParam = new FetchAds();
		menuParam.adPositionId = -9;
//		menuParam.tokenId = Const.cache.getTokenId();
//		request(menuParam);
		requestNoDialog(menuParam);

	}

	/**
	 * INIT LISTVIEW
	 */
	private void initReGood() {
		if(Const.cache.getRecommendGoodsList()!=null){
		for (int i = 0; i < Const.cache.getRecommendGoodsList().size(); i++) {
			View review = View.inflate(getActivity(),
					R.layout.mainpage_recommend_item, null);
			TextView tv_recommendation_name = (TextView) review
					.findViewById(R.id.tv_recommendation_name);
			ImageView bt_main_more = (ImageView) review
					.findViewById(R.id.bt_main_more);
			MyHorizontalScrollView mHorizontalScrollView = (MyHorizontalScrollView) review
					.findViewById(R.id.id_horizontalScrollView);
			bt_main_more.setOnClickListener(new MoreGoodsOnClickListener(i));
			tv_recommendation_name.setText(Const.cache.getRecommendGoodsList().get(i).catName);
			HorizontalScrollViewAdapter mAdapter = new HorizontalScrollViewAdapter(
					getActivity(), Const.cache.getRecommendGoodsList().get(i).goodlistbean);
			mHorizontalScrollView.initDatas(mAdapter);
			lv_main_pager.addView(review);
		}
	   }
	}

	class MoreGoodsOnClickListener implements OnClickListener {
		private int position;

		public MoreGoodsOnClickListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(), GoodListActivity.class);
			intent.putExtra(GoodsListFragment.Mode_GoodsCatIdOne,
					Integer.parseInt(Const.cache.getRecommendGoodsList().get(position).catId));
			startActivity(intent);
		}

	}

	private void settips() {
		tips = new ImageView[Const.cache.getAdvertisementList().size()];
		for (int i = 0; i < tips.length; i++) {
			ImageView imageView = new ImageView(getActivity());
			tips[i] = imageView;
			if (i == 0) {
				tips[i].setBackgroundResource(R.drawable.onfocuse);
			} else {
				tips[i].setBackgroundResource(R.drawable.focuse);
			}
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					new ViewGroup.LayoutParams(10, 10));
			layoutParams.leftMargin = 5;
			layoutParams.rightMargin = 5;
			tipGroup.addView(imageView, layoutParams);
		}
	}

	private void setimage() {
		if (Const.cache.getAdvertisementList().size() == 2 || Const.cache.getAdvertisementList().size() == 3) {
			mImageViews = new ImageView[Const.cache.getAdvertisementList().size() * 2];
		} else {
			mImageViews = new ImageView[Const.cache.getAdvertisementList().size()];
		}
		for (int i = 0; i < mImageViews.length; i++) {
			ImageView imageView = new ImageView(getActivity());
			((BaseActivity) getActivity()).loadOnLongRectangleImage(
					Const.BASE_URL
							+ Const.cache.getAdvertisementList().get(
									i > (Const.cache.getAdvertisementList().size() - 1) ? i
											- Const.cache.getAdvertisementList().size() : i)
									.getAdFile(), imageView);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			mImageViews[i] = imageView;
		}

	}

	private void initHight() {
		WindowManager wm = (WindowManager) getActivity().getSystemService(
				Context.WINDOW_SERVICE);
		SortFiled.height = wm.getDefaultDisplay().getHeight();
		SortFiled.width = wm.getDefaultDisplay().getWidth();

	}
	private GroupGoodsCart groupGoodsCart=new GroupGoodsCart();
	private void getShoppingCarInfo(){
		if(Const.isLogin) {
			groupGoodsCart.tokenId = Const.cache.getTokenId();
			requestNoDialog(groupGoodsCart);
		}

	}
	/**
	 * 初始化定位
	 *
	 * @since 2.8.0
	 * @author hongming.wang
	 *
	 */
	private void initLocation(){
		//初始化client
		locationClient = new AMapLocationClient(this.getActivity().getApplicationContext());
		//设置定位参数
		locationClient.setLocationOption(getDefaultOption());
		// 设置定位监听
		locationClient.setLocationListener(locationListener);

		startLocation();
	}

	/**
	 * 默认的定位参数
	 * @since 2.8.0
	 * @author hongming.wang
	 *
	 */
	private AMapLocationClientOption getDefaultOption(){
		AMapLocationClientOption mOption = new AMapLocationClientOption();
		mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
		mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
		mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
		mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
		mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
		mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
		mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
		AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
		mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
		return mOption;
	}

	/**
	 * 定位监听
	 */
	AMapLocationListener locationListener = new AMapLocationListener() {
		@Override
		public void onLocationChanged(AMapLocation loc) {
			if (null != loc) {
//				closeProgress();
				//解析定位结果
				location = LocationUtils.getLocationStr(loc);
//				Toast.makeText(getActivity(),location,Toast.LENGTH_SHORT).show();

				if (!location.equals(StrUtil.null2Str(Const.cache.getCity().getCityname()))){
					tWidget.setCity(location);

					City city = new City("",location);
//					Const.cache.city.setCityname(location);
					Const.cache.setCity(city);

				}
				Log.d("location:",location);
//				String result = LocationUtils.getLocationStr(loc);
//				txtLocation.setText(result);
			} else {
//				closeProgress();
//				txtLocation.setText("定位失败");
			}
		}
	};

	// 根据控件的选择，重新设置定位参数
	private void resetOption() {
		// 设置是否需要显示地址信息
		locationOption.setNeedAddress(true);
		/**
		 * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
		 * 注意：只有在高精度模式下的单次定位有效，其他方式无效
		 */
		locationOption.setGpsFirst(false);
		// 设置是否开启缓存
		locationOption.setLocationCacheEnable(true);
		//设置是否等待设备wifi刷新，如果设置为true,会自动变为单次定位，持续定位时不要使用
		locationOption.setOnceLocationLatest(true);
		//设置是否使用传感器
		locationOption.setSensorEnable(false);
		String strInterval = "2000";
		if (!TextUtils.isEmpty(strInterval)) {
			try{
				// 设置发送定位请求的时间间隔,最小值为1000，如果小于1000，按照1000算
				locationOption.setInterval(Long.valueOf(strInterval));
			}catch(Throwable e){
				e.printStackTrace();
			}
		}

		String strTimeout = "30000";
		if(!TextUtils.isEmpty(strTimeout)){
			try{
				// 设置网络请求超时时间
				locationOption.setHttpTimeOut(Long.valueOf(strTimeout));
			}catch(Throwable e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 开始定位
	 *
	 * @since 2.8.0
	 * @author hongming.wang
	 *
	 */
	private void startLocation(){
		//根据控件的选择，重新设置定位参数
		resetOption();
		// 设置定位参数
		locationClient.setLocationOption(locationOption);
		// 启动定位
		locationClient.startLocation();
	}

	/**
	 * 停止定位
	 *
	 * @since 2.8.0
	 * @author hongming.wang
	 *
	 */
	private void stopLocation(){
		// 停止定位
		locationClient.stopLocation();
	}

	/**
	 * 销毁定位
	 *
	 * @since 2.8.0
	 * @author hongming.wang
	 *
	 */
	private void destroyLocation(){
		if (null != locationClient) {
			/**
			 * 如果AMapLocationClient是在当前Activity实例化的，
			 * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
			 */
			locationClient.onDestroy();
			locationClient = null;
			locationOption = null;
		}
	}
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		destroyLocation();
//	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		destroyLocation();
	}
}
