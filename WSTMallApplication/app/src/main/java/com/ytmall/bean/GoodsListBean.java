package com.ytmall.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GoodsListBean implements Serializable{
	
	public String shopName;//店铺名称
	public String shopId;//店铺ID
	public String goodsId;//商品ID
	public String goodsName;//商品名称
	public String goodsThums;//商品缩略图
	public String shopPrice;//商品价格
	public double userDistance;//距离
	public double deliveryStartMoney;//订单配送起步价
	public double score;//星星
	public String goodsAttrId; //商品属性ID,
	public int goodsStock; //商品库存,
	public int goodscount;//用于购物车单个商品数目
	public String goodsSpec;// 规格
	public String goodsUnit;
	public String appraiseNum;//评论数量
	public int goodsNum;
	public double goodsPrice;
	public String isFavorite;// 0:未关注 1:已关注
	public String favoriteId;//关注记录ID，等于0就是没关注，大于0就是关注
	//商品属性
	public String priceAttrName;
	public String priceAttrVal;
	public String isCheck;
	public List<GoodPriceAttrs> priceAttrs=new ArrayList<GoodPriceAttrs>();
	public List<GoodAttrs> attrs=new ArrayList<GoodAttrs>();
	

}
