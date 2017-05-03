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
# 创建日期：2015-8-23
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
 */
package com.ytmall.fragment.sort;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ytmall.R;
import com.ytmall.activity.goodlist.GoodListActivity;
import com.ytmall.adapter.MyBaseAdapter;
import com.ytmall.application.GoodsKinds.GoodsKindsId;
import com.ytmall.bean.SortRight;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.fragment.goods.GoodsListFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;


@FragmentView(id = R.layout.fragment_sort_right)
public class SortRightFragment extends BaseFragment {

	@InjectView(id = R.id.sort_right_listview)
	private ListView sort_right_listview;
	
	private List<GoodsKindsId> ancestralList;
	private List<SortRight> sortRightList;

	public SortRightFragment(List<GoodsKindsId> ancestralList) {
		this.ancestralList = ancestralList;
	}

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		sortRightList=new ArrayList<SortRight>();
		if(ancestralList!=null){
		for(int i=ancestralList.size()-1;i>=0;i--){
			sortRightList.add(new SortRight(ancestralList.get(i)));
			if(ancestralList.get(i).getChildList()!=null){
			int temp=ancestralList.get(i).getChildList().size()/3;
			
			int x=ancestralList.get(i).getChildList().size()-1;
			for(int j=0;j<temp;j++){
				sortRightList.add(new SortRight(ancestralList.get(i).getChildList().get(x--), ancestralList.get(i).getChildList().get(x--), ancestralList.get(i).getChildList().get(x--)));
			}
			if(ancestralList.get(i).getChildList().size()%3==2){
				sortRightList.add(new SortRight(ancestralList.get(i).getChildList().get(x--), ancestralList.get(i).getChildList().get(x--), null));
			}else if(ancestralList.get(i).getChildList().size()%3==1){
				sortRightList.add(new SortRight(ancestralList.get(i).getChildList().get(x--), null, null));
			}
			}
		}
		sort_right_listview.setAdapter(new SortRightAdapter(getActivity(), sortRightList));
		}
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub

	}
	
	private class SortRightAdapter extends MyBaseAdapter<SortRight>{

		public SortRightAdapter(Context context, List<SortRight> list) {
			super(context, list);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			ViewHolder holder;
			
			if(convertView==null){
				convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sort_right, null);
				holder=new ViewHolder();
				holder.parent_layout=(View) convertView.findViewById(R.id.parent_layout);
				holder.parent_title=(TextView) convertView.findViewById(R.id.parent_title);
				holder.child_layout=(View) convertView.findViewById(R.id.child_layout);
				holder.child_title_1=(TextView) convertView.findViewById(R.id.child_title_1);
				holder.child_title_2=(TextView) convertView.findViewById(R.id.child_title_2);
				holder.child_title_3=(TextView) convertView.findViewById(R.id.child_title_3);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			
			SortRight sortRight=getItem(position);
			
			if(sortRight.isParentBoolean){
				holder.parent_layout.setVisibility(View.VISIBLE);
				holder.child_layout.setVisibility(View.GONE);
				holder.parent_title.setText(sortRight.parent.getName());
				holder.parent_layout.setOnClickListener(new SortRightParentOnClickListener(sortRight.parent.getId()));
			}else{
				holder.parent_layout.setVisibility(View.GONE);
				holder.child_layout.setVisibility(View.VISIBLE);
				holder.child_title_1.setText(sortRight.child_1.getName());
				holder.child_title_1.setOnClickListener(new SortRightOnClickListener(sortRight.child_1.getId()));
				if(sortRight.child_2!=null){
					holder.child_title_2.setText(sortRight.child_2.getName());
					holder.child_title_2.setVisibility(View.VISIBLE);
					holder.child_title_2.setOnClickListener(new SortRightOnClickListener(sortRight.child_2.getId()));
				}else{
					holder.child_title_2.setVisibility(View.INVISIBLE);
				}
				if(sortRight.child_3!=null){
					holder.child_title_3.setText(sortRight.child_3.getName());
					holder.child_title_3.setVisibility(View.VISIBLE);
					holder.child_title_3.setOnClickListener(new SortRightOnClickListener(sortRight.child_3.getId()));
				}else{
					holder.child_title_3.setVisibility(View.INVISIBLE);
				}
			}
			
			
			return convertView;
		}
		
		private class ViewHolder{
			View parent_layout,child_layout;
			TextView parent_title,child_title_1,child_title_2,child_title_3;
		}
		
		private class SortRightParentOnClickListener implements OnClickListener{
			private String targetId;

			public SortRightParentOnClickListener(String targetId){
				this.targetId=targetId;
			}
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(), GoodListActivity.class);
				intent.putExtra(GoodsListFragment.Mode_GoodsCatIdSecond, Integer.parseInt(targetId));
				startActivity(intent);
			}
			
		}
		
		private class SortRightOnClickListener implements OnClickListener{
			private String targetId;

			public SortRightOnClickListener(String targetId){
				this.targetId=targetId;
			}
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(), GoodListActivity.class);
				intent.putExtra(GoodsListFragment.GoodsCatId, Integer.parseInt(targetId));
				startActivity(intent);
			}
			
		}
		
	}
	


}
