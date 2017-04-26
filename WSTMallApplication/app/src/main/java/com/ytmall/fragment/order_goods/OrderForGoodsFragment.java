package com.ytmall.fragment.order_goods;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.ytmall.R;
import com.ytmall.activity.MainActivity;
import com.ytmall.activity.pwd.ChangePayPwdActivity;
import com.ytmall.activity.wechat.WeChatPayActivity;
import com.ytmall.activity.widget.HtmlViewActivity;
import com.ytmall.adapter.OrderForGoodsExpandAdapter;
import com.ytmall.api.addorder.AddOrders;
import com.ytmall.api.addorder.GroupGoodsForOrder;
import com.ytmall.api.order.CheckUseScore;
import com.ytmall.api.pay.ToPay;
import com.ytmall.api.pwd.CheckPayPwd;
import com.ytmall.api.useradress.GetUserAddress;
import com.ytmall.application.Const;
import com.ytmall.application.WSTMallApplication;
import com.ytmall.bean.GoodsForOrder;
import com.ytmall.bean.GoodsListBean;
import com.ytmall.bean.PayType;
import com.ytmall.bean.UserAdressBean;
import com.ytmall.bean.UserSoreBean;
import com.ytmall.bean.YuEPayReturn;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.fragment.shoppingCart.ShoppingCartFragment;
import com.ytmall.fragment.user.ChangePwdsFragment;
import com.ytmall.fragment.user.ShippingAdressFragment;
import com.ytmall.internet.APICallBack;
import com.ytmall.internet.LocalAPI;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.util.StrUtil;
import com.ytmall.widget.TipsDialog;
import com.ytmall.widget.TipsDialog.TipsTodo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@FragmentView(id = R.layout.fragment_order_for_goods)
public class OrderForGoodsFragment extends BaseFragment implements
        OnClickListener, TipsTodo {
    private static final String TAG = "OrderForGoodsFragment";
    @InjectView(id = R.id.el_orderforgood)
    private ExpandableListView el_orderforgood;
    @InjectView(id = R.id.bt_send_order)
    private Button bt_send_order;
    @InjectView(id = R.id.tv_total_price)
    private TextView tv_total_price;

//    @InjectView(id = R.id.cbUserBalance)
    CheckBox cbUserBalance;
//    @InjectView(id = R.id.txtUserMoney)
    TextView txtUserMoney;
//    @InjectView(id = R.id.llPayPwd)
    LinearLayout llPayPwd;
//    @InjectView(id = R.id.etPwd)
    EditText etPwd;
//    @InjectView(id = R.id.txtChange)
    TextView txtChange;

    private CheckPayPwd pwdParam;

    private GroupGoodsForOrder getgoodsfororder = new GroupGoodsForOrder();
    public static List<GoodsForOrder> goodsfororder;
    private OrderForGoodsExpandAdapter orfe;
    public static AddOrders addorderIsInvoice = new AddOrders();// 可修改是否开发票
//    private String addorderIsInvoice.isyuePay = ""
    public static int addressPosition;// 选取地址时使用
    public static boolean isChangeadr; // 判断是否选取地址
    private LayoutInflater inflater;
    private GetUserAddress getuseraddress;
    public static UserAdressBean adressBean;
    private List<UserAdressBean> userAdressBeanList;
    private View headadressview;
    // 单个商品购买
    private int orderType;
    private String goodsAttr;
    //
    private TipsDialog tipsDialog;
    private double total;
    public static List<PayType> payOnlines = new ArrayList<PayType>();
    public static List<PayType> payUnlines = new ArrayList<PayType>();
    public static String padCode;
    public static List<PayType> payType = new ArrayList<PayType>();

    public static int PAY_POSITION = 0;

    //
    public static String requireData = "0000-00-00";
    public static String requireTime = "00:00";
    //积分 API
    private CheckUseScore checkUseScore = new CheckUseScore();
    private UserSoreBean userSoreBean;
    private boolean isUseSore;
    private boolean pwdIsTrue = false;


/*	public OrderForGoodsFragment(int orderType, String goodsAttr,
            int goodCount, double totalprice) {
		this.orderType = orderType;
		this.goodsAttr = goodsAttr;
	}*/

    public OrderForGoodsFragment() {

    }

    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(getgoodsfororder.getA())) {
            JSONObject jsonobj;
            try {
                jsonobj = new JSONObject(data);
                if (jsonobj.has("data")) {
                    JSONArray jsonArray = jsonobj.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        GoodsForOrder shopbean = new Gson().fromJson(jsonArray
                                .getJSONObject(i).getJSONObject("shopInfo")
                                .toString(), GoodsForOrder.class); // shopinfo
                        shopbean.goodslist = new ArrayList<GoodsListBean>();
                        JSONArray goodsarray = jsonArray.getJSONObject(i)
                                .getJSONArray("goods");
                        for (int j = 0; j < goodsarray.length(); j++) {
                            GoodsListBean gbean = new Gson().fromJson(
                                    goodsarray.getJSONObject(j).toString(),
                                    GoodsListBean.class);
                            shopbean.goodslist.add(gbean);
                        }

                        JSONArray cmarray = jsonArray.getJSONObject(i)
                                .getJSONArray("communitys");
                        for (int j = 0; j < cmarray.length(); j++) {
                            shopbean.communityId.add(cmarray.getString(j));
                            Log.i(TAG, "社区ID" + cmarray.getString(j));
                        }
                        goodsfororder.add(shopbean);
                    }
                    payType.clear();
                    JSONObject jsPayType = jsonobj.getJSONObject("payType");

                    try {
                        JSONArray jsPayOnline = jsPayType
                                .getJSONArray("onlines");
                        if (jsPayOnline.length() > 0) {
                            for (int j = 0; j < jsPayOnline.length(); j++) {
                                PayType payOnline = new Gson()
                                        .fromJson(jsPayOnline.getJSONObject(j)
                                                .toString(), PayType.class);
                                Log.i(TAG, payOnline.payName);
                                payOnlines.add(payOnline);
                                payType.add(payOnline);
                            }
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }

                    try {
                        JSONArray jsPayUnline = jsPayType
                                .getJSONArray("unlines");
                        if (jsPayUnline.length() > 0) {
                            for (int j = 0; j < jsPayUnline.length(); j++) {
                                PayType payUnline = new Gson()
                                        .fromJson(jsPayUnline.getJSONObject(j)
                                                .toString(), PayType.class);
                                Log.i(TAG, payUnline.payName);
                                payUnlines.add(payUnline);
                                payType.add(payUnline);
                            }
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }

                    //积分
                    JSONObject scoreJsonObject = jsonobj.getJSONObject("scoreInfo");
                    userSoreBean = new Gson().fromJson(scoreJsonObject.toString(), UserSoreBean.class);
                }
            } catch (JSONException e) {

            } finally {
                if (payType.size() != 0) {
                    addorderIsInvoice.payWay = payType.get(PAY_POSITION).isOnline;
                    padCode = payType.get(PAY_POSITION).payCode;
                }
                initShop();
                initHead();
                if (addorderIsInvoice.isSelf.equals("0")) {
                    total = countCanAddOrderTotalPrice() + countDeliverMoney();
                    tv_total_price.setText("实时付款:" + total + "元");
                } else {
                    total = countCanAddOrderTotalPrice();
                    tv_total_price.setText("实时付款:" + total + "元");
                }
//                initSore();


                orfe = new OrderForGoodsExpandAdapter(getActivity());
                el_orderforgood.setAdapter(orfe);
                el_orderforgood.setGroupIndicator(null);
                for (int c = 0; c < orfe.getGroupCount(); c++) {
                    el_orderforgood.expandGroup(c);
                }
                initPwd();

            }
        } else if (url.contains(getuseraddress.getA())) { // 获取默认收货地址：
            JSONObject jsonobj;

            try {
                jsonobj = new JSONObject(data);
                if (jsonobj.has("data")) {
                    JSONArray jsonArray = jsonobj.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        UserAdressBean adressbean = new Gson().fromJson(
                                jsonArray.getJSONObject(i).toString(),
                                UserAdressBean.class);
                        userAdressBeanList.add(adressbean);
                    }
                }
            } catch (JSONException e) {

            } finally {
                request(getgoodsfororder);
            }
        } else if (url.contains(addorderIsInvoice.getA())) {
            JSONObject jsonobj;
            try {
                jsonobj = new JSONObject(data);
                String orderId = jsonobj.getString("orderIds");
                ToPay toPay = new ToPay();
                toPay.orderId = orderId.substring(1, orderId.indexOf("]"));
                toPay.tokenId = Const.cache.getTokenId();

              //  winXinPay.orderId = orderId.substring(1, orderId.indexOf("]"));
              //  winXinPay.tokenId = Const.cache.getTokenId();
                if (addorderIsInvoice.isyuePay.equals("true")){

                    Toast.makeText(getActivity(), "支付成功！", Toast.LENGTH_SHORT)
                            .show();
                    deleteShoppingCarAddOrder();
                    getActivity().finish();

                }else {
                    if (padCode.equals("alipay")) {//支付宝支付 1
                        Intent intent = new Intent(getActivity(),
                                HtmlViewActivity.class);
                        intent.putExtra("title", "在线支付");
                        intent.putExtra("Url", Const.API_BASE_URL + "&a=" + toPay.getA()
                                + toPay.getString());
                        startActivity(intent);
                        deleteShoppingCarAddOrder();
                        getActivity().finish();
                    } else if (padCode.equals("app_weixin")) {//跳到微信支付
                        //  request(winXinPay);
                        startActivity(new Intent(getActivity(), WeChatPayActivity.class).putExtra("orderId",
                                orderId.substring(1, orderId.indexOf("]"))).putExtra("from",""));
                    } else {
                        Toast.makeText(getActivity(), "下单成功！", Toast.LENGTH_SHORT)
                                .show();
                        deleteShoppingCarAddOrder();
                        getActivity().finish();
                    }

                }
//                if (padCode.equals("alipay")) {//支付宝支付 1
//                    Intent intent = new Intent(getActivity(),
//                            HtmlViewActivity.class);
//                    intent.putExtra("title", "在线支付");
//                    intent.putExtra("Url", Const.API_BASE_URL + "&a=" + toPay.getA()
//                            + toPay.getString());
//                    startActivity(intent);
//                    deleteShoppingCarAddOrder();
//                    getActivity().finish();
//                } else if (padCode.equals("app_weixin")) {//跳到微信支付
//                  //  request(winXinPay);
//                    startActivity(new Intent(getActivity(), WeChatPayActivity.class).putExtra("orderId",
//                            orderId.substring(1, orderId.indexOf("]"))).putExtra("from",""));
//                } else {
//                    Toast.makeText(getActivity(), "下单成功！", Toast.LENGTH_SHORT)
//                            .show();
//                    deleteShoppingCarAddOrder();
//                    getActivity().finish();
//                }
				if (orderType == 1) {
					getActivity().finish();
				} else {
					deleteShoppingCarAddOrder();
					getActivity().finish();
				}
            } catch (JSONException e) {

            }

        }else if (url.contains(pwdParam.getA())){
            try {
                JSONObject jso = new JSONObject(data);
                pwdIsTrue = true;
                addorderIsInvoice.isyuePay = "true";
                //用余额支付
                double totalPrice = countCanAddOrderTotalPrice();
                if ((Double.valueOf(Const.user.userMoney) - totalPrice) < 0){
                    Toast.makeText(getActivity(),"余额不足，请选其他支付方式",Toast.LENGTH_SHORT).show();
                    return;

                }else {
                    sendOrder();
                }



            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void flagFailed(String url) {
        super.flagFailed(url);
        pwdIsTrue = false;
    }

    @Override
    public void bindDataForUIElement() {
        // TODO Auto-generated method stub
        tWidget.setCenterViewText("提交订单");
        goodsfororder = new ArrayList<GoodsForOrder>();
        userAdressBeanList = new ArrayList<UserAdressBean>();
        getgoodsfororder.tokenId = Const.cache.getTokenId();
        getuseraddress = new GetUserAddress();
        getuseraddress.tokenId = Const.cache.getTokenId();
        requestNoDialog(getuseraddress);
        isUseSore = false;
        addorderIsInvoice.payWay = "2";


    }

    @Override
    protected void bindEvent() {
        // TODO Auto-generated method stub
        bt_send_order.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_send_order:
                String payPwd = etPwd.getText().toString();
                if (cbUserBalance.isChecked()){

                    checkPwd();
                }else {
                    sendOrder();
                }
                break;
        }
    }
    private void sendOrder(){
        if (notDeliveryShop().length() != 0
                || notPriceAddOrder().length() != 0) {
            tipsDialog = new TipsDialog(getActivity(),
                    R.style.MyDialogStyle, notDeliveryShop()
                    + notPriceAddOrder());
            tipsDialog.setSureToDoListener(this);
            tipsDialog.show();
        } else {
            if (adressBean != null) {
                if (payType.size() == 0) {
                    Toast.makeText(getActivity(), "抱歉，后台暂没开启支付方式", Toast.LENGTH_SHORT).show();
                } else {
                    addorderIsInvoice();
                    if (addorderIsInvoice.payWay.equals("2")) {
                        boolean isPaySupported = WXAPIFactory.createWXAPI(getActivity(), null).getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
                        if (isPaySupported) {
                            request(addorderIsInvoice);
                        } else {
                            Toast.makeText(getActivity(), "抱歉，请升级微信到最新版本", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        request(addorderIsInvoice);
                    }
                }
            } else {
                Toast.makeText(getActivity(), "亲，还没收货地址额！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 校验密码
     */
    private void checkPwd(){
        pwdParam = new CheckPayPwd();

        pwdParam.tokenId = Const.cache.getTokenId();
        pwdParam.payPwd = etPwd.getText().toString();
        request(pwdParam);


    }

    // 是否开发票，用户可以修改
    public void addorderIsInvoice() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < goodsfororder.size(); i++) {
            if (goodsfororder.get(i).isdelivery) {
                for (int j = 0; j < goodsfororder.get(i).goodslist.size(); j++) {
                    GoodsListBean bean = goodsfororder.get(i).goodslist.get(j);
                    sb.append(bean.goodsId);
                    sb.append("_");
                    sb.append(bean.goodsAttrId);
                    sb.append("_");
                    sb.append(bean.goodsNum);
                    sb.append(";");
                }
            }
        }
        addorderIsInvoice.tokenId = Const.cache.getTokenId();
        addorderIsInvoice.adressId = adressBean.addressId;
        if (sb.length() != 0) {
            addorderIsInvoice.goodsIds = sb.substring(0, sb.length() - 1)
                    .toString().trim();
        } else {
            addorderIsInvoice.goodsIds = null;
        }
    }

    // 列出不在送货区域的商家。
    public String notDeliveryShop() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < goodsfororder.size(); i++) {
            if (!goodsfororder.get(i).isdelivery) {
                sb.append(goodsfororder.get(i).shopName + ",");
            }
        }
        if (sb.length() == 0) {
            return sb.toString();
        } else {
            return "亲!  " + sb.substring(0, sb.length() - 1).toString().trim()
                    + "不在配送区域额！";
        }

    }

    // 列出价格满足下单条件的商家
    public String notPriceAddOrder() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < goodsfororder.size(); i++) {
            if (!goodsfororder.get(i).isMinDeliveryPrice) {
                sb.append(goodsfororder.get(i).shopName + ",");
            }
        }
        if (sb.length() == 0) {
            return sb.toString();
        } else {
            return "亲!  " + sb.substring(0, sb.length() - 1).toString().trim()
                    + "不满足店铺下单的最低价格";
        }

    }

    public String initOrder() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ShoppingCartFragment.shoppinglist.size(); i++) {
            for (int j = 0; j < ShoppingCartFragment.shoppinglist.get(i).goods
                    .size(); j++) {
                GoodsListBean bean = ShoppingCartFragment.shoppinglist.get(i).goods
                        .get(j);
                if (ShoppingCartFragment.shoppinglist.get(i).goods.get(j).isCheck.equals("1")) {
                    sb.append(bean.goodsId + "_" + bean.goodsAttrId + "_"
                            + bean.goodscount + ";");
                }
            }
        }
        return sb.substring(0, sb.length() - 1).toString().trim();
    }

    /**
     * 初始化不在配送区域的商家
     *
     * @param adressBean
     */

//    public void initDelivery(UserAdressBean adressBean) {
//        for (int i = 0; i < goodsfororder.size(); i++) {
//            if (goodsfororder.get(i).isDistributAll != null && goodsfororder.get(i).isDistributAll.equals("0")) {
//                if (!goodsfororder.get(i).communityId.contains(adressBean
//                        .getCommunityId())) {
//                    goodsfororder.get(i).isdelivery = false;
//                }
//            }
//        }
//    }

    /**
     * 单个店铺的商品总价格数据写入,是否满足下单条件写入(配送+最低下单价格)
     */
    public void initShop() {
        for (int i = 0; i < goodsfororder.size(); i++) {
            if (goodsfororder.get(i).isdelivery) {
                double singleShopPrice = 0;
                for (int j = 0; j < goodsfororder.get(i).goodslist.size(); j++) {
                    singleShopPrice = Double
                            .parseDouble(goodsfororder.get(i).goodslist.get(j).shopPrice)
                            * goodsfororder.get(i).goodslist.get(j).goodsNum
                            + singleShopPrice;
                }
                goodsfororder.get(i).goodsTotalPrice = singleShopPrice;

                if (singleShopPrice > goodsfororder.get(i).deliveryStartMoney) {
                    goodsfororder.get(i).isMinDeliveryPrice = true;
                } else {
                    goodsfororder.get(i).isMinDeliveryPrice = false;
                }
            }
        }
    }

    /*
     * 计算可以下单的商品总价格,不包括运费，
     */
    public double countCanAddOrderTotalPrice() {
        double totalPrice = 0;
        for (int i = 0; i < goodsfororder.size(); i++) {
            if (goodsfororder.get(i).isdelivery
                    && goodsfororder.get(i).isMinDeliveryPrice) {
                if (goodsfororder.get(i).isdelivery) {
                    for (int j = 0; j < goodsfororder.get(i).goodslist.size(); j++) {
                        totalPrice = Double
                                .parseDouble(goodsfororder.get(i).goodslist
                                        .get(j).shopPrice)
                                * goodsfororder.get(i).goodslist.get(j).goodsNum
                                + totalPrice;
                    }
                }
            }
        }

        return changeDouble(totalPrice);
    }

    /**
     * 计算总运费
     */
    public double countDeliverMoney() {
        double deliverMoney = 0;
        for (int i = 0; i < goodsfororder.size(); i++) {
            if (goodsfororder.get(i).goodsTotalPrice < goodsfororder.get(i).deliveryFreeMoney && goodsfororder.get(i).isdelivery) {
                deliverMoney = goodsfororder.get(i).deliveryMoney
                        + deliverMoney;
            }
        }
        return changeDouble(deliverMoney);
    }

    // 删除提交订单的商品
    public void deleteShoppingCarAddOrder() {

        int n = ShoppingCartFragment.shoppinglist.size();
        int k = 0;
        for (int i = 0; i < n; i++) {
            if (ShoppingCartFragment.shoppinglist.get(k).shopInfo.cbgroup) {
                // 整组删除购物车数据
                for (int a = 0; a < ShoppingCartFragment.shoppinglist.get(k).goods
                        .size(); a++) {
                    Const.cache
                            .deleteGoodsFromShoppingCartWithGoodsId(ShoppingCartFragment.shoppinglist
                                    .get(k).goods.get(a).goodsId);
                }
                ShoppingCartFragment.shoppinglist.remove(k);
            } else {
                int m = ShoppingCartFragment.shoppinglist.get(k).goods.size();
                int t = 0;
                for (int j = 0; j < m; j++) {
                    if (ShoppingCartFragment.shoppinglist.get(k).goods.get(t).isCheck.equals("1")) {
                        // 删除缓存单个数据
                        Const.cache
                                .deleteGoodsFromShoppingCartWithGoodsId(ShoppingCartFragment.shoppinglist
                                        .get(k).goods.get(t).goodsId);
                        ShoppingCartFragment.shoppinglist.get(k).goods
                                .remove(t);
                    } else {
                        t++;
                    }
                }
                k++;
            }
        }
        MainActivity.mainActivity.refreshBuyNum(13);
        ((WSTMallApplication) getActivity().getApplication()).saveCache();
    }

    /**
     * 初始化Headview
     */
    public void initHead() {
        // UserAdressBean Bean = null;
        inflater = (LayoutInflater) getActivity().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        if (userAdressBeanList.size() != 0) {
            headadressview = inflater.inflate(R.layout.mine_adress_item, null);
            if (!isChangeadr) {
                adressBean = userAdressBeanList.get(0);
            } else {
                adressBean = userAdressBeanList.get(addressPosition);
                isChangeadr = true;
            }
            TextView tv_person_name = (TextView) headadressview
                    .findViewById(R.id.tv_person_name);
            tv_person_name.setText(adressBean.getUserName());

            TextView tv_cellphone = (TextView) headadressview
                    .findViewById(R.id.tv_cellphone);
            tv_cellphone.setText(adressBean.getUserPhone());

            TextView tv_telephone = (TextView) headadressview
                    .findViewById(R.id.tv_telephone);
            tv_telephone.setText(adressBean.getUserTel());

            TextView tv_area = (TextView) headadressview
                    .findViewById(R.id.tv_area);
            tv_area.setText(adressBean.getAreaName());

            TextView tv_adress = (TextView) headadressview
                    .findViewById(R.id.tv_adress);
            tv_adress.setText(adressBean.getAddress());
//            initDelivery(adressBean);
        } else {
            headadressview = inflater.inflate(
                    R.layout.order_for_good_no_adress, null);
            Toast.makeText(getActivity(), "亲，还没收货地址额！", Toast.LENGTH_SHORT).show();
        }
        el_orderforgood.addHeaderView(headadressview);
        headadressview.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                replaceFragment(new ShippingAdressFragment(0), true);
            }
        });

        // 订单配送什么的
        View paydeliveryview = inflater.inflate(
                R.layout.goods_for_order_pay_delivery, null);
        RelativeLayout ll_pay_delivery = (RelativeLayout) paydeliveryview
                .findViewById(R.id.rl_pay_delivery);
        TextView tv_pay_type = (TextView) paydeliveryview
                .findViewById(R.id.tv_pay_type);
        TextView tv_is_self_get = (TextView) paydeliveryview
                .findViewById(R.id.tv_is_self_get);
        TextView tv_invoice_ms = (TextView) paydeliveryview
                .findViewById(R.id.tv_invoice_ms);


        if (payType.size() != 0) {
            tv_pay_type
                    .setText(payType.get(OrderForGoodsFragment.PAY_POSITION).payName);
        }
        if (addorderIsInvoice.isSelf.equals("1")) {
            tv_is_self_get.setText("自提");
        } else {
            tv_is_self_get.setText("不自提");
        }
        if (addorderIsInvoice.isInvoice.equals("1")) {
            tv_invoice_ms.setText("开发票");
        } else {
            tv_invoice_ms.setText("不开发票");
        }

        ll_pay_delivery.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                replaceFragment(new AddOrderFragment(), true);
            }
        });
        el_orderforgood.addHeaderView(paydeliveryview);
        // 运费
        View deiveryview = inflater.inflate(
                R.layout.goods_for_order_delivery_goods_money, null);
        TextView tv_shop_total_price = (TextView) deiveryview
                .findViewById(R.id.tv_shop_total_price);
        TextView tv_shop_delivery_money = (TextView) deiveryview
                .findViewById(R.id.tv_shop_delivery_money);
        tv_shop_total_price.setText(countCanAddOrderTotalPrice() + "元");
        if (addorderIsInvoice.isSelf.equals("0")) {
            tv_shop_delivery_money.setText(countDeliverMoney() + "元");
        } else {
            tv_shop_delivery_money.setText(0.0 + "元");
        }
        el_orderforgood.addHeaderView(deiveryview);
    }

    private void initSore() {
        //积分
        View soreView = inflater.inflate(
                R.layout.item_use_score, null);
        TextView tv_sore_detail = (TextView) soreView.findViewById(R.id.tv_sore_detail);
        RelativeLayout rl_use_sore = (RelativeLayout) soreView.findViewById(R.id.rl_use_sore);
        TextView tv_sore = (TextView) soreView.findViewById(R.id.tv_sore);
        final ImageView iv_if_use_sore = (ImageView) soreView.findViewById(R.id.iv_if_use_sore);

        tv_sore_detail.setText("共" + userSoreBean.userScore + "个积分，" + "本次可用" + userSoreBean.useScore + "个积分");
        tv_sore.setText("￥" + userSoreBean.scoreMoney + "元");
        if (addorderIsInvoice.isScorePay.equals("0")) {
            iv_if_use_sore.setBackgroundResource(R.drawable.btn_c_close);
            isUseSore = false;
            addorderIsInvoice.isScorePay = "0";
            tv_total_price.setText("实时付款:" + changeDouble(total) + "元");
        } else {
            iv_if_use_sore.setBackgroundResource(R.drawable.btn_c_open);
            isUseSore = true;
            addorderIsInvoice.isScorePay = "1";
            tv_total_price.setText("实时付款:" + changeDouble(total - userSoreBean.scoreMoney) + "元");
        }
        rl_use_sore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isUseSore) {
                    iv_if_use_sore.setBackgroundResource(R.drawable.btn_c_open);
                    isUseSore = true;
                    addorderIsInvoice.isScorePay = "1";
                    tv_total_price.setText("实时付款:" + changeDouble(total - userSoreBean.scoreMoney) + "元");
                } else {
                    iv_if_use_sore.setBackgroundResource(R.drawable.btn_c_close);
                    isUseSore = false;
                    addorderIsInvoice.isScorePay = "0";
                    tv_total_price.setText("实时付款:" + changeDouble(total) + "元");
                }
            }
        });
        el_orderforgood.addHeaderView(soreView);
    }

    private void initPwd(){
        View pwdView = inflater.inflate(R.layout.layout_order_pwd,null);
//        pwdView.findViewById(R.id.llUserBalance);
        cbUserBalance = (CheckBox) pwdView.findViewById(R.id.cbUserBalance);
        txtUserMoney = (TextView) pwdView.findViewById(R.id.txtUserMoney);
        llPayPwd = (LinearLayout) pwdView.findViewById(R.id.llPayPwd);
        etPwd = (EditText) pwdView.findViewById(R.id.etPwd);
        txtChange = (TextView) pwdView.findViewById(R.id.txtChange);

        txtUserMoney.setText("使用余额：（可用余额：¥"+Const.user.userMoney+"）");

        cbUserBalance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    llPayPwd.setVisibility(View.VISIBLE);
                }else {
                    llPayPwd.setVisibility(View.GONE);
                }
            }
        });

        txtChange.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ChangePayPwdActivity.class);
                i.putExtra("from","OrderForGoodsFragment");
                startActivity(i);
            }
        });

        el_orderforgood.addHeaderView(pwdView);

    }
    // 默认的支付，自提，不开发票。
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        addorderIsInvoice.isSelf = "0";
        addorderIsInvoice.isInvoice = "0";
        addorderIsInvoice.isScorePay = "0";
        addorderIsInvoice.isyuePay = "";

    }

    @Override
    public void sureTodo() {
        // TODO Auto-generated method stub
        if (adressBean != null) {
            addorderIsInvoice();
            request(addorderIsInvoice);
        } else {
            Toast.makeText(getActivity(), "亲，还没收货地址额！", Toast.LENGTH_SHORT).show();
        }
    }

    //double 类型保留二位小数
    public double changeDouble(Double price) {
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        price = Double.parseDouble(numberFormat.format(price));
        return price;
    }
}
