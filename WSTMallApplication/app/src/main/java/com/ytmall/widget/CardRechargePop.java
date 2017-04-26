package com.ytmall.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ytmall.R;
import com.ytmall.adapter.CardRechargePopAdapter;

import java.util.List;

/**
 * Created by lee on 2017/1/18.
 */

public class CardRechargePop extends PopupWindow {
    private Context ctxt;
    private String title;
    public ListView listView;
    private List<String> list;
    public String text;
//    private OnItem itemClick;
    public CardRechargePop(Context ctxt, String title, final List<String> list){
        this.ctxt = ctxt;
        this.title = title;
        this.list = list;
//        this.itemClick = itemClick;

        final View view = LayoutInflater.from(ctxt).inflate(R.layout.pop_card_select,null);
        this.setContentView(view);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);

        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);

        listView = (ListView) view.findViewById(R.id.listview);
        TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        CardRechargePopAdapter adapter = new CardRechargePopAdapter(ctxt,list);
        listView.setAdapter(adapter);

        txtTitle.setText(title);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                text = list.get(position);
//
//            }
//        });

        view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.llCardBg).getTop();
                int bottom = view.findViewById(R.id.llCardBg).getBottom();
                int left = view.findViewById(R.id.llCardBg).getLeft();
                int right = view.findViewById(R.id.llCardBg).getRight();
                int y=(int) event.getY();
                int x = (int) event.getX();
                if(event.getAction()== MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();

                    }
                }
                if(event.getAction()== MotionEvent.ACTION_UP){
                    if(y>bottom){
                        dismiss();


                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if (x < left){
                        dismiss();
                    }

                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if (x > right){
                        dismiss();
                    }

                }
                return true;
            }
        });
    }

    private void setBankList(){

    }




}
