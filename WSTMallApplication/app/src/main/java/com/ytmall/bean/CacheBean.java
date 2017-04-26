/**
#************************************************
# 项目名称：WSTMall
# 版本号： V1.0  
#************************************************
# 文件说明：
#          
#************************************************
# 子模块说明：
#                     
#************************************************
# 创建人员： 谈泳豪
# 创建人员QQ：1511895018
# 创建日期：2015-6-25
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
 */
package com.ytmall.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.util.Log;

import com.ytmall.activity.MainActivity;
import com.ytmall.api.recharge.GetCashAccInfo;
import com.ytmall.application.Const;
import com.ytmall.bean.Advertisement;
import com.ytmall.bean.City;
import com.ytmall.bean.GoodsListBean;
import com.ytmall.bean.Point;
import com.ytmall.bean.RecommendGoodsBean;
import com.ytmall.fragment.shoppingCart.ShoppingCartFragment;
import com.ytmall.util.EAJson;

/**
 * @ClassName CacheBean
 * @Create Date 2015-6-25 下午1:44:40
 * 
 * @author Tans QQ 1511895018 WSTMall开源商城-合作团队 官网地址:http://www.wstmall.com
 *         联系QQ:707563272
 */
public class CacheBean implements Serializable {

	private static final String TAG = "么么哒";
//	public Point point = new Point(23.050914, 113.393996);// 经纬度坐标
	public Point point = new Point(28.21, 113.00);// 经纬度坐标
	public City city = new City("410000", "长沙市"); // 城市
//	public City city = new City("440100", "广州市"); // 城市
	public City city2 = new City("440106", "天河区"); // 县区

	private List<String> searchList;// 历史搜索
	private List<GoodsListBean> shoppingCartList;// 购物车列表
	private int shoppingCartSum;
	private String tokenId;
	private boolean flag;
	private String userTel;
	private String userShuifei;
	private String userDianfei;
	private String userYouka;

	public void clearInfo(){
		userTel = "";
		userShuifei = "";
		userDianfei = "";
		userYouka = "";
		Const.isNeedSaveCache = true;
	}

	private GetCashConfBean bankAcc;

	public GetCashConfBean getBankAcc() {
		return bankAcc;
	}

	public void setBankAcc(GetCashConfBean bankAcc) {
		this.bankAcc = bankAcc;
	}

	public String getWEIXIN_APP_KEY() {
		return WEIXIN_APP_KEY;
	}

	public void setWEIXIN_APP_KEY(String WEIXIN_APP_KEY) {
		this.WEIXIN_APP_KEY = WEIXIN_APP_KEY;
		Const.isNeedSaveCache = true;
	}

	private String WEIXIN_APP_KEY ;
	// 首页数据缓存
	private List<Advertisement> advertisementlist;
	private List<RecommendGoodsBean> recommendgoodlist;
	private List<FetchAdsBean> menuList;
	//网络购物车
	public void addAdvList(Advertisement advertisement) {
		if (advertisementlist == null) {
			advertisementlist = new ArrayList<Advertisement>();
		}
		advertisementlist.add(advertisement);
		Const.isNeedSaveCache = true;
	}

	public void addRgList(RecommendGoodsBean recommendGoodsBean) {
		if (recommendgoodlist == null) {
			recommendgoodlist = new ArrayList<RecommendGoodsBean>();
		}
		recommendgoodlist.add(recommendGoodsBean);
		Const.isNeedSaveCache = true;
	}
	public void addMenuList(FetchAdsBean menuBean) {
		if (menuList == null){
			menuList = new ArrayList<>();
		}
		menuList.add(menuBean);
		Const.isNeedSaveCache = true;

	}
	public void removeAdvList() {
		if (advertisementlist != null) {
			advertisementlist.clear();
		}
		Const.isNeedSaveCache = true;
	}

	public void removeRgList() {
		if (recommendgoodlist != null) {
			recommendgoodlist.clear();
		}
		Const.isNeedSaveCache = true;
	}
	public void removeMenuList(){
		if (menuList != null){
			menuList.clear();
		}
		Const.isNeedSaveCache = true;
	}

	public List<Advertisement> getAdvertisementList() {
		return advertisementlist;
	}

	public List<RecommendGoodsBean> getRecommendGoodsList() {
		return recommendgoodlist;
	}

	public List<FetchAdsBean> getMenuList(){
		return menuList;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
		Const.isNeedSaveCache = true;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
		Const.isNeedSaveCache = true;
	}

	public String getUserShuifei() {
		return userShuifei;
	}

	public void setUserShuifei(String userShuifei) {
		this.userShuifei = userShuifei;
		Const.isNeedSaveCache = true;
	}

	public String getUserDianfei() {
		return userDianfei;
	}

	public void setUserDianfei(String userDianfei) {
		this.userDianfei = userDianfei;
		Const.isNeedSaveCache = true;
	}

	public String getUserYouka() {
		return userYouka;
	}

	public void setUserYouka(String userYouka) {
		this.userYouka = userYouka;
		Const.isNeedSaveCache = true;
	}

	//购物车数据缓存
	public void addShoppingCartList(GoodsListBean bean) {
		if (shoppingCartList == null) {
			shoppingCartList = new ArrayList<GoodsListBean>();
		}
		flag = true;
		for (int i = 0; i < Const.cache.shoppingCartList.size(); i++) {
			if (Const.cache.shoppingCartList.get(i).goodsId
					.equals(bean.goodsId)&&Const.cache.shoppingCartList.get(i).goodsAttrId.equals(bean.goodsAttrId)) {
				Log.i(TAG, "购物车存在相同商品");
				Const.cache.shoppingCartList.get(i).goodscount = Const.cache.shoppingCartList
						.get(i).goodscount + 1;
				flag = false;
			}
		}
		if (flag) {
			bean.goodscount=1;
			shoppingCartList.add(bean);
		}
		Const.isNeedSaveCache = true;
	}

	public void addShoppingCartListWithNum(GoodsListBean bean, int num) {
		if (shoppingCartList == null) {
			shoppingCartList = new ArrayList<GoodsListBean>();
		}
		flag = true;
		for (int i = 0; i < Const.cache.shoppingCartList.size(); i++) {
			if (Const.cache.shoppingCartList.get(i).goodsId
					.equals(bean.goodsId)&&Const.cache.shoppingCartList.get(i).goodsAttrId.equals(bean.goodsAttrId)) {
				Const.cache.shoppingCartList.get(i).goodscount = Const.cache.shoppingCartList
						.get(i).goodscount + num;
				flag = false;
			}
		}
		if (flag) {
			bean.goodscount=num;
			shoppingCartList.add(bean);
		}
		Const.isNeedSaveCache = true;
	}

	public void clearShoppingCartList() {
		shoppingCartList = null;
		Const.isNeedSaveCache = true;
		shoppingCartSum = 0;
	}

	public int getShoppingCartSum() {
		shoppingCartSum = 0;
		boolean isExit;
		if (shoppingCartList != null) {
			for (int i = 0; i < Const.cache.shoppingCartList.size(); i++) {
				isExit = false;
				for (int j = 0; j < ShoppingCartFragment.shoppinglist.size(); j++) {
					for (int k = 0; k < ShoppingCartFragment.shoppinglist.get(j).goods.size(); k++) {
						if (ShoppingCartFragment.shoppinglist.get(j).goods.get(k).goodsId.equals(shoppingCartList.get(i).goodsId) && ShoppingCartFragment.shoppinglist.get(j).goods.get(k).goodsAttrId.equals(shoppingCartList.get(i).goodsAttrId)) {
							isExit = true;
						}
						;
					}
				}
				if (!isExit) {
					shoppingCartSum++;
				}
			}
			return shoppingCartSum;
		}
		return shoppingCartSum;
	}

	public int getShoppingCartListSize() {
		if (shoppingCartList == null) {
			shoppingCartList = new ArrayList<GoodsListBean>();
			return 0;
		}
		return shoppingCartList.size();
	}

	public GoodsListBean getGoodsBeanFromShoppingCartList(int location) {
		return shoppingCartList.get(location);
	}

	public void deleteGoodsFromShoppingCartWithGoodsId(String goodsId) {
		for (int i = 0; i < shoppingCartList.size(); i++) {
			if (shoppingCartList.get(i).goodsId.equals(goodsId)) {
				shoppingCartSum -= shoppingCartList.get(i).goodscount;
				shoppingCartList.remove(i);
				break;
			}
		}
	}

	public void addSearchTarget(String searchTarget) {
		if (searchList == null) {
			searchList = new ArrayList<String>();
		}
		searchList.add(searchTarget);
		Const.isNeedSaveCache = true;
	}

	public List<String> getSearchList() {
		return searchList;
	}

	public void clearSearchList() {
		searchList = null;
		Const.isNeedSaveCache = true;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
		Const.isNeedSaveCache = true;
	}
}
