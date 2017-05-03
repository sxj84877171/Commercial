package com.ytmall.fragment.user;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.ytmall.R;
import com.ytmall.adapter.AccountFromAdapter;
import com.ytmall.api.user.AccountFrom;
import com.ytmall.api.user.UpdateSource;
import com.ytmall.application.Const;
import com.ytmall.bean.AccountFromBean;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.util.StrUtil;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2017/2/11.
 */
@FragmentView(id = R.layout.fragment_account_from)
public class AccountFromFragment extends BaseFragment {
    @InjectView(id = R.id.listAccount)
    ListView listAccount;

    private AccountFromAdapter adapter;
    private AccountFrom param = new AccountFrom();
    private List<AccountFromBean> list = new ArrayList<>();
    private UpdateSource updateParam = new UpdateSource();
    private int pos;


    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(param.getA())){
            try {
                JSONObject jso = new JSONObject(data);
                Type type = new TypeToken<ArrayList<AccountFromBean>>(){}.getType();
                List<AccountFromBean> result = gson.fromJson(jso.getJSONArray("data").toString(),type);
                list.clear();
                list.addAll(result);
                adapter.notifyDataSetChanged();


            }catch (Exception e){
                e.printStackTrace();
            }
        }else if (url.contains(updateParam.getA())){
            Const.user.user_source = pos;
            Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_SHORT).show();

            leftClick();

        }
    }

    @Override
    protected void flagFailed(String url) {
        super.flagFailed(url);
    }

    @Override
    public void bindDataForUIElement() {
        //1.云莲商会
        adapter = new AccountFromAdapter(getActivity(),list);
        listAccount.setAdapter(adapter);
        getAccountFrom();

        listAccount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list.get(position).isSelected = true;
                adapter.notifyDataSetChanged();
                updateParam.tokenId = Const.cache.getTokenId();
                updateParam.user_source = String.valueOf(position+1);
                pos = position+1;
                request(updateParam);
            }
        });


    }
    private void getAccountFrom(){
        param.tokenId = Const.cache.getTokenId();
        request(param);

    }


    @Override
    protected void bindEvent() {

    }
}
