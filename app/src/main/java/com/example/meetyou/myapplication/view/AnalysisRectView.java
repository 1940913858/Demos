package com.example.meetyou.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.meetyou.myapplication.R;


/**
 * 西柚周期分析界面  流量和痛经 提示rect
 *
 * @author chensenfa <chensenfa@xiaoyouzi.com>
 * @since 2019/8/23
 */
public class AnalysisRectView extends View {

    private Paint mPaint;
    private Context mContext;

    public AnalysisRectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnalysisRectView(Context context) {
        this(context, null);
    }


    public AnalysisRectView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    private void init() {
        //init paint
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#888888"));
        mPaint.setTextSize(getResources().getDimensionPixelOffset(R.dimen.dp_10));
    }

    private RectF rectF = new RectF();
    private String[] flowColorList = new String[]{"#F66767", "#F98788", "#FABDB3", "#FCD3D3",
            "#FCE8E8"};
    private String[] tongjingColorList = new String[]{"#F8914B", "#F9B07F", "#FBCFB1", "#FCE2D1",
            "#FCEEE5"};


    String text = "剧烈";
    String textLow = "基本无";

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widht = 0;
        int height = 12 * 3;
        widht = getTextWidth(text) + 9 + 28 * 3 + 9 + getTextWidth(textLow);
        setMeasuredDimension(widht, height);
    }

    private int mType = 2;
    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);

        if (mType == 2) {
            //Y轴居中
            Paint.FontMetricsInt fmi = mPaint.getFontMetricsInt();
            int textCenterY = getMeasuredHeight() / 2 - fmi.top / 2 - fmi.bottom / 2;

            c.drawText(text, 0, textCenterY, mPaint);

            for (int i = 4; i >= 0; i--) {
                rectF.left = getTextWidth(text) + 9 + i * 4 * 3;
                rectF.right = rectF.left + 12 * 3;
                rectF.top = 0;
                rectF.bottom = 12 * 3;
                mPaint.setColor(Color.parseColor(flowColorList[i]));
                c.drawRoundRect(rectF, 4 * 3, 4 * 3, mPaint);
            }

            //第五个圈所在X坐标
            int x = getTextWidth(text) + 9 + 28 * 3;

            c.drawText(textLow, x + 9, textCenterY, mPaint);
        }
    }

    public int getTextWidth(String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            mPaint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }


}
