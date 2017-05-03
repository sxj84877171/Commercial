package com.ytmall.fragment.user;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.adapter.AdressAdapter;
import com.ytmall.api.useradress.province.GetAreasByParentId;
import com.ytmall.application.Const;
import com.ytmall.bean.AreaBean;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@FragmentView(id = R.layout.shipping_adress_list)
public class DistrictFragment extends BaseFragment {
	@InjectView(id = R.id.lv_adress)
	private ListView lv_adress;

	private GetAreasByParentId getAreasByParentId=new GetAreasByParentId();
	private List<AreaBean> areaBeanList=new ArrayList<>();
	private AdressAdapter adressadapter;
	public static String parentId;
	private String province;
	public DistrictFragment(String parentId,String province){
		this.parentId=parentId;
		this.province=province;
	}
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
					//adressadapter=new AdressAdapter(areaBeanList,getActivity(),areaBeanList.size(),3,"hi");
					lv_adress.setAdapter(adressadapter);

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
		tWidget.setCenterViewText("我的省份");
		tWidget.setLeftVisibility("visible");
		getProvince();
	}
	private void getProvince(){
		getAreasByParentId.parentId=parentId;
		getAreasByParentId.tokendId= Const.cache.getTokenId();
		request(getAreasByParentId);
	}
	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub

		lv_adress.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				((BaseActivity) getActivity()).replaceFragment(new CommunitysFragment(areaBeanList.get(position).areaId,province+areaBeanList.get(position).areaName), false);
			}
		});
	}

}
