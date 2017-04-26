package com.ytmall.activity.money;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.api.advertisement.GetAds;
import com.ytmall.api.brands.GetBrands;
import com.ytmall.application.Const;
import com.ytmall.internet.APICallBack;
import com.ytmall.internet.APIResult;
import com.ytmall.internet.LocalAPI;
import com.ytmall.model.BrandsReturn;
import com.ytmall.sys.BaseAct;
import com.ytmall.util.StrUtil;
import com.ytmall.widget.CustomPop;

/**
 * Created by lee on 2016/12/27.
 */

public class MoneyManageActivity extends BaseAct implements View.OnClickListener{
    private ScrollView scrollMoney;
    private CustomPop pop;
    private Activity act;
    private Button btnTakeMoney;
    private TextView txtDistributMoney,txtLockMoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_money_manage);
        act = this;
        initUI();
    }
    private void initUI(){
        titleBar.setTxtTitle("资金管理");
        TextView txtRight = titleBar.setRight("记录");
        txtRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(act,TakeMoneyRecordActivity.class);
                i.putExtra("from","MoneyManageActivity");
                startActivity(i);

            }
        });


        LinearLayout llTakeCash = (LinearLayout) findViewById(R.id.llTakeCash);
        scrollMoney = (ScrollView) findViewById(R.id.scrollMoney);
        TextView txtUseMoney = (TextView) findViewById(R.id.txtUseMoney);
        txtLockMoney = (TextView) findViewById(R.id.txtLockMoney);
        txtDistributMoney= (TextView) findViewById(R.id.txtDistributMoney);
        txtUseMoney.setText("可用金额：¥"+Const.user.userMoney);
        txtLockMoney.setText("冻结金额：¥"+Const.user.lockMoney);
        txtDistributMoney.setText("堂宝：¥"+Const.user.distributMoney);
        llTakeCash.setOnClickListener(this);

        btnTakeMoney = (Button) findViewById(R.id.btnTakeMoney);
        btnTakeMoney.setOnClickListener(this);

        if (StrUtil.str2double(Const.user.distributMoney) > 0){
            btnTakeMoney.setBackgroundResource(R.drawable.btn_login_bg);
        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llTakeCash:
                View popView = LayoutInflater.from(this).inflate(R.layout.pop_to_set_paypwd,null);
                LinearLayout popSetPwd = (LinearLayout) popView.findViewById(R.id.popSetPwd);
                pop = new CustomPop(this,R.layout.pop_to_set_paypwd,popSetPwd,0);
                TextView btnToSet = (TextView) pop.getContentView().findViewById(R.id.btnToSet);
                btnToSet.setOnClickListener(this);
                ImageView imgExit = (ImageView) pop.getContentView().findViewById(R.id.imgSetPwdExit);
                imgExit.setOnClickListener(this);
                pop.showAtLocation(scrollMoney, Gravity.CENTER,0,0);

                break;
            case R.id.btnToSet:
                Intent i = new Intent(this,SetPayPwdActivity.class);
                startActivity(i);
                pop.dismiss();

                break;
            case R.id.imgSetPwdExit:
                pop.dismiss();
                break;
            case R.id.btnTakeMoney:
                Intent iTakeMoney = new Intent(this,TakeMoneyActivity.class);
                startActivity(iTakeMoney);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        txtDistributMoney.setText("堂宝：¥"+Const.user.distributMoney);
        txtLockMoney.setText("冻结金额：¥"+Const.user.lockMoney);
    }
}
