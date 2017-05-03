package com.ytmall.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ytmall.R;
import com.ytmall.activity.BaseActivity;
import com.ytmall.activity.MainActivity;
import com.ytmall.activity.money.BankAccountActivity;
import com.ytmall.activity.money.EditBankAccountActivity;
import com.ytmall.api.recharge.DelCashAcc;
import com.ytmall.application.Const;
import com.ytmall.bean.GetCashConfBean;
import com.ytmall.bean.YuEPayReturn;
import com.ytmall.fragment.login.LoginFragment;
import com.ytmall.fragment.money.BankAccountFragment;
import com.ytmall.fragment.shoppingCart.ShoppingCartFragment;
import com.ytmall.internet.APICallBack;
import com.ytmall.internet.LocalAPI;
import com.ytmall.util.SharedPreferencesUtils;
import com.ytmall.util.StrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2017/1/23.
 */

public class BankAccounAdapter extends BaseAdapter {
    private Context ctxt;
    private List<GetCashConfBean> list = new ArrayList<>();
    private DelCashAcc deleteParam = new DelCashAcc();
    private String from;
    public BankAccounAdapter(Context ctxt,List<GetCashConfBean> list,String from){
        this.ctxt = ctxt;
        this.list = list;
        this.from = from;

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        HoldItem item;
        if (view == null){
            item = new HoldItem();
            view = LayoutInflater.from(ctxt).inflate(R.layout.item_list_bank_account,null);
            item.txtBankName = (TextView) view.findViewById(R.id.txtBankName);
            item.txtAccountNumber = (TextView) view.findViewById(R.id.txtAccountNumber);
            item.imgDelete = (Button) view.findViewById(R.id.imgDelete);

            view.setTag(item);
        }else {
            item = (HoldItem) view.getTag();
        }
        final GetCashConfBean model = list.get(position);
        item.txtBankName.setText(model.bankName);
        item.txtAccountNumber.setText(model.accNo);
        if (model.accNo.length() > 18){
            item.txtAccountNumber.setText("**** **** ****"+model.accNo.
                    substring(model.accNo.length()-4,model.accNo.length()));

        }
        item.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(model.id,position);



            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ctxt,"item",Toast.LENGTH_SHORT).show();
                if (StrUtil.null2Str(from).equals("ShopMoneyManageFragment")){
                    Intent i = new Intent(ctxt,EditBankAccountActivity.class);
                    i.putExtra("id",model.id);
                    ctxt.startActivity(i);
                }else {
                    Const.cache.setBankAcc(model);
                    ((BankAccountActivity)ctxt).finish();
                }

            }
        });

        return view;
    }
    private class HoldItem{
        private TextView txtBankName,txtAccountNumber;
        private Button imgDelete;

    }
    private void delete(final String bankId, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctxt);
        builder.setMessage("确定删除账户？").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteParam.tokenId = Const.cache.getTokenId();
                deleteParam.id = bankId;
                LocalAPI.deleteAccount(deleteParam, new APICallBack<YuEPayReturn>() {
                    @Override
                    public void success(YuEPayReturn result) {
                        Toast.makeText(ctxt,"删除成功",Toast.LENGTH_SHORT).show();
                        if (Const.cache.getBankAcc() != null){
                            if (bankId.equals(Const.cache.getBankAcc().id)){
                                GetCashConfBean bean = new GetCashConfBean();
                                bean.accNo = "";
                                bean.bankName = "";
                                bean.id = "";
                                bean.accUser = "";
                                bean.accType = "";

                                Const.cache.setBankAcc(bean);
                            }

                        }
                        list.remove(position);
                        notifyDataSetChanged();




                    }

                    @Override
                    public void error(String msg) {
                        Toast.makeText(ctxt,msg,Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        // Create the AlertDialog object and return it
        builder.create().show();
    }
}
