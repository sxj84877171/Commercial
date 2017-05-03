package com.ytmall.fragment.message;

import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ytmall.R;
import com.ytmall.adapter.MessageAdapter;
import com.ytmall.api.message.DelMessages;
import com.ytmall.api.message.GetMessageContent;
import com.ytmall.application.Const;
import com.ytmall.bean.MessageBean;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.util.JSONTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/4/4.
 */
@FragmentView(id = R.layout.fragment_message_detail)
public class MessageDetailFragment extends BaseFragment {
    @InjectView(id = R.id.tv_message_detail)
    private TextView tv_message_detail;
    private GetMessageContent getMessageContent=new GetMessageContent();
    private DelMessages delMessages=new DelMessages();
    private String id;//信息ID
    public MessageDetailFragment(String id){
        this.id=id;
    }
    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(getMessageContent.getA())) {
            try {
                JSONObject jsonreobj = new JSONObject(data);
                String messageContent=jsonreobj.getString("data");
                tv_message_detail.setText(messageContent);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {

            }
        }else if(url.contains(delMessages.getA())){
                getActivity().finish();
        }

    }

    @Override
    public void bindDataForUIElement() {
        tWidget.setRightBtnText("删除");
        getMessageContent();
    }
    @Override
    public void rightClick() {
        delMessages.tokenId=Const.cache.getTokenId();
        delMessages.ids=id;
        request(delMessages);
    }
    @Override
    protected void bindEvent() {

    }
    private void getMessageContent(){
        getMessageContent.tokenId= Const.cache.getTokenId();
        getMessageContent.id=id;
        request(getMessageContent);
    }
}
