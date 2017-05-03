/**
#************************************************
# 项目名称：WSTMall
# 版本号： V1.0  
#************************************************
# 文件说明：
#          
#************************************************
# 子模块说明：
#                     
#************************************************
# 创建人员： 谈泳豪
# 创建人员QQ：1511895018
# 创建日期：2015-6-12
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
 */
package com.ytmall.fragment.sort;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.ytmall.R;
import com.ytmall.adapter.MyBaseAdapter;
import com.ytmall.api.goods.GetGoodsCats;
import com.ytmall.application.GoodsKinds;
import com.ytmall.application.GoodsKinds.GoodsKindsId;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;


@FragmentView(id = R.layout.fragment_sort_page)
public class SortFragment extends BaseFragment {

	@InjectView(id = R.id.left_listView)
	private ListView left_listView;

	private View lastLeftSelectView;
	private int lastLeftSelectPosition;
	private List<SortRightFragment> sortRightList = new ArrayList<SortRightFragment>();
	private GetGoodsCats getGoodsCats=new GetGoodsCats();
	private int scrollPosition;
	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		request(getGoodsCats);
	}
	
	@SuppressLint("NewApi") private void initSortList(){
		left_listView.setAdapter(new SortLeftAdapter(getActivity(), GoodsKinds.goodsKindsList));

		for (int i = 0; i < GoodsKinds.goodsKindsList.size(); i++) {
			sortRightList.add(new SortRightFragment(GoodsKinds.goodsKindsList.get(i).getChildList()));
		}

		FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.right_list, sortRightList.get(0));
		fragmentTransaction.commit();
		
		left_listView.setOnItemClickListener(new OnItemClickListener() {
			@SuppressLint("NewApi") @Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
			//	lastLeftSelectView.setBackgroundResource(R.color.sort_gray);
				TextView lasttv=(TextView) lastLeftSelectView.findViewById(R.id.title_side);
				lasttv.setTextColor(Color.parseColor("#000000"));
				lastLeftSelectPosition = position;
				//view.setBackgroundResource(R.color.app_bg);
				TextView tv=(TextView) view.findViewById(R.id.title_side);
				tv.setTextColor(Color.parseColor("#FF6666"));
				lastLeftSelectView = view;
				FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
				fragmentTransaction.replace(R.id.right_list, sortRightList.get(position));
				fragmentTransaction.commit();
				scrollPosition=position;
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						left_listView.smoothScrollToPositionFromTop(scrollPosition,0);
					}
				}, 200);
			}
		});
		left_listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void requestSuccess(String url, String data) {
		if(url.contains(getGoodsCats.getA())){
			JSONObject jsonobj;
			try {
				jsonobj = new JSONObject(data);
				new GoodsKinds(jsonobj.getJSONArray("data")); 
				initSortList();
			} catch (JSONException e) {
			}
		}
	}

	private class SortLeftAdapter extends MyBaseAdapter<GoodsKindsId> {

		public SortLeftAdapter(Context context, List<GoodsKindsId> list) {
			super(context, list);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			ViewHolder holder;

			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sort_left, null);
				holder = new ViewHolder();
				holder.title_side = (TextView) convertView.findViewById(R.id.title_side);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			GoodsKindsId goodsKindsId = getItem(position);
			holder.title_side.setText(goodsKindsId.getName().replace("、", "\n"));

			// 复用的时候，确定颜色
			if (lastLeftSelectPosition == position) {
				//convertView.setBackgroundResource(R.color.app_bg); 
				holder.title_side.setTextColor(Color.parseColor("#FF6666"));
			} else {
				//convertView.setBackgroundResource(R.color.sort_gray); 
				holder.title_side.setTextColor(Color.parseColor("#000000"));
			}

			// 初始化lastLeftSelectView
			if (lastLeftSelectView == null && position == lastLeftSelectPosition) {
				lastLeftSelectView = convertView;
			}

			return convertView;
		}

		private class ViewHolder {
			TextView title_side;
		}

	}

}
