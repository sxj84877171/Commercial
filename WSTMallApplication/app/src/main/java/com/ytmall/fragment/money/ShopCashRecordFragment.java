package com.ytmall.fragment.money;

import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ytmall.R;
import com.ytmall.adapter.ShopCashRecordAdapter;
import com.ytmall.api.recharge.GetShopdrawsList;
import com.ytmall.application.Const;
import com.ytmall.bean.GetShopDrawlistBean;
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
public class ShopCashRecordFragment extends BaseFragment {
    @InjectView(id = R.id.listMoney)
    PullToRefreshListView pullList;

    private GetShopdrawsList param;
    private int page = 1;
    private ShopCashRecordAdapter adapter ;
    private List<GetShopDrawlistBean> list = new ArrayList<>();
    @Override
    protected void bindEvent() {

    }

    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(param.getA())){
            try {

                JSONObject jso = new JSONObject(data);
                Type type = new TypeToken<ArrayList<GetShopDrawlistBean>>(){}.getType();
                List<GetShopDrawlistBean> result = gson.fromJson(jso.getJSONArray("data").toString(),type);
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
        tWidget.setCenterViewText("商家提现记录");
        ListView listView = pullList.getRefreshableView();
        listView.setDividerHeight(25);
        pullList.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        pullList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getShopdrawsList();

            }
        });
        adapter = new ShopCashRecordAdapter(getActivity(),list);
        listView.setAdapter(adapter);

        getShopdrawsList();

    }
    private void getShopdrawsList(){
        param = new GetShopdrawsList();
        param.currPage = page;
        param.tokenId = Const.cache.getTokenId();
        if (page == 1){
            list.clear();
        }
        request(param);

    }
}
