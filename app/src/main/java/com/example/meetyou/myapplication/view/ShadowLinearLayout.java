package com.example.meetyou.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.meetyou.myapplication.R;


/**
 * ClassName: ShadowLinearLayout <br/> 带阴影的LinearLayout
 * Function: TODO ADD FUNCTION. <br/>
 * Date:2019-6-25
 *
 * @author chensenfa@xiaoyouzi.com
 * @version 1.2.1
 */
public class ShadowLinearLayout extends LinearLayout {

    private Context mContext;
    private int minWidth;

    public ShadowLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public ShadowLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowLinearLayout(Context context) {
        this(context, null);
    }

    private void init() {
        this.setWillNotDraw(false);
    }


    @Override
    protected void onDraw(Canvas pCanvas) {
        super.onDraw(pCanvas);

        RectF rectShadow = new RectF();
        rectShadow.left = 15;
        rectShadow.top = 15;
        rectShadow.right = getMeasuredWidth() - 15;
        rectShadow.bottom = getMeasuredHeight() - 24;

        Paint shadowPaint = new Paint();
        shadowPaint.setColor(Color.TRANSPARENT);
        shadowPaint.setAntiAlias(true);
        shadowPaint.setShadowLayer(30, 0, 0, Color.parseColor("#B3FD6976"));
        shadowPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        pCanvas.drawRoundRect(rectShadow, 30, 30, shadowPaint);


        Paint mOriginPaint = new Paint();
        mOriginPaint.setColor(Color.parseColor("#ff0000"));

        RectF rect = new RectF();
        rect.left = 0;
        rect.top = 0;
        rect.right = getMeasuredWidth();
        rect.bottom = getMeasuredHeight() -18;
        pCanvas.drawRoundRect(rect, 30, 30, mOriginPaint);
    }


}
