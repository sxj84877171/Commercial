package com.ytmall.fragment.login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.activity.MainActivity;
import com.ytmall.activity.user.LoginActivity;
import com.ytmall.activity.user.MineActivity;
import com.ytmall.api.login.WeixinBind;
import com.ytmall.api.shoppingcar.GroupGoodsCart;
import com.ytmall.application.Const;
import com.ytmall.bean.User;
import com.ytmall.domain.ShoppingCart;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.fragment.user.MineFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.util.SharedPreferencesUtils;
import com.ytmall.util.StrUtil;
import com.ytmall.widget.ClearEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2017/2/14.
 */
@FragmentView(id = R.layout.fragment_third_login)
public class ThirdLoginFragment extends BaseFragment {
    @InjectView(id = R.id.et_name)
    ClearEditText et_name;
    @InjectView(id = R.id.et_password)
    ClearEditText et_password;
    @InjectView(id = R.id.btn_third)
    Button btn_third;
    private WeixinBind param  ;
    private String openid,unionid;
    private boolean isFromMainActivity = false;
    private GroupGoodsCart groupGoodsCart=new GroupGoodsCart();
    public final static String fromMainActivity = "FromMainActivity";

    public ThirdLoginFragment(String openid,String unionid,String mode){
        this.openid = openid;
        this.unionid = unionid;
        if (mode.equals(fromMainActivity)) {
            isFromMainActivity = true;
        }



    }


    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(param.getA())){
            try {
                Toast.makeText(getActivity(),"绑定成功",Toast.LENGTH_SHORT).show();
                JSONObject jso = new JSONObject(data);
                Const.user = gson.fromJson(jso.get("data").toString(), User.class);
                jso = new JSONObject(jso.get("data").toString());
                Const.cache.setTokenId(jso.getString("tokenId"));
                Const.isLogin = true;
                MineActivity.autoToMine=true;

                leftClick();
                LoginActivity.act.finish();
                getShoppingCarInfo();

            }catch (Exception e){
                e.printStackTrace();
            }

        }else if (url.contains(groupGoodsCart.getA())){
            try {
                JSONObject jsonObject = new JSONObject(data);
                JSONArray jsonArrayData = jsonObject.getJSONArray("data");
                Type listType = new TypeToken<ArrayList<ShoppingCart>>() {
                }.getType();
                List<ShoppingCart> shoppingListTemp = new Gson().fromJson(jsonArrayData.toString(), listType);
                MainActivity.mainActivity.refreshBuyNum(getShoppingCartNum(shoppingListTemp));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                if(isFromMainActivity){
                    ((BaseActivity) getActivity()).replaceFragment(new MineFragment(), false);
                }else{
                    leftClick();
                }
            }
        }
    }

    @Override
    protected void flagFailed(String url) {
        super.flagFailed(url);
        if(url.contains(groupGoodsCart.getA())){
            if(isFromMainActivity){
                ((BaseActivity) getActivity()).replaceFragment(new MineFragment(), false);
            }else{
                leftClick();
            }
        }
    }
    private void getShoppingCarInfo(){
        groupGoodsCart.tokenId=Const.cache.getTokenId();
        requestNoDialog(groupGoodsCart);
    }

    @Override
    public void bindDataForUIElement() {
        btn_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_name.getText().length() > 0 && !StrUtil.null2Str(et_name.getText().toString()).equals("")){

                }else {
                    Toast.makeText(getActivity(),"请输入用户名",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_password.getText().length() > 0 && !StrUtil.null2Str(et_password.getText().toString()).equals("")){

                }else {
                    Toast.makeText(getActivity(),"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                thirdLogin();

            }
        });

    }
    private void thirdLogin(){
        param = new WeixinBind();
        param.loginName = et_name.getText().toString();
        param.loginPwd = et_password.getText().toString();
        param.openid = openid;
        param.unionid = unionid;
        request(param);

    }

    @Override
    protected void bindEvent() {

    }
    @Override
    public void onResume() {
        super.onResume();
        if(!isFromMainActivity&&Const.isLogin){
            leftClick();
        }
    }
    private int getShoppingCartNum(List<ShoppingCart> shoppinglist){
        int goodsTotalNum=0;
        for(int i=0;i<shoppinglist.size();i++){
            for(int j=0;j<shoppinglist.get(i).goods.size();j++){
                //goodsTotalNum=goodsTotalNum+shoppinglist.get(i).goods.get(j).goodsNum;
                goodsTotalNum++;
            }
        }
        SharedPreferencesUtils.saveValue(getActivity(), "cartNum", String.valueOf(goodsTotalNum));
        return goodsTotalNum;
    }
}
