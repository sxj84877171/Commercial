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
 * WSTMallApplication
 * 作者： Elvis
 * 时间： 2017/5/4
 * 版本：1.0.0.0
 * 邮编：410000
 * 程序功能：
 * 该类为
 */
@FragmentView(id = R.layout.fragment_back_scroe1)
public class RetBackScoreFragment extends BaseFragment {
    @InjectView(id = R.id.etId)
    EditText etId;
    private BackScroe param = new BackScroe();

    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(param.getA())){
            try {
                JSONObject js = new JSONObject(data);
                Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_SHORT).show();
                Const.user.jifenPhone = etId.getText().toString();
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
                    Toast.makeText(getActivity(),"返积分手机号码不能为空",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void updateAccount(){
        param.tokenId = Const.cache.getTokenId();
        param.jifenPhone = etId.getText().toString();
        param.a = "updatejifenPhone" ;
        request(param);

    }
}
