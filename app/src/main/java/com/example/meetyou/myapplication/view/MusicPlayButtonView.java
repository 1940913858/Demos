package com.example.meetyou.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.meetyou.myapplication.R;

import java.math.BigDecimal;

/**
 * 儿歌音乐播放按钮
 *
 * @author chensenfa <chensenfa@xiaoyouzi.com>
 * @since 2021/03/01
 */
public class MusicPlayButtonView extends View {
    /**
     * 画笔对象的引用
     */
    private Paint paint, circlePaint, outsideCirclePaint;
    private int inColor;
    private int circleWidth;
    private int dp1;
    private int dp26;

    private int radiusDial;
    private RectF mRect;
    private int max = 100;
    private int progress = 30;
    private int status = PLAY_STATUS;//播放状态

    public MusicPlayButtonView(Context context) {
        this(context, null);
    }

    public MusicPlayButtonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicPlayButtonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        dp1 = dp2px(1);
        dp26 = dp2px(26);
        initAttrs(context, attrs);
        initPaint();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.MusicPlayButtonView);
        inColor = attributes.getColor(R.styleable.MusicPlayButtonView_color_in, Color.parseColor("#E8E8E8"));
        circleWidth = attributes.getInt(R.styleable.MusicPlayButtonView_color_circle_width, 2);
        attributes.recycle();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(dp2px(circleWidth));

        outsideCirclePaint = new Paint();
        outsideCirclePaint.setAntiAlias(true);
        outsideCirclePaint.setStyle(Paint.Style.STROKE);
        outsideCirclePaint.setStrokeWidth(dp2px(circleWidth));
        outsideCirclePaint.setStrokeCap(Paint.Cap.ROUND);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int desiredHeight = dp2px(28);
        setMeasuredDimension(desiredHeight, desiredHeight);

        radiusDial = Math.min(getMeasuredWidth(), getMeasuredHeight()) / 2;
        mRect = new RectF(dp1, dp1, dp26 + dp1, dp26 + dp1);//由于带有stroke，需要偏移一下，以防截断
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRound(canvas);
        drawMiddleImage(canvas);
    }

    /**
     * 画圆圈
     *
     * @param canvas
     */
    private void drawRound(Canvas canvas) {
        circlePaint.setColor(inColor);
        outsideCirclePaint.setColor(Color.parseColor("#FF0000"));
        //画底部圆
        canvas.drawArc(mRect, 0, 360, false, circlePaint);

        //画进度圆弧
        int percent = (int) (((float) progress / (float) max) * 360);
        canvas.drawArc(mRect, -90, percent, false, outsideCirclePaint);
    }

    /**
     * 画上面的字
     *
     * @param canvas
     */
    private void drawMiddleImage(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.tools_edu_ic_musicbar_play);
        if (status == PLAY_STATUS) {
            bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.tools_edu_ic_musicbar_suspend);
        }
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }

    public void setMax(int middleMax) {
        this.max = middleMax;
        invalidate();
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public void setStatus(int status) {
        this.status = status;
        invalidate();
    }

    public static int PLAY_STATUS = 1;
    public static int PAUSE_STATUS = 2;

    /**
     * valueOne:除数
     * valueTwo:被除数
     * scale:保留几位小数
     * 四舍五入
     */
    public static float divide(float v1, float v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return (float) b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * dp转px
     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}