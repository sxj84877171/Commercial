package com.ytmall.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by lee on 16/3/11.
 */
public class ListViewForScroll extends ListView {
    public ListViewForScroll(Context ctxt){
        super(ctxt);
    }
    public ListViewForScroll(Context ctxt, AttributeSet attr){
        super(ctxt,attr);

    }
    public ListViewForScroll(Context ctxt, AttributeSet attr, int defStyleAttr){
        super(ctxt,attr,defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
    }
}
