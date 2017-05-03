package com.ytmall.activity.bank;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ytmall.R;
import com.ytmall.activity.MainActivity;
import com.ytmall.api.UploadPic;
import com.ytmall.application.Const;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.util.FileUtil;
import com.ytmall.util.FragmentView;
import com.ytmall.util.InjectView;
import com.ytmall.widget.BottomPopWindow;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

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
public class BankFragment extends BaseFragment implements View.OnClickListener , BottomPopWindow.OnMenuSelect{

    @InjectView(id = R.id.bank_name)
    //银行或支付宝
    private EditText bank_name;

    @InjectView(id = R.id.use_name)
    //请输入户名
    private EditText use_name;

    @InjectView(id = R.id.card_no)
    //银行卡号或支付宝账号
    private EditText card_no;

    @InjectView(id = R.id.accout_num)
    //请输入转账金额
    private EditText accout_num;

    @InjectView(id = R.id.account_from)
    //请输入流水号
    private EditText account_from;

    @InjectView(id = R.id.back_score)
    //充值账户
    private TextView back_score;

    @InjectView(id=R.id.choose_type)
    private View choose_type;

    // 提交
    @InjectView(id = R.id.submit_button)
    private View submit_button;

    @InjectView(id=R.id.back_score_phone)
    private View back_score_phone;

    @InjectView(id=R.id.path_load)
    private TextView path_load;

    private int type = 0 ;

    private String path ;
    @Override
    public void bindDataForUIElement() {
        tWidget.setCenterViewText("线下转账");
    }

    @Override
    protected void bindEvent() {
        submit_button.setOnClickListener(this);
        choose_type.setOnClickListener(this);
        back_score_phone.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.choose_type){
            new android.support.v7.app.AlertDialog.Builder(getActivity()).setSingleChoiceItems(new String[]{"商城充值", "银堂宝充值"}, type, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    type = which;
                    if (which == 0) {
                        back_score.setText("商城充值");
                    } else {
                        back_score.setText("云堂充值");
                    }
                    dialog.dismiss();
                }
            }).show();
        }if(v.getId() == R.id.back_score_phone){
            showButtomPop(this, new String[] { "拍照", "我的相册" });
        } else{
            BankBean bankBean = new BankBean();
            bankBean.bank_name = bank_name.getText().toString();
            bankBean.user_name = use_name.getText().toString();
            bankBean.card_no = card_no.getText().toString();
            String accout_num_str = accout_num.getText().toString();
            bankBean.transfer_sn = account_from.getText().toString();
            String back_score_str = back_score.getText().toString();
            bankBean.image = path;
            bankBean.small_image = path;
            bankBean.type = "1";
            bankBean.tokenId = Const.cache.getTokenId();

            if (TextUtils.isEmpty(bankBean.bank_name)) {
                Toast.makeText(getActivity(), "银行或支付宝不能为空", Toast.LENGTH_LONG).show();
                bank_name.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(bankBean.user_name)) {
                Toast.makeText(getActivity(), "户名不能为空", Toast.LENGTH_LONG).show();
                use_name.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(bankBean.card_no)) {
                Toast.makeText(getActivity(), "银行卡号或支付宝账号不能为空", Toast.LENGTH_LONG).show();
                card_no.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(accout_num_str)) {
                Toast.makeText(getActivity(), "转账金额不能为空", Toast.LENGTH_LONG).show();
                accout_num.requestFocus();
                return;
            }
            try {
                bankBean.money = Integer.parseInt(accout_num_str.trim());
            }catch (Exception ex){
                Toast.makeText(getActivity(), "转账金额填写错误", Toast.LENGTH_LONG).show();
                return ;
            }

            if (TextUtils.isEmpty(bankBean.transfer_sn)) {
                Toast.makeText(getActivity(), "流水号不能为空", Toast.LENGTH_LONG).show();
                back_score.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(back_score_str)) {
                Toast.makeText(getActivity(), "充值账户不能为空", Toast.LENGTH_LONG).show();
                back_score.requestFocus();
                return;
            }

            if(TextUtils.isEmpty(bankBean.image)){
                Toast.makeText(getActivity(), "请上传图片凭证", Toast.LENGTH_LONG).show();
                return;
            }

            request(bankBean);
        }

    }


    @Override
    protected void requestSuccess(String url, String data) {
        super.requestSuccess(url, data);
        if(url.contains("uploadPic")){
            try {
                JSONObject jsonobj = new JSONObject(data);
                jsonobj = jsonobj.getJSONObject("Filedata");
                path = jsonobj.getString("savepath") + jsonobj.getString("savename");
                path_load.setText(path);
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return;
        }
        getActivity().finish();
        Toast.makeText(getActivity(),"上传成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemMenuSelect(int position) {
        // TODO Auto-generated method stub
        switch (position) {
            case 0:
                if (MainActivity.getCaramePermission()){
                    startCamera();
                }

                break;
            case 1:
                if (MainActivity.getWritePermission()){
                   startSelectPhoto();
                }

                break;
        }
        buttomPop.dismiss();
    }

    @Override
    public void onCancelSelect() {

    }

    private Uri selectedImageUri;


    public void startCamera() {// 用户点击拍照
        String FILE_NAME = UUID.randomUUID() + ".jpg";
        File photo = FileUtil.getNewFile(getActivity(), FILE_NAME);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (photo != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
            selectedImageUri = Uri.fromFile(photo);
            startActivityForResult(intent, 100);
        }
    }

    public void startSelectPhoto() {// 用户点击 从相册获取
        Intent mIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(mIntent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("Test","requestCode" + requestCode);

        switch (requestCode) {
            case 100:
                Uri origina = data.getData();
                uploadPic.Filedata = new File(getFilePath(origina));
                request(uploadPic);
                break;
            case 101:// 选择相册
                if (data != null) {
                    Uri originalUri = data.getData();
                    uploadPic.Filedata = new File(getFilePath(originalUri));
                    request(uploadPic);
                }
                break;
        }
    }

    private UploadPic uploadPic = new UploadPic();

    /**
     * 根据Uri返回文件路径
     */
    private String getFilePath(Uri mUri) {
        try {
            if (mUri.getScheme().equals("file")) {
                return mUri.getPath();
            } else {
                return getFilePathByUri(mUri);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private String getFilePathByUri(Uri mUri) throws FileNotFoundException {
        String imgPath;
        Cursor cursor = getActivity().getContentResolver().query(mUri, null, null, null, null);
        cursor.moveToFirst();
        imgPath = cursor.getString(1); // 图片文件路径
        return imgPath;
    }
}
