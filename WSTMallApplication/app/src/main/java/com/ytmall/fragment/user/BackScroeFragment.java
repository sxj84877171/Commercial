package com.ytmall.fragment.user;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ytmall.R;
import com.ytmall.api.user.BackScroe;
import com.ytmall.application.Const;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;

import org.json.JSONObject;

/**
 * Created by lee on 2017/2/11.
 */
@FragmentView(id = R.layout.fragment_back_scroe)
public class BackScroeFragment extends BaseFragment {
    @InjectView(id = R.id.etId)
    EditText etId;
    private BackScroe param = new BackScroe();

    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(param.getA())){
            try {
                JSONObject js = new JSONObject(data);
                Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_SHORT).show();
                Const.user.user_link_account = etId.getText().toString();
                leftClick();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void flagFailed(String url) {
        super.flagFailed(url);
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void bindDataForUIElement() {
        tWidget.setRightBtnText("确定");
        tWidget.getRightButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etId.getText().length() > 0){
                    updateAccount();
                }else {
                    Toast.makeText(getActivity(),"返回ID不能为空",Toast.LENGTH_SHORT).show();
                }

            }
        });



    }



    private void updateAccount(){
        param.tokenId = Const.cache.getTokenId();
        param.user_link_account = etId.getText().toString();
        request(param);

    }
}
