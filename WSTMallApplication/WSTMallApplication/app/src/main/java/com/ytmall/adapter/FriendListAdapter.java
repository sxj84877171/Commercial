package com.ytmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.application.Const;
import com.ytmall.bean.RecommendUserBean;
import com.ytmall.bean.User;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.StrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2017/1/17.
 */

public class FriendListAdapter extends BaseAdapter {
    private Context ctxt;
    private List<RecommendUserBean> list = new ArrayList<>();
    public FriendListAdapter(Context ctxt,List<RecommendUserBean> list){
        this.ctxt = ctxt;
        this.list = list;

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        HoldItem item;
        RecommendUserBean model = list.get(position);
        if (view == null){
            view = LayoutInflater.from(ctxt).inflate(R.layout.item_list_friend,null);
            item = new HoldItem();
            item.txtName = (TextView) view.findViewById(R.id.txtName);
            item.txtDate = (TextView) view.findViewById(R.id.txtDate);

            view.setTag(item);
        }else {
            item = (HoldItem) view.getTag();
        }
        if (StrUtil.null2Str(model.userName).length() > 0){
            item.txtName.setText(model.userName);
        }else {
            item.txtName.setText(model.loginName);
        }
        item.txtDate.setText(model.createTime);
        return view;
    }
    private class HoldItem{
        private TextView txtName,txtDate;
    }
}
