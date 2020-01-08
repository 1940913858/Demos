package com.example.meetyou.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

public class AutoScrollTextViewMeiyou extends TextView {
    public static final String TAG = AutoScrollTextView.class.getSimpleName();
    public static final float SPEED_SLOW = 1.0F;
    public static final float SPEED_NORMAL = 2.5F;
    public static final float SPEED_FAST = 5.0F;
    private float textLength = 0.0F;
    private float viewWidth = 0.0F;
    private float step = 0.0F;
    private float y = 0.0F;
    private float x = 0.0F;
    private float temp_view_plus_text_length = 0.0F;
    private float temp_view_plus_two_text_length = 0.0F;
    public boolean isStarting = false;
    private Paint paint = null;
    private String text = "";
    private boolean first = true;
    private int mTextColor;
    private float speed;
    boolean is_hodler;
    private final int HOLDER_MOMENT_SRCOLL;
    Handler mHandler;

    public AutoScrollTextViewMeiyou(Context context) {
        super(context);
        this.mTextColor = Color.BLACK;
        this.speed = 2.5F;
        this.is_hodler = true;
        this.HOLDER_MOMENT_SRCOLL = 1008;
        this.mHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 1008) {
                    AutoScrollTextViewMeiyou.this.is_hodler = false;
                }

            }
        };
        this.setFocusable(true);
        this.init();
    }

    public AutoScrollTextViewMeiyou(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTextColor = Color.BLACK;
        this.speed = 2.5F;
        this.is_hodler = true;
        this.HOLDER_MOMENT_SRCOLL = 1008;
        this.mHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 1008) {
                    AutoScrollTextViewMeiyou.this.is_hodler = false;
                }

            }
        };
        this.setFocusable(true);
        this.init();
    }

    public AutoScrollTextViewMeiyou(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mTextColor = Color.BLACK;
        this.speed = 2.5F;
        this.is_hodler = true;
        this.HOLDER_MOMENT_SRCOLL = 1008;
        this.mHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 1008) {
                    AutoScrollTextViewMeiyou.this.is_hodler = false;
                }

            }
        };
        this.setFocusable(true);
        this.init();
    }

    public void setSpeed(float model) {
        if (model != 5.0F && model != 2.5F && model != 1.0F) {
            model = 2.5F;
        }

        this.speed = model;
    }

    private void init() {
        this.paint = this.getPaint();
        this.paint.setColor(this.mTextColor);
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
        if (this.paint != null) {
            this.paint.setColor(mTextColor);
        }

    }

    public void startScroll() {
        this.isStarting = true;
        this.invalidate();
    }

    public void stopScroll() {
        this.isStarting = false;
        this.invalidate();
    }

    public void onDraw(Canvas canvas) {
        if (this.first) {
            this.viewWidth = (float)this.getWidth();
            this.textLength = this.paint.measureText(this.text);
            if (this.textLength > this.viewWidth) {
                this.isStarting = true;
            } else {
                this.isStarting = true;
            }

            this.step = this.textLength;
            this.temp_view_plus_text_length = this.viewWidth + this.textLength;
            this.temp_view_plus_two_text_length = this.viewWidth + this.textLength * 2.0F;
            this.y = this.getTextSize() + (float)this.getPaddingTop() + 2.0F;
            this.x = (float)this.getPaddingLeft();
            this.first = false;
            this.step = this.temp_view_plus_text_length - 10.0F;
            this.mHandler.sendEmptyMessageDelayed(1008, 3000L);
        }

        if (!this.isStarting) {
            canvas.drawText(this.text, this.x, this.y, this.paint);
        } else {
            canvas.drawText(this.text, this.temp_view_plus_text_length - this.step, this.y, this.paint);
            if (!this.is_hodler) {
                this.step += this.speed;
                if (this.step > this.temp_view_plus_two_text_length) {
                    this.step = this.textLength;
                }
            }

            this.invalidate();
        }
    }

    public void setText(String text) {
        this.text = text;
    }
}
