package com.ytmall.fragment.order;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ytmall.R;
import com.ytmall.activity.order.complain.GetOrderComplainDetailActivity;
import com.ytmall.adapter.ComplainListAdapter;
import com.ytmall.api.order.GetComplainsForUser;
import com.ytmall.application.Const;
import com.ytmall.bean.MessageBean;
import com.ytmall.bean.OrderComplainList;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pzl on 2016/4/10.
 */
@FragmentView(id = R.layout.fragment_get_complain_list)
public class GetOrderComplainListFragment extends BaseFragment {
    @InjectView(id = R.id.lv_complain)
    private PullToRefreshListView lv_complain;
    private ComplainListAdapter complainListAdapter;
    private List<OrderComplainList> orderComplainLists=new ArrayList<>();
    private GetComplainsForUser getComplainsForUser=new GetComplainsForUser();
    protected void requestSuccess(String url, String data) {
        if (url.contains(getComplainsForUser.getA())) {
            try {
                JSONObject jsonreobj = new JSONObject(data);
                JSONArray js = jsonreobj.getJSONArray("data");
                Type listType = new TypeToken<ArrayList<OrderComplainList>>(){}.getType();
                List<OrderComplainList> orderComplainListsTemp = new Gson().fromJson(js.toString(), listType);
                if(orderComplainListsTemp!=null&&orderComplainListsTemp.size()>0) {
                    orderComplainLists.addAll(orderComplainListsTemp);
                }
                complainListAdapter.notifyDataSetChanged();
                lv_complain.onRefreshComplete();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {

            }
        }
    }
    @Override
    public void bindDataForUIElement() {
        lv_complain.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        complainListAdapter=new ComplainListAdapter(orderComplainLists,getActivity());
        lv_complain.setAdapter(complainListAdapter);
        getComplainsForUser();
    }

    @Override
    protected void bindEvent() {
        lv_complain.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                orderComplainLists.clear();
                getComplainsForUser();
            }
        });
        lv_complain.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                getComplainsForUser.p++;
                requestNoDialog(getComplainsForUser);
            }
        });
        lv_complain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //replaceFragment(new GetOrderComplainDetailFragment(orderComplainLists.get(position).complainId),false);
                Intent intent=new Intent(getActivity(), GetOrderComplainDetailActivity.class);
                intent.putExtra("complainId",orderComplainLists.get(position-1).complainId);
                startActivity(intent);
            }
        });
    }
    private void getComplainsForUser(){
        getComplainsForUser.tokenId= Const.cache.getTokenId();
        getComplainsForUser.orderNo="";
        getComplainsForUser.p=1;
        requestNoDialog(getComplainsForUser);
    }
}
