package com.ytmall.activity.money;

import android.os.Bundle;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.api.recharge.TakeCash;
import com.ytmall.application.Const;
import com.ytmall.fragment.money.TakeMoneyRecordFragment;
import com.ytmall.internet.LocalAPI;
import com.ytmall.sys.BaseAct;

/**
 * Created by lee on 2017/1/10.
 */

public class TakeMoneyRecordActivity extends BaseActivity{
    private PullToRefreshListView pullMoney;
    private int page = 1;
    private String from = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        from = getIntent().getStringExtra("from");
        replaceFragment(new TakeMoneyRecordFragment(from),false);

//        initUI();

    }
    private void initUI(){
        pullMoney = (PullToRefreshListView) findViewById(R.id.listMoney);
        ListView listMoney = pullMoney.getRefreshableView();
        pullMoney.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getList();
            }
        });

    }
    private void getList(){
        TakeCash param = new TakeCash();
        if (from.equals("recharge")){
            param.a = "queryRechargeList";
        }else {
            param.a = "queryCashDrawList";
        }
        param.currPage = page;
        param.tokenId = Const.cache.getTokenId();

    }
}
