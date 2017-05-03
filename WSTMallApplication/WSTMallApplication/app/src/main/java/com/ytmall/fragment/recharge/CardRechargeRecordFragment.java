package com.ytmall.fragment.recharge;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ytmall.R;
import com.ytmall.adapter.CardRechargeRecordAdapter;
import com.ytmall.api.recharge.QueryCardsList;
import com.ytmall.application.Const;
import com.ytmall.bean.QueryCardsListBean;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2017/1/20.
 */
@FragmentView(id = R.layout.activity_take_money_record)
public class CardRechargeRecordFragment extends BaseFragment {
    @InjectView(id = R.id.listMoney)
    PullToRefreshListView pullList;
    private ListView listView;
    private QueryCardsList param;
    private List<QueryCardsListBean> list = new ArrayList<>();
    private int page = 1;
    private CardRechargeRecordAdapter adapter;

    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(param.getA())){
            try {
                JSONObject jso = new JSONObject(data);
                Type type = new TypeToken<ArrayList<QueryCardsListBean>>(){}.getType();
                List<QueryCardsListBean> result = gson.fromJson(jso.get("data").toString(),type);
                list.addAll(result);
                adapter.notifyDataSetChanged();
                pullList.onRefreshComplete();

            }catch (Exception e){
                e.printStackTrace();

            }

        }else {

        }
    }

    @Override
    protected void flagFailed(String url) {
        super.flagFailed(url);
        pullList.onRefreshComplete();
    }

    @Override
    public void bindDataForUIElement() {
        tWidget.setCenterViewText("卡充值记录");
        listView = pullList.getRefreshableView();
        listView.setDividerHeight(15);
        pullList.setMode(PullToRefreshBase.Mode.BOTH);
        pullList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                getList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getList();

            }
        });
        adapter = new CardRechargeRecordAdapter(getActivity(),list);
        listView.setAdapter(adapter);

        getList();


    }
    private void getList(){
        param = new QueryCardsList();
        param.tokenId = Const.cache.getTokenId();
        param.currPage = page;
        if (page == 1){
            list.clear();
        }
        request(param);
    }

    @Override
    protected void bindEvent() {

    }
}
