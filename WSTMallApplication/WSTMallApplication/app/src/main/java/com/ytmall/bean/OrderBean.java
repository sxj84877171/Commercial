package com.ytmall.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * "payType":支付方式：0-货到付款 1-在线支付 "orderId":订单ID "orderNo":订单号
 * "orderStatus":订单详细状态 -5:门店同意取消 -4:门店同意拒收 -3:用户拒收 -2:未付款的订单 -1：用户取消 0:未受理
 * 1:已受理 2:打包中 3:配送中 4:已到货 5:门店确认已收货 "needPay":需付金额 "createTime":下单时间
 * "totalMoney":总金额 "shopName":店铺名称 "shopId":店铺ID
 **/

public class OrderBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8770717321354247275L;
	public int isAppraises;
	public int complainId;//0则没有进行投诉的，不为0则已经进行投诉
	public String payType;
	public String orderId;
	public String orderNo;
	public String userName;
	public int orderStatus;
	public double needPay;
	public String createTime;
	public double useScore;//使用的积分
	public double scoreMoney;//积分抵扣的钱
	//public double totalMoney;
	public double realTotalMoney;
	public String shopName;
	public String shopId;
	public List<GoodsListBean> goodlist=new ArrayList<GoodsListBean>();
	public int shopgoodcount;
	public double shopgoodtotalprice;

	public int isInvoice;
	public String invoiceClient;
	public String userAddress;
	public String requireTime;
	public String userTel;
	public String userPhone;

	public int isPay;
	public String deliverType;
	public String orderRemarks;
	public double totalMoney;
	public double deliverMoney;
}
