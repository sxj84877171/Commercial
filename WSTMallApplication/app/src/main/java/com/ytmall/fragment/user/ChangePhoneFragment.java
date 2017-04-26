package com.ytmall.fragment.user;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ytmall.R;
import com.ytmall.api.user.UpdatePhone;
import com.ytmall.application.Const;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;

/**
 * Created by lee on 2017/2/11.
 */
@FragmentView(id = R.layout.fragment_change_phone)
public class ChangePhoneFragment extends BaseFragment {
    private UpdatePhone param = new UpdatePhone();

    @InjectView(id = R.id.etPhone)
    EditText etPhone;
    @Override
    protected void bindEvent() {

    }

    @Override
    public void bindDataForUIElement() {
//        tWidget.setRightBtnText("确定");
        tWidget.getRightButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPhone.getText().length() > 0 && !etPhone.getText().toString().equals("")){
                    updatePhone();
                }else {
                    Toast.makeText(getActivity(),"请输入手机号",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void requestSuccess(String url, String data) {
        Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_SHORT).show();
        leftClick();

    }

    private void updatePhone(){
        param.tokenId = Const.cache.getTokenId();
        param.userPhone = etPhone.getText().toString();
        request(param);

    }
}
