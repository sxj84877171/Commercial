package com.ytmall.fragment.user;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ytmall.R;
import com.ytmall.activity.CountDownTimerButton;
import com.ytmall.api.user.UpdatePhone;
import com.ytmall.application.Const;
import com.ytmall.bean.AbstractParam;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;

/**
 * Created by lee on 2017/2/11.
 */
@FragmentView(id = R.layout.fragment_change_phone)
public class ChangePhoneFragment extends BaseFragment {

    class GetCode extends AbstractParam{
        public String a = "sendBindCode";
        @Override
        public String getA() {
            return a;
        }
    }

    private UpdatePhone param = new UpdatePhone();

    @InjectView(id = R.id.etPhone)
    EditText etPhone;

    @InjectView(id = R.id.code)
    EditText  code ;

    @InjectView(id = R.id.get_code)
    CountDownTimerButton get_code;

    @Override
    protected void bindEvent() {
        get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestNoDialog(new GetCode());
            }
        });
    }



    @Override
    public void bindDataForUIElement() {
//        tWidget.setRightBtnText("确定");
        if(TextUtils.isEmpty(Const.user.userPhone)){
            etPhone.setText("");
            etPhone.setEnabled(true);
            etPhone.setClickable(true);
            tWidget.getRightButton().setText("绑定");
        }else{
            etPhone.setText(Const.user.userPhone);
            etPhone.setEnabled(false);
            etPhone.setClickable(false);
            tWidget.getRightButton().setText("解绑");
        }
        tWidget.getRightButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etPhone.getText().toString();
                String c = code.getText().toString();
                if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(c)){
                    updatePhone(phone,c);
                }else {
                    Toast.makeText(getActivity(),"请输入完整",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void requestSuccess(String url, String data) {
        Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_SHORT).show();
        leftClick();

    }

    private void updatePhone(String phone , String c){
        param.tokenId = Const.cache.getTokenId();
        param.userPhone = phone;
        param.phoneCode = c;
        request(param);

    }

    @Override
    protected void flagFailed(String url) {
        super.flagFailed(url);
        get_code.stop();
    }
}
