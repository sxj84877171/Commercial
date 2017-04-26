
package com.ytmall.fragment.shoppingCart;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.ytmall.R;
import com.ytmall.activity.MainActivity;
import com.ytmall.activity.goods.GoodsActivity;
import com.ytmall.activity.order.goods.OrderForGoodsActivity;
import com.ytmall.activity.user.LoginActivity;
import com.ytmall.adapter.ShoppingCartExpandAdapter;
import com.ytmall.adapter.ShoppingCartExpandAdapter.ShopTotalPriceChange;
import com.ytmall.api.shoppingcar.AddToCart;
import com.ytmall.api.shoppingcar.ChangeCartGoodsnum;
import com.ytmall.api.shoppingcar.DelCartGoods;
import com.ytmall.api.shoppingcar.GroupGoodsCart;
import com.ytmall.application.Const;
import com.ytmall.application.WSTMallApplication;
import com.ytmall.bean.GoodsListBean;
import com.ytmall.bean.ShopInfo;
import com.ytmall.domain.ShoppingCart;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.util.NetWorkUtil;
import com.ytmall.util.SharedPreferencesUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


@FragmentView(id = R.layout.fragment_shoppingcar_page)
public class ShoppingCartFragment extends BaseFragment implements
		View.OnClickListener, ShopTotalPriceChange {
	protected static final String TAG = "ShoppingCartFragment";

	@InjectView(id = R.id.shopping_balance)
	private Button shopping_balance;

	@InjectView(id = R.id.cb_chioc)
	private CheckBox cb_chioc;

	@InjectView(id = R.id.ll_balance)
	private RelativeLayout ll_balance;

	@InjectView(id = R.id.ll_null_spc)
	private LinearLayout ll_null_spc;

	@InjectView(id = R.id.shopping_delete)
	private Button shopping_delete;

	@InjectView(id = R.id.tv_total_price)
	private TextView tv_total_price;
	@InjectView(id = R.id.button_space)
	private View button_space;

	@InjectView(id = R.id.el_shoppingcar)
	private ExpandableListView el_shoppingcar;

	private ShoppingCartExpandAdapter scadapter;
	public boolean flag;
	public static List<ShoppingCart> shoppinglist=new ArrayList<>();
	private boolean isBalance = false;
	private ShopTotalPriceChange shop = this;
	//
	private int goodsTotalNum;
	//API
	private GroupGoodsCart groupGoodsCart=new GroupGoodsCart();
	private AddToCart addToCart=new AddToCart();
	private ChangeCartGoodsnum changeCartGoodsnum=new ChangeCartGoodsnum();
	private DelCartGoods delCartGoods=new DelCartGoods();
	@Override
	protected void requestSuccess(String url, String data) {
		if (url.contains(groupGoodsCart.getA())) {
			try {
				JSONObject jsonObject = new JSONObject(data);
				JSONArray jsonArrayData = jsonObject.getJSONArray("data");
				Type listType = new TypeToken<ArrayList<ShoppingCart>>() {
				}.getType();
				List<ShoppingCart> shoppingListTemp = new Gson().fromJson(jsonArrayData.toString(), listType);
				shoppinglist.clear();
				scadapter.notifyDataSetChanged();
				shoppinglist.addAll(shoppingListTemp);
				refreshCartAdapter();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else if(url.contains("addToCart")){
			try {
				JSONObject jsonObject=new JSONObject(data);
				if(jsonObject.getString("status").equals("1")){
					//加入购物车成功
					 if(Const.cache.getShoppingCartListSize()==0){
						 getShoppingCarInfo();
					 }
				}else{
					//加入购物车失败

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else if(url.contains(delCartGoods.getA())){
				getShoppingCarInfo();
		}else if(url.contains(changeCartGoodsnum.getA())){
				getShoppingCarInfo();
		}
	}

	@Override
	protected void flagFailed(String url) {
		if(url.contains(groupGoodsCart.getA())) {
			shoppinglist.clear();
			ll_balance.setVisibility(View.GONE);
			ll_null_spc.setVisibility(View.VISIBLE);
			el_shoppingcar.setVisibility(View.GONE);
			SharedPreferencesUtils.saveValue(getActivity(), "cartNum", "0");
			MainActivity.mainActivity.refreshBuyNum(0);
		}
	}

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		tWidget.setCenterViewText("购物车");
		tWidget.setRightBtnText("编辑");
		tWidget.setTitleAlpha(225);
		tWidget.right.setVisibility(View.VISIBLE);
		if (getActivity()
				.getClass()
				.getName()
				.equals("com.ytmall.activity.shoppingCart.ShoppingCartWithoutMainpageActivity")) {
			tWidget.left.setVisibility(View.VISIBLE);
			button_space.setVisibility(View.GONE);
		}
		flag = false;
		scadapter = new ShoppingCartExpandAdapter(getActivity());
		scadapter.setTotalPriceChangeListener(this);
		el_shoppingcar.setAdapter(scadapter);

	}

	@Override
	public void rightClick() {
		if (flag) {
			shopping_delete.setVisibility(View.GONE);
			shopping_balance.setVisibility(View.VISIBLE);
			ll_balance.setBackgroundResource(R.color.light_black);
			flag = false;
			scadapter.notifyDataSetChanged();
			tWidget.setRightBtnText("编辑");
		} else {// 编辑中
			shopping_delete.setVisibility(View.VISIBLE);
			shopping_balance.setVisibility(View.GONE);
			ll_balance.setBackgroundResource(R.color.gray_deep_other);
			flag = true;
		//	isSelectAll(false);
			setTotalPriceCheckAble();
			tv_total_price.setText("合计：" + CountTotalPrice() + "元");
			//scadapter.notifyDataSetChanged();
			tWidget.setRightBtnText("完成");
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId()) {
		case R.id.shopping_delete:
			AlertDialog.Builder builder = new Builder(getActivity());
			builder.setMessage("确认删除？");
			builder.setTitle("提示");
			builder.setPositiveButton("确认", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					deleteShoppingCarSelect();
					setTotalPriceCheckAble();
					tv_total_price.setText("合计：" + CountTotalPrice() + "元");
					scadapter.notifyDataSetChanged();
					for (int i = 0; i < scadapter.getGroupCount(); i++) {
						el_shoppingcar.expandGroup(i);
					}
					shopping_delete.setVisibility(View.GONE);
					shopping_balance.setVisibility(View.VISIBLE);
					ll_balance.setBackgroundResource(R.color.light_black);
					flag = false;
					tWidget.setRightBtnText("编辑");
				}
			});
			builder.setNegativeButton("取消", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.create().show();
			break;
		case R.id.shopping_balance:
			if (Const.isLogin) {
				balanceLoading();
			} else {
				isBalance = true;
				intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
			}
			break;
		}
	}

	private void balanceLoading() {
		if (!isShoppingListSelectNull()) {

			int checkedNumber = 0;
			for (int i = 0; i < shoppinglist.size(); i++) {
				if (shoppinglist.get(i).shopInfo.cbgroup){
					checkedNumber++;
				}
			}
			if (checkedNumber > 1){
				Toast.makeText(getActivity(), "请一次结算一个店铺的商品", Toast.LENGTH_LONG).show();
			}else {
				Intent intent = new Intent(getActivity(),
						OrderForGoodsActivity.class);
				intent.putExtra("totalprice", CountTotalPrice());
				startActivity(intent);
			}

		} else {
			Toast.makeText(getActivity(), "请选择商品", Toast.LENGTH_LONG).show();
		}
	}

	// 判断结算时有没选择商品
	public boolean isShoppingListSelectNull() {
		int goodNum = 0;
		for (int i = 0; i < ShoppingCartFragment.shoppinglist.size(); i++) {
			for (int j = 0; j < ShoppingCartFragment.shoppinglist.get(i).goods
					.size(); j++) {
				if (ShoppingCartFragment.shoppinglist.get(i).goods.get(j).isCheck.equals("1")) {
					goodNum = goodNum + 1;
				}
			}
		}
		if (goodNum == 0) {
			return true;
		} else {
			return false;
		}
	}
	//判断结算时有几家店铺
//	public boolean isSigleChecked(){
//		int shopId = 0;
//		for (int i = 0;i < ShoppingCartFragment.shoppinglist.size();i++){
//			ShopInfo info = shoppinglist.get(i).shopInfo;
//
//		}
//
//
//	}

	/**删除选中的商品*/
	public void deleteShoppingCarSelect() {

		StringBuilder stringBuilder=new StringBuilder();
		for(int i=0;i<shoppinglist.size();i++){
			for(int j=0;j<shoppinglist.get(i).goods.size();j++){
				if(shoppinglist.get(i).goods.get(j).isCheck.equals("1")){
					stringBuilder.append(shoppinglist.get(i).goods.get(j).goodsId+"_"+shoppinglist.get(i).goods.get(j).goodsAttrId+";");
				}
			}
		}
		delCartGoods.tokenId=Const.cache.getTokenId();
		delCartGoods.goodsIds=stringBuilder.substring(0,stringBuilder.length()-1).toString();
		request(delCartGoods);

		int n = shoppinglist.size();
		int k = 0;
		for (int i = 0; i < n; i++) {
			if (shoppinglist.get(k).shopInfo.cbgroup) {
				// 整组删除购物车数据
				for (int a = 0; a < shoppinglist.get(k).goods.size(); a++) {
				}
				shoppinglist.remove(k);
			} else {
				int m = shoppinglist.get(k).goods.size();
				int t = 0;
				for (int j = 0; j < m; j++) {
					if (shoppinglist.get(k).goods.get(t).isCheck.equals("1")) {
						// 删除缓存单个数据
						shoppinglist.get(k).goods.remove(t);
					} else {
						t++;
					}
				}
				k++;
			}
		}

	}
	@Override
	public void onResume() {
		super.onResume();
		tWidget.setTitleAlpha(225);
		setCacheCartToServer();
	}

	private void refreshCartAdapter(){
		ll_balance.setVisibility(View.VISIBLE);
		ll_null_spc.setVisibility(View.GONE);
		el_shoppingcar.setVisibility(View.VISIBLE);
		setTotalPriceCheckAble();
		initShopTotalMoney();
		initShopCheckStatus();
		MainActivity.mainActivity.refreshBuyNum(goodsTotalNum);
		totalPriceChange();
		scadapter.notifyDataSetChanged();
		for (int i = 0; i < scadapter.getGroupCount(); i++) {
			el_shoppingcar.expandGroup(i);
		}

		if (isBalance) {
			if (Const.isLogin) {
				balanceLoading();
			}
			isBalance = false;
		}
		tv_total_price.setText("合计：" + CountTotalPrice() + "元");
	}

	private void getShoppingCarInfo(){
		groupGoodsCart.tokenId=Const.cache.getTokenId();
		request(groupGoodsCart);
	}

	private void setCacheCartToServer(){
		if(Const.isLogin) {
			if (NetWorkUtil.isNetworkConnected(getActivity())) {
				if (Const.cache.getShoppingCartListSize() != 0) {
					for (int i = 0; i < Const.cache.getShoppingCartListSize(); i++) {
						addToCart.tokenId = Const.cache.getTokenId();
						addToCart.goodsId = Const.cache.getGoodsBeanFromShoppingCartList(i).goodsId;
						addToCart.goodsAttrId = Const.cache.getGoodsBeanFromShoppingCartList(i).goodsAttrId;
						addToCart.goodsNum = String.valueOf(Const.cache.getGoodsBeanFromShoppingCartList(i).goodscount);
						requestNoDialog(addToCart);
					}
					Const.cache.clearShoppingCartList();
				} else {
					getShoppingCarInfo();
				}
			} else {
				Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		shopping_balance.setOnClickListener(this);
		shopping_delete.setOnClickListener(this);
		shopping_balance.setOnClickListener(this);

		cb_chioc.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (cb_chioc.isChecked()) {
					isSelectAll(true);
					scadapter.notifyDataSetChanged();
					tv_total_price.setText("合计：" + CountTotalPrice() + "元");
					changeCartGoodsnum.goodsIds=getGroupCheckStatusString(shoppinglist,"1");
				} else {
					isSelectAll(false);
					scadapter.notifyDataSetChanged();
					tv_total_price.setText("合计：" + CountTotalPrice() + "元");
					changeCartGoodsnum.goodsIds=getGroupCheckStatusString(shoppinglist,"0");
				}
				changeCartGoodsnum.tokenId = Const.cache.getTokenId();
				request(changeCartGoodsnum);
			}
		});
	}
	private String getGroupCheckStatusString(List<ShoppingCart> shoppinglist,String status){
		StringBuilder stringBuilder=new StringBuilder();
		for(int j=0;j<shoppinglist.size();j++) {
			List<GoodsListBean> goodsListBeanList=shoppinglist.get(j).goods;
			for (int i = 0; i < goodsListBeanList.size(); i++) {
				stringBuilder.append(goodsListBeanList.get(i).goodsId + "_" + goodsListBeanList.get(i).goodsAttrId + "_" + goodsListBeanList.get(i).goodsNum + "_" + status + ";");
			}
		}
		return stringBuilder.substring(0,stringBuilder.length()-1).toString();
	}
	// 判断是否全部勾选。
	public void setTotalPriceCheckAble() {
		if (TotalPrice() == CountTotalPrice() && TotalPrice() != 0) {
			cb_chioc.setChecked(true);
		} else {
			cb_chioc.setChecked(false);
		}
	}

	/**计算勾选的购物车总价格*/
	public double CountTotalPrice() {
		double totalprice=0;
		if (shoppinglist != null) {
			for (int i = 0; i < shoppinglist.size(); i++) {
				for (int j = 0; j < shoppinglist.get(i).goods.size(); j++) {
					if (shoppinglist.get(i).goods.get(j).isCheck.equals("1")) {
						totalprice = totalprice
								+ Double.parseDouble(shoppinglist.get(i).goods
										.get(j).shopPrice)
								* shoppinglist.get(i).goods.get(j).goodsNum;
					}
				}
			}
		}
		return changeDouble(totalprice);
	}
	//double 类型保留二位小数
	public double changeDouble(Double price){
		DecimalFormat  numberFormat=new DecimalFormat("#.00");
		price = Double.parseDouble(numberFormat.format(price));
		return price;
	}
	//计算单个店铺商品总价格
	public void initShopTotalMoney(){
		if (shoppinglist != null) {
			for (int i = 0; i < shoppinglist.size(); i++) {
				for (int j = 0; j < shoppinglist.get(i).goods.size(); j++) {
					shoppinglist.get(i).shopInfo.totalPrice = Double
							.parseDouble(shoppinglist.get(i).goods.get(j).shopPrice)
							* shoppinglist.get(i).goods.get(j).goodsNum
							+ shoppinglist.get(i).shopInfo.totalPrice;
				}
			}
		}
	}
	/**初始化判断店铺是否勾选*/
	private void initShopCheckStatus(){
		goodsTotalNum=0;
		for(int i=0;i<shoppinglist.size();i++){
			shoppinglist.get(i).shopInfo.cbgroup=true;
			for(int j=0;j<shoppinglist.get(i).goods.size();j++){
				if(!shoppinglist.get(i).goods.get(j).isCheck.equals("1")){
					shoppinglist.get(i).shopInfo.cbgroup=false;
				}
				//goodsTotalNum=goodsTotalNum+shoppinglist.get(i).goods.get(j).goodsNum;
				goodsTotalNum++;
			}
		}
		SharedPreferencesUtils.saveValue(getActivity(),"cartNum",String.valueOf(goodsTotalNum));
	}
	// 计算总价格，不管是否勾选
	public double TotalPrice() {
		double totalprice = 0;
		if (shoppinglist != null) {
			for (int i = 0; i < shoppinglist.size(); i++) {
				for (int j = 0; j < shoppinglist.get(i).goods.size(); j++) {
					totalprice = totalprice
							+ Double.parseDouble(shoppinglist.get(i).goods
									.get(j).shopPrice)
							* shoppinglist.get(i).goods.get(j).goodsNum;
				}
			}
		}
		return changeDouble(totalprice);
	}

	// 设置购物车全部是否勾选
	public void isSelectAll(boolean ifAll) {
		for (int i = 0; i < shoppinglist.size(); i++) {
			if (ifAll) {
				shoppinglist.get(i).shopInfo.cbgroup = true;
				for (int j = 0; j < shoppinglist.get(i).goods.size(); j++) {
					shoppinglist.get(i).goods.get(j).isCheck = "1";
				}
			} else {
				shoppinglist.get(i).shopInfo.cbgroup = false;
				for (int j = 0; j < shoppinglist.get(i).goods.size(); j++) {
					shoppinglist.get(i).goods.get(j).isCheck = "0";
				}
			}
		}
	}

	// 选取商品计算总额接口回调
	@Override
	public void totalPriceChange() {
		// TODO Auto-generated method stub
		tv_total_price.setText("合计：" + CountTotalPrice() + "元");
		if (TotalPrice() == CountTotalPrice()) {
			cb_chioc.setChecked(true);
		} else {
			cb_chioc.setChecked(false);
		}
	}

	@Override
	public void setCheck(String value) {
		changeCartGoodsnum.tokenId = Const.cache.getTokenId();
		changeCartGoodsnum.goodsIds=value;
		request(changeCartGoodsnum);
	}

	@Override
	public void cancelCheck(String value) {
		changeCartGoodsnum.tokenId = Const.cache.getTokenId();
		changeCartGoodsnum.goodsIds=value;
		request(changeCartGoodsnum);
	}

	@Override
	public void changeGoodsNum(String value) {
		changeCartGoodsnum.tokenId = Const.cache.getTokenId();
		changeCartGoodsnum.goodsIds=value;
		requestNoDialog(changeCartGoodsnum);
	}
}
