package com.ytmall.fragment.shop;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ytmall.R;
import com.ytmall.adapter.ShopOrderProductAdapter;
import com.ytmall.api.shoporder.ShopOrderAccept;
import com.ytmall.api.shoporder.ShopOrderDetail;
import com.ytmall.application.Const;
import com.ytmall.bean.ShopOrderAcceptBean;
import com.ytmall.bean.ShopOrderDetailBean;
import com.ytmall.bean.ShopOrderProduct;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.widget.ListViewForScroll;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2017/1/7.
 */
@FragmentView(id = R.layout.fragment_shop_order_detail)
public class ShopOrderDetailFragment extends BaseFragment {
    @InjectView(id = R.id.txtOrderStatus)
    TextView txtOrderStatus;
    @InjectView(id = R.id.txtName)
    TextView txtName;
    @InjectView(id = R.id.txtPhone)
    TextView txtPhone;
    @InjectView(id = R.id.txtAddress)
    TextView txtAddress;
    @InjectView(id = R.id.txtProPrice)
    TextView txtProPrice;
    @InjectView(id = R.id.txtBanlance)
    TextView txtBanlance;
    @InjectView(id = R.id.txtProSend)
    TextView txtProSend;
    @InjectView(id = R.id.txtAccount)
    TextView txtAccount;
    @InjectView(id = R.id.txtOrderNo)
    TextView txtOrderNo;
    @InjectView(id = R.id.txtOrderTime)
    TextView txtOrderTime;
    @InjectView(id = R.id.listPro)
    ListViewForScroll listPro;
    @InjectView(id = R.id.btnAccept)
    Button btnAccept;

    private String orderId;
    private ShopOrderDetail param;
    private ShopOrderProductAdapter adapter;
    private List<ShopOrderProduct> list = new ArrayList<>();
    private String  status;
    private ShopOrderAccept acceptParam;
    private int position = -1;

    public ShopOrderDetailFragment(String orderId,String status,int position){
        this.status = status;
        this.orderId = orderId;
        this.position = position;

    }

    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(param.getA())){
            Log.d("orderdetail",data);
            try {
                JSONObject jsonObject = new JSONObject(data);
                ShopOrderDetailBean orderDetail = gson.fromJson(jsonObject.get("data").toString(),
                        ShopOrderDetailBean.class);
                list.clear();
                list.addAll(orderDetail.goodsList);
                adapter.notifyDataSetChanged();

                txtName.setText(orderDetail.order.userName);
                txtPhone.setText(orderDetail.order.userPhone);
                txtAddress.setText(orderDetail.order.userAddress);
                txtProPrice.setText(orderDetail.order.totalMoney);
                txtProSend.setText("+ ¥"+orderDetail.order.deliverMoney);
                txtBanlance.setText("- ¥"+orderDetail.order.pd_money);
                txtAccount.setText("¥"+orderDetail.order.realTotalMoney);
                txtOrderNo.setText(orderDetail.order.orderNo);
                txtOrderTime.setText(orderDetail.order.createTime);



            }catch (Exception e){

            }

        }else if (url.contains(acceptParam.getA())){
            try {
                JSONObject jso = new JSONObject(data);
                ShopOrderAcceptBean bean = gson.fromJson(jso.get("data").toString(),ShopOrderAcceptBean.class);
                Log.d("order accept",data);
                if (bean.status != null){
                    Toast.makeText(getActivity(),"受理成功",Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    ShopOrderManageFragment.delete(position);
                }

            }catch (Exception ex){

            }


        }
    }

    @Override
    protected void flagFailed(String url) {
        super.flagFailed(url);

    }

    @Override
    public void bindDataForUIElement() {
        if (status.equals("未受理")){
            btnAccept.setClickable(true);
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    acceptParam = new ShopOrderAccept();

                    acceptParam.orderId = orderId;
                    acceptParam.tokenId = Const.cache.getTokenId();
                    request(acceptParam);


                }
            });

        }else {
            btnAccept.setVisibility(View.GONE);
        }

        getDetail();
        adapter = new ShopOrderProductAdapter(getActivity(),list);
        listPro.setAdapter(adapter);
        txtOrderStatus.setText("订单状态:"+status);

    }
    private  void getDetail(){
        param = new ShopOrderDetail();
        param.orderId = orderId;
        param.tokenId = Const.cache.getTokenId();
        request(param);

    }

    @Override
    protected void bindEvent() {

    }
}
