package com.ytmall.bean;

import java.io.Serializable;
/**shopId:店铺ID
shopName:店铺名称
shopAddress:店铺地址
shopTel:店铺电话
goodsScore:商品评分
serviceScore:服务评分
timeScore:时效评分
**/
public class BusinessHomeBean implements Serializable {
	public String shopId;
	public String shopName;
	public String shopAddress;
	public String shopImg;
	public String shopTel;
	public String goodsScore;
	public String serviceScore;
	public String timeScore;
	public String isFavorite;
	public String favoriteId;//关注记录ID，等于0就是没关注，大于0就是关注
}
