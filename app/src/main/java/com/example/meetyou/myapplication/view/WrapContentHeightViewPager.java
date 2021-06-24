//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.meetyou.myapplication.view;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;

public class WrapContentHeightViewPager extends ViewPager {


    int touchSlop;

    public WrapContentHeightViewPager(Context context) {
        super(context);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop() / 3;
    }


    public WrapContentHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop() / 3;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = 0;

        for (int i = 0; i < this.getChildCount(); ++i) {
            View child = this.getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, 0));
            int h = child.getMeasuredHeight();
            if (h > height) {
                height = h;
            }
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, 1073741824);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    float mLastX;
    float mLastY;

    /**
     * 滑动的时候拦截，点击的时候传递给子viewpager
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int dx = 0;

        int dy = 0;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                dx = (int) Math.abs(x - mLastX);
                dy = (int) Math.abs(y - mLastY);
                if (dx <= touchSlop && dy <= touchSlop) {//点击的时候不拦截
                    return false;
                }
                return true;
            case MotionEvent.ACTION_UP:
                if (dx <= touchSlop && dy <= touchSlop) {//点击的时候不拦截
                    return false;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + ev.getAction());
        }
        return super.onInterceptTouchEvent(ev);
    }
}
