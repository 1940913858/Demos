package com.example.meetyou.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.meetyou.myapplication.R;

import java.util.List;


public class AnalysisHabitView extends View {
    private Paint mPaint;
    private int mOriginColor = 0xff000000;
    private int mChangeColor = 0xffff0000;

    private float mProgress;
    private RectF rect;
    float[] radiusArray = {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};

    public AnalysisHabitView(Context context) {
        this(context, null);
    }

    public AnalysisHabitView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ColorText);

        mOriginColor = ta.getColor(R.styleable.ColorText_origin_color, mOriginColor);
        mChangeColor = ta.getColor(R.styleable.ColorText_change_color, mChangeColor);
        mProgress = ta.getFloat(R.styleable.ColorText_progress, 0);
        ta.recycle();

        initView();
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        radiusArray[0] = 42;
        radiusArray[1] = 42;
        radiusArray[2] = 42;
        radiusArray[3] = 42;
        radiusArray[4] = 42;
        radiusArray[5] = 42;
        radiusArray[6] = 42;
        radiusArray[7] = 42;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //从新测量控件的大小，确保在没有指定控件大小的情况下正常显示
        int height = measureHeight(heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);

        height = 20 * 3;
        setMeasuredDimension(width, height);

        rect = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
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
                result += getPaddingLeft() + getPaddingRight();
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
        return result;
    }


    private int mType = HABIT_TYPE_LAST_SEVEN_DAYS;//展示类型，对应1 2 3 4
    public static int HABIT_TYPE_LAST_SEVEN_DAYS = 1;//最近7天
    public static int HABIT_TYPE_YEAR = 2;//某年
    public static int HABIT_TYPE_MONTH = 3;//某年某月
    public static int HABIT_TYPE_ALL = 4;//全部
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 某个月份的样式
        setTypeMonth(canvas);
        //年以及全部的样式
        setTypeYearAll(canvas);

    }

    /**
     * 某年某月份 类型下的展示
     *
     * @param canvas
     */
    private void setTypeMonth(Canvas canvas) {
//        if (mType == HABIT_TYPE_MONTH) {
            mPaint.setColor(mChangeColor);
            RectF rect = new RectF();

            for (int i = 0; i < 31; i++) {
                if (i == 0){
                    rect.left = rect.right;
                }else{
                    rect.left = rect.right + 6;
                }

                if (i == 3 || i==5) {
                    rect.top = 0;
                } else {
                    rect.top = 4*3;
                }
                rect.bottom = getMeasuredHeight();
                rect.right = rect.left + 5 * 3;
//
//                if (habitWeekModel.getHasHabit() == 0) {
//                    mPaint.setColor(Color.parseColor("#F5F5F5"));
//                } else {
//                    mPaint.setColor(mChangeColor);
//                }
                canvas.drawRoundRect(rect, 40, 40, mPaint);
            }
//        }
    }

    /**
     * 年份以及全部 类型下的展示
     *
     * @param canvas
     */
    private void setTypeYearAll(Canvas canvas) {
        if (mType == HABIT_TYPE_ALL || mType == HABIT_TYPE_YEAR) {
            calcuteProgress();
            //底部round rect
            mPaint.setColor(mOriginColor);
            canvas.drawRoundRect(rect, getMeasuredWidth() / 2, getMeasuredWidth() / 2, mPaint);
            canvas.save();

            Path path = new Path();
            path.addRoundRect(new RectF(rect.left, rect.top, getMeasuredWidth() * mProgress,
                    getMeasuredHeight()), radiusArray, Path.Direction.CW);
            canvas.clipPath(path);
            mPaint.setColor(mChangeColor);
            //彩色rect
            canvas.drawRoundRect(rect, getMeasuredWidth() / 2, getMeasuredWidth() / 2, mPaint);
            canvas.restore();
        }
    }

    /**
     * 计算progress ，只有type = 年以及全部，才展示
     */
    private void calcuteProgress() {
        try {
            if (mType == HABIT_TYPE_LAST_SEVEN_DAYS) {

            } else if (mType == HABIT_TYPE_YEAR) {
                //mProgress = mHabitModel.count / (float) 365;
                mProgress = 50/ (float) 365;
            } else if (mType == HABIT_TYPE_MONTH) {

            } else if (mType == HABIT_TYPE_ALL) {
                //mProgress = mHabitModel.count / (float) 30;
                mProgress = 20 / (float) 30;
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }



    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
        invalidate();
    }

}
