package com.ytmall.fragment.order;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;

import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.activity.order.OrderActivity;
import com.ytmall.api.goods.AddAppraises;
import com.ytmall.application.Const;
import com.ytmall.bean.OrderBean;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.widget.EditTextBar;

@FragmentView(id = R.layout.fragment_order_evaluation)
public class EvaluationFragment extends BaseFragment {
	private int shopPosition;
	@InjectView(id = R.id.lv_order_eva)
	private ListView order_eva;

	private OrderBean orderBean;

	public EvaluationFragment(int shopPosition) {
		this.shopPosition = shopPosition;
	}

	private List<AddAppraises> addAppraises;

	private int tipsTime = 0;

	private int isAppraisesComplete=0; //0填写完整 1：没填写完整
	protected void requestSuccess(String url, String data) {
		tipsTime++;
		if (tipsTime == addAppraises.size()) {
			//if (url.contains(addAppraises.get(0).getA())) {
				tipsTime = 0;
				try {
					JSONObject jsonreobj = new JSONObject(data);// 获取分类对象集合
					if (jsonreobj.getString("status").equals("1")) {
						Toast.makeText(getActivity(), "提交成功", Toast.LENGTH_LONG)
								.show();
						OrderActivity.isNeedRefresh=true;
						getActivity().finish();
					} else {
						Toast.makeText(getActivity(), "提交失败，请稍后重试",
								Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			//}
		}
	}

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		tWidget.setCenterViewText("商品评价");
		tWidget.setRightBtnText("提交");
		addAppraises = new ArrayList<AddAppraises>();
		orderBean = OrderManagement.orderbeanlist.get(shopPosition);
		initAppraises();
		order_eva.setAdapter(new AppriseAdapter());
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
	}

	@Override
	public void rightClick() {
		isAppraisesComplete();
		if(isAppraisesComplete==0) {
			for (int i = 0; i < addAppraises.size(); i++) {
				request(addAppraises.get(i));
			}
		}else{
			Toast.makeText(getActivity(),"请填写完整的评价",Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 初始化评价提交接口列表
	 */
	private void initAppraises(){
		addAppraises.clear();
		for(int i=0;i<orderBean.goodlist.size();i++){
			AddAppraises aa = new AddAppraises();
			addAppraises.add(aa);
		}
	}
	/**
	 * 判断评价信息是否完整
	 */
	private void isAppraisesComplete() {
		isAppraisesComplete=0;
			for(int i=0;i<addAppraises.size();i++){
				if(addAppraises.get(i).goodsScore==0){
					isAppraisesComplete++;
				}else if(addAppraises.get(i).timeScore==0){
					isAppraisesComplete++;
				}else if(addAppraises.get(i).serviceScore==0){
					isAppraisesComplete++;
				}
				if(addAppraises.get(i).content==null){
					isAppraisesComplete++;
				}
				else if(addAppraises.get(i).content.length()<10){
					isAppraisesComplete++;
				}
			}
	}

	public class AppriseAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return orderBean.goodlist.size();
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
			convertView = LayoutInflater.from(getActivity()).inflate(
					R.layout.order_evaluation_item, null);

			ImageView im_order_good_img = (ImageView) convertView
					.findViewById(R.id.im_order_good_img);
			TextView tv_order_good_name = (TextView) convertView
					.findViewById(R.id.tv_order_good_name);
			TextView tv_order_good_price = (TextView) convertView
					.findViewById(R.id.tv_order_good_price);
			TextView tv_order_good_count = (TextView) convertView
					.findViewById(R.id.tv_order_good_count);
			RatingBar rb_goodsScore = (RatingBar) convertView
					.findViewById(R.id.rb_goodsScore);
			RatingBar rb_timeScore = (RatingBar) convertView
					.findViewById(R.id.rb_timeScore);
			RatingBar rb_serviceScore = (RatingBar) convertView
					.findViewById(R.id.rb_serviceScore);
			EditText et_content = (EditText) convertView
					.findViewById(R.id.et_content);
			rb_goodsScore.setRating(addAppraises.get(position).goodsScore);
			rb_timeScore.setRating(addAppraises.get(position).timeScore);
			rb_serviceScore.setRating(addAppraises.get(position).serviceScore);

			addAppraises.get(position).tokenId = Const.cache.getTokenId();
			addAppraises.get(position).orderId = orderBean.orderId;
			addAppraises.get(position).goodsId = orderBean.goodlist
					.get(position).goodsId;
			addAppraises.get(position).goodsAttrId = orderBean.goodlist.get(position).goodsAttrId;
			((BaseActivity) getActivity()).loadOnImage(Const.BASE_URL
							+ orderBean.goodlist.get(position).goodsThums,
					im_order_good_img);
			tv_order_good_name
					.setText(orderBean.goodlist.get(position).goodsName);
			tv_order_good_price.setText("￥"
					+ orderBean.goodlist.get(position).goodsPrice + "");
			tv_order_good_count.setText("x"
					+ orderBean.goodlist.get(position).goodsNum + "");

			rb_goodsScore.setOnRatingBarChangeListener(new GSore(position));
			rb_timeScore.setOnRatingBarChangeListener(new TSore(position));
			rb_serviceScore.setOnRatingBarChangeListener(new SSore(position));
			if(addAppraises.get(position).content!=null) {
				et_content.setText(addAppraises.get(position).content + "");
			}
			et_content.addTextChangedListener(new CTextWatcher(position));


			return convertView;
		}

		private class CTextWatcher implements TextWatcher {
			private int position;

			public CTextWatcher(int position) {
				this.position = position;
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				addAppraises.get(position).content = s.toString();
			}
		}

		private class GSore implements OnRatingBarChangeListener {
			private int position;

			public GSore(int position) {
				this.position = position;
			}

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				addAppraises.get(position).goodsScore = (int) rating;
			}
		}

		private class TSore implements OnRatingBarChangeListener {
			private int position;

			public TSore(int position) {
				this.position = position;
			}

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				addAppraises.get(position).timeScore = (int) rating;
			}
		}

		private class SSore implements OnRatingBarChangeListener {
			private int position;

			public SSore(int position) {
				this.position = position;
			}

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				addAppraises.get(position).serviceScore = (int) rating;
			}
		}
	}

}
