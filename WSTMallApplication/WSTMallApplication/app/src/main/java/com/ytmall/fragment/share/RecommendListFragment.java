package com.ytmall.fragment.share;

import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ytmall.R;
import com.ytmall.adapter.FriendListAdapter;
import com.ytmall.adapter.RecommendListAdapter;
import com.ytmall.api.share.FriendList;
import com.ytmall.api.share.RecommendList;
import com.ytmall.application.Const;
import com.ytmall.bean.RecommendMoneyBean;
import com.ytmall.bean.User;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2017/1/17.
 */
@FragmentView(id = R.layout.fragment_friend_list)
public class RecommendListFragment extends BaseFragment {
    @InjectView(id = R.id.pullList
    )
    PullToRefreshListView pullMoneys;
    @InjectView(id = R.id.txtUserNumber)
    TextView txtMoney;

    private int page = 1;
    private RecommendList listParam;

    private ListView listView;
    private List<RecommendMoneyBean> friendList = new ArrayList<>();
    private RecommendListAdapter adapter;
    private String money;

    public RecommendListFragment(String money){
        this.money = money;

    }

    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(listParam.getA())){
            try {
                JSONObject jso = new JSONObject(data);
                Type type = new TypeToken<ArrayList<RecommendMoneyBean>>(){}.getType();
                List<RecommendMoneyBean> list = gson.fromJson(jso.get("data").toString(),type);
                friendList.addAll(list);
                adapter.notifyDataSetChanged();
                pullMoneys.onRefreshComplete();




            }catch (Exception e){
                if (pullMoneys.isRefreshing()){
                    pullMoneys.onRefreshComplete();
                }

            }

        }else {
            if (pullMoneys.isRefreshing()){
                pullMoneys.onRefreshComplete();
            }

        }
    }

    @Override
    protected void flagFailed(String url) {
        pullMoneys.onRefreshComplete();
//        super.flagFailed(url);
    }

    @Override
    public void bindDataForUIElement() {
        tWidget.setCenterViewText("佣金信息");
        listView = pullMoneys.getRefreshableView();
        pullMoneys.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        pullMoneys.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getRecommendList();
            }
        });
        getRecommendList();
        adapter = new RecommendListAdapter(getActivity(),friendList);
        listView.setAdapter(adapter);
        txtMoney.setText("佣金总额：¥"+money);

    }
    private void getRecommendList(){
        listParam = new RecommendList();
        listParam.tokenId = Const.cache.getTokenId();
        listParam.currPage = page;
        if (page == 1){
            friendList.clear();
        }
        request(listParam);


    }

    @Override
    protected void bindEvent() {

    }
}
