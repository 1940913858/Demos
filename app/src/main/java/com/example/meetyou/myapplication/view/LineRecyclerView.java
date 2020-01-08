package com.example.meetyou.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;


public class LineRecyclerView extends RecyclerView {

    private Context mContext;

    public LineRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    public LineRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineRecyclerView(Context context) {
        this(context, null);
    }


    private Paint mPaint = new Paint();
    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);

        c.drawLine(300,100,300,1000,mPaint);

    }
}
