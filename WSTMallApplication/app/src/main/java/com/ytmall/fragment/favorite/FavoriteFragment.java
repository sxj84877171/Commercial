package com.ytmall.fragment.favorite;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ytmall.R;
import com.ytmall.activity.goods.GoodsActivity;
import com.ytmall.activity.nearbybusiness.ShopHomePageActivity;
import com.ytmall.adapter.FavoriteGoodsAdapter;
import com.ytmall.adapter.FavoriteShopsAdapter;
import com.ytmall.api.favorite.Favorite;
import com.ytmall.api.favorite.QueryGoodsByPage;
import com.ytmall.api.favorite.QueryShopsByPage;
import com.ytmall.application.Const;
import com.ytmall.bean.FavoriteGoods;
import com.ytmall.bean.FavoriteShop;
import com.ytmall.domain.ShoppingCart;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.fragment.business.BusinessHomeFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.widget.LoadingListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/26.
 */
@FragmentView(id = R.layout.fragment_favorite)
public class FavoriteFragment extends BaseFragment {
    @InjectView(id = R.id.tl_navigation)
    private TabLayout tl_navigation;

    @InjectView(id = R.id.vp_favorite)
    private ViewPager vp_favorite;

    private FavoriteVPAdapter favoriteVPAdapter;
    private List<View> favoriteViewList = new ArrayList<>();

    //关注的商品
    private int goodsPage;
    private int goodsTotalPage;
    private QueryGoodsByPage queryGoodsByPage = new QueryGoodsByPage();
    private List<FavoriteGoods> favoriteGoodsList = new ArrayList<>();
    private FavoriteGoodsAdapter favoriteGoodsAdapter;
    private LoadingListView favoriteGoodsLv;
    //关注的店铺
    private int shopPage;
    private int shopsTotalPage;
    private QueryShopsByPage queryShopsByPage = new QueryShopsByPage();
    private List<FavoriteShop> favoriteShopList = new ArrayList<>();
    private FavoriteShopsAdapter favoriteShopAdapter;
    private LoadingListView favoriteShopsLv;

    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(queryGoodsByPage.getA())) {
            try {
                JSONObject jsonObject = new JSONObject(data);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                goodsTotalPage = jsonObject.getInt("totalPage");
                Type listType = new TypeToken<ArrayList<FavoriteGoods>>() {
                }.getType();
                List<FavoriteGoods> tempFavoriteGoodsList = new Gson().fromJson(jsonArray.toString(), listType);
                favoriteGoodsList.addAll(tempFavoriteGoodsList);
                favoriteGoodsAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                favoriteGoodsLv.setOnRefreshFinish();
            }
        }else if(url.contains(queryShopsByPage.getA())){
            try {
                JSONObject jsonObject = new JSONObject(data);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                shopsTotalPage = jsonObject.getInt("totalPage");
                Type listType = new TypeToken<ArrayList<FavoriteShop>>() {
                }.getType();
                List<FavoriteShop> tempFavoriteShopsList = new Gson().fromJson(jsonArray.toString(), listType);
                favoriteShopList.addAll(tempFavoriteShopsList);
                favoriteShopAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                favoriteShopsLv.setOnRefreshFinish();
            }
        }

    }

    @Override
    protected void flagFailed(String url) {
        favoriteGoodsLv.setOnRefreshFinish();
    }

    @Override
    public void bindDataForUIElement() {
        tWidget.setCenterViewText("关注");
        initFavoriteGoodsView();
        initNavigation();
        goodsPage = 1;
        shopPage=1;
        getFavoriteGoods();
    }

    @Override
    protected void bindEvent() {
        tl_navigation.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                    if(tab.getPosition()==0){
                        vp_favorite.setCurrentItem(0);
                    }else{
                        getFavoriteShops();
                        vp_favorite.setCurrentItem(1);
                    }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void getFavoriteGoods() {
        queryGoodsByPage.tokenId = Const.cache.getTokenId();
        queryGoodsByPage.p = goodsPage;
        requestNoDialog(queryGoodsByPage);
    }
    private void getFavoriteShops(){
        if(favoriteShopList.size()==0) {
            queryShopsByPage.tokenId = Const.cache.getTokenId();
            queryShopsByPage.p = shopPage;
            requestNoDialog(queryShopsByPage);
        }
    }

    private void initFavoriteGoodsView() {

        LinearLayout favoriteGoodsView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.vp_favorite, null);
        favoriteGoodsLv = (LoadingListView) favoriteGoodsView.findViewById(R.id.ll_loadingListView);
        favoriteGoodsAdapter = new FavoriteGoodsAdapter(getActivity(), favoriteGoodsList);
        favoriteGoodsLv.setAdapter(favoriteGoodsAdapter);
        favoriteViewList.add(favoriteGoodsView);
        favoriteGoodsLv.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GoodsActivity.class);
                intent.putExtra(GoodsActivity.goodsID,
                        favoriteGoodsList.get(position).goodsId);
                startActivity(intent);
            }
        });
        favoriteGoodsLv.setOnRefresh(new LoadingListView.KateOnRefreshListener() {
            @Override
            public void onReFresh() {
                goodsPage = 1;
                favoriteGoodsList.clear();
                favoriteGoodsAdapter.notifyDataSetChanged();
                getFavoriteGoods();
            }

            @Override
            public void onLastItemVisible() {
                goodsPage++;
                if (goodsPage <= goodsTotalPage) {
                    getFavoriteGoods();
                } else {
                    favoriteGoodsLv.setLastPagerStatus(true);
                }

            }
        });

        LinearLayout favoriteShopsView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.vp_favorite, null);
        favoriteShopsLv = (LoadingListView) favoriteShopsView.findViewById(R.id.ll_loadingListView);
        favoriteShopAdapter = new FavoriteShopsAdapter(getActivity(), favoriteShopList);
        favoriteShopsLv.setAdapter(favoriteShopAdapter);
        favoriteViewList.add(favoriteShopsView);

        favoriteShopsLv.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(getActivity(), ShopHomePageActivity.class);
                intent.putExtra("shopId",favoriteShopList.get(position).shopId);
                intent.putExtra("shopName",favoriteShopList.get(position).shopName);
                startActivity(intent);
            }
        });
        favoriteShopsLv.setOnRefresh(new LoadingListView.KateOnRefreshListener() {
            @Override
            public void onReFresh() {
                shopPage = 1;
                favoriteShopList.clear();
                favoriteShopAdapter.notifyDataSetChanged();
                getFavoriteShops();
            }

            @Override
            public void onLastItemVisible() {
                shopPage++;
                if (shopPage <= shopsTotalPage) {
                    getFavoriteShops();
                } else {
                    favoriteShopsLv.setLastPagerStatus(true);
                }
            }
        });
    }

    private void initNavigation() {
        tl_navigation.setTabMode(TabLayout.MODE_FIXED);
        favoriteVPAdapter = new FavoriteVPAdapter(favoriteViewList);
        vp_favorite.setAdapter(favoriteVPAdapter);
        tl_navigation.setupWithViewPager(vp_favorite);
        tl_navigation.setTabsFromPagerAdapter(favoriteVPAdapter);
        tl_navigation.getTabAt(0).setText("商品");
        tl_navigation.getTabAt(1).setText("店铺");
    }

    static class FavoriteVPAdapter extends PagerAdapter {
        private List<View> favoriteViewList;

        public FavoriteVPAdapter(List<View> favoriteViewList) {
            this.favoriteViewList = favoriteViewList;
        }

        @Override
        public int getCount() {
            return favoriteViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(favoriteViewList.get(position));
            return favoriteViewList.get(position);
        }
    }

}
