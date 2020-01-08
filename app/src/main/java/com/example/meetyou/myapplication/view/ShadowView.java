package com.example.meetyou.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


public class ShadowView extends View {

    private int mNumber = 0;
    private Context mContext;
    private int width;

    public ShadowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    public ShadowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowView(Context context) {
        this(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas pCanvas) {
        super.onDraw(pCanvas);
        Paint mOriginPaint = new Paint();
        mOriginPaint.setAntiAlias(true);
        mOriginPaint.setColor(Color.parseColor("#FD5858"));

        RectF rects = new RectF();
        rects.left = 60;
        rects.top = 0;
        rects.right=600;
        rects.bottom = 90;

        int mShadowRadius = 10;

        RectF rectd= new RectF(rects.left + mShadowRadius, rects.top + mShadowRadius, rects.right - mShadowRadius,
                rects.bottom);

        Paint mShadowPaint = new Paint();
        mShadowPaint.setColor(Color.TRANSPARENT);
        mShadowPaint.setAntiAlias(true);
        mShadowPaint.setShadowLayer(30, 0, 18, Color.parseColor("#4DFD6976"));
        mShadowPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));


        setLayerType(LAYER_TYPE_SOFTWARE, null);
        pCanvas.drawRoundRect(rectd, 12, 12, mShadowPaint);

        pCanvas.drawRoundRect(rects, 30, 30, mOriginPaint);
    }


}
