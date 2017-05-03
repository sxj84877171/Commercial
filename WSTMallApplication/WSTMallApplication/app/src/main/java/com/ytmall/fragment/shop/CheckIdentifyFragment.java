package com.ytmall.fragment.shop;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ytmall.R;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;

/**
 * Created by lee on 16/12/14.
 */
@FragmentView(id = R.layout.fragment_check_identity)
public class CheckIdentifyFragment extends BaseFragment implements View.OnClickListener{
    @InjectView(id = R.id.etName)
    EditText etName;
    @InjectView(id = R.id.etIdentity)
    EditText etIdentity;
    @InjectView(id = R.id.btnComfirm)
    Button btnComfirm;
    @Override
    protected void requestSuccess(String url, String data) {
        super.requestSuccess(url, data);
    }

    @Override
    protected void flagFailed(String url) {
        super.flagFailed(url);
    }

    @Override
    public void bindDataForUIElement() {
        tWidget.setCenterViewText("身份认证");

    }

    @Override
    protected void bindEvent() {
        btnComfirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnComfirm:
                String name = etName.getText().toString();
                String identity = etIdentity.getText().toString();
                if (!(name.length() > 0)){
                    Toast.makeText(getActivity(),"名字不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!(identity.length() > 0)){
                    Toast.makeText(getActivity(),"身份证不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
        }
    }
}
