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
# 创建日期：2015-6-25
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
 */
package com.ytmall.fragment.search;

import android.R.anim;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.activity.search.SearchActivity;
import com.ytmall.activity.shoppingCart.ShoppingCartActivity;
import com.ytmall.application.Const;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.fragment.business.BusinessHomeFragment;
import com.ytmall.fragment.goods.GoodsListFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;


@FragmentView(id = R.layout.fragment_search)
public class SearchFragment extends BaseFragment {
	
	@InjectView(id = R.id.clear_search_list_button)
	private Button clear_search_list_button;
	@InjectView(id = R.id.listView)
	private ListView listView;
	
	private ArrayAdapter<String> adapter;

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		if(Const.searchType==1){
			tWidget.changeMode(tWidget.Search_Mode);//商品搜索
		}else{
			tWidget.changeMode(tWidget.Search_Mode);
			tWidget.setTitleSearchType("搜索附件商家");//商家搜索
		}
		tWidget.setSearchAnimaScla();
		tWidget.title_search_edittext.setFocusable(true);
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if(position!=0){
				tWidget.title_search_edittext.setText(adapter.getItem(position));
				tWidget.title_search_edittext.setSelection(tWidget.title_search_edittext.getText().length());
				}
			}
		});
		
		clear_search_list_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Const.cache.clearSearchList();
				listView.setAdapter(null);
			}
		});
		
		tWidget.title_search_edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

				if (actionId == EditorInfo.IME_ACTION_SEARCH) {

					rightClick();

					return true;
				}
				return false;
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		refreshSearchList();
	}

	@Override
	public void leftClick() {
		((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
				getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		getActivity().finish();
	}
	
	private void refreshSearchList(){
		if(Const.cache.getSearchList()!=null){
			
			adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
			adapter.add("历史搜索");
			for(int i=Const.cache.getSearchList().size()-1;i>=0;i--){
			adapter.add(Const.cache.getSearchList().get(i));
			}
			listView.setAdapter(adapter);
		}
	}

	@Override
	public void rightClick() {
		
		String searchTarget = tWidget.title_search_edittext.getText().toString().trim();
		
		if(TextUtils.isEmpty(searchTarget)){
			return;
		}
		
		//遍历一遍历史搜索，不重复则纳入
		if(Const.cache.getSearchList()!=null){
		for(int i=0;i<Const.cache.getSearchList().size();i++){
			if(searchTarget.equals(Const.cache.getSearchList().get(i))){
				break;
			}else if(i==Const.cache.getSearchList().size()-1){
				Const.cache.addSearchTarget(searchTarget);
			}
		}
		}else{
			Const.cache.addSearchTarget(searchTarget);
		}
		
		((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
				getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		
		Intent intent = new Intent();
		intent.putExtra(SearchActivity.SearchTarget_String, searchTarget);
		getActivity().setResult(Activity.RESULT_OK, intent);
		
		getActivity().finish();
		
	}

}
