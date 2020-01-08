package com.example.meetyou.myapplication.progresscolor;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.example.meetyou.myapplication.R;

/**
 * 作者： q2
 * 时间： 2017/8/10
 */

public class RoundedRectangleProgress extends View {


    private int mTextStartX;
    private int mTextStartY;

    //方向
    private String mText = "";
    private Paint mPaint;
    private int mTextSize;

    private int mOriginColor = 0xff000000;
    private int mChangeColor = 0xffff0000;

    private int mTextOriginColor = 0xff000000;
    private int mTextChangeColor = 0xffff0000;
    private Rect mTextBound = new Rect();
    private int mTextWidth;
    private int mTextHeight;
    private float mProgress;
    private int screenWidth;
    private RectF rect;


    public RoundedRectangleProgress(Context context) {
        this(context, null);
    }

    public RoundedRectangleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ColorText);
        mText = ta.getString(R.styleable.ColorText_text);
        mTextSize = ta.getDimensionPixelSize(R.styleable.ColorText_text_size, mTextSize);
        mTextOriginColor = ta.getColor(R.styleable.ColorText_text_origin_color, mTextOriginColor);
        mTextChangeColor = ta.getColor(R.styleable.ColorText_text_change_color, mTextChangeColor);

        mOriginColor = ta.getColor(R.styleable.ColorText_origin_color, mOriginColor);
        mChangeColor = ta.getColor(R.styleable.ColorText_change_color, mChangeColor);
        mProgress = ta.getFloat(R.styleable.ColorText_progress, 0);
        ta.recycle();


        getScreenWidth(context);
        initView();
    }


    public int getScreenWidth(Context c) {
        WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return screenWidth = size.x;

    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mTextSize);
    }

    //测量文本内容的宽高
    private void measureText() {
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        mTextHeight = (int) Math.ceil(fm.descent - fm.top);
        mPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
        mTextHeight = mTextBound.height();
        mTextWidth = (int) mPaint.measureText(mText);
        if (mTextWidth > screenWidth) {
            int length = (int) (((float) (screenWidth - mTextHeight) / (float) mTextWidth) *
                    mText.length());
            mText = mText.substring(0, length);
            mTextWidth = (int) mPaint.measureText(mText);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureText();
        //从新测量控件的大小，确保在没有指定控件大小的情况下正常显示
        int height = measureHeight(heightMeasureSpec);
//        int width = measureWidth(widthMeasureSpec);

        //最大180，按180份平分
//        if (circle_max_day < 30) {
//            circle_max_day = 30;
//        }
        float itemWidth = 27.9f;
        //根据数值计算长度
        int width = (int) (itemWidth * 26);

        if (width < 100) {//限制最短安全距离
            width = 100;
        }
        if (width > getMeasuredWidth()) {//限制最大长度
            width = getMeasuredWidth();
        }

        setMeasuredDimension(width, height);
        mTextStartX = getMeasuredWidth() - mTextWidth;
        mTextStartY = getMeasuredHeight() / 2 - mTextHeight / 2;

    }

    private int measureHeight(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int val = MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = val;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = mTextHeight;
                result += getPaddingTop() + getPaddingBottom();
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
        return result;
    }

    private int measureWidth(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int val = MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = val;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                // result = mTextBound.width();
                result = mTextWidth + getMeasuredHeight();
                result += getPaddingLeft() + getPaddingRight();
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
        return result;
    }

    private boolean isFirst = true;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#0DFD6976"));
        RectF rectss = new RectF(0, 9, getMeasuredWidth(), getMeasuredHeight()-27+9);
        canvas.drawRoundRect(rectss, getMeasuredWidth() / 2, getMeasuredWidth() / 2, paint);

        Paint paints = new Paint(Paint.ANTI_ALIAS_FLAG);
        paints.setColor(Color.parseColor("#10FD6976"));
        RectF rectsss = new RectF(0, 6, getMeasuredWidth(), getMeasuredHeight()-27+6);
        canvas.drawRoundRect(rectsss, getMeasuredWidth() / 2, getMeasuredWidth() / 2, paints);

        Paint paintss = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintss.setColor(Color.parseColor("#15FD6976"));
        RectF rectssss = new RectF(0, 6, getMeasuredWidth(), getMeasuredHeight()-27 +6);
        canvas.drawRoundRect(rectssss, getMeasuredWidth() / 2, getMeasuredWidth() / 2, paintss);

        Paint paintsss = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintsss.setColor(Color.parseColor("#20FD6976"));
        RectF rectsssss = new RectF(0, 6, getMeasuredWidth(), getMeasuredHeight()-27 +6);
        canvas.drawRoundRect(rectsssss, getMeasuredWidth() / 2, getMeasuredWidth() / 2, paintsss);

        Paint paintssss = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintssss.setColor(Color.parseColor("#25FD6976"));
        RectF rectssssss = new RectF(0, 6, getMeasuredWidth(), getMeasuredHeight()-27 +6);
        canvas.drawRoundRect(rectssssss, getMeasuredWidth() / 2, getMeasuredWidth() / 2, paintssss);


        mPaint.setColor(mOriginColor);
        rect = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight()-27);
        canvas.drawRoundRect(rect, getMeasuredWidth() / 2, getMeasuredWidth() / 2, mPaint);

//        canvas.save();
////        canvas.clipRect(0, 0, getMeasuredWidth()*mProgress, getMeasuredHeight());// left, top,
//        // right, bottom
//        float[] radiusArray = {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};
//        radiusArray[0] = 24;
//        radiusArray[1] = 24;
//        radiusArray[2] = 24;
//        radiusArray[3] = 24;
//        radiusArray[4] = 24;
//        radiusArray[5] = 24;
//        radiusArray[6] = 24;
//        radiusArray[7] = 24;
//        Path path = new Path();
//        path.addRoundRect(new RectF(0, 0, getMeasuredWidth() * mProgress, getMeasuredHeight()),
//                radiusArray, Path.Direction.CW);
//        canvas.clipPath(path);
//
//        mPaint.setColor(mChangeColor);
//
//        LinearGradient linearGradient = new LinearGradient(
//                rect.left, rect.top,
//                getMeasuredWidth() * mProgress, rect.bottom,
//                Color.parseColor("#FD5858"), Color.parseColor("#FA7397"),
//                Shader.TileMode.MIRROR
//        );
//
//        mPaint.setShader(linearGradient);
//
//        canvas.drawRoundRect(rect, getMeasuredWidth() / 2, getMeasuredWidth() / 2, mPaint);
//        canvas.restore();
//
//        drawText_h(canvas,
//                mTextOriginColor,
//                0,
//                (int) (getMeasuredWidth() * mProgress));
//
//        drawText_h(canvas,
//                mTextChangeColor,
//                (int) (getMeasuredWidth() * mProgress),
//                getMeasuredWidth());
    }


    private void drawText_h(Canvas canvas, int color, int startX, int endX) {
        Paint paint = new Paint();
        paint.setTextSize(mTextSize);
        paint.setColor(color);
        canvas.save();
        //设置画布的显示区域 ,默认交集显示
        canvas.clipRect(startX, 0, endX, getMeasuredHeight());// left, top,right, bottom
        canvas.drawText(mText,
                mTextStartX,
                //mPaint.descent() + mPaint.ascent()文本高度
                getMeasuredHeight() / 2 - ((paint.descent() + paint.ascent()) / 2),
                paint);
        canvas.restore();
    }


    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
        mText = "下载" + (int) (progress * 100) + "%";
        invalidate();
    }

    public int getTextSize() {
        return mTextSize;
    }

    public void setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
        mPaint.setTextSize(mTextSize);
        requestLayout();
        invalidate();
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        this.mText = text;
        requestLayout();
        invalidate();
    }

    public int getTextOriginColor() {
        return mOriginColor;
    }

    public void setTextOriginColor(int mOriginColor) {
        this.mOriginColor = mOriginColor;
        invalidate();
    }

    public int getTextChangeColor() {
        return mChangeColor;
    }

    public void setTextChangeColor(int mChangeColor) {
        this.mChangeColor = mChangeColor;
        invalidate();
    }

    private static final String PROGRESS = "progress";
    private static final String STATE = "state";

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putFloat(PROGRESS, mProgress);
        bundle.putParcelable(STATE, super.onSaveInstanceState());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mProgress = bundle.getFloat(PROGRESS);
            super.onRestoreInstanceState(bundle.getParcelable(STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }


}
