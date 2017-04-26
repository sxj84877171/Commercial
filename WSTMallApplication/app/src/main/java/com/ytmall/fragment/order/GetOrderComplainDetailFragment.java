package com.ytmall.fragment.order;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ytmall.R;
import com.ytmall.api.order.GetComplainDetail;
import com.ytmall.application.Const;
import com.ytmall.bean.ComplainDetailbean;
import com.ytmall.bean.OrderComplainList;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.widget.ImagePopWindow;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/10.
 */
@FragmentView(id = R.layout.fragment_complian_detail)
public class GetOrderComplainDetailFragment extends BaseFragment {
    @InjectView(id = R.id.tv_order_id)
    private TextView tv_order_id;
    //用户投诉
    @InjectView(id = R.id.tv_complain_type)
    private TextView tv_complain_type;
    @InjectView(id = R.id.tv_complain_status)
    private TextView tv_complain_status;
    @InjectView(id = R.id.tv_complain_content)
    private TextView tv_complain_content;

    @InjectView(id = R.id.tv_complain_time)
    private TextView tv_complain_time;

    @InjectView(id = R.id.tv_complain_result)
    private TextView tv_complain_result;

    @InjectView(id = R.id.tv_complain_img_flag)
    private TextView tv_complain_img_flag;
    @InjectView(id = R.id.ll_complain_image)
    private LinearLayout ll_complain_image;

    @InjectView(id = R.id.ll_goods_image)
    private LinearLayout ll_goods_image;
    //应诉
    @InjectView(id = R.id.tv_complain_content_response)
    private TextView tv_complain_content_response;

    @InjectView(id = R.id.tv_complain_time_response)
    private TextView tv_complain_time_response;

    @InjectView(id = R.id.tv_complain_img_flag_response)
    private TextView tv_complain_img_flag_response;

    @InjectView(id = R.id.ll_complain_image_response)
    private LinearLayout ll_complain_image_response;

    @InjectView(id = R.id.ll_shop_response)
    private LinearLayout ll_shop_response;

    private GetComplainDetail getComplainDetail=new GetComplainDetail();
    private ComplainDetailbean complainDetailbean=new ComplainDetailbean();
    private String id; //投诉记录ID
    private ImagePopWindow imagePopWindow;
    private GetOrderComplainDetailFragment getOrderComplainDetailFragment=this;
    public GetOrderComplainDetailFragment(String id){
        this.id=id;
    }
    protected void requestSuccess(String url, String data) {
        if (url.contains(getComplainDetail.getA())) {
            try {
                JSONObject jsonreobj = new JSONObject(data);
                JSONObject js=jsonreobj.getJSONObject("data");
             //   Type listType = new TypeToken<ArrayList<ComplainDetailbean>>(){}.getType();
                complainDetailbean = new Gson().fromJson(js.toString(),ComplainDetailbean.class );
                initView();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally {

            }
        }
    }
    @Override
    public void bindDataForUIElement() {
        getComplainDetail();
    }

    @Override
    protected void bindEvent() {

    }
    private void initView(){
        tv_order_id.setText(complainDetailbean.orderId);
        tv_complain_type.setText(getComplainType(complainDetailbean.complainType));
        tv_complain_status.setText(getComplainState(complainDetailbean.complainStatus));
        tv_complain_content.setText(complainDetailbean.complainContent);
        tv_complain_time.setText(complainDetailbean.complainTime);
        if(complainDetailbean.finalResult!=null) {
            tv_complain_result.setText(complainDetailbean.finalResult);
        }else{
            tv_complain_result.setText("暂无结果...");
        }
        //图片
        if(complainDetailbean.complainAnnex!=null){
             final String[] complainImg=complainDetailbean.complainAnnex.split(",");
            //投诉图片
            for(int i=0;i<complainImg.length;i++) {
                ImageView imageView=new ImageView(getActivity());
                final String imageUrl=complainImg[i];
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imagePopWindow=new ImagePopWindow(getActivity(),imageUrl,getOrderComplainDetailFragment);
                        imagePopWindow.showAtLocation(tWidget,0,0, Gravity.CENTER);
                    }
                });
                loadOnImage(
                        Const.BASE_URL + complainImg[i],
                        imageView);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        new ViewGroup.LayoutParams(100, 100));
                layoutParams.leftMargin = 8;
                layoutParams.rightMargin = 8;
                ll_complain_image.addView(imageView, layoutParams);
            }
        }else{
            ll_complain_image.setVisibility(View.GONE);
            tv_complain_img_flag.setVisibility(View.GONE);
        }
        //商品图片
            for(int i=0;i<complainDetailbean.goodsList.size();i++) {
                ImageView imageView=new ImageView(getActivity());
                final String imageUrl=complainDetailbean.goodsList.get(i).goodsThums;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imagePopWindow=new ImagePopWindow(getActivity(),imageUrl,getOrderComplainDetailFragment);
                        imagePopWindow.showAtLocation(tWidget,0,0, Gravity.CENTER);
                    }
                });
            loadOnImage(
                    Const.BASE_URL + complainDetailbean.goodsList.get(i).goodsThums,
                    imageView);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        new ViewGroup.LayoutParams(100, 100));
                layoutParams.leftMargin = 8;
                layoutParams.rightMargin = 8;
                ll_goods_image.addView(imageView, layoutParams);
             }
        //应诉内容
        if(complainDetailbean.respondContent!=null) {
            tv_complain_content_response.setText(complainDetailbean.respondContent);
            tv_complain_time_response.setText(complainDetailbean.respondTime);
        }else{
            ll_shop_response.setVisibility(View.GONE);
        }
        //应诉图片
        if(complainDetailbean.respondAnnex!=null){
            String[] complainImg=complainDetailbean.respondAnnex.split(",");
            for(int i=0;i<complainImg.length;i++) {
                ImageView imageView=new ImageView(getActivity());
                loadOnImage(
                        Const.BASE_URL + complainImg[i],
                        imageView);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        new ViewGroup.LayoutParams(100, 100));
                layoutParams.leftMargin = 8;
                layoutParams.rightMargin = 8;
                ll_complain_image_response.addView(imageView, layoutParams);
            }
        }else{
            ll_complain_image_response.setVisibility(View.GONE);
            tv_complain_img_flag_response.setVisibility(View.GONE);
        }

    }
    /**
     * 0:待处理 1:转给应诉人 2:应诉人回应 3:等待仲裁 4:已仲裁
     */
    private String getComplainState(int satus){
        switch (satus){
            case 0:
                return "待处理";
            case 1:
                return "已转给应诉人";
            case 2:
                return "应诉人回应";
            case 3:
                return "等待仲裁";
            case 4:
                return "已仲裁";
        }
        return "";
    }

    /**
     * 投诉类型 （1:承诺的没有做到 2:未按约定时间发货 3:未按成交价格进行交易 4:恶意骚扰
     */
    private String getComplainType(int type){
        switch (type){
            case 1:
                return "承诺的没有做到";
            case 2:
                return "未按约定时间发货";
            case 3:
                return "未按成交价格进行交易";
            case 4:
                return "恶意骚扰";
        }
        return "";
    }
    private void getComplainDetail(){
        getComplainDetail.tokenId= Const.cache.getTokenId();
        getComplainDetail.id=id;
        request(getComplainDetail);
    }
}
