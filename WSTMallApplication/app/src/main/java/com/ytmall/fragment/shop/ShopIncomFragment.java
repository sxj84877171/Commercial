package com.ytmall.fragment.shop;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.ytmall.R;
import com.ytmall.adapter.ShopIncomeAdapter;
import com.ytmall.bean.ShopIncomeModel;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.widget.MyPullRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 16/12/8.
 */
@FragmentView(id = R.layout.fragment_income_detail)
public class ShopIncomFragment extends BaseFragment {
    @InjectView(id = R.id.pull_income_list)
    private MyPullRefreshListView pull_income_list;
    private List<ShopIncomeModel> list = new ArrayList<>();
    @Override
    public void bindDataForUIElement() {
        tWidget.setCenterViewText("收入明细");
        initData();
        pull_income_list.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        ShopIncomeAdapter adapter = new ShopIncomeAdapter(getActivity(),list);
        pull_income_list.setAdapter(adapter);

    }

    @Override
    protected void bindEvent() {

    }
    private void initData(){
        for (int i = 0;i < 3;i++){
            ShopIncomeModel model = new ShopIncomeModel();
            model.setBalance("100.00");
            model.setDate("1990-11-06");
            model.setImg(R.drawable.ytlogo);
            model.setIncome("100.00");
            model.setNum(2);
            model.setPrice("100.00");
            model.setProName("原装正品原装正品原装正品原装正品原装正品原装正品原装正品");
            model.setProDescrib("规格：原装正品原装正品原装正品原装正品原装正品原装正品原装正品");
            list.add(model);
        }
    }
}
