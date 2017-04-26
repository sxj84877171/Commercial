package com.ytmall.activity.bank;

import com.ytmall.R;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;

/**
 * WSTMallApplication
 * 作者： Elvis
 * 时间： 2017/4/26
 * 公司：长沙硕铠电子科技有限公司
 * Email：sunxiangjin@soarsky-e.com
 * 公司网址：http://www.soarsky-e.com
 * 公司地址（Add）  ：湖南省长沙市岳麓区麓谷信息港A座8楼
 * 版本：1.0.0.0
 * 邮编：410000
 * 程序功能：
 * 该类为
 */
@FragmentView(id = R.layout.fragment_bank_exchange)
public class BankFragment extends BaseFragment {

    @Override
    public void bindDataForUIElement() {
        tWidget.setCenterViewText("线下转账");
    }

    @Override
    protected void bindEvent() {

    }
}
