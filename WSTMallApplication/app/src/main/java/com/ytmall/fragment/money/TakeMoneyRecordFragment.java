package com.ytmall.fragment.money;

import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ytmall.R;
import com.ytmall.adapter.RechargeRecordAdapter;
import com.ytmall.adapter.TakeCashRecordAdapter;
import com.ytmall.api.recharge.RechargeList;
import com.ytmall.api.recharge.TakeCash;
import com.ytmall.application.Const;
import com.ytmall.bean.RechargeRecordBean;
import com.ytmall.bean.TakeCashListBean;
import com.ytmall.bean.TakeCashRecordBean;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2017/1/10.
 */
@FragmentView(id = R.layout.activity_take_money_record)
public class TakeMoneyRecordFragment extends BaseFragment {
    @InjectView(id = R.id.listMoney)
    PullToRefreshListView pullMoney;

    private String from ;
    private int page = 1;
    private TakeCash cashParam;
    private RechargeList rechargeParam;
    private List<TakeCashRecordBean> cashList = new ArrayList<>();
    private List<RechargeRecordBean> rechargeList = new ArrayList<>();
    private TakeCashRecordAdapter cashAdapter;
    private RechargeRecordAdapter rechargeAdapter;
    public TakeMoneyRecordFragment(String from){
        this.from = from;

    }
    @Override
    protected void requestSuccess(String url, String data) {
        if (from.equals("MoneyManageActivity")){
            if (url.contains(cashParam.getA())){
                Log.d("record cash:",data);
                try {
                    JSONObject jso = new JSONObject(data);
                    JSONArray jar = jso.getJSONArray("data");
                    Type type = new TypeToken<ArrayList<TakeCashRecordBean>>(){}.getType();
                    List<TakeCashRecordBean> cashListBeen = new Gson().fromJson(jar.toString(),type);
                    cashList.addAll(cashListBeen);
                    cashAdapter.notifyDataSetChanged();
                    pullMoney.onRefreshComplete();



                }catch (Exception ex){

                }

            }
        }else {
            if (url.contains(rechargeParam.getA())){
                Log.d("record recharge:",data);
                try {
                    JSONObject jso = new JSONObject(data);
                    JSONArray jar = jso.getJSONArray("data");
                    Type type = new TypeToken<ArrayList<RechargeRecordBean>>(){}.getType();
                    List<RechargeRecordBean> rechargeListBeen = new Gson().fromJson(jar.toString(),type);
                    rechargeList.addAll(rechargeListBeen);
                    rechargeAdapter.notifyDataSetChanged();
                    pullMoney.onRefreshComplete();



                }catch (Exception ex){

                }

            }

        }
    }

    @Override
    protected void flagFailed(String url) {
        Toast.makeText(getActivity(),"网络异常",Toast.LENGTH_SHORT).show();
        pullMoney.onRefreshComplete();
    }

    @Override
    public void bindDataForUIElement() {
        ListView listMoney = pullMoney.getRefreshableView();
        pullMoney.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getCashList();
            }
        });

        if (from.equals("MoneyManageActivity")){
            getCashList();
            tWidget.setCenterViewText("提现记录");
            cashAdapter = new TakeCashRecordAdapter(getActivity(),cashList);
            listMoney.setAdapter(cashAdapter);
        }else if (from.equals("recharge")){
            getRechargeList();
            tWidget.setCenterViewText("充值记录");
            rechargeAdapter = new RechargeRecordAdapter(getActivity(),rechargeList);
            listMoney.setAdapter(rechargeAdapter);
        }

    }

    @Override
    protected void bindEvent() {

    }

    /**
     * 提现记录
     */
    private void getCashList(){
        cashParam = new TakeCash();
        cashParam.currPage = page;
        if (page == 1){
            cashList.clear();
        }
        cashParam.tokenId = Const.cache.getTokenId();
        request(cashParam);

    }
    private void getRechargeList(){
        rechargeParam = new RechargeList();
        rechargeParam.currPage = page;
        if (page == 1){
            rechargeList.clear();

        }
        rechargeParam.tokenId = Const.cache.getTokenId();
        request(rechargeParam);

    }
}
