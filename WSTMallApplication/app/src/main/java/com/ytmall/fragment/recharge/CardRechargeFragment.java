package com.ytmall.fragment.recharge;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ytmall.R;
import com.ytmall.activity.recharge.QueryCardListActivity;
import com.ytmall.activity.recharge.RechargeActivity;
import com.ytmall.api.recharge.AddCardByUser;
import com.ytmall.api.recharge.ToCardRechargeMark;
import com.ytmall.application.Const;
import com.ytmall.bean.CardRechargeMarkBean;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.util.StrUtil;
import com.ytmall.widget.CardRechargePop;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2017/1/18.
 */
@FragmentView(id = R.layout.fragment_card_recharge)
public class CardRechargeFragment extends BaseFragment implements View.OnClickListener{
    @InjectView(id = R.id.llType)
    LinearLayout llType;
    @InjectView(id = R.id.llMoney)
    LinearLayout llMoney;
    @InjectView(id = R.id.txtType)
    TextView txtType;
    @InjectView(id = R.id.txtMoney)
    TextView txtMoney;
    @InjectView(id = R.id.btnSure)
    Button btnSure;
    @InjectView(id = R.id.rlCardBg)
    LinearLayout rlCardBg;
    @InjectView(id = R.id.imgMoney)
    ImageView imgMoney;
    @InjectView(id = R.id.imgType)
    ImageView imgType;
    @InjectView(id = R.id.etMark)
    EditText etMark;
    private AddCardByUser param = new AddCardByUser();
    private int type = 0;
    private CardRechargePop typePop,moneyPop;
    private List<String> list = new ArrayList<>();
    private List<String> typelist = new ArrayList<>();
    private ToCardRechargeMark markParam = new ToCardRechargeMark();

    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(param.getA())){
            try {
                JSONObject jso = new JSONObject(data);
                Toast.makeText(getActivity(),"充值成功",Toast.LENGTH_SHORT).show();

                leftClick();

            }catch (Exception e){

            }

        }else if (url.contains(markParam.getA())){
            try {
                JSONObject jso = new JSONObject(data);
                CardRechargeMarkBean result = gson.fromJson(jso.get("data").toString(), CardRechargeMarkBean.class);

//                jso = new JSONObject(jso.get("data").toString());
//                jso.getJSONObject("remark");
//                String remark = jso.getString("remark");
                etMark.setText(StrUtil.null2Str(result.remark));
//                Toast.makeText(getActivity(),data,Toast.LENGTH_SHORT).show();


            }catch (Exception e){
                e.printStackTrace();

            }

        }
    }

    @Override
    protected void flagFailed(String url) {
        super.flagFailed(url);
        if (url.contains(param.getA())){

            Toast.makeText(getActivity(),"余额不足",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getActivity(),RechargeActivity.class);
            startActivity(i);
//            leftClick();
        }else if (url.contains(markParam.getA())){
            etMark.setText("");


        }
    }




    private void initData(){
        for (int i = 1;i < 11;i++){
            if (type == 0){
                int a = 300*i;
                list.add(String.valueOf(a));

            }else if (type == 1 ){
                int b = 1800 * i;
                list.add(String.valueOf(b));
            }else {
                int c = 600 * i ;
                list.add(String.valueOf(c));
            }
        }
    }
    private void initType(){
        typelist.add("话费");
        typelist.add("油卡");
        typelist.add("水费");
        typelist.add("电费");
    }

    @Override
    public void bindDataForUIElement() {
        initType();
        initData();
        tWidget.setCenterViewText("卡充值");
        tWidget.setRightVisibility("visible");
        tWidget.setRightBtnText("记录");
        tWidget.right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QueryCardListActivity.class);
                startActivity(intent);
            }
        });
        btnSure.setOnClickListener(this);
        llType.setOnClickListener(this);
        llMoney.setOnClickListener(this);

        getRremark();



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSure:
                confirm();
                break;
            case R.id.llType:
                typePop = new CardRechargePop(getActivity(),"充值类型",typelist);

                typePop.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        type = position;
                        txtType.setText(typelist.get(position));
                        list.clear();
                        initData();
                        txtMoney.setText(list.get(0));
                        typePop.dismiss();
                        getRremark();
                    }
                });
                typePop.showAtLocation(rlCardBg, Gravity.CENTER,0,0);
                break;
            case R.id.llMoney:
                moneyPop = new CardRechargePop(getActivity(),"充值金额",list);
                moneyPop.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        txtMoney.setText(list.get(position));
                        moneyPop.dismiss();

                    }
                });
                moneyPop.showAtLocation(rlCardBg, Gravity.CENTER,0,0);
                break;
        }
    }


    private void confirm(){
        param.tokenId = Const.cache.getTokenId();
        param.card_type = type;
        param.money = txtMoney.getText().toString();
        if (etMark.getText().toString().length() > 0){
            param.remark = etMark.getText().toString();
        }else {
            Toast.makeText(getActivity(),"请输入备注",Toast.LENGTH_SHORT).show();
            return;
        }
        request(param);


    }
    private void getRremark(){
        markParam.tokenId = Const.cache.getTokenId();
        markParam.card_type = String.valueOf(type);
        request(markParam);

    }

    @Override
    protected void bindEvent() {

    }
}
