package com.example.meetyou.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.meetyou.myapplication.R;

import java.util.Calendar;


public class AnalysiMoodsView extends View {
    private Paint mPaint;
    private Paint mTextPaint;
    private Paint mTextYearPaint;
    private int mOriginColor = 0xff000000;
    private int mChangeColor = 0xffff0000;

    private float mProgress;
    private RectF rect;

    public AnalysiMoodsView(Context context) {
        this(context, null);
    }

    public AnalysiMoodsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ColorText);

        mOriginColor = ta.getColor(R.styleable.ColorText_origin_color, mOriginColor);
        mChangeColor = ta.getColor(R.styleable.ColorText_change_color, mChangeColor);
        mProgress = ta.getFloat(R.styleable.ColorText_progress, 0);
        ta.recycle();

        initView();
    }

    private Calendar calendarStartClone;
    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#40A9FF"));

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.parseColor("#888888"));
        mTextPaint.setTextSize(36);
//        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mTextYearPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextYearPaint.setColor(Color.parseColor("#484848"));
        mTextYearPaint.setTextSize(42);

        Calendar calendar = Calendar.getInstance();
        calendarStart = getCalendarEndMonth(calendar.get(Calendar.YEAR), calendar.get
                (Calendar.MONTH) + 1);

        Calendar calendarClone = (Calendar) calendar.clone();
        calendarClone.add(Calendar.MONTH, -3);
        calendarEnd = calendarClone;

        days = daysBetween(calendarEnd, calendarStart) + 1;
        Log.e("senfa", "initView: " + days);
    }

    private int days;

    public int daysBetween(Calendar start, Calendar end) {
        if (start == null || end == null)
            return 0;
        //只有 时间一致，相减才能得出正确的天数
        Calendar startTag = (Calendar) start.clone();
        startTag.set(Calendar.HOUR_OF_DAY, end.get(Calendar.HOUR_OF_DAY));
        startTag.set(Calendar.MINUTE, end.get(Calendar.MINUTE));
        startTag.set(Calendar.SECOND, end.get(Calendar.SECOND));
        startTag.set(Calendar.MILLISECOND, end.get(Calendar.MILLISECOND));
        long timeStart = startTag.getTimeInMillis();
        long timeEnd = end.getTimeInMillis();
        long days = (timeEnd - timeStart) / (1000 * 3600 * 24);
        return Math.round(days);
    }

    /**
     * 获取某月最后一天
     *
     * @param year
     * @param month
     * @return
     */
    @NonNull
    private Calendar getCalendarEndMonth(int year, int month) {
        //某年某月的第一天，比如8月1号这样子
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.set(Calendar.YEAR, year);
        calendarEnd.set(Calendar.MONTH, month);
        calendarEnd.set(Calendar.DAY_OF_MONTH, 1);
        calendarEnd.set(Calendar.HOUR, 0);
        calendarEnd.set(Calendar.MINUTE, 0);
        calendarEnd.add(Calendar.DAY_OF_MONTH, -1);
        return calendarEnd;
    }

    /**
     * 获取某月第一天
     *
     * @param year
     * @param month
     * @return
     */
    @NonNull
    private Calendar getCalendarStartMonth(int year, int month) {
        //某年某月的第一天，比如8月1号这样子
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.YEAR, year);
        calendarStart.set(Calendar.MONTH, month - 1);
        calendarStart.set(Calendar.DAY_OF_MONTH, 1);
        calendarStart.set(Calendar.HOUR, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        return calendarStart;
    }

    private Calendar calendarStart;
    private Calendar calendarEnd;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //从新测量控件的大小，确保在没有指定控件大小的情况下正常显示
        int height = measureHeight(heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);

        day = 300;
        int line = day / 7;
        int widht = line * 16 *3 +(line-1)*4*3;

        setMeasuredDimension(widht, height);


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


    private int day = 0;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        calendarStartClone = (Calendar)calendarStart.clone();
        day = 300;
        rect = new RectF();

        for (int i = 0; i < day; i++) {
            int line = i / 7;
            int num = i % 7;

            if (num == 0){
                rect.top = getMeasuredHeight() - 16*3;
                rect.bottom = rect.top + 16*3;
            }else{
                rect.top = getMeasuredHeight() - 16*3*(num+1) -12*num;
                rect.bottom = rect.top + 16*3;
            }
            rect.left = 16*3*line+ 2*3*line+25*3;
            rect.right = rect.left + 16*3;

            canvas.drawRoundRect(rect,12,12,mPaint);

            calendarNow = (Calendar) calendarStartClone.clone();
            calendarStartClone.add(Calendar.DAY_OF_YEAR, -1);
            calendarNext = (Calendar) calendarStartClone.clone();

            //draw month
            if (line == 0 || !isYearMonthSame(calendarNow,calendarNext)){//第一行或者不同月份的时候展示
                if (line == 0){
                    mTextPaint.setColor(Color.parseColor("#484848"));
                }else{
                    mTextPaint.setColor(Color.parseColor("#888888"));
                }
                //float x = (rect.right + rect.left) / 2;
                canvas.drawText((calendarNext.get(Calendar.MONTH)+1)+"月",rect.left,150,mTextPaint);
            }

            //draw year
            if(line == 0 || !isYearSame(calendarNow,calendarNext)){
                canvas.drawText((calendarNext.get(Calendar.YEAR))+"年",rect.left,50,mTextYearPaint);
            }
        }


        for (int i =0;i<7;i++){
            int top;
            int bottom;
            if (i == 0) {
                top = getMeasuredHeight() - 16 * 3;
                bottom = top + 16 * 3;
            } else {
                top= getMeasuredHeight() - 16 * 3 * (i + 1) - 12 * i;
                bottom = top + 16 * 3;
            }

            int y = (bottom + top ) / 2+12;
            String text = "";
            switch (i){
                case 0:
                    text="六";
                    break;
                case 1:
                    break;
                case 2:
                    text="四";
                    break;
                case 3:
                    break;
                case 4:
                    text="二";
                    break;
                case 5:
                    break;
                case 6:
                    text="日";
                    break;
            }
            canvas.drawText(text,30,y,mTextPaint);
        }


    }

    public boolean isYearMonthSame(Calendar calendar, Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            return false;
        }
        if (calendar.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)) {
            if (calendar.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)) {
                return true;
            }
        }
        return false;
    }

    public  boolean isYearSame(Calendar calendar, Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            return false;
        }
        if (calendar.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)) {
            return true;
        }
        return false;
    }


    Calendar calendarNow ;
    Calendar calendarNext ;

}
