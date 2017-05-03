package com.ytmall.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class OrderDetailBean implements Serializable {
	public String orderNo;
	public int payType;
	public int isPay;
	public String deliverType;
	public String orderRemarks;
	public String createTime;
	public double totalMoney;
	public double deliverMoney;
	public int isInvoice;
	public String userName;
	public String invoiceClient;
	public int orderStatus;
	public String userAddress;
	public String requireTime;
	public String userTel;
	public String userPhone;
//	public String logs;
	public  List<GoodsListBean> orderGoodList=new ArrayList<GoodsListBean>();

}
