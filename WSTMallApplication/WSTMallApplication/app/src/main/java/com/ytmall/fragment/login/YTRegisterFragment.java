package com.ytmall.fragment.login;

import android.app.Activity;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ytmall.R;
import com.ytmall.adapter.ChooseAccountAdapter;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.util.YTUtil;
import com.ytmall.widget.CustomPop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 16/12/10.
 */
@FragmentView(id = R.layout.fragment_yt_register)
public class YTRegisterFragment extends BaseFragment implements View.OnClickListener{
    @InjectView(id = R.id.text1)
    private TextView text1;
    @InjectView(id = R.id.text2)
    private TextView text2;
    @InjectView(id = R.id.text3)
    private TextView text3;
    @InjectView(id = R.id.text4)
    private TextView text4;
    @InjectView(id = R.id.txtFrom)
    private TextView txtFrom;
    @InjectView(id = R.id.ll1)
    private LinearLayout ll1;
    @InjectView(id = R.id.ll2)
    private LinearLayout ll2;
    @InjectView(id = R.id.ll3)
    private LinearLayout ll3;
    @InjectView(id = R.id.ll4)
    private LinearLayout ll4;
    @InjectView(id = R.id.llRegister)
    private LinearLayout llRegister;
    @InjectView(id = R.id.imgArrow)
    private ImageView imgArrow;
    @InjectView(id = R.id.btnGetCode)
    private Button btnGetCode;
    @InjectView(id = R.id.btnRigster)
    private Button btnRigster;
    @InjectView(id = R.id.etPhone)
    private EditText etPhone;
    @InjectView(id = R.id.etPwd)
    private EditText etPwd;
    @InjectView(id = R.id.imgPhoneDelete)
    private ImageButton imgPhoneDelete;
    @InjectView(id = R.id.imgPwdDelete)
    private ImageButton imgPwdDelete;
    private Activity act;
    private List<String> list = new ArrayList<>();
    private MyCount countDown;

    @Override
    protected void requestSuccess(String url, String data) {
        super.requestSuccess(url, data);
    }

    @Override
    protected void flagFailed(String url) {
        super.flagFailed(url);
    }
    private void initData(){
        list.add("云联商会");
        list.add("大唐天下");

    }

    @Override
    public void bindDataForUIElement() {
        initData();
        act = getActivity();
        tWidget.setCenterViewText("注册");
        countDown = new MyCount(60000,1000);
        ll1.setOnClickListener(this);
        imgPhoneDelete.setOnClickListener(this);
        imgPwdDelete.setOnClickListener(this);
        btnGetCode.setOnClickListener(this);
        btnRigster.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll1:
                Animation animation = AnimationUtils.loadAnimation(act,R.anim.arrow_rotate_up);
                animation.setFillAfter(true);
                imgArrow.startAnimation(animation);

                final CustomPop pop = new CustomPop(getActivity(),R.layout.pop_choose_account,llRegister,0);
                ListView listChoose = (ListView) pop.root.findViewById(R.id.listChoose);
                ChooseAccountAdapter adapter = new ChooseAccountAdapter(act,list);
                listChoose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        txtFrom.setText(list.get(position));
                        txtFrom.setTextColor(getResources().getColor(R.color.black));
                        pop.dismiss();
                    }
                });
                listChoose.setAdapter(adapter);
                pop.showAsDropDown(ll1,0,2);

                pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        Animation animation = AnimationUtils.loadAnimation(act,R.anim.arrow_rotate_down);
                        animation.setFillAfter(true);
                        imgArrow.startAnimation(animation);
                    }
                });

                break;
            case R.id.imgPhoneDelete:
                etPhone.setText("");
                break;
            case R.id.imgPwdDelete:
                etPwd.setText("");
                break;
            case R.id.btnGetCode:
                GetCode();
                break;
            case R.id.btnRigster:
                Register();
                break;
        }
    }
    //获取验证码
    private void GetCode(){
        countDown.start();


    }
    //注册
    public void Register(){
        if (!YTUtil.isPhone(etPhone)) {
            YTUtil._TOAST(act,"手机格式错误");
        }

    }
    public class MyCount extends CountDownTimer {

        public MyCount(long millisInFuture,long countDownInterval){
            super(millisInFuture,countDownInterval);

        }
        @Override
        public void onTick(long millisUntilFinished) {
            btnGetCode.setClickable(false);
            btnGetCode.setText(millisUntilFinished / 1000 +"s后再次获取");

        }

        @Override
        public void onFinish() {
            btnGetCode.setClickable(true);
            btnGetCode.setText("重新获取");

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        text1.post(new Runnable() {
            @Override
            public void run() {
                int width = text1.getMeasuredWidth();
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                text2.setLayoutParams(params);
                text3.setLayoutParams(params);
                text4.setLayoutParams(params);

                int height = ll4.getMeasuredHeight();
                LinearLayout.LayoutParams hParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,height);
                ll1.setLayoutParams(hParams);
                ll2.setLayoutParams(hParams);
                ll3.setLayoutParams(hParams);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDown != null){
            countDown.cancel();
        }
    }

    @Override
    protected void bindEvent() {

    }
}
