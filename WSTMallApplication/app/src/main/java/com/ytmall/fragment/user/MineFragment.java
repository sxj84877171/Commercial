package com.ytmall.fragment.user;

import org.json.JSONException;
import org.json.JSONObject;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jakewharton.rxbinding.view.RxView;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.activity.MainActivity;
import com.ytmall.activity.about.AboutActivity;
import com.ytmall.activity.bank.BankActivity;
import com.ytmall.activity.code.CodeResultActivity;
import com.ytmall.activity.code.GetMoneyCodeActivity;
import com.ytmall.activity.favorite.FavoriteActivity;
import com.ytmall.activity.message.MessageActivity;
import com.ytmall.activity.money.MoneyManageActivity;
import com.ytmall.activity.money.ShopMoneyManageActivity;
import com.ytmall.activity.order.OrderActivity;
import com.ytmall.activity.order.complain.GetOrderComplainListActivity;
import com.ytmall.activity.recharge.CardRechargeActivity;
import com.ytmall.activity.recharge.RechargeActivity;
import com.ytmall.activity.share.ShareActivity;
import com.ytmall.activity.shop.MyShopActivity;
import com.ytmall.activity.shop.ShopOrderManageActivity;
import com.ytmall.activity.silver.SilverActivity;
import com.ytmall.activity.user.MineActivity;
import com.ytmall.activity.user.UpdateUserActivity;
import com.ytmall.api.login.GetUserInfo;
import com.ytmall.api.message.Message;
import com.ytmall.api.order.CheckUseScore;
import com.ytmall.api.order.GetOrdersStatus;
import com.ytmall.api.version.VersionCheckParam;
import com.ytmall.api.version.VersionCheckReturn;
import com.ytmall.application.Const;
import com.ytmall.bean.User;
import com.ytmall.bean.UserSoreBean;
import com.ytmall.bean.YinToJin;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.fragment.login.LoginFragment;
import com.ytmall.fragment.order.GetOrderComplainListFragment;
import com.ytmall.fragment.order.OrderManagement;
import com.ytmall.fragment.shoppingCart.ShoppingCartFragment;
import com.ytmall.internet.APICallBack;
import com.ytmall.internet.LocalAPI;
import com.ytmall.service.UpdataService;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.util.SharedPreferencesUtils;
import com.ytmall.util.UpdateManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import rx.functions.Action0;
import rx.functions.Action1;


@FragmentView(id = R.layout.fragment_personal_center)
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private String TAG = "MineFragment";
    @InjectView(id = R.id.ib_personimg)
    private ImageView ib_personimg;

    @InjectView(id = R.id.tv_person_points)
    private TextView tv_person_points;

    @InjectView(id = R.id.tv_person_name)
    private TextView tv_person_name;

    @InjectView(id = R.id.person_order)
    private RelativeLayout person_order;

    @InjectView(id = R.id.bank_charge)
    private RelativeLayout bank_charge;

    @InjectView(id = R.id.person_message)
    private RelativeLayout person_message;

    @InjectView(id = R.id.person_complain)
    private RelativeLayout person_complain;

    @InjectView(id = R.id.person_info)
    private RelativeLayout person_info;

    @InjectView(id = R.id.person_shippingaddress)
    private RelativeLayout person_shippingaddress;

    @InjectView(id = R.id.person_safty)
    private RelativeLayout person_safty;

    @InjectView(id = R.id.rl_favorite)
    private RelativeLayout rl_favorite;
    @InjectView(id = R.id.rl_update)
    private RelativeLayout rl_update;
    //充值
    @InjectView(id = R.id.rl_recharge)
    private TextView rl_recharge;

    @InjectView(id = R.id.rl_bank_charge)
    private TextView rl_bank_charge;

    @InjectView(id = R.id.view1)
    private View view1;
    //店铺管理
    @InjectView(id = R.id.rl_shop_manage)
    private RelativeLayout rl_shop_manage;

    @InjectView(id = R.id.rl_shop_take_money)
    private RelativeLayout rl_shop_take_money;

    //订单显示
    @InjectView(id = R.id.ll_way_pay)
    private LinearLayout ll_way_pay;
    //资金管理
    @InjectView(id = R.id.money_manage)
    private RelativeLayout money_manage;

    @InjectView(id = R.id.ll_way_accept)
    private LinearLayout ll_way_accept;

    @InjectView(id = R.id.ll_way_receive)
    private LinearLayout ll_way_receive;

    @InjectView(id = R.id.ll_way_eva)
    private LinearLayout ll_way_eva;

    @InjectView(id = R.id.ll_refuse)
    private LinearLayout ll_refuse;

    @InjectView(id = R.id.tv_way_pay)
    private TextView tv_way_pay;

    @InjectView(id = R.id.tv_way_accept)
    private TextView tv_way_accept;

    @InjectView(id = R.id.tv_way_receive)
    private TextView tv_way_receive;

    @InjectView(id = R.id.tv_way_eva)
    private TextView tv_way_eva;

    @InjectView(id = R.id.tv_message_count)
    private TextView tv_message_count;
    //经销商升级按钮
    @InjectView(id = R.id.tv_personcount)
    private TextView tv_personcount;

    //我的金额
    @InjectView(id = R.id.txtUserMoney)
    private TextView txtUserMoney;
    //冻结金额
    @InjectView(id = R.id.txtLockMoney)
    private TextView txtLockMoney;
    //店铺销售金额
    @InjectView(id = R.id.txtShopMoney)
    private TextView txtShopMoney;
    //店铺冻结金额
    @InjectView(id = R.id.txtShopLockMoney)
    private TextView txtShopLockMoney;
    //是否是经销商
    @InjectView(id = R.id.llShopMoney)
    private LinearLayout llShopMoney;
    //付款二维码
    @InjectView(id = R.id.get_money_code)
    RelativeLayout get_money_code;
    //分享赚钱
    @InjectView(id = R.id.rl_share)
    TextView rl_share;
    @InjectView(id = R.id.rl_about)
    RelativeLayout rl_about;
    @InjectView(id = R.id.rl_card_recharge)
    TextView rl_card_recharge;

    @InjectView(id = R.id.glod_bank_info_1)
    private TextView glod_bank_info_1;

    @InjectView(id = R.id.glod_bank_info_3)
    private TextView glod_bank_info_3;
    @InjectView(id = R.id.silver_bank_info_2)
    private TextView silver_bank_info_2;
    /**
     * 商城消息数量
     */
    private String messageCount;
    private GetOrdersStatus getOrdersStatus = new GetOrdersStatus();
    private Message messageApi = new Message();
    private VersionCheckParam versionparam = new VersionCheckParam();
    private YinToJin yinToJin = new YinToJin();
    //积分 API
    private GetUserInfo getUserInfo = new GetUserInfo();
//    private RxPermissions rxPermissions;

    // 要申请的权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private AlertDialog dialog;

    protected void requestSuccess(String url, String data) {
        if (url.contains(getOrdersStatus.getA())) {
            try {
                JSONObject jsonreobj = new JSONObject(data);
                JSONObject js = jsonreobj.getJSONObject("data");
                tv_way_pay.setText(js.getString("-2"));
                tv_way_accept.setText(js.getString("0"));
                tv_way_receive.setText(js.getString("3"));
                tv_way_eva.setText(js.getString("4"));
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                tv_message_count.setText("");
                getMessage();
            }
        } else if (url.contains(messageApi.getA())) {
            try {
                JSONObject jsonreobjs = new JSONObject(data);
                messageCount = jsonreobjs.getString("data");
                tv_message_count.setText(messageCount + "");
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                GetUserInfo();
            }
        } else if (url.contains(getUserInfo.getA())) {
            JSONObject jsonobj;
            try {
                jsonobj = new JSONObject(data);
                Const.user = gson.fromJson(jsonobj.get("data").toString(), User.class);
                MineActivity.autoToMine = true;
                Const.isLogin = true;
                if (Const.user.is_super_user == 0) {
                    tv_person_points.setText("普通会员");
                    tv_personcount.setVisibility(View.VISIBLE);
                } else {
                    tv_person_points.setText("经销商");
                    tv_personcount.setVisibility(View.GONE);
                }
//                tv_person_points.setText(Const.user.userScore);
                txtUserMoney.setText("我的金额：" + Const.user.userMoney);
                txtLockMoney.setText("冻结金额：" + Const.user.lockMoney);
                if (Const.user.hasShop == 1) {
                    llShopMoney.setVisibility(View.VISIBLE);
                    txtShopMoney.setText("店铺销售金额：" + Const.user.shopMoney);
                    txtShopLockMoney.setText("店铺冻结金额：" + Const.user.shopLockMoney);
                    txtShopLockMoney.setVisibility(View.GONE);

                    get_money_code.setVisibility(View.VISIBLE);

                    rl_shop_manage.setVisibility(View.VISIBLE);
                    rl_shop_take_money.setVisibility(View.VISIBLE);
                } else {
                    llShopMoney.setVisibility(View.GONE);
                    get_money_code.setVisibility(View.GONE);
                    rl_shop_manage.setVisibility(View.GONE);
                    rl_shop_take_money.setVisibility(View.GONE);
                }
                if (Const.user.jinMoney != null) {
                    glod_bank_info_3.setText("" + Const.user.jinMoney);
                }
                if (Const.user.yinMoney != null) {
                    glod_bank_info_1.setText("" + Const.user.yinMoney);
                }
//                yinToJin.tokenId = Const.cache.getTokenId();
                request(yinToJin);

            } catch (JSONException e) {
            } finally {

            }
        } else if (url.contains(versionparam.getA())) {
            try {
                JSONObject jso = new JSONObject(data);
                VersionCheckReturn result = gson.fromJson(jso.get("data").toString(), VersionCheckReturn.class);
                int versionCode = getVersionCode(getActivity());
//                Toast.makeText(getActivity(),"线上"+result.app_version+"，"+"本地"+versionCode,Toast.LENGTH_SHORT).show();
                if (result.app_version > versionCode) {
                    UpdateManager manager = new UpdateManager(getActivity());
                    manager.checkUpdate(result.download_url);
//                    upData(result.download_url);
                } else {
                    Toast.makeText(getActivity(), "已经是最新版本", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (url.contains(yinToJin.getA())) {
            try {
                JSONObject jso = new JSONObject(data);
                silver_bank_info_2.setText("上一天单元转换堂宝数 | " + jso.get("data").toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    private int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo("com.ytmall", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    @Override
    protected void flagFailed(String url) {
        super.flagFailed(url);
        if (url.contains(messageApi.getA())) {
            GetUserInfo();
        } else if (url.contains(getOrdersStatus.getA())) {
            getMessage();
        }
    }

    private void GetUserInfo() {
        if (Const.cache.getTokenId() != null) {
            getUserInfo.tokenId = Const.cache.getTokenId();
            requestNoDialog(getUserInfo);
        }

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent intent;
        switch (v.getId()) {
            case R.id.person_order:
                Intent orderintent = new Intent(getActivity(), OrderActivity.class);
                startActivity(orderintent);
                break;
            case R.id.bank_charge:
                Intent bankintent = new Intent(getActivity(), BankActivity.class);
                startActivity(bankintent);
                break;
            case R.id.person_message:
                Intent messageIntent = new Intent(getActivity(), MessageActivity.class);
                startActivity(messageIntent);
                break;
            case R.id.person_complain:
                Intent complainIntent = new Intent(getActivity(), GetOrderComplainListActivity.class);
                startActivity(complainIntent);
                break;
            case R.id.person_info:
                MainActivity.mHost.getTabWidget().setVisibility(View.GONE);
                replaceFragment(new EditUserInfoFragment(), true);
                break;
            case R.id.person_shippingaddress:
                MainActivity.mHost.getTabWidget().setVisibility(View.GONE);
                replaceFragment(new ShippingAdressFragment(1), true);
                break;
            case R.id.person_safty:
                MainActivity.mHost.getTabWidget().setVisibility(View.GONE);
                replaceFragment(new ChangePwdsFragment(), true);
//                replaceFragment(new AccountSecurityFragment(), true);
                break;
            case R.id.ll_way_pay:
                Intent payIntent = new Intent(getActivity(), OrderActivity.class);
                payIntent.putExtra("orderType", 1);
                startActivity(payIntent);
                break;
            //充值
            case R.id.rl_recharge:
                Intent rechargeIntent = new Intent(getActivity(), RechargeActivity.class);
                startActivity(rechargeIntent);
                break;
            case R.id.rl_bank_charge:
                Intent rlBankIntent = new Intent(getActivity(), SilverActivity.class);
                startActivity(rlBankIntent);
                break;
            case R.id.ll_way_accept:
                Intent acIntent = new Intent(getActivity(), OrderActivity.class);
                acIntent.putExtra("orderType", 2);
                startActivity(acIntent);
                break;
            case R.id.ll_way_receive:
                Intent reIntent = new Intent(getActivity(), OrderActivity.class);
                reIntent.putExtra("orderType", 3);
                startActivity(reIntent);
                break;
            case R.id.ll_way_eva:
                Intent evaIntent = new Intent(getActivity(), OrderActivity.class);
                evaIntent.putExtra("orderType", 4);
                startActivity(evaIntent);
                break;
            case R.id.ll_refuse:
                Intent refluseIntent = new Intent(getActivity(), OrderActivity.class);
                refluseIntent.putExtra("orderType", 6);
                startActivity(refluseIntent);
                break;
            case R.id.rl_favorite:
                Intent favoriteIntent = new Intent(getActivity(), FavoriteActivity.class);
                startActivity(favoriteIntent);
                break;
            //店铺订单
            case R.id.rl_shop_manage:

                Intent shopIntent = new Intent(getActivity(), ShopOrderManageActivity.class);
//                Intent shopIntent=new Intent(getActivity(), MyShopActivity.class);
                startActivity(shopIntent);
                break;
            //资金管理
            case R.id.money_manage:
//                Intent moneyManage = new Intent(getActivity(), MyShopActivity.class);
                Intent moneyManage = new Intent(getActivity(), MoneyManageActivity.class);
                startActivity(moneyManage);
                break;
            case R.id.get_money_code:
//
                Intent codeManage = new Intent(getActivity(), GetMoneyCodeActivity.class);
                startActivity(codeManage);
                break;
            case R.id.rl_share:
                Intent shareIntent = new Intent(getActivity(), ShareActivity.class);
                startActivity(shareIntent);
                break;
            case R.id.rl_about:
                Intent aboutIntnet = new Intent(getActivity(), AboutActivity.class);
                startActivity(aboutIntnet);
                break;
            case R.id.rl_card_recharge:
                Intent cardItent = new Intent(getActivity(), CardRechargeActivity.class);
                startActivity(cardItent);
                break;
            case R.id.tv_personcount:
                Intent shengjiIntent = new Intent(getActivity(), UpdateUserActivity.class);
                startActivity(shengjiIntent);
                break;
            case R.id.rl_shop_take_money:
                Intent iShopTakeMoney = new Intent(getActivity(), ShopMoneyManageActivity.class);
                startActivity(iShopTakeMoney);
                break;
            case R.id.rl_update:
                boolean isWrite = MainActivity.getWritePermission();
                if (isWrite == true) {
                    getAppVersion();
                }
                break;
        }
    }

    private void getAppVersion() {
        versionparam.a = "getAppVersion";
//        versionparam = new VersionCheckParam();
//        versionparam.tokenId = Const.cache.getTokenId();
        request(versionparam);


    }

    private void initOrderTips() {

        ll_way_pay.getBackground().setAlpha(100);
        ll_way_accept.getBackground().setAlpha(100);
        ll_way_receive.getBackground().setAlpha(100);
        ll_way_eva.getBackground().setAlpha(100);
        ll_refuse.getBackground().setAlpha(100);

        ll_way_pay.setOnClickListener(this);
        ll_way_accept.setOnClickListener(this);
        ll_way_receive.setOnClickListener(this);
        ll_way_eva.setOnClickListener(this);
        ll_refuse.setOnClickListener(this);
        person_complain.setOnClickListener(this);
        rl_favorite.setOnClickListener(this);

        rl_recharge.setOnClickListener(this);
        rl_bank_charge.setOnClickListener(this);
        rl_card_recharge.setOnClickListener(this);
        tv_personcount.setOnClickListener(this);

        rl_shop_take_money.setOnClickListener(this);
//        rl_shop_take_money.setVisibility(View.GONE);

    }

    private void confirLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("确定退出登录？").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                SharedPreferencesUtils.saveValue(getActivity(), "cartNum", String.valueOf(0));
                ShoppingCartFragment.shoppinglist.clear();
                Const.cache.clearSearchList();
                ((BaseActivity) getActivity()).reLogin();
                replaceFragment(new LoginFragment(LoginFragment.fromMainActivity), false);
                MainActivity.mainActivity.refreshBuyNum(0);

                Const.cache.clearInfo();

            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        // Create the AlertDialog object and return it
        builder.create().show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Const.isLogin) {
            MainActivity.mHost.getTabWidget().setVisibility(View.VISIBLE);
            requestNoDialog(getOrdersStatus);
            if (Const.user.is_super_user == 0) {
                tv_person_points.setText("普通会员");
                tv_personcount.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
            } else {
                tv_person_points.setText("经销商");
                tv_personcount.setVisibility(View.GONE);
                view1.setVisibility(View.GONE);
            }
        } else {
            replaceFragment(new LoginFragment(), false);
        }
//        RxPermissions rxPermissions = new RxPermissions(getActivity());
//        rxPermissions.setLogging(true);
//        RxView.clicks(rl_update)
//                // Ask for permissions when button is clicked
//                .compose(rxPermissions.ensureEach(Manifest.permission.WRITE_EXTERNAL_STORAGE))
//                .subscribe(new Action1<Permission>() {
//                               @Override
//                               public void call(Permission permission) {
//                                   Log.i(TAG, "Permission result " + permission);
//                                   if (permission.granted) {
//                                       getAppVersion();
//                                   } else if (permission.shouldShowRequestPermissionRationale) {
//                                       // Denied permission without ask never again
//                                       Toast.makeText(getActivity(),
//                                               "Denied permission without ask never again",
//                                               Toast.LENGTH_SHORT).show();
//                                   } else {
//                                       // Denied permission with ask never again
//                                       // Need to go to the settings
//                                       Toast.makeText(getActivity(),
//                                               "Permission denied, can't enable the camera",
//                                               Toast.LENGTH_SHORT).show();
//                                   }
//                               }
//                           },
//                        new Action1<Throwable>() {
//                            @Override
//                            public void call(Throwable t) {
//                                Log.e(TAG, "onError", t);
//                            }
//                        },
//                        new Action0() {
//                            @Override
//                            public void call() {
//                                Log.i(TAG, "OnComplete");
//                            }
//                        });

    }

    @Override
    public void bindDataForUIElement() {
        // TODO Auto-generated method stub
        tWidget.setCenterViewText("会员中心");
        tWidget.setRightBtnText("注销");
        tWidget.setTitleAlpha(0);
        tWidget.right.setVisibility(View.VISIBLE);
        tv_person_name.setText(Const.user.getName());//loginName
        tv_person_points.setText(Const.user.userScore);
        if (Const.user.userPhoto != null && !Const.user.userPhoto.equals("")) {
            loadOnRoundImage(Const.BASE_URL + Const.user.userPhoto, ib_personimg);
        }
//        requestReadStorageRuntimePermission();


        getOrdersStatus.tokenId = Const.cache.getTokenId();
        initOrderTips();

    }

    @Override
    protected void bindEvent() {
        // TODO Auto-generated method stub
        person_order.setOnClickListener(this);
        bank_charge.setOnClickListener(this);
        person_message.setOnClickListener(this);
        person_info.setOnClickListener(this);
        person_shippingaddress.setOnClickListener(this);
        person_safty.setOnClickListener(this);

        rl_shop_manage.setOnClickListener(this);
        money_manage.setOnClickListener(this);

        get_money_code.setOnClickListener(this);

        rl_share.setOnClickListener(this);
        rl_about.setOnClickListener(this);
        rl_update.setOnClickListener(this);


    }

    @Override
    public void rightClick() {
        confirLogout();
    }

    /**
     * 获取商城消息
     */
    private void getMessage() {
        messageApi.tokenId = Const.cache.getTokenId();
        requestNoDialog(messageApi);
    }

    private void getPremission() {

    }


}
