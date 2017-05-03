package com.ytmall.fragment.user;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.adapter.AdressAdapter;
import com.ytmall.adapter.UserAdressAdapter;
import com.ytmall.api.useradress.GetUserAddress;
import com.ytmall.api.useradress.community.GetCommunitys;
import com.ytmall.api.useradress.editadress.EditAddress;
import com.ytmall.api.useradress.province.GetAreasByParentId;
import com.ytmall.application.Const;
import com.ytmall.bean.AdressCommunitysbean;
import com.ytmall.bean.AdressDistrictsbean;
import com.ytmall.bean.AreaBean;
import com.ytmall.bean.CacheBean;
import com.ytmall.bean.FavoriteGoods;
import com.ytmall.bean.UserAdressBean;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.fragment.order_goods.OrderForGoodsFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@FragmentView(id = R.layout.shipping_adress_list)
public class ProvinceFragment extends BaseFragment {
    @InjectView(id = R.id.lv_adress)
    private ListView lv_adress;
    @InjectView(id = R.id.llAddressHead)
    private LinearLayout llAddressHead;

    private GetAreasByParentId getAreasByParentId = new GetAreasByParentId();
    private GetCommunitys getcommunitys = new GetCommunitys();
    private List<AreaBean> areaBeanList = new ArrayList<>();
    private AdressAdapter adressadapter;
    private List<AdressCommunitysbean> adresscommunitylist = new ArrayList<>();

    private int areaItem; ///1 :sheng 2:shi 3:qu 4:shequ

    public static String areaId1;//所在省份ID
    public static String areaId2;//所在城市ID
    public static String areaId3;//所在县区ID
    public static String communityId;//所在社区ID
    public static String allName;
    private String province;
    private String city;
    private String district;
    private String community;
    private LinearLayout headView;


    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(getAreasByParentId.getA())) {
            JSONObject jsonobj;
            try {
                jsonobj = new JSONObject(data);
                if (jsonobj.has("data")) {
                    JSONArray jsonArray = jsonobj.getJSONArray("data");
                    Type listType = new TypeToken<ArrayList<AreaBean>>() {
                    }.getType();
                    List<AreaBean> tempList = new Gson().fromJson(jsonArray.toString(), listType);
                    areaBeanList.clear();
                    areaBeanList.addAll(tempList);
                    adressadapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {

            }
        } else if (url.contains(getcommunitys.getA())) {
            JSONObject jsonobj;
            try {
                jsonobj = new JSONObject(data);
                if (jsonobj.has("data")) {
                    JSONArray jsonArray = jsonobj.getJSONArray("data");
                    adresscommunitylist.clear();
                    areaBeanList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        AdressCommunitysbean Bean = new Gson()
                                .fromJson(jsonArray.getJSONObject(i).toString(), AdressCommunitysbean.class);
                        AreaBean areaBean=new AreaBean();
                        areaBean.areaName=Bean.communityName;
                        areaBean.areaId=Bean.communityId;
                        adresscommunitylist.add(Bean);
                        areaBeanList.add(areaBean);
                    }
                    adressadapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {

            }
        }
    }

    @Override
    public void rightClick() {

    }

    @Override
    public void bindDataForUIElement() {
        // TODO Auto-generated method stub
        tWidget.setCenterViewText("省份");
        tWidget.setLeftVisibility("visible");
        areaItem = 1;
        areaId1 = "";
        areaId2 = "";
        areaId3 = "";
        communityId = "";
        allName = "";
//        initHeadView();
        adressadapter = new AdressAdapter(areaBeanList, getActivity());
        lv_adress.setAdapter(adressadapter);
        initHeadView();
        getProvince("0");

    }

    private void initHeadView() {
//        headView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.item_back, null);
        llAddressHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (areaItem == 2) {
                    areaItem--;
                    llAddressHead.setVisibility(View.GONE);
//                    lv_adress.removeHeaderView(headView);
                    adressadapter.notifyDataSetChanged();
                    allName="";
                    getProvince("0");
                } else if (areaItem == 3) { //市
                    llAddressHead.setVisibility(View.VISIBLE);
                    areaItem--;
                    getProvince(areaId1);
                } else if (areaItem == 4) {//县
                    llAddressHead.setVisibility(View.VISIBLE);
                    areaItem--;
                    getProvince(areaId2);
                }
            }
        });

    }

    private void getProvince(String parentId) {
        getAreasByParentId.parentId = parentId;
        getAreasByParentId.tokendId = Const.cache.getTokenId();
        request(getAreasByParentId);
    }

    private void getcommunitys(String areaId3) {
        getcommunitys.areaId3 = areaId3;
        request(getcommunitys);
    }

    @Override
    protected void bindEvent() {
        // TODO Auto-generated method stub

        lv_adress.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                switch (areaItem) {
                    case 1:
                        llAddressHead.setVisibility(View.VISIBLE);
                        getProvince(areaBeanList.get(position).areaId);
                        areaId1 = areaBeanList.get(position).areaId;
                        areaItem++;
                        province = areaBeanList.get(position).areaName;
                        tWidget.setCenterViewText("市");
                        break;
                    case 2:
                        llAddressHead.setVisibility(View.VISIBLE);
                        getProvince(areaBeanList.get(position).areaId);
                        areaId2 = areaBeanList.get(position).areaId;
                        areaItem++;
                        city= areaBeanList.get(position).areaName;
                        tWidget.setCenterViewText("县/区");
                        break;
                    case 3:
                        llAddressHead.setVisibility(View.VISIBLE);
                        areaId3 = areaBeanList.get(position).areaId;
                        areaItem++;
                        district= areaBeanList.get(position).areaName;
                        tWidget.setCenterViewText("社区");
//
                        allName=province+city+district;
                        ModifyAddAdressFragment.area = true;
                        getActivity().getFragmentManager().popBackStack();

                        break;
                    case 4:
//                        communityId = adresscommunitylist.get(position-1).getCommunityId();
//                        community=adresscommunitylist.get(position-1).getCommunityName();
//                        allName=province+city+district+community;
//                        ModifyAddAdressFragment.area = true;
//                        getActivity().getFragmentManager().popBackStack();
                        break;
                }
            }
        });
    }

}
