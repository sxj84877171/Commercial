package com.ytmall.fragment.brands;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.ytmall.R;
import com.ytmall.adapter.BrandsAdapter;
import com.ytmall.api.brands.GetBrands;
import com.ytmall.application.Const;
import com.ytmall.bean.Brandsbean;
import com.ytmall.bean.BusinessListbean;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.fragment.business.BusinessHomeFragment;
import com.ytmall.fragment.goods.GoodsListFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;

@FragmentView(id = R.layout.fragment_brands)
public class BrandsFragment extends BaseFragment {
	@InjectView(id = R.id.gd_brand)
	private GridView gd_brand;
	private GetBrands getbrand;
	private List<Brandsbean> brandsbeanlist;
	private BrandsAdapter brandsadapter;
	@Override
	protected void requestSuccess(String url, String data) {
		if (url.contains(getbrand.getA())) {
			JSONObject jsonobj;
			try {
				jsonobj = new JSONObject(data);
				if (jsonobj.has("data")) {
					JSONArray jsonArray = jsonobj.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {
						Brandsbean bean = new Gson().fromJson(jsonArray
								.getJSONObject(i).toString(),
								Brandsbean.class);
						brandsbeanlist.add(bean); // 接收的数据集
					}
					brandsadapter.notifyDataSetChanged();
				}
			} catch (JSONException e) {

			}
		}
	}
	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		tWidget.setCenterViewText("品牌馆");
		brandsbeanlist=new ArrayList<Brandsbean>();
		brandsadapter=new BrandsAdapter(getActivity(), brandsbeanlist);
		gd_brand.setAdapter(brandsadapter);
		getbrand = new GetBrands();
		getbrand.areaId3=Const.cache.city2.getCityid();
		request(getbrand);
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		gd_brand.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				replaceFragment(new GoodsListFragment(brandsbeanlist.get(position).brandId,brandsbeanlist.get(position).brandName,GoodsListFragment.Mode_FromBrands), true);
			}
		});
	}

}
