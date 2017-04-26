package com.ytmall.adapter;

import java.util.List;

import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.activity.MainActivity;
import com.ytmall.activity.goodlist.GoodListActivity;
import com.ytmall.activity.goods.GoodsActivity;
import com.ytmall.application.Const;
import com.ytmall.bean.RecommendGoodsBean;
import com.ytmall.fragment.goods.GoodsListFragment;
import com.ytmall.widget.HorizontalScrollViewAdapter;
import com.ytmall.widget.MyHorizontalScrollView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ReGoodsAdapter extends BaseAdapter {
	private List<RecommendGoodsBean> recommendgoodlist;
	private Context context;
	private LayoutInflater minflater;
	private int windowsWidth;

	public ReGoodsAdapter(Context context,
			List<RecommendGoodsBean> recommendgoodlist) {
		this.context = context;
		this.recommendgoodlist = recommendgoodlist;
		WindowManager wm1 = ((Activity) context).getWindowManager();
		this.windowsWidth = wm1.getDefaultDisplay().getWidth();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return recommendgoodlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		minflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ReHolder reHolder=null;
		if(convertView==null){
			reHolder=new ReHolder();
			convertView = minflater.inflate(R.layout.mainpage_recommend_item,
					null);
				reHolder.tv_recommendation_name = (TextView) convertView
						.findViewById(R.id.tv_recommendation_name);
				reHolder.bt_main_more = (ImageView) convertView
						.findViewById(R.id.bt_main_more);
				reHolder.mHorizontalScrollView = (MyHorizontalScrollView) convertView.findViewById(R.id.id_horizontalScrollView);
			
				convertView.setTag(reHolder);	
		}else{
			reHolder=(ReHolder) convertView.getTag();
		}

		reHolder.bt_main_more.setOnClickListener(new MoreGoodsOnClickListener(
				position));
		reHolder.tv_recommendation_name
				.setText(recommendgoodlist.get(position).catName);
		HorizontalScrollViewAdapter mAdapter = new HorizontalScrollViewAdapter(context, recommendgoodlist.get(position).goodlistbean);
		reHolder.mHorizontalScrollView.initDatas(mAdapter);
		return convertView;
	}

	static class ReHolder {
		TextView tv_recommendation_name;
		ImageView bt_main_more;
		MyHorizontalScrollView mHorizontalScrollView;
	}

	class MoreGoodsOnClickListener implements OnClickListener {
		private int position;

		public MoreGoodsOnClickListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(context, GoodListActivity.class);
			intent.putExtra(GoodsListFragment.Mode_GoodsCatIdOne,
					Integer.parseInt(recommendgoodlist.get(position).catId));
			context.startActivity(intent);
		}

	}

	
}
