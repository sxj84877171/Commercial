package com.ytmall.fragment.shop;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ytmall.R;
import com.ytmall.activity.shop.ShopIncomeActivity;
import com.ytmall.activity.shop.ShopManageActivity;
import com.ytmall.activity.shop.ShopOrderManageActivity;
import com.ytmall.bean.AbstractParam;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;

/**
 * Created by lee on 16/12/8.
 */
@FragmentView (id = R.layout.fragment_my_shop)
public class MyShopFragment extends BaseFragment implements View.OnClickListener{
    private Activity mContext;
    @InjectView (id = R.id.llIncome)
    private LinearLayout llIncome;
    @InjectView(id = R.id.txtShopIcome)
    private TextView txtShopIcome;
    @InjectView (id = R.id.llOrder)
    private LinearLayout llOrder;
    @InjectView(id = R.id.llShopManage)
    private LinearLayout llShopManage;

    @Override
    protected void requestSuccess(String url, String data) {

        super.requestSuccess(url, data);
    }

    @Override
    protected void flagFailed(String url) {
        super.flagFailed(url);
    }

    @Override
    public void bindDataForUIElement() {
        mContext = getActivity();
        tWidget.setCenterViewText("我的店铺");
        llIncome.setOnClickListener(this);
        llOrder.setOnClickListener(this);
        llShopManage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //店铺收入
            case R.id.llIncome:
                Intent incomeIntent = new Intent(getActivity(), ShopIncomeActivity.class);
                startActivity(incomeIntent);
                break;
            //订单管理
            case R.id.llOrder:
                Intent orderIntent = new Intent(getActivity(), ShopOrderManageActivity.class);
                startActivity(orderIntent);
                break;
            //店铺管理
            case R.id.llShopManage:
                Intent manageIntent = new Intent(getActivity(), ShopManageActivity.class);
                startActivity(manageIntent);
                break;
        }
    }

    @Override
    protected void bindEvent() {

    }

}
