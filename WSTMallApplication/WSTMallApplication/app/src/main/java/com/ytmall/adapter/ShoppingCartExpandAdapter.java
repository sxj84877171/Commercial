package com.ytmall.adapter;

import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.activity.goods.GoodsActivity;
import com.ytmall.application.Const;
import com.ytmall.bean.GoodsListBean;
import com.ytmall.domain.ShoppingCart;
import com.ytmall.fragment.shoppingCart.ShoppingCartFragment;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ShoppingCartExpandAdapter extends BaseExpandableListAdapter {
	protected static final String TAG = "ShoppingCartExpandAdapter";
	private Context context;
	private CheckBox cb_child;
	private CheckBox cb_group;
	private int child_check_count;
	private ShopTotalPriceChange totalpricechange;

	public ShoppingCartExpandAdapter(Context context) {
		this.context = context;

	}

	public int getGroupCount() {
		// TODO Auto-generated method stub
		return ShoppingCartFragment.shoppinglist.size();
	}

	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return ShoppingCartFragment.shoppinglist.get(groupPosition).goods
				.size();
	}

	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		// return shoppinglist.get(groupPosition);
		return null;
	}

	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		// return
		// shoppinglist.get(groupPosition).getGoodsimg().get(childPosition);
		return null;
	}

	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ShoppingCart spc = ShoppingCartFragment.shoppinglist.get(groupPosition);
		View viewbusiness = View.inflate(context,
				R.layout.shoppingcar_business_item, null);

		TextView name = (TextView) viewbusiness
				.findViewById(R.id.tv_shoppingcar_businessname);
		name.setText(spc.shopInfo.shopName);
		name.getPaint().setFakeBoldText(true);
		TextView free_price = (TextView) viewbusiness
				.findViewById(R.id.tv_free_price);
			if(spc.shopInfo.deliveryFreeMoney!=0){
			free_price.setText("亲，购买商品达到" + spc.shopInfo.deliveryFreeMoney + "元,可以免运费");
			}else{
				free_price.setText("店铺免运费");
			}
		
		// checkbox
		cb_group = (CheckBox) viewbusiness.findViewById(R.id.cb_group);

		cb_group.setVisibility(View.VISIBLE);
		cb_group.setChecked(ShoppingCartFragment.shoppinglist
				.get(groupPosition).shopInfo.cbgroup);
		cb_group.setOnCheckedChangeListener(new groupcheck(groupPosition));

		viewbusiness.setClickable(true);
		return viewbusiness;
	}

	class groupcheck implements OnCheckedChangeListener {
		private int groupPosition;

		groupcheck(int groupPosition) {
			this.groupPosition = groupPosition;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if (isChecked) {
				ShoppingCartFragment.shoppinglist.get(groupPosition).shopInfo.cbgroup = true;
				for (int i = 0; i < ShoppingCartFragment.shoppinglist
						.get(groupPosition).goods.size(); i++) {
					ShoppingCartFragment.shoppinglist.get(groupPosition).goods
							.get(i).isCheck = "1";
				}
				if (totalpricechange != null) {
					totalpricechange.totalPriceChange();
					totalpricechange.setCheck(getGroupCheckStatusString(ShoppingCartFragment.shoppinglist
							.get(groupPosition).goods, "1"));
				}
				notifyDataSetChanged();
			} else {
				ShoppingCartFragment.shoppinglist.get(groupPosition).shopInfo.cbgroup = false;
				for (int i = 0; i < ShoppingCartFragment.shoppinglist
						.get(groupPosition).goods.size(); i++) {
					ShoppingCartFragment.shoppinglist.get(groupPosition).goods
							.get(i).isCheck = "0";
				}
				if (totalpricechange != null) {
					totalpricechange.totalPriceChange();
					totalpricechange.cancelCheck(getGroupCheckStatusString(ShoppingCartFragment.shoppinglist
							.get(groupPosition).goods,"0"));
				}
				notifyDataSetChanged();
			}
		}

	}
	private String getGroupCheckStatusString(List<GoodsListBean> goodsListBeanList,String status){
		StringBuilder stringBuilder=new StringBuilder();
		for(int i=0;i<goodsListBeanList.size();i++){
			stringBuilder.append(goodsListBeanList.get(i).goodsId+"_"+goodsListBeanList.get(i).goodsAttrId+"_"+goodsListBeanList.get(i).goodsNum+"_"+status+";");
		}
		return stringBuilder.substring(0,stringBuilder.length()-1).toString();
	}
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View viewgoods = View.inflate(context, R.layout.shoppingcar_goods_item,
				null);
		ImageView im = (ImageView) viewgoods
				.findViewById(R.id.im_shppingcar_godsimg);
		((BaseActivity) context)
				.loadOnImage(
						Const.BASE_URL
								+ ShoppingCartFragment.shoppinglist
										.get(groupPosition).goods
										.get(childPosition).goodsThums, im);
		TextView goods_describe = (TextView) viewgoods
				.findViewById(R.id.tv_shoppingcar_goods_describe);
		goods_describe.setText(ShoppingCartFragment.shoppinglist
				.get(groupPosition).goods.get(childPosition).goodsName);
		goods_describe.getPaint().setFakeBoldText(true);

		LinearLayout ll_spc_good_item = (LinearLayout) viewgoods
				.findViewById(R.id.ll_spc_good_item);

		ll_spc_good_item.setOnClickListener(new ChildItemClick(groupPosition, childPosition));

		TextView goods_price = (TextView) viewgoods
				.findViewById(R.id.tv_shoppingcar_goods_price);
		goods_price.setText(ShoppingCartFragment.shoppinglist
				.get(groupPosition).goods.get(childPosition).shopPrice + "");

		TextView goods_count = (TextView) viewgoods
				.findViewById(R.id.tv_shoppingcar_goods_count);//库存
		TextView tv_goods_attrs= (TextView) viewgoods
				.findViewById(R.id.tv_goods_attrs);
		goods_count.setText(ShoppingCartFragment.shoppinglist
				.get(groupPosition).goods.get(childPosition).goodsStock + "");
		TextView goods_count_right = (TextView) viewgoods
				.findViewById(R.id.tv_shoppingcar_goods_count_right);
		goods_count_right.setText(ShoppingCartFragment.shoppinglist
				.get(groupPosition).goods.get(childPosition).goodsNum + "");
		if(ShoppingCartFragment.shoppinglist
				.get(groupPosition).goods.get(childPosition).priceAttrName!=null){
		tv_goods_attrs.setText(ShoppingCartFragment.shoppinglist
				.get(groupPosition).goods.get(childPosition).priceAttrName+":"+ShoppingCartFragment.shoppinglist
				.get(groupPosition).goods.get(childPosition).priceAttrVal);
		}
		// checkbox
		cb_child = (CheckBox) viewgoods.findViewById(R.id.cb_child);
		if(ShoppingCartFragment.shoppinglist.get(groupPosition).goods.get(childPosition).isCheck.equals("1")) {
			cb_child.setChecked(true);
		}else{
			cb_child.setChecked(false);
		}

		cb_child.setOnCheckedChangeListener(new childcheck(groupPosition,
				childPosition));
		//购物车加减
		final int[] tempBuyNum = new int[1];
		tempBuyNum[0]=ShoppingCartFragment.shoppinglist.get(groupPosition).goods.get(childPosition).goodsNum;
		final String goodsId=ShoppingCartFragment.shoppinglist.get(groupPosition).goods.get(childPosition).goodsId;
		final String goodsAttr=ShoppingCartFragment.shoppinglist.get(groupPosition).goods.get(childPosition).goodsAttrId;
		final String isCheck=ShoppingCartFragment.shoppinglist.get(groupPosition).goods.get(childPosition).isCheck;
		final int numStock=ShoppingCartFragment.shoppinglist.get(groupPosition).goods.get(childPosition).goodsStock;
		TextView minus_buy_button= (TextView) viewgoods.findViewById(R.id.minus_buy_button);
		TextView add_buy_button= (TextView) viewgoods.findViewById(R.id.add_buy_button);
		final EditText buy_num_edittext= (EditText) viewgoods.findViewById(R.id.buy_num_edittext);
		buy_num_edittext.setText(ShoppingCartFragment.shoppinglist.get(groupPosition).goods.get(childPosition).goodsNum+"");
		minus_buy_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (tempBuyNum[0] > 1) {
					tempBuyNum[0]--;
					buy_num_edittext.setText(tempBuyNum[0] + "");
				}
			}
		});
		add_buy_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (tempBuyNum[0] < numStock) {
					tempBuyNum[0]++;
					buy_num_edittext.setText(tempBuyNum[0] + "");
				}
			}
		});
		buy_num_edittext.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String tempString = buy_num_edittext.getText().toString();
				// 如果输入长度或数值超过库存
				if (tempString.length() > String.valueOf(numStock).length()
						|| Integer.parseInt(tempString) > numStock) {
					Toast.makeText(context, "已超过库存", Toast.LENGTH_SHORT)
							.show();
					tempBuyNum[0] = numStock;
					buy_num_edittext.setText("" + numStock);
					totalpricechange.changeGoodsNum(goodsId+"_"+goodsAttr+"_"+numStock+"_"+isCheck);
					return;
				}
				//tempBuyNum[0] = Integer.parseInt(tempString);
				totalpricechange.changeGoodsNum(goodsId+"_"+goodsAttr+"_"+tempString+"_"+isCheck);
			}
		});
		return viewgoods;
	}

	class childcheck implements OnCheckedChangeListener {
		private int groupPosition;
		private int childPosition;

		childcheck(int groupPosition, int childPosition) {
			this.groupPosition = groupPosition;
			this.childPosition = childPosition;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub

			if (isChecked) {
				ShoppingCartFragment.shoppinglist.get(groupPosition).goods
						.get(childPosition).isCheck = "1";
				if (totalpricechange != null) {
					totalpricechange.totalPriceChange();
					String value=ShoppingCartFragment.shoppinglist.get(groupPosition).goods
							.get(childPosition).goodsId+"_"+ShoppingCartFragment.shoppinglist.get(groupPosition).goods
							.get(childPosition).goodsAttrId+"_"+ShoppingCartFragment.shoppinglist.get(groupPosition).goods
							.get(childPosition).goodsNum+"_"+"1";
					totalpricechange.setCheck(value);
				}
				// 判断是否全选
				child_check_count = 0;
				for (int i = 0; i < ShoppingCartFragment.shoppinglist
						.get(groupPosition).goods.size(); i++) {
					if (ShoppingCartFragment.shoppinglist.get(groupPosition).goods
							.get(i).isCheck.equals("1")) {

						child_check_count = child_check_count + 1;
					}
				}
				if (child_check_count == ShoppingCartFragment.shoppinglist
						.get(groupPosition).goods.size()) {
					ShoppingCartFragment.shoppinglist.get(groupPosition).shopInfo.cbgroup = true;
				} else {
					ShoppingCartFragment.shoppinglist.get(groupPosition).shopInfo.cbgroup = false;
				}
				notifyDataSetChanged();
			} else {
				ShoppingCartFragment.shoppinglist.get(groupPosition).goods
						.get(childPosition).isCheck = "0";
				child_check_count = 0;
				for (int i = 0; i < ShoppingCartFragment.shoppinglist
						.get(groupPosition).goods.size(); i++) {
					if (ShoppingCartFragment.shoppinglist.get(groupPosition).goods
							.get(i).isCheck.equals("1")) {

						child_check_count = child_check_count + 1;
					}
				}
				if (child_check_count == ShoppingCartFragment.shoppinglist
						.get(groupPosition).goods.size()) {
					ShoppingCartFragment.shoppinglist.get(groupPosition).shopInfo.cbgroup = true;
				} else {
					ShoppingCartFragment.shoppinglist.get(groupPosition).shopInfo.cbgroup = false;
				}
				if (totalpricechange != null) {
					totalpricechange.totalPriceChange();
					String value=ShoppingCartFragment.shoppinglist.get(groupPosition).goods
							.get(childPosition).goodsId+"_"+ShoppingCartFragment.shoppinglist.get(groupPosition).goods
							.get(childPosition).goodsAttrId+"_"+ShoppingCartFragment.shoppinglist.get(groupPosition).goods
							.get(childPosition).goodsNum+"_"+"0";
					totalpricechange.cancelCheck(value);
				}
				notifyDataSetChanged();
			}
		}
	}

	public class ChildItemClick implements OnClickListener {
		private int groupPosition;
		private int childPosition;

		ChildItemClick(int groupPosition, int childPosition) {
			this.groupPosition = groupPosition;
			this.childPosition = childPosition;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(context, GoodsActivity.class);
			intent.putExtra(GoodsActivity.goodsID, ShoppingCartFragment.shoppinglist
					.get(groupPosition).goods.get(childPosition).goodsId);
			context.startActivity(intent);
		}

	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

	public interface ShopTotalPriceChange {
		void totalPriceChange();
		void setCheck(String value);
		void cancelCheck(String value);
		void changeGoodsNum(String value);
	}

	public void setTotalPriceChangeListener(
			ShopTotalPriceChange totalpricechange) {
		this.totalpricechange = totalpricechange;
	}
}
