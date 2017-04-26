package com.ytmall.fragment.share;

import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ytmall.R;
import com.ytmall.adapter.FriendListAdapter;
import com.ytmall.api.share.FriendList;
import com.ytmall.api.share.QueryRecommdCount;
import com.ytmall.application.Const;
import com.ytmall.bean.RecommendUserBean;
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
public class FriendListFragment extends BaseFragment {
    @InjectView(id = R.id.pullList)
    PullToRefreshListView pullList;
    @InjectView(id = R.id.txtUserNumber)
    TextView txtUserNumber;

    private int page = 1;
    private FriendList listParam;

    private ListView listView;
    private List<RecommendUserBean> friendList = new ArrayList<>();
    private FriendListAdapter adapter;

    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(listParam.getA())){
            try {
                JSONObject jso = new JSONObject(data);
                Type type = new TypeToken<ArrayList<RecommendUserBean>>(){}.getType();
                List<RecommendUserBean> list = gson.fromJson(jso.get("data").toString(),type);
                friendList.addAll(list);
                adapter.notifyDataSetChanged();
                txtUserNumber.setText("用户列表："+friendList.size()+"人");
                pullList.onRefreshComplete();



            }catch (Exception e){

            }

        }else {

        }
    }

    @Override
    protected void flagFailed(String url) {
        pullList.onRefreshComplete();
        super.flagFailed(url);
    }

    @Override
    public void bindDataForUIElement() {
        tWidget.setCenterViewText("用户列表");
        listView = pullList.getRefreshableView();
        pullList.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        pullList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getFriendList();
            }
        });
        getFriendList();
        adapter = new FriendListAdapter(getActivity(),friendList);
        listView.setAdapter(adapter);

    }
    private void getFriendList(){
        listParam = new FriendList();
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
