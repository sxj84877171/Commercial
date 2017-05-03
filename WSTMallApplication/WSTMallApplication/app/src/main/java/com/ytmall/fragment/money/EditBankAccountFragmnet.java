package com.ytmall.fragment.money;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ytmall.R;
import com.ytmall.api.recharge.GetBanksList;
import com.ytmall.api.recharge.GetCashAccInfo;
import com.ytmall.api.recharge.GetCashConfList;
import com.ytmall.api.recharge.GetMoneyBagInfo;
import com.ytmall.api.recharge.SaveCashAcc;
import com.ytmall.api.useradress.province.GetAreasByParentId;
import com.ytmall.application.Const;
import com.ytmall.bean.AreaBean;
import com.ytmall.bean.BankBean;
import com.ytmall.bean.GetCashAccInfoBean;
import com.ytmall.bean.GetCashConfBean;
import com.ytmall.bean.QueryCardsListBean;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.util.StrUtil;
import com.ytmall.widget.CardRechargePop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2017/1/23.
 */
@FragmentView(id = R.layout.fragment_set_bank_account)
public class EditBankAccountFragmnet extends BaseFragment implements View.OnClickListener{
    @InjectView(id = R.id.llProvince)
    LinearLayout llProvince;
    @InjectView(id = R.id.llBankAccount)
    LinearLayout llBankAccount;
    @InjectView(id = R.id.llBankType)
    LinearLayout llBankType;
    @InjectView(id = R.id.llCity)
    LinearLayout llCity;
    @InjectView(id = R.id.txtProvince)
    TextView txtProvince;
    @InjectView(id = R.id.txtCity)
    TextView txtCity;
    @InjectView(id = R.id.etName)
    EditText etName;
    @InjectView(id = R.id.etNumber)
    EditText etNumber;
    @InjectView(id = R.id.etBank)
    EditText etBank;
    @InjectView(id = R.id.btnSure)
    Button btnSure;
    @InjectView(id = R.id.txtBankType)
    TextView txtBankType;

    private GetBanksList bankParam = new GetBanksList();
    private SaveCashAcc saveParam = new SaveCashAcc();
    private GetCashAccInfo infoParam = new GetCashAccInfo();
    private GetAreasByParentId getAreasByParentId = new GetAreasByParentId();

    private String bankId;
    private String areaId;
    private String id;

    private List<String> bankList = new ArrayList<>();
    private List<String> provinceList = new ArrayList<>();
    private List<String> cityList = new ArrayList<>();

    private List<BankBean> bank = new ArrayList<>();
    private List<AreaBean> province = new ArrayList<>();
    private List<AreaBean> city = new ArrayList<>();

    private int bankPosition = -1;
    private int provincePostion = -1;
    private int cityPosition = -1;

    private int areaItem = 1;

    private GetCashConfBean bean;





    private CardRechargePop provincePop,cityPop,bankPop;

    public EditBankAccountFragmnet (String id){
        this.id = id;

    }

//    @Override
//    public void setArguments(Bundle args) {
//        super.setArguments(args);
//    }

    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(bankParam.getA())){
            try {
                JSONObject jso = new JSONObject(data);
                Type bankType = new TypeToken<ArrayList<BankBean>>(){}.getType();
                List<BankBean> result = gson.fromJson(jso.get("data").toString(),bankType);
                bankList.clear();
                for (int i = 0;i < result.size();i++){
                    bankList.add(result.get(i).bankName);
                }
                bank.clear();
                bank.addAll(result);

                showBankPop();

            }catch (Exception e){
                e.printStackTrace();

            }

        }else if (url.contains(getAreasByParentId.getA())) {
            JSONObject jsonobj;
            try {
                jsonobj = new JSONObject(data);
                if (jsonobj.has("data")) {
                    JSONArray jsonArray = jsonobj.getJSONArray("data");
                    Type listType = new TypeToken<ArrayList<AreaBean>>() {
                    }.getType();
                    List<AreaBean> tempList = new Gson().fromJson(jsonArray.toString(), listType);

//                    if (areaItem == 1){
//                        provinceList.clear();
//                        for (int i = 0;i < tempList.size();i++){
//                            provinceList.add(tempList.get(i).areaName);
//                        }
//                        province.clear();
//                        province.addAll(tempList);
//
//                        areaItem = 2;
//                        showProvince();
//
//
//
//
//                    }else {
//                        cityList.clear();
//                        for (int i = 0 ;i < tempList.size();i ++){
//                            cityList.add(tempList.get(i).areaName);
//                        }
//                        city.clear();
//                        city.addAll(tempList);
//                        txtCity.setText("请选择");
//
////                        showCity();
//
//                    }

                }
            } catch (JSONException e) {

            }
        }
        else if (url.contains(saveParam.getA())){
            Toast.makeText(getActivity(),"添加成功",Toast.LENGTH_SHORT).show();
            getActivity().finish();

        }else if (url.contains(infoParam.getA())){
            try {
                JSONObject jso = new JSONObject(data);
                GetCashAccInfoBean result = new Gson().fromJson(jso.get("data").
                        toString(), GetCashAccInfoBean.class);
                txtBankType.setText(result.bank_name);
//                txtProvince.setText(result.provincia_name);
//                txtCity.setText(result.city_name);
                etName.setText(result.accUser);
                etNumber.setText(result.accNo);
                //支行
                etBank.setText(StrUtil.null2Str(result.branch_bank));

                id = result.id;
                bankId = result.accTargetId;
                areaId = result.areaId2;

                bankPosition = 0;
                provincePostion = 0;
                cityPosition = 0;



            }catch (Exception e){

            }
        }
    }

    @Override
    protected void flagFailed(String url) {
        super.flagFailed(url);
    }

    @Override
    public void bindDataForUIElement() {
//        llProvince.setOnClickListener(this);
//        llCity.setOnClickListener(this);
        llBankType.setOnClickListener(this);
        btnSure.setOnClickListener(this);

        if (StrUtil.null2Str(id).length() > 0){
            getCashAccInfo();
        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llBankType:
                getBankList();

                break;
            case R.id.llProvince:
                areaItem = 1;
                getProvince("0");

                break;
            case R.id.llCity:
                areaItem = 2;
                if (cityList != null){
                    showCity();
                }


                break;
            case R.id.btnSure:
//                if (txtProvince.getText().equals("请选择")){
//                    Toast.makeText(getActivity(),"请先选择城市",Toast.LENGTH_SHORT).show();
//                    return;
//
//                }
                if (txtBankType.getText().equals("请选择")){
                    Toast.makeText(getActivity(),"请先选择开户银行",Toast.LENGTH_SHORT).show();
                    return;
                }

//                if (txtCity.getText().equals("请选择")){
//                    Toast.makeText(getActivity(),"请先选择城市",Toast.LENGTH_SHORT).show();
//                    return;
//                }

                if (etName.getText().length() > 0 && !etName.getText().equals("")){

                }else {
                    Toast.makeText(getActivity(),"请填写持卡人",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (etNumber.getText().length() != 19){
                    Toast.makeText(getActivity(),"请填写正确的银行卡号",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (etBank.getText().length() > 0 && !etName.getText().equals("")){

                }else {
                    Toast.makeText(getActivity(),"请填写开户支行",Toast.LENGTH_SHORT).show();
                    return;
                }


                saveCashAcc();

                break;
        }
    }
    private void getBankList(){
//        bankParam = new GetBanksList();
        bankParam.tokenId = Const.cache.getTokenId();
        request(bankParam);

    }
    private void showBankPop(){
        bankPop = new CardRechargePop(getActivity(),"请选择银行",bankList);
        bankPop.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtBankType.setText(bankList.get(position));
                bankPosition = position;
                bankId = bank.get(position).bankId;
                bankPop.dismiss();

            }
        });
        bankPop.showAtLocation(llBankAccount, Gravity.CENTER,0,0);

    }
    private void getProvince(String parentId) {
        getAreasByParentId.parentId = parentId;
        getAreasByParentId.tokendId = Const.cache.getTokenId();
        request(getAreasByParentId);
    }
    private void showProvince(){
        provincePop = new CardRechargePop(getActivity(),"省份",provinceList);
        provincePop.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                provincePostion = position;
                txtProvince.setText(provinceList.get(position));
                provincePop.dismiss();
                getProvince(province.get(provincePostion).areaId);
            }
        });
        provincePop.showAtLocation(llBankAccount, Gravity.CENTER,0,0);
    }
    private void showCity(){
        cityPop = new CardRechargePop(getActivity(),"城市",cityList);
        cityPop.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtCity.setText(cityList.get(position));
                cityPosition = position;
                cityPop.dismiss();

            }
        });
        cityPop.showAtLocation(llBankAccount, Gravity.CENTER,0,0);
    }
    private void saveCashAcc(){
        saveParam.tokenId = Const.cache.getTokenId();
        saveParam.accTargetId = bankId;
        if (StrUtil.null2Str(id).length() > 0){
            saveParam.id = id;
        }
        String branch_bank = etBank.getText().toString();
        saveParam.branch_bank = branch_bank;
//        if (StrUtil.null2Str(branch_bank).length() > 0){
//            saveParam.branch_bank = branch_bank;
//        }

        saveParam.accUser = etName.getText().toString();
        saveParam.accNo = etNumber.getText().toString();
//        for (int i = 0;i < cityList.size() ;i ++){
//            if (cityList.get(i).contains(txtCity.getText().toString())){
//                saveParam.areaId2 = city.get(i).areaId;
//            }
//        }

        request(saveParam);



    }


    @Override
    protected void bindEvent() {

    }
    private void getCashAccInfo(){
//        infoParam = new GetCashAccInfo();
        infoParam.tokenId = Const.cache.getTokenId();
        infoParam.id = id;
        request(infoParam);

    }
}
