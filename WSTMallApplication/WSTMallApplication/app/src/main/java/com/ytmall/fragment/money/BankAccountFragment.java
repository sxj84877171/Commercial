package com.ytmall.fragment.money;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ytmall.R;
import com.ytmall.activity.money.BankAccountActivity;
import com.ytmall.activity.money.EditBankAccountActivity;
import com.ytmall.adapter.BankAccounAdapter;
import com.ytmall.api.recharge.DelCashAcc;
import com.ytmall.api.recharge.GetCashConfList;
import com.ytmall.application.Const;
import com.ytmall.bean.GetCashConfBean;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2017/1/23.
 */
@FragmentView(id = R.layout.activity_take_money_record)
public class BankAccountFragment extends BaseFragment {
    @InjectView(id = R.id.listMoney)
    PullToRefreshListView listAccount;

    private BankAccounAdapter adapter;
    private GetCashConfList param = new GetCashConfList();
    private List<GetCashConfBean> list = new ArrayList<>();
    private String from;
    @Override
    protected void bindEvent() {

    }

    public BankAccountFragment (String from){
        this.from = from;
    }
    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(param.getA())){
            try {
                JSONObject jso = new JSONObject(data);
                Type type = new TypeToken<ArrayList<GetCashConfBean>>(){}.getType();
                List<GetCashConfBean> result = gson.fromJson(jso.getJSONArray("data").toString(),
                        type);
                list.clear();
                list.addAll(result);
                adapter.notifyDataSetChanged();
                listAccount.onRefreshComplete();

            }catch (Exception e){
                e.printStackTrace();
                listAccount.onRefreshComplete();
            }
        }
    }

    @Override
    protected void flagFailed(String url) {
        super.flagFailed(url);
        listAccount.onRefreshComplete();
    }

    @Override
    public void bindDataForUIElement() {
        ListView listView = listAccount.getRefreshableView();
        listView.setDividerHeight(20);
        listView.setSelector(R.drawable.item_bank_account_select);
        listAccount.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listAccount.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                getAccount();

            }
        });
        adapter = new BankAccounAdapter(getActivity(),list,from);
        listView.setAdapter(adapter);

        tWidget.setRightBtnText("添加");

        tWidget.getRightButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), EditBankAccountActivity.class);
                i.putExtra("id","");
                startActivity(i);
            }
        });

//        getAccount();


    }
    private void getAccount(){

        param.tokenId = Const.cache.getTokenId();
        request(param);

    }

    @Override
    public void onResume() {
        super.onResume();
        getAccount();
    }
}
