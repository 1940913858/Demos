    package com.example.meetyou.myapplication.view;


    import android.content.Context;
    import android.graphics.Canvas;
    import android.graphics.Color;
    import android.graphics.Paint;
    import android.graphics.PaintFlagsDrawFilter;
    import android.graphics.Path;
    import android.graphics.RectF;
    import android.util.AttributeSet;
    import android.widget.ImageView;

    /**
     * 描边
     */
    public class RoundImageView extends ImageView {

        private Path mPath;

        private RectF mRectF;

        private int mCorner = 100;

        private Paint mPaint;

        public RoundImageView(Context context) {
            this(context,null);
        }

        public RoundImageView(Context context, AttributeSet attrs) {

            super(context, attrs);

            //PS:一定不要再draw里面新建RectF，一定不要再draw里面新建RectF，一定不要再draw里面新建RectF，

            //重要的事情说三遍，会严重消耗内存

            mRectF = new RectF();

            mPath = new Path();

            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

            mPaint.setStyle(Paint.Style.STROKE);

            mPaint.setStrokeWidth(6);

            mPaint.setColor(Color.parseColor("#FFFFFF"));

            setPadding(6,6,6,6);
        }

        @Override
        public void draw(Canvas canvas) {
            //canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
            mRectF.set(0+6, 0+6, getWidth()-6, getHeight()-6);
            super.draw(canvas);
            //画出描边
            canvas.drawRoundRect(mRectF, mCorner, mCorner, mPaint);
        }

    }