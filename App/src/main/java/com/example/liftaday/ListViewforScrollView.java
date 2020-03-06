package com.example.liftaday;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ListViewforScrollView extends ListView {
    public ListViewforScrollView(Context context) {
        super(context);
    }

    public ListViewforScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewforScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int customSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, customSpec);
    }
}
