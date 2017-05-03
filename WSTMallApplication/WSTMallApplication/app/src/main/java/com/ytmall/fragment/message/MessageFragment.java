package com.ytmall.fragment.message;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ytmall.R;
import com.ytmall.activity.message.MessageDetailActivity;
import com.ytmall.adapter.MessageAdapter;
import com.ytmall.api.message.MessageList;
import com.ytmall.application.Const;
import com.ytmall.bean.MessageBean;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.util.JSONTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/2.
 */
@FragmentView(id = R.layout.fragment_message)
public class MessageFragment extends BaseFragment {
    @InjectView(id = R.id.lv_message)
    private PullToRefreshListView lv_message;
    private MessageAdapter messageAdapter;
    /**消息请求静态类*/
    private MessageList messageList=new MessageList();
    /**消息请求结果*/
    private List<MessageBean> messageBeanList=new ArrayList<>();
    protected void requestSuccess(String url, String data) {
        if (url.contains(messageList.getA())) {
            try {
                JSONObject jsonreobj = new JSONObject(data);
                JSONArray js = jsonreobj.getJSONArray("data");
                Type listType = new TypeToken<ArrayList<MessageBean>>(){}.getType();
                List<MessageBean> messageBeanListTemp = new Gson().fromJson(js.toString(), listType);
                if(messageBeanListTemp!=null&&messageBeanListTemp.size()>0) {
                    messageBeanList.addAll(messageBeanListTemp);
                }
                messageAdapter.notifyDataSetChanged();
                lv_message.onRefreshComplete();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {

            }
        }
    }
    @Override
    public void bindDataForUIElement() {
       lv_message.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        messageAdapter=new MessageAdapter(messageBeanList,getActivity());
        lv_message.setAdapter(messageAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        messageBeanList.clear();
        messageAdapter.notifyDataSetChanged();
        getMessageList();
    }

    @Override
    protected void bindEvent() {
        lv_message.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                messageBeanList.clear();
                getMessageList();
            }
        });
        lv_message.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                messageList.p = messageList.p + 1;
                requestNoDialog(messageList);
            }
        });
        lv_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                messageBeanList.get(position-1).msgStatus="1";
                messageAdapter.notifyDataSetChanged();
               // replaceFragment(new MessageDetailFragment(messageBeanList.get(position-1).id),false);
                Intent intent=new Intent(getActivity(), MessageDetailActivity.class);
                intent.putExtra("id",messageBeanList.get(position-1).id);
                startActivity(intent);
            }
        });
    }
    private void getMessageList(){
        messageList.tokenId= Const.cache.getTokenId();
        messageList.p=1;
        requestNoDialog(messageList);
    }
}
