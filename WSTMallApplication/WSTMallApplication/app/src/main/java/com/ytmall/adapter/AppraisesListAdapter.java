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
# 创建日期：2015-8-10
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
 */
package com.ytmall.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.application.Const;
import com.ytmall.bean.AppraiseListBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @ClassName AppraisesListAdapter
 * @Create Date 2015-8-10 下午11:03:03
 * 
 * @author Tans      QQ     1511895018
 * WSTMall开源商城-合作团队     官网地址:http://www.wstmall.com   联系QQ:707563272
 */
public class AppraisesListAdapter extends MyBaseAdapter<AppraiseListBean> {

	public AppraisesListAdapter(Context context, List<AppraiseListBean> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder;

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_appraises, null);
			holder = new ViewHolder();
			holder.user_photo = (ImageView) convertView.findViewById(R.id.user_photo);
			holder.user_name = (TextView) convertView.findViewById(R.id.user_name);
			holder.create_time = (TextView) convertView.findViewById(R.id.create_time);

			holder.goods_star = new ArrayList<ImageView>();
			holder.goods_star.add((ImageView) convertView.findViewById(R.id.goods_star_1));
			holder.goods_star.add((ImageView) convertView.findViewById(R.id.goods_star_2));
			holder.goods_star.add((ImageView) convertView.findViewById(R.id.goods_star_3));
			holder.goods_star.add((ImageView) convertView.findViewById(R.id.goods_star_4));
			holder.goods_star.add((ImageView) convertView.findViewById(R.id.goods_star_5));

			holder.time_star = new ArrayList<ImageView>();

			holder.time_star.add((ImageView) convertView.findViewById(R.id.time_star_1));
			holder.time_star.add((ImageView) convertView.findViewById(R.id.time_star_2));
			holder.time_star.add((ImageView) convertView.findViewById(R.id.time_star_3));
			holder.time_star.add((ImageView) convertView.findViewById(R.id.time_star_4));
			holder.time_star.add((ImageView) convertView.findViewById(R.id.time_star_5));

			holder.service_star = new ArrayList<ImageView>();
			holder.service_star.add((ImageView) convertView.findViewById(R.id.service_star_1));
			holder.service_star.add((ImageView) convertView.findViewById(R.id.service_star_2));
			holder.service_star.add((ImageView) convertView.findViewById(R.id.service_star_3));
			holder.service_star.add((ImageView) convertView.findViewById(R.id.service_star_4));
			holder.service_star.add((ImageView) convertView.findViewById(R.id.service_star_5));

			holder.appraise_text = (TextView) convertView.findViewById(R.id.appraise_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		AppraiseListBean bean = getItem(position);

		if (bean.userPhoto != null && !bean.userPhoto.equals("")) {
			((BaseActivity) mContext).loadOnRoundImage(Const.BASE_URL + bean.userPhoto, holder.user_photo);
		} else {
			holder.user_photo.setImageResource(R.drawable.person_img);
		}

		holder.user_name.setText(bean.loginName);
		holder.create_time.setText(bean.createTime);

		int i = 1;
		for (; i < holder.goods_star.size(); i++) {
			if (setStar(holder.goods_star.get(i), i, Integer.parseInt(bean.goodsScore))) {
				break;
			}
		}
		i++;
		for (; i < holder.goods_star.size(); i++) {
			holder.goods_star.get(i).setImageResource(R.drawable.android_ratingbar_single);
		}

		i = 1;
		for (; i < holder.time_star.size(); i++) {
			if (setStar(holder.time_star.get(i), i, Integer.parseInt(bean.timeScore))) {
				break;
			}
		}
		i++;
		for (; i < holder.time_star.size(); i++) {
			holder.time_star.get(i).setImageResource(R.drawable.android_ratingbar_single);
		}

		i = 1;
		for (; i < holder.service_star.size(); i++) {
			if (setStar(holder.service_star.get(i), i, Integer.parseInt(bean.serviceScore))) {
				break;
			}
		}
		i++;
		for (; i < holder.service_star.size(); i++) {
			holder.service_star.get(i).setImageResource(R.drawable.android_ratingbar_single);
		}

		holder.appraise_text.setText(bean.content);

		return convertView;
	}

	private boolean setStar(ImageView imageView, int num, int score) {
		if (num + 1 <= score) {
			imageView.setImageResource(R.drawable.android_ratingbar_single_light);
			return false;
		} else {
			imageView.setImageResource(R.drawable.android_ratingbar_single);
			return true;
		}
	}

	private class ViewHolder {
		TextView user_name, create_time, appraise_text;
		ImageView user_photo, iv_add_spc;
		List<ImageView> goods_star, time_star, service_star;
	}

}
