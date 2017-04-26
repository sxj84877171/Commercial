package com.ytmall.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by lee on 16/2/23.
 */
public class GridviewForScroll extends GridView{
    public GridviewForScroll(Context context) {
        super(context);
    }

    public GridviewForScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridviewForScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
    }
}
