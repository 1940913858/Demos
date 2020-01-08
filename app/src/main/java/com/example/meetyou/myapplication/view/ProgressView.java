package com.example.meetyou.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.meetyou.myapplication.activity.ProgressActivity;

public class ProgressView extends View {
    private int mCurrentPos;
    //弧形宽度
    private int arcWidth = 48;
    //20%时最低的宽度
    private float minProgressPercen = 0.2f;

    //80%时最大的宽度
    private float maxProgressPercen = 0.8f;
    //进度
    private int progress = 50;

    //最大进度
    private int maxProgress = 100;

    //斜线宽度
    private int lineWidth = 15 * 3;
    //斜线间距
    private int lineSpace = 20;
    //贝塞尔曲线弧形，左半部分上角
    private int arcDegreeLeftTop = 10;
    //贝塞尔曲线弧形，左半部分下角
    private int arcDegreeLeftBottom = 5;
    //贝塞尔曲线弧形，右半部分上角
    private int arcDegreeRightTop = 5;
    //贝塞尔曲线弧形，右半部分下角
    private int arcDegreeRightBottom = 10;

    public ProgressView(Context context) {
        super(context);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private Paint leftPaint;
    private Paint paintRight;
    private Paint textPaint;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();

        int leftWidth = getMeasuredWidth() - arcWidth * 2 + arcWidth / 2;
        int rightWidth = getMeasuredWidth();
        int height = getMeasuredHeight();

        //左边 进度百分比 最大80%，最大20%
        float trueProgress = ProgressActivity.divide(progress, maxProgress, 1);
        if (trueProgress > maxProgressPercen) {
            trueProgress = maxProgressPercen;
        }
        if (trueProgress < minProgressPercen) {
            trueProgress = minProgressPercen;
        }

        //左边渐变色gradient
        LinearGradient linearGradient = new LinearGradient(
                0, 0,
                arcWidth + trueProgress * leftWidth - arcDegreeLeftTop, 0,
                Color.parseColor("#FD6876"), Color.parseColor("#FE8865"),
                Shader.TileMode.MIRROR
        );
        leftPaint.setShader(linearGradient);

        //右边渐变色gradient
        LinearGradient linearGradientRight = new LinearGradient(
                arcWidth + trueProgress * leftWidth - lineWidth + lineSpace + arcDegreeRightTop, 0,
                getMeasuredWidth(), 0,
                Color.parseColor("#99FD6876"), Color.parseColor("#99FE8865"),
                Shader.TileMode.MIRROR
        );
        paintRight.setShader(linearGradientRight);

        //开始画
        Path path = new Path();
        path.moveTo(arcWidth, 0);
        //横线
        path.lineTo(arcWidth + trueProgress * leftWidth - arcDegreeLeftTop, 0);
        //贝塞尔曲线圆角，右上角
        path.quadTo(arcWidth + trueProgress * leftWidth, 0, arcWidth + trueProgress * leftWidth,
                arcDegreeLeftTop);
        //竖线
        path.lineTo(arcWidth + trueProgress * leftWidth - lineWidth + arcDegreeLeftTop, height -
                arcDegreeLeftTop);
        //贝塞尔曲线圆角，右下角
        path.quadTo(arcWidth + trueProgress * leftWidth - lineWidth + arcDegreeLeftBottom, height,
                arcWidth + trueProgress * leftWidth - lineWidth - arcDegreeLeftBottom, height);

        canvas.drawPoint(arcWidth + trueProgress * leftWidth - lineWidth + arcDegreeLeftBottom,
                height, leftPaint);

        //横线
        path.lineTo(arcWidth, height);
        RectF rectF = new RectF(0, 0, arcWidth * 2, height);
        path.arcTo(rectF, 90, 180, true);
        canvas.drawPath(path, leftPaint);

        //右边
        path.reset();
        path.moveTo(arcWidth + trueProgress * leftWidth + lineSpace, arcDegreeRightTop);
        //竖线
        path.lineTo(arcWidth + trueProgress * leftWidth - lineWidth + lineSpace + arcDegreeRightTop,
                height - arcDegreeRightBottom);
        //贝塞尔曲线圆角，下角
        path.quadTo(arcWidth + trueProgress * leftWidth - lineWidth + lineSpace, height, arcWidth +
                trueProgress * leftWidth - lineWidth + lineSpace + arcDegreeRightBottom, height);
        //下横线
        path.lineTo(rightWidth - arcWidth, height);
        //圆弧
        rectF = new RectF(rightWidth - arcWidth * 2, 0, rightWidth, height);
        path.arcTo(rectF, 90, -180, true);
        //上横线
        path.lineTo(arcWidth + trueProgress * leftWidth + lineSpace + arcDegreeRightTop, 0);
        //贝塞尔曲线圆角，上角
        path.quadTo(arcWidth + trueProgress * leftWidth + lineSpace + arcDegreeRightTop, 0,
                arcWidth + trueProgress * leftWidth + lineSpace, arcDegreeRightTop);

        canvas.drawPath(path, paintRight);


        //Y轴居中
        Paint.FontMetricsInt fmi = textPaint.getFontMetricsInt();
        int textCenterY = getMeasuredHeight() / 2 - fmi.top / 2 - fmi.bottom / 2;
        //text展示
        float pressent = (float) progress / maxProgress * 100;
        String text = (int) pressent + "%";
        canvas.drawText(text, arcWidth + textPaint.measureText(text)/2, textCenterY, textPaint);

    }

    private void initPaint() {
        //设置左边Paint
        leftPaint = new Paint();
        leftPaint.setColor(Color.RED);
        leftPaint.setAntiAlias(true);
        leftPaint.setStyle(Paint.Style.FILL);
        leftPaint.setStrokeWidth(1f);


        //设置右边Paint
        paintRight = new Paint();
        paintRight.setColor(Color.RED);
        paintRight.setAntiAlias(true);
        paintRight.setStyle(Paint.Style.FILL);
        paintRight.setStrokeWidth(1f);


        //设置文字paint
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(39);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
        invalidate();
    }
}
