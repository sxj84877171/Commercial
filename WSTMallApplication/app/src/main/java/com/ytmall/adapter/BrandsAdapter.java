package com.ytmall.adapter;

import java.util.List;

import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.application.Const;
import com.ytmall.bean.Brandsbean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class BrandsAdapter extends MyBaseAdapter<Brandsbean> {

	public BrandsAdapter(Context context, List<Brandsbean> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.brand_item, null);
			holder.iv=(ImageView) convertView.findViewById(R.id.iv_brand);
			holder.tv=(TextView) convertView.findViewById(R.id.tv_brand_name);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		((BaseActivity) mContext).loadOnRectangleImage(
				Const.BASE_URL + getItem(position).brandIco,holder.iv);	
		holder.tv.setText(getItem(position).brandName);
		holder.tv.getBackground().setAlpha(100);
		return convertView;
	}

	private class ViewHolder {
		ImageView iv;
		TextView tv;
	}
}
