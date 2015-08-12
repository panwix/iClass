package com.panwix.iclass.CustomWidgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Panwix on 15/7/22.
 */
public class myLinearLayout extends LinearLayout {

    public myLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(-12, getMeasuredWidth()/2, getMeasuredHeight()/2);
        super.onDraw(canvas);
    }
}
