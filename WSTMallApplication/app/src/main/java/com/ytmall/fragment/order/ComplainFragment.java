package com.ytmall.fragment.order;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ytmall.R;
import com.ytmall.activity.order.OrderActivity;
import com.ytmall.activity.order.complain.AlbumActivity;
import com.ytmall.activity.order.complain.ComplainActivity;
import com.ytmall.activity.order.complain.GalleryActivity;
import com.ytmall.activity.user.MineActivity;
import com.ytmall.api.UploadPic;
import com.ytmall.api.order.SaveComplain;
import com.ytmall.application.Const;
import com.ytmall.bean.OrderBean;
import com.ytmall.fragment.BaseFragment;
import com.ytmall.fragment.order_goods.OrderForGoodsFragment;
import com.ytmall.util.FileUtil;
import com.ytmall.util.FragmentView;
import com.ytmall.util.ImageUtils;
import com.ytmall.util.InjectView;
import com.ytmall.util.slectphotos.Bimp;
import com.ytmall.util.slectphotos.FileUtils;
import com.ytmall.util.slectphotos.ImageItem;
import com.ytmall.util.slectphotos.PublicWay;
import com.ytmall.widget.BottomPopWindow;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2016/4/7.
 */
@FragmentView(id = R.layout.fragment_complain)
public class ComplainFragment extends BaseFragment implements View.OnClickListener,BottomPopWindow.OnMenuSelect {
    @InjectView(id = R.id.tv_order_shop_name)
    private TextView tv_order_shop_name;
    @InjectView(id = R.id.ll_good_list)
    private LinearLayout ll_good_list;
    private int position;
    private OrderBean orderBean;
    //投诉类型
    @InjectView(id = R.id.rl_complain_type_1)
    private RelativeLayout rl_complain_type_1;
    @InjectView(id = R.id.tv_complain_flag_1)
    private TextView tv_complain_flag_1;

    @InjectView(id = R.id.rl_complain_type_2)
    private RelativeLayout rl_complain_type_2;
    @InjectView(id = R.id.tv_complain_flag_2)
    private TextView tv_complain_flag_2;

    @InjectView(id = R.id.rl_complain_type_3)
    private RelativeLayout rl_complain_type_3;
    @InjectView(id = R.id.tv_complain_flag_3)
    private TextView tv_complain_flag_3;

    @InjectView(id = R.id.rl_complain_type_4)
    private RelativeLayout rl_complain_type_4;
    @InjectView(id = R.id.tv_complain_flag_4)
    private TextView tv_complain_flag_4;
    @InjectView(id = R.id.et_complain)
    private EditText et_complain;

    //上传图片
    @InjectView(id = R.id.tv_select_photo)
    private TextView tv_select_photo;

    //投诉类型 默认为1
    private int complainType=1;
    //投诉接口
    private SaveComplain saveComplain=new SaveComplain();
    //上传图片接口
    private UploadPic uploadComplainPic=new UploadPic();
    /**上传图片服务器返回的路径*/
    private List<String> complainSearchPath=new ArrayList<>();
    //上传附件
    //显示要上传的图片
    @InjectView(id = R.id.gv_complain_image)
    private GridView gv_complain_image;
    private GridAdapter adapter;
    public static Bitmap bimap ;
    //接口一次只能传一个图片，标志是否全部上传完图片
    private int isUploadImgDone=0;
    @Override
    protected void requestSuccess(String url, String data) {
        if (url.contains(uploadComplainPic.getA())) {
            try {
                JSONObject jsonobj = new JSONObject(data);
                jsonobj=jsonobj.getJSONObject("Filedata");
                String userPhoto=jsonobj.getString("savepath")+jsonobj.getString("savename");
                Log.e("userPhoto",userPhoto);
                complainSearchPath.add(userPhoto);
                isUploadImgDone++;
                if(isUploadImgDone==Bimp.tempSelectBitmap.size()) {
                    saveComplain();
                    complainSearchPath.clear();
                    isUploadImgDone=0;
                }
            } catch (JSONException e) {
            }
        }else if (url.contains(saveComplain.getA())) {
                Toast.makeText(getActivity(),"提交成功",Toast.LENGTH_SHORT).show();
                 OrderActivity.isNeedRefresh=true;
                getActivity().finish();
        }
    }
    public ComplainFragment(int position){
        this.position=position;
        orderBean=OrderManagement.orderbeanlist.get(position);
    }
    @Override
    public void bindDataForUIElement() {
        initView();
        initComplainPhotos();
    }
    @Override
    protected void bindEvent() {
        rl_complain_type_1.setOnClickListener(this);
        rl_complain_type_2.setOnClickListener(this);
        rl_complain_type_3.setOnClickListener(this);
        rl_complain_type_4.setOnClickListener(this);
        tv_select_photo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.rl_complain_type_1:
                    setComplainTypeSelect(1);
                    break;
                case R.id.rl_complain_type_2:
                    setComplainTypeSelect(2);
                    break;
                case R.id.rl_complain_type_3:
                    setComplainTypeSelect(3);
                    break;
                case R.id.rl_complain_type_4:
                    setComplainTypeSelect(4);
                    break;
                case R.id.tv_select_photo://上传附件
                case R.id.head_image_layout:
                    showButtomPop(this, new String[]{"拍照", "我的相册"});
                    break;
            }
    }

    private void initView(){
        tv_order_shop_name.setText(orderBean.shopName);
        tWidget.setRightBtnText("提交");
        bimap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.icon_addpic_unfocused);
        initComplainGoodList();
    }
    private void initComplainGoodList(){
        for(int i=0;i<orderBean.goodlist.size();i++) {
            ImageView imageView=new ImageView(getActivity());
            loadOnImage(
                    Const.BASE_URL
                            + orderBean.goodlist.get(i).goodsThums,
                    imageView);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(100, 100));
            layoutParams.leftMargin = 8;
            layoutParams.rightMargin = 8;
            ll_good_list.addView(imageView, layoutParams);
        }
    }
    private void resetComplainTypeSelect(){
        tv_complain_flag_1.setBackgroundResource(R.drawable.btn_c_close);
        tv_complain_flag_2.setBackgroundResource(R.drawable.btn_c_close);
        tv_complain_flag_3.setBackgroundResource(R.drawable.btn_c_close);
        tv_complain_flag_4.setBackgroundResource(R.drawable.btn_c_close);
    }
    private void setComplainTypeSelect(int item){
        complainType=item;
        resetComplainTypeSelect();
        switch (item){
            case 1:
                tv_complain_flag_1.setBackgroundResource(R.drawable.btn_c_open);
                break;
            case 2:
                tv_complain_flag_2.setBackgroundResource(R.drawable.btn_c_open);
                break;
            case 3:
                tv_complain_flag_3.setBackgroundResource(R.drawable.btn_c_open);
                break;
            case 4:
                tv_complain_flag_4.setBackgroundResource(R.drawable.btn_c_open);
                break;
        }
    }
    @Override
    public void rightClick() {
       // saveComplain();
        if(et_complain.getText().toString().equals("")){
            Toast.makeText(getActivity(),"请填写投诉内容",Toast.LENGTH_SHORT).show();
        }else {
            if (Bimp.tempSelectBitmap.size() != 0) {
                uploadComplainPic();
            } else {
                saveComplain();
            }
        }
    }
    /**
     * 提交投诉
     */
    private void saveComplain(){
        if(et_complain.getText().toString().equals("")){
            Toast.makeText(getActivity(),"请填写投诉内容",Toast.LENGTH_SHORT).show();
        }else {
            saveComplain.tokenId = Const.cache.getTokenId();
            saveComplain.orderId = orderBean.orderId;
            saveComplain.complainType = complainType;
            saveComplain.complainContent = et_complain.getText().toString();
            StringBuilder complainString=new StringBuilder();
            if(Bimp.tempSelectBitmap.size()!=0) {
                for (int i = 0; i < complainSearchPath.size(); i++) {
                    complainString.append(complainSearchPath.get(i) + ",");
                }
                saveComplain.complainAnnex = complainString.toString().substring(0, complainString.toString().length() - 1);
            }
            request(saveComplain);
        }
    }

    /**
     * 上传图片
     */
    private void uploadComplainPic(){
        showDialog();
        for(int i=0;i<Bimp.tempSelectBitmap.size();i++) {
            Log.e("FileDir",Bimp.tempSelectBitmap.get(i).getImagePath());
           // uploadComplainPic.Filedata = new File(Bimp.tempSelectBitmap.get(i).getImagePath());
            uploadComplainPic.Filedata =compressComplainPhoto(i);
            uploadComplainPic.dir = "complains";
            requestNoDialog(uploadComplainPic);
        }
    }

    /**
     * 压缩上传的图片
     */
    private File compressComplainPhoto(int i){
        FileInputStream fis = null;
        File file=null;
        try {
            fis = new FileInputStream(Bimp.tempSelectBitmap.get(i).getImagePath());
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            Bitmap compressBimay = ImageUtils.compImage(bitmap,1024);//超过1M进行压缩
            file = FileUtil.getNewFile(getActivity(), UUID.randomUUID() + ".jpg");
            saveDrawableToCache(compressBimay, file);
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }
    /**
     * 将Bitmap放入缓存，
     */
    private boolean saveDrawableToCache(Bitmap bitmap, File file) {
        try {
            OutputStream outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private void initComplainPhotos(){
        gv_complain_image.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(getActivity());
        adapter.update();
        gv_complain_image.setAdapter(adapter);
        gv_complain_image.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == Bimp.tempSelectBitmap.size()) {
                    //显示图片
                    showPopWindows();
                } else {
                    Intent intent = new Intent(getActivity(),
                            GalleryActivity.class);
                    intent.putExtra("position", "1");
                    intent.putExtra("ID", arg2);
                    startActivity(intent);
                }
            }
        });
    }

    private void showPopWindows(){
        showButtomPop(this, new String[]{"拍照", "我的相册"});
    }
    @Override
    public void onItemMenuSelect(int position) {
        switch (position) {
            case 0://拍照
                photo();
                break;
            case 1: //相册
                Intent intent = new Intent(getActivity(),
                        AlbumActivity.class);
                startActivity(intent);
                break;
        }
        buttomPop.dismiss();
    }

    @Override
    public void onCancelSelect() {
        buttomPop.dismiss();
    }

    //
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int selectedPosition = -1;
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            if(Bimp.tempSelectBitmap.size() == PublicWay.num){
                return PublicWay.num;
            }
            return (Bimp.tempSelectBitmap.size() + 1);
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_published_grida,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position ==Bimp.tempSelectBitmap.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.icon_addpic_unfocused));
                if (position == PublicWay.num) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
            }

            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        Log.e("handler","handleMessage");
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (Bimp.max == Bimp.tempSelectBitmap.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            Bimp.max += 1;
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }
                }
            }).start();
        }
    }
    public String getString(String s) {
        String path = null;
        if (s == null)
            return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.update();
    }

    public static final int TAKE_PICTURE = 0x000001;

    public void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ComplainFragment.TAKE_PICTURE:
                if (Bimp.tempSelectBitmap.size() < PublicWay.num) {
                    String fileName = String.valueOf(System.currentTimeMillis());
                    if(data!=null) {
                        Bitmap bm = (Bitmap) data.getExtras().get("data");
                        String filePath = FileUtils.saveBitmap(bm, fileName);
                        ImageItem takePhoto = new ImageItem();
                        takePhoto.setBitmap(bm);
                        takePhoto.setImagePath(filePath);
                        Bimp.tempSelectBitmap.add(takePhoto);
                    }
                }
                break;
        }
    }
}
