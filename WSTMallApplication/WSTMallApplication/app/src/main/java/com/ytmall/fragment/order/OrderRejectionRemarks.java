package com.ytmall.fragment.order;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.ytmall.R;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;

@FragmentView(id = R.layout.fragment_order_remark)
public class OrderRejectionRemarks extends BaseFragment implements
		OnClickListener {

	@InjectView(id = R.id.ll_order_all)
	private LinearLayout ll_order_all;
	private String remarksTitle;
	public OrderRejectionRemarks(String remarksTitle) {
		this.remarksTitle=remarksTitle;
	}

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		tWidget.setCenterViewText(remarksTitle);

	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
