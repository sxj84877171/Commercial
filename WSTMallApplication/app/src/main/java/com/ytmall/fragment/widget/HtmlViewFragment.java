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
# 创建日期：2015-6-30
#
# @Copyright (c) 2015 Tans All right reserved.
#************************************************
*/
package com.ytmall.fragment.widget;

import com.ytmall.R;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @ClassName HtmlViewFragment
 * @Create Date 2015-6-30 下午4:09:48 
 * 
 * @author Tans      QQ     1511895018
 * WSTMall开源商城-合作团队     官网地址:http://www.wstmall.com   联系QQ:707563272
 */
@FragmentView(id = R.layout.fragment_htmlview)
public class HtmlViewFragment extends BaseFragment {

	@InjectView(id = R.id.htmlView)
	private WebView mWebview;

	private String Url;
	private String title;

	public HtmlViewFragment(String Url, String title) {
		this.Url = Url;
		this.title = title;
	}

	private void webViewSetting() {
		WebSettings webSettings = mWebview.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);// 支持缩放
		webSettings.setBuiltInZoomControls(true);// 允许缩放控制
		webSettings.setDisplayZoomControls(false); // 不显示缩放按钮
		webSettings.setLoadWithOverviewMode(true); // 当需要从一个网页跳转到另一个网页时,目标网页仍然在当前WebView中显示,而不是打开系统浏览器
		webSettings.setDefaultTextEncodingName("utf-8");
		webSettings.setSupportMultipleWindows(false);// 禁止网页多窗口
		webSettings.setUseWideViewPort(true);// 任意比例缩放
	}
	
	public void closePop(){
		mWebview.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
	}

	@Override
	public void bindDataForUIElement() {
		tWidget.setCenterViewText(title);
		webViewSetting();
		closePop();
		loading();
	}

	private void loading() {
		mWebview.loadUrl(Url);
	}

	@Override
	protected void bindEvent() {

	}

	@Override
	public void leftClick() {
		getActivity().finish();
	}

}

